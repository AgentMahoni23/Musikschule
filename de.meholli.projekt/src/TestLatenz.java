import java.net.InetAddress;
public class TestLatenz {




        public static long[] getLatencies(String[] ipAddresses) {
            long[] latencies = new long[ipAddresses.length];

            for (int i = 0; i < ipAddresses.length; i++) {
                try {
                    InetAddress inet = InetAddress.getByName(ipAddresses[i]);
                    long startTime = System.nanoTime();
                    if (inet.isReachable(5000)) {  // Timeout von 5000 ms
                        long endTime = System.nanoTime();
                        latencies[i] = (endTime - startTime) / 1000000;  // Umrechnung von Nanosekunden in Millisekunden
                    } else {
                        System.out.println("Host " + ipAddresses[i] + " ist nicht erreichbar.");
                        latencies[i] = -1;  // Markiere nicht erreichbare Hosts mit -1
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    latencies[i] = -1;  // Markiere Fehler mit -1
                }
            }

            return latencies;
        }

        public static void main(String[] args) {
            String[] ipAddresses = {"8.8.8.8", "10.1.8.6", "10.1.8.3" }; // Beispiel: Mehrere IP-Adressen
            long[] latencies = getLatencies(ipAddresses);

            for (int i = 0; i < latencies.length; i++) {
                if (latencies[i] != -1) {
                    System.out.println("Latenz zu " + ipAddresses[i] + ": " + latencies[i] + " ms");
                } else {
                    System.out.println("Latenzmessung für " + ipAddresses[i] + " fehlgeschlagen.");
                }
            }
        }
    }


