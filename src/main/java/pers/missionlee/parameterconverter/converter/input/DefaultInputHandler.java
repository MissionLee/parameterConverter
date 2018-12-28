package pers.missionlee.parameterconverter.converter.input;

import pers.missionlee.parameterconverter.converter.input.builtin.MapInputWrapper;

import java.util.Map;

/**
 * @description: use right wrapper to deal with input object
 * @author: Mission Lee
 * @create: 2018-12-26 16:33
 */
public class DefaultInputHandler implements InputHandler{
    public InputWrapper getWrappedInput(Object inputObject) {
        if (inputObject instanceof Map) {
            return new MapInputWrapper((Map) inputObject);
        } else {
            throw new RuntimeException("DefaultConverterInputHandler : can not deal with inputObject instanceof " + inputObject.getClass());
        }
    }
}
