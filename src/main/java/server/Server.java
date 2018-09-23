package server;

import cashes.CacheTop;
import config.CacheCFG;
import main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import util.DTOObject;

import java.net.*;
import java.io.*;
public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] ar)    {
        int port = 6666; // случайный порт (может быть любое число от 1025 до 65535)
        try {
            ServerSocket ss = new ServerSocket(port); // создаем сокет сервера и привязываем его к вышеуказанному порту

            Socket socket = ss.accept(); // заставляем сервер ждать подключений и выводим сообщение когда кто-то связался с сервером

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            ApplicationContext context = new AnnotationConfigApplicationContext(CacheCFG.class);
            CacheTop cacheTop = context.getBean(CacheTop.class);
            String line = null;
            while(true) {
                logger.info(cacheTop.toString());

                line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
                cacheTop.put(new DTOObject(line), line.concat(line));
                out.writeUTF(line); // отсылаем клиенту обратно ту самую строку текста.
                out.flush(); // заставляем поток закончить передачу данных.
                //logger.info(cacheTop.toString());

            }
        } catch(Exception x) { x.printStackTrace(); }
    }
}