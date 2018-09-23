package cashes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import server.Server;
import util.DTOObject;

import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collector;

@Component
@Scope("singleton") //по умолчанию singleton
public class CacheTop<T> {

    private static final Logger logger = LoggerFactory.getLogger(CacheTop.class);

    private Long lifetime = 10000L;

    public volatile ConcurrentHashMap<DTOObject, T> concurrentHashMap = new ConcurrentHashMap();

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread th = new Thread(r);
        th.setDaemon(true);
        return th;
    });

    //private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r){{setDaemon(true);}});

    public CacheTop(){
        scheduler.scheduleAtFixedRate(() -> {
            logger.error("НАЧАЛООО: {}", System.currentTimeMillis());
            concurrentHashMap.keySet().parallelStream().forEach(key ->
                {if (!key.isDead()) {
                    concurrentHashMap.remove(key);
                    logger.error("SCHEDULER ЗАРАБОТАЛ: " + String.valueOf(concurrentHashMap.size()));

                }
            });
        logger.error("КОНЕЕЦ: {}", System.currentTimeMillis());
        }, 1, lifetime/10, TimeUnit.MILLISECONDS);


    }

    private static volatile long numb;

    public static synchronized long inc() {
        return ++numb;
    }

    public void put(DTOObject key, T value) {
        concurrentHashMap.put(key, value);
    }

    public void get(String key) {
        concurrentHashMap.get(new DTOObject(key));
    }

    public void remove(String key) {
        concurrentHashMap.get(new DTOObject(key));
    }

    public void removeAll() {
        concurrentHashMap.clear();
    }

    private Function<Map.Entry<DTOObject, T>, String> consumer = entry -> {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(entry.getKey().getSomeObject().toString()).append(":= ").append(entry.getValue().toString()).append(", ").toString();
    };

    @Override
    public String toString() {
        //return concurrentHashMap.entrySet().stream().map(consumer).collect(Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append, StringBuilder::toString));
        return String.valueOf(concurrentHashMap.size());
    }




}


