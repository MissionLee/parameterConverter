package pers.missionlee.parameterconverter.test;

import pers.missionlee.parameterconverter.configuration.DefaultJsonExecutorConfigurationBuilder;
import pers.missionlee.parameterconverter.configuration.ExecutorConfiguration;
import pers.missionlee.parameterconverter.executor.ConvertExecutor;
import pers.missionlee.parameterconverter.executor.MapConvertExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: TempTest
 * @author: Mission Lee
 * @create: 2018-12-27 09:42
 */
public class TempTest {
    public static void main(String[] args) {
        Map<String,Object> inputMap = new HashMap<>();
        inputMap.put("kg","100");
        inputMap.put("weight",50.0);
        inputMap.put("name","MissionLee");
        inputMap.put("endTime",System.currentTimeMillis());
        Map<String,Object> aucMap = new HashMap<>();
        aucMap.put("kg","1060");
        aucMap.put("weight",570.0);
        aucMap.put("name","MissionLi");
        aucMap.put("endTime",System.currentTimeMillis());

        DefaultJsonExecutorConfigurationBuilder builder = new DefaultJsonExecutorConfigurationBuilder(TempTest.class.getPackage());
        ExecutorConfiguration configuration = builder.build();
        ConvertExecutor executor = new MapConvertExecutor(configuration);
        System.out.println(((MapConvertExecutor) executor).convert(inputMap));
        System.out.println(((MapConvertExecutor) executor).convert("auctions",aucMap));
    }
}
