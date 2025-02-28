package com.wolfs.models;
import java.time.LocalDateTime;

public class Comment extends Post {
    private String commentContent;
    private int parentId;

    public Comment(int postId, int forumId, int idUser, int votes, LocalDateTime dateCreation,
                   LocalDateTime dateModification, String cheminFichier, String status, int nbrSignal,
                   String commentContent, int parentId) {
        super(postId, forumId, idUser, votes, dateCreation, dateModification, cheminFichier, "comment", status,nbrSignal);
        this.commentContent = commentContent;
        this.parentId = parentId;
    }

    public String getCommentContent() { return commentContent; }

    public void setCommentContent(String content) {
        this.commentContent = content;
    }
    public int getParentId() { return parentId; }
    public void setParentId(int parentId) { this.parentId = parentId; }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
}
