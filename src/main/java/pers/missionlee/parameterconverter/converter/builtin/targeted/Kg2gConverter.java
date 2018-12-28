package pers.missionlee.parameterconverter.converter.builtin.targeted;

import pers.missionlee.parameterconverter.annotation.ConverterAnnotation;
import pers.missionlee.parameterconverter.converter.TargetedConverter;

/**
 * @description: kg to g
 * @author: Mission Lee
 * @create: 2018-12-27 09:35
 */
@ConverterAnnotation(type = ConverterAnnotation.Type.TARGETED,name = "kg2k")
public class Kg2gConverter implements TargetedConverter {
    @Override
    public Object convert(Object input) {
        return multiplication2int(input,1000);
    }
    private static int multiplication2int(Object origin,int times){
        int result = 0;
        if(origin  instanceof String){
            result =(int)(Float.parseFloat(origin.toString())*times);
        }else if(origin instanceof Integer){
            result = (int)origin*times;
        }else if(origin instanceof Float){
            result =(int)((Float)origin*times);
        }else if(origin instanceof Double){
            result=(int)((double)origin*times);
        }
        return result;
    }
}
