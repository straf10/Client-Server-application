/* Κλάση που περιλαμβάνει όλα τα χαρακτηριστικά ενός μηνύματος
Το messageId(id) για να είναι μοναδικό προκύπτει απο την εξής σχέση:
Πολλαπλασιάζει το authToken του χρήστη που στέλνει το μήνυμα * 100 και έπειτα με ένα αυτοαυξανόμενο counter προσθέτει + 1.
 */

public class Message {
    private boolean isRead;
    private String sender;
    private String receiver;
    private String body;
    private int id; // Μοναδικός αναγνωριστικός αριθμός για κάθε μήνυμα

    public Message(String sender, String receiver, String body, int id) {
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        this.isRead = false;
        this.id = id;
    }

    // Getters and Setters
    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getBody() {
        return body;
    }

    public int getId() {
        return id;
    }
}
