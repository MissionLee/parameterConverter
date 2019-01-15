package pers.missionlee.utils;

/**
 * @description:
 * @author: Mission Lee
 * @create: 2019-01-15 21:27
 */
public class ConverterUtils {
    /**
     *  String 数组转 String
     *  对 null  空字符串  带空格字符串等进行了预处理
     */
    public static String stringArray2String(String[] strings,String split){
        return StringArray2String.stringArray2String(strings,split);
    }
    /**
     * @Description:
     * @Param: [strings]
     * @return: java.lang.String
     * @Author: Mission Lee
     * @date: 2018/12/25
     */
    public static String stringArray2String(String[] strings){

        return StringArray2String.stringArray2String(strings);
    }
    public static String stringArray2String(String[] strings,String split,String emptyHolder){
        return StringArray2String.stringArray2String(strings,split,emptyHolder);
    }


    public static void main(String[] args) {
        String[] strings = new String[10];
        strings[1] = "1";
        System.out.println(stringArray2String(strings, "-"));
        System.out.println(stringArray2String(strings, "-", "EMPTY"));
        System.out.println(stringArray2String(strings));
    }
}
