import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        int port = 5000; // Ορισμός της θύρας στο 5000
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind("Server", new ServerImplementation());
            System.out.println("RMI Server is running on port " + port);
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
