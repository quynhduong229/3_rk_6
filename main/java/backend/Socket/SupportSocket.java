package backend.Socket;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
@ServerEndpoint("/support/{emailid}")
public class SupportSocket {

    private static MessageRepository msgRepo;

    /*
     * Grabs the MessageRepository singleton from the Spring Application
     * Context.  This works because of the @Controller annotation on this
     * class and because the variable is declared as static.
     * There are other ways to set this. However, this approach is
     * easiest.
     */
    @Autowired
    public void setMessageRepository(MessageRepository repo) {
        msgRepo = repo;  // we are setting the static variable
    }

    // Store all socket session and their corresponding emailid.
    private static final Map<Session, String> sessionemailidMap = new Hashtable<>();
    private static final Map<String, Session> emailidSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(SupportSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("emailid") String emailid) {

        logger.info("Entered into Open");

        // store connecting user information
        sessionemailidMap.put(session, emailid);
        emailidSessionMap.put(emailid, session);

        //Send chat history to the newly connected user
        //sendMessageToParticularUser(emailid, getChatHistory());

        // broadcast that new user joined
        String message = "User " + emailid + " has Joined the Chat";
        broadcast(message);
    }


    @OnMessage
    public void onMessage(Session session, String message) {

        // Handle new messages
        logger.info("Entered into Message: Got Message:" + message);
        String emailid = sessionemailidMap.get(session);

        // Direct message to a user using the format "@emailid <message>"
        if (message.startsWith("@")) {
            String destemailid = message.split(" ")[0].substring(1);

            // send the message to the sender and receiver
            sendMessageToParticularUser(destemailid, "[DM] " + emailid + ": " + message);
            sendMessageToParticularUser(emailid, "[DM] " + emailid + ": " + message);

        }
        else { // broadcast
            broadcast(emailid + ": " + message);
        }

        // Saving chat history to repository
        msgRepo.save(new Message(emailid, message));
    }


    @OnClose
    public void onClose(Session session) {
        logger.info("Entered into Close");

        // remove the user connection information
        String emailid = sessionemailidMap.get(session);
        sessionemailidMap.remove(session);
        emailidSessionMap.remove(emailid);

        // broadcase that the user disconnected
        String message = emailid + " disconnected";
        broadcast(message);
    }


    @OnError
    public void onError(Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }


    private void sendMessageToParticularUser(String emailid, String message) {
        try {
            emailidSessionMap.get(emailid).getBasicRemote().sendText(message);
        }
        catch (IOException e) {
            logger.info("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void broadcast(String message) {
        sessionemailidMap.forEach((session, emailid) -> {
            try {
                session.getBasicRemote().sendText(message);
            }
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage());
                e.printStackTrace();
            }

        });

    }


    // Gets the Chat history from the repository
    private String getChatHistory() {
        List<Message> messages = msgRepo.findAll();

        // convert the list to a string
        StringBuilder sb = new StringBuilder();
        if(messages.size() != 0) {
            for (Message message : messages) {
                sb.append(message.getEmailid()).append(": ").append(message.getContent()).append("\n");
            }
        }
        return sb.toString();
    }

} // end of Class

