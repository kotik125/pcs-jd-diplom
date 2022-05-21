import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        //System.out.println(engine.search("бизнес"));

        // здесь создайте сервер, который отвечал бы на нужные запросы
        // слушать он должен порт 8989
        // отвечать на запросы /{word} -> возвращённое значение метода search(word) в JSON-формате

        ServerSocket serverSocket = new ServerSocket(8989);

        while(true) {
            try (
                    Socket clientSocket = serverSocket.accept(); // ждем подключения
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                String word = in.readLine();

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.setPrettyPrinting().create();
                Type listType = new TypeToken<List<PageEntry>>() {}.getType();

                out.println(gson.toJson(engine.search(word), listType));
                System.out.println("Произведён поиск по слову " + word);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}