import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class ServerImplementation extends UnicastRemoteObject implements ServerInterface {

    private HashMap<String, Account> accounts;
    private int authTokenCounter;

    public ServerImplementation() throws RemoteException {
        super();
        accounts = new HashMap<>();
        authTokenCounter = 0; // Αρχικοποίηση του μετρητή για τα authTokens
    }


    // Μέθοδος για την κατασκεύη νέου account.
    public String createAccount(String username) throws RemoteException {
        if (accounts.containsKey(username)) {
            return "Sorry, the user already exists";
        }
        if (!isValidUsername(username)) {
            return "Invalid Username";
        }
        Account newAccount = new Account(username, authTokenCounter++);
        accounts.put(username, newAccount);
        return String.valueOf(newAccount.getAuthToken());
    }


    // Μέθοδος που επιστρέφει όλους τους χρήστες του συστήματος
    public String showAccounts(int authToken) throws RemoteException {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (String username : accounts.keySet()) {
            sb.append(index++).append(". ").append(username).append("\n");
        }
        return sb.toString();
    }


    // Μέθοδος για την αποστολή μηνυμάτων
    public String sendMessage(int authToken, String recipient, String messageBody) throws RemoteException {
        Account sender = getAccountByToken(authToken);
        if (sender == null) return "Invalid Auth Token";

        Account receiver = accounts.get(recipient);
        if (receiver == null) return "User does not exist";

        int messageId = authToken * 100 + receiver.getMessageBox().size() + 1;
        Message message = new Message(sender.getUsername(), recipient, messageBody, messageId);
        receiver.getMessageBox().add(message);
        return "OK";
    }



    // Μέθοδος για την εμφάνιση των μηνυμάτων ενός χρήστη
    public String showInbox(int authToken) throws RemoteException {
        Account account = getAccountByToken(authToken);
        if (account == null) return "Invalid Auth Token";

        StringBuilder sb = new StringBuilder();
        for (Message message : account.getMessageBox()) {
            sb.append(message.getId()).append(". from:").append(message.getSender())
                    .append(message.isRead() ? "" : "*").append("\n");
        }
        return sb.toString();
    }


    // Μέθοδος για το διάβασμα ενός μηνύματος.
    // Με το διάβασμα ενημερώνεται η message.setRead ώστε στην μέθοδο showInbox να φεύγει ο * δίπλα από το μήνυμα.
    public String readMessage(int authToken, int messageId) throws RemoteException {
        Account account = getAccountByToken(authToken);
        if (account == null) return "Invalid Auth Token";

        Message message = getMessageById(account, messageId);
        if (message == null) return "Message ID does not exist";

        message.setRead(true);
        return "(" + message.getSender() + ") " + message.getBody();
    }


    // Μέθοδος για την διαγραφή ενός μηνύματος
    public String deleteMessage(int authToken, int messageId) throws RemoteException {
        Account account = getAccountByToken(authToken);
        if (account == null) return "Invalid Auth Token";

        Message message = getMessageById(account, messageId);
        if (message == null) return "Message does not exist";

        account.getMessageBox().remove(message);
        return "OK";
    }


    // Ελέγχει αν το username περιλαμβάνει μόνο αλφαρηθμιτικούς χαρακτήρες και "_".
    private boolean isValidUsername(String username) {
        return username.matches("[a-zA-Z0-9_]+");
    }

    // Μέθοδος για την αναζήτηση ενός λογαριασμού με βάση το authToken
    private Account getAccountByToken(int authToken) {
        for (Account account : accounts.values()) {
            if (account.getAuthToken() == authToken) {
                return account;
            }
        }
        return null; // Επιστρέφει null αν δεν βρεθεί λογαριασμός
    }

    // Μέθοδος για την αναζήτηση ενός μηνύματος με βάση το messageId
    private Message getMessageById(Account account, int messageId) {
        for (Message message : account.getMessageBox()) {
            if (message.getId() == messageId) {
                return message;
            }
        }
        return null; // Επιστρέφει null αν δεν βρεθεί μήνυμα
    }
}