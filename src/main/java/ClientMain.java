import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMain {

    public static void callServer(String word) {

        try(Socket clientSocket = new Socket("localhost", 8989);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            out.println(word);
            in.lines().forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        callServer("бизнес");
        callServer("Бизнес");

    }
}