package com.example.airplanned.model;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * the purpose of this class is to create an post object
 * each post will include an image, title, description, likes, comments, date, and publisher
 * @author Saiyara Iftekharuzzaman
 */
public class Post implements Serializable {
    //number
    private int id;
    private String image;
    private String title;
    private String description;
    private String date;
    //user author
    private User user;
    private String emailId;
    private int likesCounter;
    private String showComments;

    //new posts will being without any comments so an empty string
    private ArrayList<Comment> comments;

    /**
     *post constructor
     *the likes and comments start at 0 and null
     *this will be altered based on user interaction --> if a user comments then it should
     *append the comment string to the specific users post, and likewise the like counter should
     *update if user likes the image
     * @param postImage
     * Post's image
     * @param postTitle
     * Post's title
     * @param description
     * Post's description
     * @param postDate
     * Post's date
     * @param publisher
     * Posts's publisher
     * @param likesCounter
     * Post's likes
     * @param comments
     * Post's comments
     */
    public Post(String postImage, String postTitle,
                String description, String postDate, String publisher, int likesCounter, ArrayList<Comment> comments) {
        this.image = postImage;
        this.title = postTitle;
        this.description = description;
        this.date = postDate;
        this.emailId = publisher;
        this.likesCounter = likesCounter;
        this.comments = comments;
    }

    /**
     * this is a post object constructor has a set value for the
     * post likes, and date to start off, also creates a blank list of comments
     * @param postTitle
     * Post's title
     * @param description
     * Post's description
     * @param user
     * Post's author
     */
    public Post(String postTitle, String description,String date, User user){
        this.image = null;
        this.title = postTitle;
        this.description = description;
        //how am i supposed to get the date
        this.user = user;
        this.date = date;
        this.likesCounter = 0;
        this.comments = new ArrayList<>();
    }


    /**
     * gets the title of the post
     * @return
     * String title
     */
    public String getPostTitle() {
        return title;
    }

    /**
     * sets the title if the user wants to update the post
     * @param postTitle
     * Post's title
     */
    public void setPostTitle(String postTitle) {
        this.title = postTitle;
    }

    /**
     * gets the date of the post
     * @return
     * String date
     */
    public String getPostDate() {
        return date;
    }

    /**
     * sets the date of the post
     * @param postDate
     * Post's date
     */
    public void setPostDate(String postDate) {
        this.date = postDate;
    }

    /**
     * gets the number of likes on the post
     * @return
     * int like
     */
    public int getLikesCounter() {
        return likesCounter;
    }

    /**
     * updates the number of likes on the post
     * @param likesCounter
     * Post's number of likes
     */
    public void setLikesCounter(int likesCounter) {
        this.likesCounter = likesCounter;
    }

    /**
     * gets all the comments under the post
     * @return
     * array list of comment objects
     */
    public ArrayList<Comment> getComments() {
        return comments;
    }

    /**
     * sets the arraylist of comments under a post
     * @param comments
     * Post's comments
     */
    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    /**
     * adds a comment to the list of comments of a specific post
     * @param comment
     * @return
     * Array list of comments
     */
    public ArrayList<Comment> addComments(Comment comment){
        ArrayList<Comment> update = this.comments;
        update.add(comment);
        return update;
    }


    /**
     * gets the messages from a list of comments under a post
     * @param comments
     * Post's comments
     * @return
     * string of the messages
     */
    public String getMessages(ArrayList<Comment> comments){
        String display ="";
        for (int i=0; i<comments.size(); i++){
            display += comments.get(i).printableComment() + "\n";

        }
        return display;
    }


    /**
     * gets the post id
     * @return
     * int id
     */
    public int getPostId() {
        return id;
    }

    /**
     * sets the post id
     * @param postId
     * Post's id
     */
    public void setPostId(int postId) {
        this.id = postId;
    }

    /**
     * gets the image
     * @return
     * String image
     */
    public String getPostImage() {
        return image;
    }

    /**
     * sets the post image
     * @param postImage
     * Post's image
     */
    public void setPostImage(String postImage) {
        this.image = postImage;
    }

    /**
     * gets the post description
     * @return
     * String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the description of a post
     * @param description
     * post's description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return emailId;
    }

    public void setPublisher(String publisher) {
        this.emailId = publisher;
    }

    /**
     * takes info from comments and converts it to a string
     * @param comments
     * post's comments
     * @return
     * String of comments
     */
    public static String printableComments(ArrayList<Comment> comments){
        String printComments = "";
        for(int i=0;i<comments.size();i++) {
            printComments += comments.get(i).printableComment() + "\n";
        }
        return printComments;
    }


   public String printablePost(){
       return "Title: " + this.title
               + "\n price: " + this.emailId
               + "\n date: " + this.date
               + "\n description: " +this.description
               + "\n likes: " +this.likesCounter;

   }

}

