package backend.Posts;

import backend.Comments.Comment;
import backend.Users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * the backend's version of the post object.
 * @author asher
 */

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * the primary key
     */
    private int id;
    /**
     * the image of the post.
     */
    private String image;
    /**
     * the title of the post.
     */
    private String title;
    /**
     * the description of the post.
     */
    private String description;
    /**
     * the date that the post was created.
     */
    private String date;
    /**
     * the amount of likes the post has.
     */
    private int likesCounter;

    private String emailid;

    @OneToMany
    @JoinColumn(name = "comment_id")
    /**
     * the comments on this post.
     */
    private List<Comment> comments;

    public Post() {
    }

    /**
     *
     * @param title title of the post
     * @param description the main body of the post
     * @param date the date of the post in the format "MM/DD/YYYY"
     * @param user the user who created the post
     */
    public Post(String title, String description, String date, User user) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.emailid = user.getEmailId();
        this.likesCounter = 0;
        this.comments = new ArrayList<>();
        this.image = null; //Will probably be implemented at a later date.
    }

    /**
     *
     * @return the post's id.
     */
    public int getId() {
        return id;
    }

    /**
     * sets the id of the post.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the post's image.
     */
    public String getImage() {
        return image;
    }

    /**
     * sets the image of the post.
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return the post's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the title of the post.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return the post's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the description of the post.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return the date the post was created.
     */
    public String getDate() {
        return date;
    }

    /**
     * sets the date of the post.
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return the user who created the post.
     */
    public String getEmailId() {
        return this.emailid;
    }

    /**
     * sets the user who created the post.
     * @param user
     */
    public void setEmailId(User user) {
        this.emailid = user.getEmailId();
    }

    /**
     *
     * @return the number of likes the post has.
     */
    public int getLikesCounter() {
        return likesCounter;
    }

    /**
     * sets the number of likes the post has.
     * @param likesCounter
     */
    public void setLikesCounter(int likesCounter) {
        this.likesCounter = likesCounter;
    }

    /**
     *
     * @return the list of comments on the post.
     */
    public List<Comment> getComments(){
        return comments;
    }

    /**
     * sets the list of comments on the post.
     * @param comments
     */
    public void setComments(List<Comment> comments){
        this.comments = comments;
    }

    /**
     * adds a comment to the post's list of comments.
     * @param post
     */
    public void addComment(Comment post){
        this.comments.add(post);
    }
}
