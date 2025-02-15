package com.wolfs.models;
import java.time.LocalDateTime;

public class Announcement extends Post {
    private String announcementTitle;
    private String announcementContent;
    private String announcementTags;

    public Announcement(int postId, int forumId, int idUser, int votes, LocalDateTime dateCreation,
                        LocalDateTime dateModification, String cheminFichier, String status, int nbrSignal,
                        String announcementTitle, String announcementContent, String announcementTags) {
        super(postId, forumId, idUser, votes, dateCreation, dateModification, cheminFichier, "announcement", status,nbrSignal);
        this.announcementTitle = announcementTitle;
        this.announcementContent = announcementContent;
        this.announcementTags = announcementTags;
    }

    public String getAnnouncementTitle() { return announcementTitle; }
    public void setAnnouncementTitle(String announcementTitle) { this.announcementTitle = announcementTitle; }
    public String getAnnouncementContent() { return announcementContent; }
    public void setAnnouncementContent(String announcementContent) { this.announcementContent = announcementContent; }
    public String getAnnouncementTags() { return announcementTags; }
    public void setAnnouncementTags(String announcementTags) { this.announcementTags = announcementTags; }
}