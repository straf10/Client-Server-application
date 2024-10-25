import java.util.ArrayList;
import java.util.List;

/* Κλάση που περιλαμβάνει όλα τα χαρακτηριστικά ενός account όπως :
 το όνομα χρήστη, το authToken που του δίνεται απο τον server και την λίστα με τα απεσταλμένα μηνύματα.
 Το authToken για να είναι μοναδικό δίνεται στον κάθε χρήστη με βάση την σειρά εγγραφής του στο server μέσω ενός αυτοαυξανόμενου counter.
 */
public class Account {
    private String username;
    private int authToken;
    private List<Message> messageBox;

    public Account(String username, int authToken) {
        this.username = username;
        this.authToken = authToken;
        this.messageBox = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public int getAuthToken() {
        return authToken;
    }

    public List<Message> getMessageBox() {
        return messageBox;
    }
}