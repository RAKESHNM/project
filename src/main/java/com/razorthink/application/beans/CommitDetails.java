package com.razorthink.application.beans;


import java.util.Date;

/**
 * Created by rakesh on 7/3/17.
 */
public class CommitDetails {

    private String commitMessage;
    private Date date;
    private String author;

    public CommitDetails(){

    }

    public CommitDetails(String commitMessage, Date date, String author) {
        super();
        this.commitMessage = commitMessage;
        this.date = date;
        author = author;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        author = author;
    }
}
