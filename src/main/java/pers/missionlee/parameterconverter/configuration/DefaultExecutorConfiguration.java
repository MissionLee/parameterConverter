package pers.missionlee.parameterconverter.configuration;

import pers.missionlee.parameterconverter.converter.TargetedConverter;
import pers.missionlee.parameterconverter.converter.UniversalConverter;
import pers.missionlee.parameterconverter.converter.input.DefaultInputHandler;
import pers.missionlee.parameterconverter.converter.input.InputHandler;

import java.util.*;

/**
 * @description: Executor Configuration default implementation with default settings
 * @author: Mission Lee
 * @create: 2018-12-26 16:10
 */
public class DefaultExecutorConfiguration implements ExecutorConfiguration {
    private static final Map<String, UniversalConverter> universalConverterMap=new HashMap<>();
    private static final Map<String, TargetedConverter> targetedConverterMap=new HashMap<>();
    //private static final Map<String, ConvertExecutor> convertExecutorMap=new HashMap<>();
    private static final Map<String, ConverterConfiguration> converterConfigurations = new HashMap<>();
    private final ConverterConfiguration defaultConfiguration = new DefaultConverterConfiguration();


    private InputHandler defaultConverterInputHandler = new DefaultInputHandler();
    private InputHandler converterInputHandler;


    public DefaultExecutorConfiguration() {
        this.converterConfigurations.put("default",defaultConfiguration);
    }

    public Map<String,UniversalConverter> getUniversalConverterMap(){
        return universalConverterMap;
    }
    public Map<String,TargetedConverter> getTargetedConverterMap(){
        return targetedConverterMap;
    }
    public ConverterConfiguration getDefaultConfiguration(){
        return defaultConfiguration;
    }
    public ConverterConfiguration getConfiguration(String name){
        return converterConfigurations.get(name);
    }
    public void addConfiguration(String name,ConverterConfiguration configuration){
        converterConfigurations.put(name,configuration);
    }
    public void setConverterInputHandler(InputHandler handler) {
        this.converterInputHandler = handler;
    }

    public InputHandler getConverterInputHandler() {
        return null == converterInputHandler ? defaultConverterInputHandler : converterInputHandler;
    }
}
