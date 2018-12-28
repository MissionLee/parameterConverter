package pers.missionlee.parameterconverter.configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import pers.missionlee.parameterconverter.annotation.ConverterAnnotation;
import pers.missionlee.parameterconverter.converter.Converter;
import pers.missionlee.parameterconverter.converter.TargetedConverter;
import pers.missionlee.parameterconverter.converter.UniversalConverter;
import pers.missionlee.utils.ImplementationClassScanner;

import java.io.*;
import java.util.*;

/**
 * @description: default builder
 * @author: Mission Lee
 * @create: 2018-12-26 17:14
 */
public class DefaultJsonExecutorConfigurationBuilder {
    private String path;
    private DefaultExecutorConfiguration configuration;
    private JSONObject originConfiguration;
    private Boolean initialized = false;
    private List<Package> converterPackages = new ArrayList<Package>() {{
        add(Converter.class.getPackage());
    }};
//    public DefaultJsonExecutorConfigurationBuilder(String ResourcePath) {
//        this(ResourcePath,null);
//    }

    public DefaultJsonExecutorConfigurationBuilder(String ResourcePath, Package... packages) {
        this.path = ResourcePath;
        this.converterPackages.addAll(Arrays.asList(packages));
        readJsonConfiguration(ResourcePath);
    }
    public DefaultJsonExecutorConfigurationBuilder(Package... packages) {
        this("convert.json",packages);
    }
    private void readJsonConfiguration(String ResourcePath) {
        BufferedReader reader = null;
        try {
            InputStream in = new FileInputStream(this.getClass().getClassLoader().getResource(ResourcePath).getFile());
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            reader = new BufferedReader(inputStreamReader);
            String jsonConfig = "";
            String jsonConfigLine = null;
            while ((jsonConfigLine = reader.readLine()) != null) {
                jsonConfig += jsonConfigLine;
            }
            reader.close();
            originConfiguration = JSON.parseObject(jsonConfig);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public DefaultExecutorConfiguration build() {
        init();
        return configuration;
    }

    public void fourceReInit(String resourcePath) {
        if (null != resourcePath)
            readJsonConfiguration(resourcePath);
        if (null != originConfiguration) {
            this.initialized = false;
            init();
        }
    }

    private void init() {
        if (null == this.originConfiguration)
            throw new RuntimeException(this.getClass().getName() + ": failed to read json configuration file <" + this.path + ">");
        if (this.initialized) {
            return;
        } else {
            System.out.println("DefaultJsonExecutorConfigurationBuilder: start init");
            this.configuration = new DefaultExecutorConfiguration();
            try {
                System.out.println("converterpackages");
                System.out.println(this.converterPackages);
                loadConverters(this.converterPackages);
                //loadConverterTypeAliasesFromJson();
                initDefaultConfiguration();
                initConfiguration();
                System.out.println("DefaultJsonExecutorConfigurationBuilder: end init");

            } catch (Exception e){
                e.printStackTrace();
            }
//            catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
        }
    }

    /**
     * @Description: 将配置文件中配置的Converter创建出来，并且按照别名存放在Configuration中
     * @Param: []
     * @return: void
     * @Author: Mission Lee
     * @date: 2018/12/25
     */
    private void loadConverterTypeAliasesFromJson() throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        JSONObject executorTypeAliases = originConfiguration.getJSONObject("executorTypeAliases");
        JSONObject universal = executorTypeAliases.getJSONObject("universal");
        JSONObject targeted = executorTypeAliases.getJSONObject("targeted");

        loadTypeAliasesAndExecutors(universal, configuration.getUniversalConverterMap());
        loadTypeAliasesAndExecutors(targeted, configuration.getTargetedConverterMap());

    }

    private void loadConverters(List<Package> packages) {
        List<Class<?>> classes = new ArrayList<>();
        for (Package thePackage : packages) {
            classes.addAll(ImplementationClassScanner.scan(Converter.class, thePackage));
        }
        System.out.println(classes);
        for (Class cls : classes) {
            System.out.println(cls);
            System.out.println(cls.getAnnotation(ConverterAnnotation.class));
            ConverterAnnotation converter = (ConverterAnnotation) cls.getAnnotation(ConverterAnnotation.class);
            try {
                if (converter.type() == ConverterAnnotation.Type.TARGETED) {

                    configuration.getTargetedConverterMap().put(converter.name(), (TargetedConverter) cls.newInstance());

                } else if (converter.type() == ConverterAnnotation.Type.UNIVERSAL) {
                    configuration.getUniversalConverterMap().put(converter.name(), (UniversalConverter) cls.newInstance());
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Description: 读取默认配置，创建Configuration模板类
     * @Param: []
     * @return: void
     * @Author: Mission Lee
     * @date: 2018/12/25
     */
    private void initDefaultConfiguration() {
        System.out.println("DefaultJsonExecutorConfigurationBuilder: " + "init DefaultConfiguration start");
        JSONObject defaultExecutorConfigurationJson = originConfiguration.getJSONObject("namedExecutorsConfiguration").getJSONObject("default");
        if (null != defaultExecutorConfigurationJson) {
            System.out.println("DefaultJsonExecutorConfigurationBuilder: " + "add universal to default");
            JSONArray universalArray = defaultExecutorConfigurationJson.getJSONArray("universal");
            Object[] universalArr = universalArray.toArray();
            ConverterConfiguration defaultConfiguration = configuration.getDefaultConfiguration();
            for (int i = 0; i < universalArr.length; i++) {
                System.out.println("DefaultJsonExecutorConfigurationBuilder: " + "add universal to default" + " " + universalArr[i]);

                defaultConfiguration.addUniversalConverter(configuration.getUniversalConverterMap().get(universalArr[i]));
            }

            JSONObject targetedJson = defaultExecutorConfigurationJson.getJSONObject("targeted");
            System.out.println("DefaultJsonExecutorConfigurationBuilder: " + "add targeted to default" + " " + targetedJson.keySet());

            initTargetedConverters(defaultConfiguration, targetedJson);
        }
    }

    private void initTargetedConverters(ConverterConfiguration defaultConfiguration, JSONObject targetedJson) {
        Set<String> converterKeys = targetedJson.keySet();
        for (String key : converterKeys) {
            Object[] keywords = targetedJson.getJSONArray(key).toArray();
            for (int i = 0; i < keywords.length; i++) {
                defaultConfiguration.addTargetedConverter(keywords[i].toString(), configuration.getTargetedConverterMap().get(key));
            }
        }
    }

    private void initConfiguration() {
        Set<String> executorNames = originConfiguration.getJSONObject("namedExecutorsConfiguration").keySet();
        for (String name : executorNames) {
            if (!"default".equals(name) && !"sample".equals(name)) {
                JSONObject config = originConfiguration.getJSONObject("namedExecutorsConfiguration").getJSONObject(name);
                JSONArray universalArray = config.getJSONArray("universal");
                ConverterConfiguration newConfiguration = configuration.getDefaultConfiguration().clone();
                if (null != universalArray) {
                    Object[] universalArr = universalArray.toArray();
                    for (int i = 0; i < universalArr.length; i++) {
                        newConfiguration.addUniversalConverter(configuration.getUniversalConverterMap().get(universalArr[i]));
                    }
                }
                JSONObject targetedJson = config.getJSONObject("targeted");
                if (null != targetedJson) {

                }
                initTargetedConverters(newConfiguration, targetedJson);
                configuration.addConfiguration(name, newConfiguration);
            }

        }

    }

    private void loadTypeAliasesAndExecutors(JSONObject jsonConfig, Map map) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Set<String> aliases = jsonConfig.keySet();
        System.out.println("DefaultJsonExecutorConfigurationBuilder: start load " + aliases);
        for (String name : aliases) {
            Converter converter = (Converter) Class.forName(jsonConfig.getString(name)).newInstance();
            map.put(name, converter);
            System.out.println("DefaultJsonExecutorConfigurationBuilder: load " + name + " - " + converter.getClass());
        }
    }
}
