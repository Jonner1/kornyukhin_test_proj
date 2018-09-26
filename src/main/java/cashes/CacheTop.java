package cashes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.DTOObject;
import util.SomeObject;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class CacheTop {

    @Autowired
    CasheBottom casheBottom;

    private static final Logger logger = LoggerFactory.getLogger(CacheTop.class);

    private Long lifetime = 15000L;

    public volatile ConcurrentHashMap<DTOObject, SomeObject> concurrentHashMap = new ConcurrentHashMap();

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread th = new Thread(r);
        th.setDaemon(true);
        return th;
    });

    public CacheTop() {
        this.casheBottom = casheBottom;
        scheduler.scheduleAtFixedRate(() -> {
            logger.error("НАЧАЛООО: {}", System.currentTimeMillis());
            concurrentHashMap.keySet().parallelStream().forEach(key ->
            {
                if (!key.isDead()) {
                    remove(key);
                    logger.error("SCHEDULER ЗАРАБОТАЛ: " + String.valueOf(concurrentHashMap.size()));

                }
            });
            logger.error("КОНЕЕЦ: {}", System.currentTimeMillis());
        }, 1, lifetime / 10, TimeUnit.MILLISECONDS);
    }

    private static volatile long numb;

    public static synchronized long inc() {
        return ++numb;
    }

    public void put(DTOObject key, SomeObject value) {
        concurrentHashMap.put(key, value);
    }

    public SomeObject get(long id) {
        if (concurrentHashMap.get(new DTOObject(id)) == null) {
                Map.Entry<DTOObject, SomeObject> entry = casheBottom.get(String.valueOf(id));
                if (entry != null && entry.getValue() != null) {
                    put(entry.getKey(), entry.getValue());
                    return entry.getValue();
                } else {
                    return null;
                }
        }
        return null;
    }

    public void remove(DTOObject dtoObject) {
        casheBottom.put(dtoObject, concurrentHashMap.remove(dtoObject));
    }

    public void removeAll() {
        concurrentHashMap.clear();
    }

    private Function<Map.Entry<DTOObject, SomeObject>, String> consumer = entry -> {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(entry.getKey()).append(":= ").append(entry.getValue().toString()).append(", ").toString();
    };

    @Override
    public String toString() {
        //return concurrentHashMap.entrySet().stream().map(consumer).collect(Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append, StringBuilder::toString));
        return String.valueOf(concurrentHashMap.size());
    }


}


