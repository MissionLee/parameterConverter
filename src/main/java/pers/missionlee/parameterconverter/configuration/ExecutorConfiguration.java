package pers.missionlee.parameterconverter.configuration;

import pers.missionlee.parameterconverter.converter.TargetedConverter;
import pers.missionlee.parameterconverter.converter.UniversalConverter;
import pers.missionlee.parameterconverter.converter.input.InputHandler;

import java.util.Map;

/**
 * @description: ExecutorConfiguration
 * @author: Mission Lee
 * @create: 2018-12-26 16:09
 */
public interface ExecutorConfiguration {
    public Map<String, UniversalConverter> getUniversalConverterMap();
    public Map<String, TargetedConverter> getTargetedConverterMap();
    public ConverterConfiguration getDefaultConfiguration();
    public ConverterConfiguration getConfiguration(String name);
    public void addConfiguration(String name,ConverterConfiguration configuration);
    public void setConverterInputHandler(InputHandler handler);
    public InputHandler getConverterInputHandler() ;
}
