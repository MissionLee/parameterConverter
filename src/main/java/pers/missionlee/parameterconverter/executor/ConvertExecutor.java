package pers.missionlee.parameterconverter.executor;

/**
 * @description: executor
 * @author: Mission Lee
 * @create: 2018-12-26 16:21
 */
public interface ConvertExecutor {
    public Object convert(Object input);
    public Object convert(String name,Object input);
}
