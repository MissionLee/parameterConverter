package pers.missionlee.parameterconverter.converter;


import pers.missionlee.parameterconverter.converter.input.InputWrapper;

/**
 * @description: ConverterAnnotation
 * @author: Mission Lee
 * @create: 2018-12-26 16:16
 */
public interface Converter {
    public Object convert(Object input);
}
