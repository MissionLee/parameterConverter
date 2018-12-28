package pers.missionlee.parameterconverter.configuration;

import pers.missionlee.parameterconverter.converter.TargetedConverter;
import pers.missionlee.parameterconverter.converter.UniversalConverter;

import java.util.List;
import java.util.Set;

/**
 * @description: ConverterConfiguration
 * @author: Mission Lee
 * @create: 2018-12-26 16:06
 */
public interface ConverterConfiguration extends Cloneable{
    public void addTargetedConverter(String key, TargetedConverter converter);
    public void addUniversalConverter(UniversalConverter converter);
    public List<UniversalConverter> getUniversalConverters();
    public TargetedConverter getTargetedConverter(String key);
    public Set<String> getTargetedKeySet();
    public ConverterConfiguration clone();
}
