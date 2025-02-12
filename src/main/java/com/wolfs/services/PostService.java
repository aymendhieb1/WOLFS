package com.wolfs.services;

import com.wolfs.models.*;
import com.wolfs.utils.DataSource;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;

public class PostService implements IService<Post> {
    private final Connection connection = DataSource.getInstance().getConnection();

    public void ajouter(Post post) {
        String req = "INSERT INTO post (id_user, forum_id, votes, date_creation, date_modification, chemin_fichier, type, status, survey_question, survey_tags, survey_user_list, announcement_title, announcement_content, announcement_tags, parent_id, comment_content, nbr_signal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pst = connection.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, post.getIdUser());
            pst.setInt(2, post.getForumId());
            pst.setInt(3, post.getVotes());
            pst.setTimestamp(4, Timestamp.valueOf(post.getDateCreation()));
            pst.setTimestamp(5, Timestamp.valueOf(post.getDateModification()));
            pst.setString(6, post.getCheminFichier());
            pst.setString(7, post.getType());
            pst.setString(8, post.getStatus());

            for (int i = 9; i <= 16; i++) {
                if (i == 15) {
                    pst.setNull(i, Types.INTEGER);
                } else {
                    pst.setNull(i, Types.VARCHAR);
                }
            }

            if (post instanceof Survey survey) {
                pst.setString(9, survey.getSurveyQuestion());
                pst.setString(10, survey.getSurveyTags());
                pst.setString(11, survey.getSurveyUserList());
            } else if (post instanceof Announcement announcement) {
                pst.setString(12, announcement.getAnnouncementTitle());
                pst.setString(13, announcement.getAnnouncementContent());
                pst.setString(14, announcement.getAnnouncementTags());
            } else if (post instanceof Comment comment) {
                pst.setInt(15, comment.getParentId());
                pst.setString(16, comment.getCommentContent());
            }

            pst.setInt(17, post.getNbrSignal());

            pst.executeUpdate();
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                post.setPostId(generatedKeys.getInt(1));
            }
            System.out.println("✅ Post ajouté avec succès!");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout du post: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(Post post) {
        String req = "DELETE FROM post WHERE post_id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, post.getPostId());
            pst.executeUpdate();
            System.out.println("✅ Post supprimé avec succès!");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression du post: " + e.getMessage());
        }
    }
    public void modifier(Post post) {
        String req = "UPDATE post SET id_user=?, forum_id=?, votes=?, date_modification=CURRENT_TIMESTAMP, " +
                "chemin_fichier=?, type=?, status=?, survey_question=?, survey_tags=?, survey_user_list=?, " +
                "announcement_title=?, announcement_content=?, announcement_tags=?, parent_id=?, comment_content=?, " +
                "nbr_signal=? WHERE post_id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, post.getIdUser());
            pst.setInt(2, post.getForumId());
            pst.setInt(3, post.getVotes());
            pst.setString(4, post.getCheminFichier());
            pst.setString(5, post.getType());
            pst.setString(6, post.getStatus());

            for (int i = 7; i <= 14; i++) {
                pst.setNull(i, Types.VARCHAR);
            }

            if (post instanceof Survey survey) {
                pst.setString(7, survey.getSurveyQuestion());
                pst.setString(8, survey.getSurveyTags());
                pst.setString(9, survey.getSurveyUserList());
            } else if (post instanceof Announcement announcement) {
                pst.setString(10, announcement.getAnnouncementTitle());
                pst.setString(11, announcement.getAnnouncementContent());
                pst.setString(12, announcement.getAnnouncementTags());
            } else if (post instanceof Comment comment) {
                pst.setInt(13, comment.getParentId());
                pst.setString(14, comment.getCommentContent());
            }

            pst.setInt(15, post.getNbrSignal());
            pst.setInt(16, post.getPostId());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Post modifié avec succès!");
            } else {
                System.out.println("⚠️ Aucun post modifié, vérifie l'ID.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la modification du post: " + e.getMessage());
        }
    }

    public List<Post> rechercher() {
        List<Post> posts = new ArrayList<>();
        String req = "SELECT * FROM post";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Post post;
                String type = rs.getString("type");
                if ("survey".equals(type)) {
                    post = new Survey(
                            rs.getInt("post_id"),
                            rs.getInt("forum_id"),
                            rs.getInt("id_user"),
                            rs.getInt("votes"),
                            rs.getTimestamp("date_creation").toLocalDateTime(),
                            rs.getTimestamp("date_modification").toLocalDateTime(),
                            rs.getString("chemin_fichier"),
                            rs.getString("status"),
                            rs.getInt("nbr_signal"),
                            rs.getString("survey_question"),
                            rs.getString("survey_tags"),
                            rs.getString("survey_user_list")
                    );
                } else if ("announcement".equals(type)) {
                    post = new Announcement(
                            rs.getInt("post_id"),
                            rs.getInt("forum_id"),
                            rs.getInt("id_user"),
                            rs.getInt("votes"),
                            rs.getTimestamp("date_creation").toLocalDateTime(),
                            rs.getTimestamp("date_modification").toLocalDateTime(),
                            rs.getString("chemin_fichier"),
                            rs.getString("status"),
                            rs.getInt("nbr_signal"),
                            rs.getString("announcement_title"),
                            rs.getString("announcement_content"),
                            rs.getString("announcement_tags")
                    );
                } else if ("comment".equals(type)) {
                    post = new Comment(
                            rs.getInt("post_id"),
                            rs.getInt("forum_id"),
                            rs.getInt("id_user"),
                            rs.getInt("votes"),
                            rs.getTimestamp("date_creation").toLocalDateTime(),
                            rs.getTimestamp("date_modification").toLocalDateTime(),
                            rs.getString("chemin_fichier"),
                            rs.getString("status"),
                            rs.getInt("nbr_signal"),
                            rs.getString("comment_content"),
                            rs.getInt("parent_id")
                    );
                } else {
                    post = new Post(
                            rs.getInt("post_id"),
                            rs.getInt("forum_id"),
                            rs.getInt("id_user"),
                            rs.getInt("votes"),
                            rs.getTimestamp("date_creation").toLocalDateTime(),
                            rs.getTimestamp("date_modification").toLocalDateTime(),
                            rs.getString("chemin_fichier"),
                            rs.getString("type"),
                            rs.getString("status"),
                            rs.getInt("nbr_signal")
                    );
                }
                posts.add(post);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des posts: " + e.getMessage());
        }
        return posts;
    }

    public void afficher() {
        List<Post> posts = rechercher();
        System.out.println("\n📝 All Posts:");

        for (Post post : posts) {
            System.out.print("Post ID: " + post.getPostId() + " ");
            System.out.print("User ID: " + post.getIdUser() + " ");
            System.out.print("Forum ID: " + post.getForumId() + " ");
            System.out.print("Votes: " + post.getVotes() + " ");
            System.out.print("Creation Date: " + post.getDateCreation() + " ");
            System.out.print("Modification Date: " + post.getDateModification() + " ");
            System.out.print("File Path: " + post.getCheminFichier() + " ");
            System.out.print("Post Type: " + post.getType() + " ");
            System.out.print("Status: " + post.getStatus() + " ");
            System.out.print("Number of Signals: " + post.getNbrSignal() + " ");

            if (post instanceof Survey) {
                Survey survey = (Survey) post;
                System.out.print("Survey Question: " + survey.getSurveyQuestion() + " ");
                System.out.print("Survey Tags: " + survey.getSurveyTags() + " ");
                System.out.print("Survey User List: " + survey.getSurveyUserList() + " ");
            } else if (post instanceof Announcement) {
                Announcement announcement = (Announcement) post;
                System.out.print("Announcement Title: " + announcement.getAnnouncementTitle() + " ");
                System.out.print("Announcement Content: " + announcement.getAnnouncementContent() + " ");
                System.out.print("Announcement Tags: " + announcement.getAnnouncementTags() + " ");
            } else if (post instanceof Comment) {
                Comment comment = (Comment) post;
                System.out.print("Comment Content: " + comment.getCommentContent() + " ");
                System.out.print("Parent Comment ID: " + comment.getParentId() + " ");
            } else {
                System.out.print("Unknown Post Type: " + post.getType() + " ");
            }

            System.out.println();
        }
    }

}
