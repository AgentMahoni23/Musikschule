import java.io.*;
import java.net.Socket;

public class ClientVerbindungAufbau implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Server2 server;

    public ClientVerbindungAufbau(Socket socket, Server2 server) {
        this.clientSocket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received: " + message);
                if (message.startsWith("SYNC")) {
                    String[] parts = message.split(" ");
                    long serverTime = Long.parseLong(parts[1]);
                    long currentTime = System.currentTimeMillis();
                    long rtt = currentTime - serverTime;

                    long waitTime = (serverTime + rtt) - System.currentTimeMillis();

                    if (waitTime > 0) {
                        Thread.sleep(waitTime);
                    }

                    startMyTask();
                } else {
                    out.println("Echo: " + message);
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Client disconnected or error occurred: " + e.getMessage());
        } finally {
            closeConnection();
            server.removeClient(this);  // Entferne den Client aus der Liste
        }
    }

    private void closeConnection() {
        try {
            if (clientSocket != null) clientSocket.close();
            if (in != null) in.close();
            if (out != null) out.close();
        } catch (IOException e) {
            System.err.println("Error closing client resources: " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private void startMyTask() {
        System.out.println("Starting task for client...");
        // Hier kommt die spezifische Logik für den Task
    }
}
