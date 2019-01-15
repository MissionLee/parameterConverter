package pers.missionlee.parameterconverter.converter.input.builtin;

import pers.missionlee.utils.ConverterUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description: HttpServletRequest wrapper for  WEB situration
 * @author: Mission Lee
 * @create: 2018-12-27 11:16
 */
public class HttpServletRequestInputWrapper extends BaseInputWrapper{
    HttpServletRequest httpServletRequest;
    Map<String,Object> inputMap;
    public HttpServletRequestInputWrapper(HttpServletRequest inputObject) {
        super(inputObject);
        this.httpServletRequest = inputObject;
        initHttpServletRequestMap();
    }

    private void initHttpServletRequestMap(){
        checkInitialized();
        Map<String,String[]> properties = httpServletRequest.getParameterMap();
        inputMap = new HashMap<>();
        Set<Map.Entry<String,String[]>> entries = properties.entrySet();
        //Map.Entry entry;
        String name = "";
        String value = null;
        for(Map.Entry<String,String[]> entry:entries){
            name = entry.getKey();
            String[] origValue = entry.getValue();
            if(origValue instanceof String[])
                value = ConverterUtils.stringArray2String(origValue,",");
            inputMap.put(name,value);
            value=null;
        }
    }

    public Object get() {
        return null;
    }

    @Override
    public Map<String, Object> getMappedObject() {
        return inputMap;
    }

    @Override
    protected Object doGet(String key) {
        return inputMap.get(key);
    }

    @Override
    protected Set<String> doKeySet() {
        return inputMap.keySet();
    }

    @Override
    protected void doSet(String key, Object value) {
        inputMap.put(key,value);
    }

}
