package pers.missionlee.parameterconverter.converter.input;

import java.util.Map;
import java.util.Set;

/**
 * @description: Unified behavior of Input Object
 * @author: Mission Lee
 * @create: 2018-12-26 16:35
 */
public interface InputWrapper {
    public Object getValue(String key);
    public Set<String> keySet();
    public void setValue(String key,Object value);
    public Object get();
    public Map<String,Object> getMappedObject();
}
