package pers.missionlee.parameterconverter.converter.builtin.universal;

import pers.missionlee.parameterconverter.annotation.ConverterAnnotation;
import pers.missionlee.parameterconverter.converter.UniversalConverter;

import java.util.Map;

/**
 * @description: current user converter
 * @author: Mission Lee
 * @create: 2018-12-27 09:46
 */
@ConverterAnnotation(type = ConverterAnnotation.Type.UNIVERSAL,name = "currentUser")
public class CurrentUserConverter implements UniversalConverter {
    @Override
    public Object convert(Object input) {
        ((Map<String, Object>)input).put("currentUserId",10086);
        return input;
    }
}
