package pers.missionlee.parameterconverter.converter.builtin.targeted;

import pers.missionlee.parameterconverter.annotation.ConverterAnnotation;
import pers.missionlee.parameterconverter.converter.TargetedConverter;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @description: timestamp to time
 * @author: Mission Lee
 * @create: 2018-12-27 09:36
 */
@ConverterAnnotation(type = ConverterAnnotation.Type.TARGETED,name = "timestamp2datetime")
public class Timestamp2DatetimeConverter implements TargetedConverter {
    @Override
    public Object convert(Object input) {
        Long timeStamp = (Long)input;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
        return sdf.format(new Date(timeStamp));   // 时间戳转换成时间
    }
}
