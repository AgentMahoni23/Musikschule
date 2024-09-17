import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args){

        try {
            Socket client = new Socket("localhost",5555);
            System.out.println("Client gestartet");

            // Streams

            OutputStream out = client.getOutputStream();
            PrintWriter writer = new PrintWriter(out);

            InputStream in = client.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            //_______________________________________________

            writer.write("Hallo Server");
            writer.flush();

            writer.close();
            reader.close();
        }catch(UnknownHostException e) {
            e.printStackTrace();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}


