package pers.missionlee.utils;

/**
 * @description:
 * @author: Mission Lee
 * @create: 2019-01-15 21:28
 */
public class StringArray2String {
    public static String stringArray2String(String[] strings, String split) {
        if(null==split)
            split="";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            if (null != strings[i]) {
                if (i > 0 && !"".equals(strings[i].trim()) && !"".equals(sb.toString()))
                    sb.append(split);
                sb.append(strings[i].trim());
            }
        }
        return sb.toString();
    }

    public static String stringArray2String(String[] strings, String split, String emptyReplacer) {
        if (null == emptyReplacer)
            emptyReplacer = "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            if (i > 0 && !"".equals(sb.toString()))
                sb.append(split);
            if (null == strings[i])
                sb.append(emptyReplacer);
            else
                sb.append("".equals(strings[i].trim()) ? emptyReplacer : strings[i].trim());
        }
        return sb.toString();
    }

    public static String stringArray2String(String[] strings) {
        return stringArray2String(strings, "");
    }

}
