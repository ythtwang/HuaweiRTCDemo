package com.ythtwang.rtc.jackson;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;

import java.io.IOException;
import java.util.Set;

public class JsonTypeInfoTest {

    static ObjectMapper mapper = new ObjectMapper();

    static {

        //类扫描并注册，在类初始化阶段使用。
        Reflections reflections = new Reflections("com.ythtwang.rtc.jackson.");
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(JsonTypeName.class);
        for (Class type : classSet) {
            System.out.println(type.getName());
        }
        mapper.registerSubtypes(classSet);
    }

    public static void main(String[] args) {
        String inputJson = " {\n" +
                "        \"dsl\": \"input\",\n" +
                "        \"label\": \"标题\",\n" +
                "        \"uiType\": \"input\",\n" +
                "        \"input\" : \"lvsheng\"\n" +
                "        \n" +
                "      }";

        System.out.println(inputJson);

        try {
            InputPageModel inputPageModel = ((InputPageModel) mapper.readValue(inputJson, Page.class));
            System.out.println(inputPageModel.getInput());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String numberJson = " {\n" +
                "        \"dsl\": \"number\",\n" +
                "        \"label\": \"价格\",\n" +
                "        \"uiType\": \"input\",\n" +
                "        \"number\" : 110\n" +
                "        \n" +
                "      }";
        System.out.println(numberJson);
        try {
            NumberPageModel numberPageModel = ((NumberPageModel) mapper.readValue(numberJson, Page.class));
            System.out.println(numberPageModel.getNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //支持LIST
        String listJson = "{\"pageList\":" + "[" + "{\n" +
                "        \"dsl\": \"input\",\n" +
                "        \"label\": \"标题\",\n" +
                "        \"uiType\": \"input\",\n" +
                "        \"input\" : \"lvsheng\"\n" +
                "        \n" +
                "      }" + ", " + "{\n" +
                "        \"dsl\": \"number\",\n" +
                "        \"label\": \"价格\",\n" +
                "        \"uiType\": \"input\",\n" +
                "        \"number\" : 110\n" +
                "        \n" +
                "      }" + "]}";
        System.out.println(listJson);
        try {
            PageGroup pageGroup = mapper.readValue(listJson, PageGroup.class);
            for (Page page : pageGroup.pageList) {
                System.out.println(page.getClass());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}