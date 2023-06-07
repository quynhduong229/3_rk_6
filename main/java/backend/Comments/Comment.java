package backend.Comments;

import backend.Users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * the backend's version of the comment object.
 * @author asher
 */

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * the primary key.
     */
    private int id;
    /**
     * the message of the comment.
     */
    private String message;

    private String emailId;

    /**
     * default no parameters constructor.
     */
    public Comment() {
    }


    /**
     *
     * @param message the message of the comment
     * @param user the user who created the message
     */
    public Comment(String message, User user) {
        this.message = message;
        this.emailId = user.getEmailId();
    }

    /**
     *
     * @param message the message of the comment
     * @param emailId the user who created the message
     */
    public Comment(String message, String emailId) {
        this.message = message;
        this.emailId = emailId;
    }


    /**
     *
     * @return the comment's id.
     */
    public int getId() {
        return id;
    }

    /**
     * sets the id of the comment.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the comment's message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message of the comment.
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return the user who created the comment.
     */
    public String getEmailId() {
        return this.emailId;
    }

    /**
     * sets the user who created the comment.
     * @param user
     */
    public void setEmailId(User user) {
        this.emailId = user.getEmailId();
    }

}
