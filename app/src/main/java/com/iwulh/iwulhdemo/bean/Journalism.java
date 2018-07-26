package com.iwulh.iwulhdemo.bean;

import java.io.Serializable;

/**
 * Auto-generated: 2018-07-12 17:14:51
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class Journalism implements Serializable {

    private int id;
    private String journalismComments;
    private String journalismContent;
    private String journalismImage;
    private String journalismLabel;
    private String journalismShares;
    private String journalismTitle;
    private String journalismVideo;
    private String journalismViews;
    private String userAutograph;
    private String userName;
    private String userPortrait;

    public int getId() {
        return id;
    }

    public String getJournalismComments() {
        return journalismComments;
    }

    public String getJournalismContent() {
        return journalismContent;
    }

    public String getJournalismImage() {
        return journalismImage;
    }

    public String getJournalismLabel() {
        return journalismLabel;
    }

    public String getJournalismShares() {
        return journalismShares;
    }

    public String getJournalismTitle() {
        return journalismTitle;
    }

    public String getJournalismVideo() {
        return journalismVideo;
    }

    public String getJournalismViews() {
        return journalismViews;
    }

    public String getUserAutograph() {
        return userAutograph;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPortrait() {
        return userPortrait;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJournalismComments(String journalismComments) {
        this.journalismComments = journalismComments;
    }

    public void setJournalismContent(String journalismContent) {
        this.journalismContent = journalismContent;
    }

    public void setJournalismImage(String journalismImage) {
        this.journalismImage = journalismImage;
    }

    public void setJournalismLabel(String journalismLabel) {
        this.journalismLabel = journalismLabel;
    }

    public void setJournalismShares(String journalismShares) {
        this.journalismShares = journalismShares;
    }

    public void setJournalismTitle(String journalismTitle) {
        this.journalismTitle = journalismTitle;
    }

    public void setJournalismVideo(String journalismVideo) {
        this.journalismVideo = journalismVideo;
    }

    public void setJournalismViews(String journalismViews) {
        this.journalismViews = journalismViews;
    }

    public void setUserAutograph(String userAutograph) {
        this.userAutograph = userAutograph;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
    }

    @Override
    public String toString() {
        return "JournalismAll{" +
                "id=" + id +
                ", journalismComments='" + journalismComments + '\'' +
                ", journalismContent='" + journalismContent + '\'' +
                ", journalismImage='" + journalismImage + '\'' +
                ", journalismLabel='" + journalismLabel + '\'' +
                ", journalismShares='" + journalismShares + '\'' +
                ", journalismTitle='" + journalismTitle + '\'' +
                ", journalismVideo='" + journalismVideo + '\'' +
                ", journalismViews='" + journalismViews + '\'' +
                ", userAutograph='" + userAutograph + '\'' +
                ", userName='" + userName + '\'' +
                ", userPortrait='" + userPortrait + '\'' +
                '}';
    }
}