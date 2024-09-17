import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Server2 {
    private ServerSocket serverSocket;
    private List<ClientVerbindungAufbau> clients = new ArrayList<>();

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port: " + port);

        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ClientVerbindungAufbau clientVerbindung = new ClientVerbindungAufbau(clientSocket, this);
                    clients.add(clientVerbindung);
                    new Thread(clientVerbindung).start();
                    System.out.println("New client connected.");
                } catch (IOException e) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        }).start();

        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        String command;

        while ((command = consoleInput.readLine()) != null) {
            if (command.equals("S")) {
                long startTime = System.currentTimeMillis() + 5000; // 5 Sekunden in der Zukunft
                sendCommandToAllClients("S " + startTime);
            } else {
                sendCommandToAllClients(command);
            }
        }

    }

    private void sendCommandToAllClients(String command) {
        for (ClientVerbindungAufbau client : clients) {
            client.sendMessage(command);
        }
    }

    public synchronized void removeClient(ClientVerbindungAufbau client) {
        clients.remove(client);
        System.out.println("Client removed. Active clients: " + clients.size());
    }

    public static void main(String[] args) {
        try {
            Server2 server = new Server2();
            server.start(12345); // Beispielport
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}