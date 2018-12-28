package pers.missionlee.parameterconverter.configuration;

import pers.missionlee.parameterconverter.converter.TargetedConverter;
import pers.missionlee.parameterconverter.converter.UniversalConverter;
import pers.missionlee.parameterconverter.converter.input.DefaultInputHandler;
import pers.missionlee.parameterconverter.converter.input.InputHandler;

import java.util.*;

/**
 * @description: default converter configuration
 * @author: Mission Lee
 * @create: 2018-12-26 16:15
 */
public class DefaultConverterConfiguration implements ConverterConfiguration{

    private Map<String, TargetedConverter> targetedConverterSettings = new HashMap<>();
    private List<UniversalConverter> universalConverterSettings = new ArrayList<>();

    public void addTargetedConverter(String key, TargetedConverter converter) {
        targetedConverterSettings.put(key, converter);
    }

    public void addUniversalConverter(UniversalConverter converter) {
        universalConverterSettings.add(converter);
    }

    public List<UniversalConverter> getUniversalConverters() {
        return universalConverterSettings;
    }

    public TargetedConverter getTargetedConverter(String key) {
        return targetedConverterSettings.get(key);
    }

    public Set<String> getTargetedKeySet() {
        return targetedConverterSettings.keySet();
    }

    public DefaultConverterConfiguration clone(){
        DefaultConverterConfiguration configuration = new DefaultConverterConfiguration();
        configuration.targetedConverterSettings.putAll(targetedConverterSettings);
        configuration.universalConverterSettings.addAll(universalConverterSettings);
        return configuration;
    }
}
