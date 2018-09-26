package main;

import cashes.CacheTop;
import config.CacheCFG;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import util.DTOObject;
import util.SomeObject;

import java.io.IOException;


public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String... args) throws IOException {
        //ApplicationContext context = new AnnotationConfigApplicationContext(CacheCFG.class);
        //CacheTop cacheTop = context.getBean(CacheTop.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.readValue("{\"somevalue\":\"aaaaaaaaaaaaaaaaaa\"}", SomeObject.class);

//        DTOObject dtoObject2 = new DTOObject("2222");
//////
//////        cacheTop.put(dtoObject2, new String("111"));
        //logger.info(cacheTop.concurrentHashMap.toString());
        //logger.info(cacheTop.toString());

    }
}
