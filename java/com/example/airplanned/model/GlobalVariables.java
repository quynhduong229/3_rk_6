package com.example.airplanned.model;

import android.app.Application;

public class GlobalVariables extends Application {
    private User current;
    private Comment newestComment;

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public Comment getNewestComment() {
        return newestComment;
    }

    public void setNewestComment(Comment newestComment) {
        this.newestComment = newestComment;
    }
}

