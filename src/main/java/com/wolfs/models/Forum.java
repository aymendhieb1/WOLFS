package com.wolfs.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Forum {

    private int forumId;
    private String name;
    private int createdBy;
    private int postCount;
    private int nbrMembers;
    private String description;
    private LocalDateTime dateCreation;
    private boolean isPrivate;

    public Forum(int forumId, String name, int createdBy, int postCount, int nbrMembers, String description, LocalDateTime dateCreation, boolean isPrivate) {
        this.forumId = forumId;
        this.name = name;
        this.createdBy = createdBy;
        this.postCount = postCount;
        this.nbrMembers = nbrMembers;
        this.description = description;
        this.dateCreation = dateCreation;
        this.isPrivate = isPrivate;
    }

    public Forum(String name, int createdBy, String description, boolean isPrivate) {
        this.name = name;
        this.createdBy = createdBy;
        this.description = description;
        this.isPrivate = isPrivate;
        this.postCount = 0;
        this.nbrMembers = 0;
        this.dateCreation = LocalDateTime.now();
    }

    public Forum() {

    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getNbrMembers() {
        return nbrMembers;
    }

    public void setNbrMembers(int nbrMembers) {
        this.nbrMembers = nbrMembers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Forum{" +
                "forumId=" + forumId +
                ", name='" + name + '\'' +
                ", createdBy=" + createdBy +
                ", postCount=" + postCount +
                ", nbrMembers=" + nbrMembers +
                ", description='" + description + '\'' +
                ", dateCreation='" + dateCreation.format(formatter) + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
