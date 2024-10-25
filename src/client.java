import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class client {
    public static void main(String[] args) {
        try {
            if (args.length < 4) {
                System.out.println("Ελλιπείς παράμετροι.");
                return;
            }

            String serverIP = args[0];
            int serverPort = Integer.parseInt(args[1]);
            int functionId = Integer.parseInt(args[2]);

            Registry registry = LocateRegistry.getRegistry(serverIP, serverPort);
            ServerInterface server = (ServerInterface) registry.lookup("Server");

            switch (functionId) {
                case 1: // Δημιουργία λογαριασμού
                    String username = args[3];
                    System.out.println(server.createAccount(username));
                    break;
                case 2: // Εμφάνιση όλων των λογαριασμών
                    int authTokenShowAccounts = Integer.parseInt(args[3]);
                    System.out.println(server.showAccounts(authTokenShowAccounts));
                    break;
                case 3: // Αποστολή μηνύματος
                    int authTokenSendMessage = Integer.parseInt(args[3]);
                    String recipient = args[4];
                    String messageBody = args[5];
                    System.out.println(server.sendMessage(authTokenSendMessage, recipient, messageBody));
                    break;
                case 4: // Εμφάνιση όλων των μηνυμάτων που έχει στείλει ο χρήστης
                    int authTokenShowSentMessages = Integer.parseInt(args[3]);
                    System.out.println(server.showInbox(authTokenShowSentMessages));
                    break;
                case 5: // Διάβασμα ενός μηνύματος με βάση το messageId
                    int authTokenReadMessage = Integer.parseInt(args[3]);
                    int messageIdRead = Integer.parseInt(args[4]);
                    System.out.println(server.readMessage(authTokenReadMessage, messageIdRead));
                    break;
                case 6: // Διαγραφή μηνύματος με βάση το messageId
                    int authTokenDeleteMessage = Integer.parseInt(args[3]);
                    int messageIdDelete = Integer.parseInt(args[4]);
                    System.out.println(server.deleteMessage(authTokenDeleteMessage, messageIdDelete));
                    break;
                case 7: // Έξοδος
                    System.exit(0);
                    break;
                default:
                    System.out.println("Άγνωστη λειτουργία.");
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
