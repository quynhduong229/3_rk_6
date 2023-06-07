package com.example.airplanned.model;


/**
 * comment class is a part of a post
 * tracks which user commented and what the message was
 * @author Saiyara Iftekharuzzaman
 */
public class Comment {

    private int id;
    private String message;
    private User user;
    private String emailId;


    /**
     * creates comment object
     * @param message
     * user input
     * @param commenter
     * takes username of the person logged in while making the comment
     */
    public Comment(String message, User commenter){
        this.message = message;
        this.user = commenter;
    }

    public Comment(String message, String emailid){
        this.message = message;
        this.emailId = emailid;
    }



    /**
     * gets the message from the comment
     * @return
     * String message
     */
    public String getMessage() {
        return message;
    }

    /**
     * replaces the current message with message passed through
     * @param message
     * String message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * gets the user object associated with the comment
     * @return
     * User
     */
    public User getUser() {
        return user;
    }

    /**
     * sets the User of the comment
     * @param user
     * User object of which user commented
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     *
     * @param emailId
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * takes comment object and makes it easily displayable by converting to strings
     * @return
     * string of info from the object
     */
    public String printableComment(){
        return this.emailId
                + ": \n" + this.message
                + "\n";
    }


}