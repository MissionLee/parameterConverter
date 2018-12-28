package pers.missionlee.parameterconverter.converter.input.builtin;

import pers.missionlee.parameterconverter.converter.input.builtin.BaseInputWrapper;

import java.util.Map;
import java.util.Set;

/**
 * @description: deal with map input
 * @author: Mission Lee
 * @create: 2018-12-26 16:51
 */
public class MapInputWrapper extends BaseInputWrapper {
    Map<String, Object> inputObject;
    public MapInputWrapper(Map<String,Object> inputObject) {
        super(inputObject);
        this.inputObject=inputObject;
    }

    public Map<String, Object> getMappedObject() {
        return inputObject;
    }

    protected Object doGet(String key) {
        return inputObject.get(key);
    }

    protected Set doKeySet() {
        return inputObject.keySet();
    }

    protected void doSet(String key, Object value) {
        inputObject.put(key,value);
    }

    public Object get() {
        return inputObject;
    }
}
