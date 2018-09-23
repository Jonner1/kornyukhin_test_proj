package server;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] ar) {
        int serverPort = 6666; // здесь обязательно нужно указать порт к которому привязывается сервер.
        String address = "127.0.0.1"; // это IP-адрес компьютера, где исполняется наша серверная программа.
        // Здесь указан адрес того самого компьютера где будет исполняться и клиент.

        try {
            InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.
            Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            // Создаем поток для чтения с файла.


            while (true) {
                files(out);
                Thread.sleep(15000);
                out.flush(); // заставляем поток закончить передачу данных.
                //line = in.readUTF(); // ждем пока сервер отошлет строку текста.
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    private static void files(DataOutputStream out){
        String line;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("C:\\Users\\alexey\\IdeaProjects\\kornyukhin_test_proj\\src\\main\\resources\\GenerateKeys.txt"), StandardCharsets.UTF_8))){
            int k = 0;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                k++;
                out.writeUTF(line); // отсылаем введенную строку текста серверу.
            }
            System.out.println(k);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}