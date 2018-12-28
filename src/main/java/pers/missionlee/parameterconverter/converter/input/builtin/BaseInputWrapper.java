package pers.missionlee.parameterconverter.converter.input.builtin;

import pers.missionlee.parameterconverter.converter.input.InputWrapper;

import java.util.Map;
import java.util.Set;

/**
 * @description: Base input wrapper with null-value test
 * @author: Mission Lee
 * @create: 2018-12-26 16:49
 */
public abstract class BaseInputWrapper implements InputWrapper {
    Object inputObject;
    protected BaseInputWrapper(Object inputObject) {
        this.inputObject=inputObject;

    }
    public Object getValue(String key){
        checkInitialized();
        return doGet(key);
    }
    public Set<String> keySet(){
        checkInitialized();
        return doKeySet();
    }
    public void setValue(String key,Object value){
        checkInitialized();
        doSet(key,value);
    }
    public abstract Map<String,Object> getMappedObject();

    protected void checkInitialized() {
        if(inputObject==null)
            throw new RuntimeException("ConvertExecutor is initialized with empty inputObject ");
    }
    protected abstract Object doGet(String key);
    protected abstract Set doKeySet();
    protected abstract void  doSet(String key,Object value);
}
