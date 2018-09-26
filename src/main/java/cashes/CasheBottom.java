package cashes;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;
import util.DTOObject;
import util.SomeObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class CasheBottom {

    private final String RELATIVE_PATH = "C:\\Users\\alexey\\IdeaProjects\\kornyukhin_test_proj\\src\\main\\resources\\writefiles";

    private ObjectMapper objectMapper = new ObjectMapper();

    private ScheduledExecutorService schedulerCleaner = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread th = new Thread(r);
        th.setDaemon(true);
        return th;
    });

    protected void put(DTOObject dtoObject, SomeObject someObject) {
        String nameFile = String.valueOf(dtoObject.getId());
        File file = new File(RELATIVE_PATH, nameFile.concat(".txt"));
        try {
            if (file.exists()) { //файл существует
                writeFile(file, someObject);
                //readFile(RELATIVE_PATH.concat(nameFile));
            } else {
                if (file.createNewFile()) { //если удалось создать
                    writeFile(file, someObject);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Map.Entry<DTOObject, SomeObject> get(String filename) {
        String fullPath = RELATIVE_PATH.concat("\\").concat(filename).concat(".txt");
        Map<DTOObject, SomeObject> map = new HashMap();
        try {
            map.put(new DTOObject(), objectMapper.readValue(readFile(fullPath), SomeObject.class));
        } catch (NoSuchElementException e){
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return map.entrySet().iterator().hasNext() ?  map.entrySet().iterator().next() : null;
        }
    }

    private String readFile(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        String s;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path), StandardCharsets.UTF_8))) {
            while ((s  = reader.readLine()) != null) {
                stringBuilder.append(s);
            }
        } catch (FileNotFoundException e){
           return null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return stringBuilder.toString();
        }
    }

    private void writeFile(File file, SomeObject someObject) {
        try (BufferedWriter reader = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file), StandardCharsets.UTF_8))) {
            reader.write(objectMapper.writeValueAsString(someObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
