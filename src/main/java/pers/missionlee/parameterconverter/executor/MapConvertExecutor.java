package pers.missionlee.parameterconverter.executor;

import pers.missionlee.parameterconverter.configuration.ExecutorConfiguration;
import pers.missionlee.parameterconverter.converter.UniversalConverter;
import pers.missionlee.parameterconverter.converter.input.InputWrapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description: A executor that return a map
 * @author: Mission Lee
 * @create: 2018-12-26 16:24
 */
public class MapConvertExecutor implements ConvertExecutor {
    ExecutorConfiguration configuration;

    public MapConvertExecutor(ExecutorConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Object convert(Object input) {
        return convert("default", input);
    }

    @Override
    public Map<String, Object> convert(String name, Object input) {
        InputWrapper converterInput = getInputWrapper(input);
        doUniversalConvert(name, converterInput);
        doTargetedConvert(name, converterInput);
        return converterInput.getMappedObject();
    }

    private InputWrapper getInputWrapper(Object input) {
        return configuration.getConverterInputHandler().getWrappedInput(input);
    }

    private void doUniversalConvert(String name, InputWrapper input) {
        List<UniversalConverter> converters = configuration.getConfiguration(name).getUniversalConverters();
        for (UniversalConverter converter : converters) {
            System.out.println("MapConvertExecutor Universal convert with " + converter.getClass());
            converter.convert(input.getMappedObject());
        }
    }

    private void doTargetedConvert(String name, InputWrapper input) {
        Set<String> targetedKeySet = configuration.getConfiguration(name).getTargetedKeySet();

        Map<String, Object> inputMap = input.getMappedObject();
        Set<String> inputKeys = inputMap.keySet();

        for (String key : targetedKeySet) {
            System.out.println("MapConvertExecutor Targeted convert with " + configuration.getConfiguration(name).getTargetedConverter(key).getClass());
            // TODO: 2018/12/27 如果实际入参并没有配置文件中写好的那个 key值，则不做处理
            // 当前的 setValue 会导致 空参 被创建
            Object value = configuration.getConfiguration(name).getTargetedConverter(key).convert(inputMap.get(key));
            input.setValue(key, value);
        }
    }
}
