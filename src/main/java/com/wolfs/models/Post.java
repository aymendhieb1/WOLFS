package com.wolfs.models;

import java.time.LocalDateTime;

public class Post {
    protected int postId;
    protected int forumId;
    protected int idUser;
    protected int votes;
    protected LocalDateTime dateCreation;
    protected LocalDateTime dateModification;
    protected String cheminFichier;
    protected String type;
    protected String status;
    protected int nbrSignal;
    protected String upVoteList;
    protected String downVoteList;
    protected String signalList;

    public Post(int postId, int forumId, int idUser, int votes, LocalDateTime dateCreation, LocalDateTime dateModification, String cheminFichier, String type, String status, int nbrSignal , String upVoteList, String downVoteList, String signalList) {
        this.postId = postId;
        this.forumId = forumId;
        this.idUser = idUser;
        this.votes = votes;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.cheminFichier = cheminFichier;
        this.type = type;
        this.status = status;
        this.nbrSignal = nbrSignal;
        this.upVoteList = upVoteList ;
        this.downVoteList = downVoteList ;
        this.signalList = signalList;
    }

    public Post() {
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNbrSignal() {
        return nbrSignal;
    }

    public void setNbrSignal(int nbrSignal) {
        this.nbrSignal = nbrSignal;
    }

    public String getDownVoteList() {
        return downVoteList;
    }

    public void setDownVoteList(String downVoteList) {
        this.downVoteList = downVoteList;
    }

    public String getSignalList() {
        return signalList;
    }

    public void setSignalList(String signalList) {
        this.signalList = signalList;
    }

    public String getUpVoteList() {
        return upVoteList;
    }

    public void setUpVoteList(String upVoteList) {
        this.upVoteList = upVoteList;
    }

    public void afficherPost() {
        System.out.println("📌 Post ID: " + postId);
        System.out.println("📜 Forum ID: " + forumId);
        System.out.println("👤 User ID: " + idUser);
        System.out.println("🔺 Votes: " + votes);
        System.out.println("📅 Created: " + dateCreation);
        System.out.println("📝 Modified: " + dateModification);
        System.out.println("📂 File Path: " + cheminFichier);
        System.out.println("📖 Type: " + type);
        System.out.println("📌 Status: " + status);
        System.out.println("🔢 Signal Count: " + nbrSignal);
        System.out.println("📖 upVoteList: "+ upVoteList);
        System.out.println("📖 downVoteList: "+ downVoteList);
        System.out.println("📖 signalList: "+ signalList);

    }
}
