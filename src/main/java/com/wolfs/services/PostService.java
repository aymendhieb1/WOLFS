package com.wolfs.services;

import com.wolfs.models.*;
import com.wolfs.utils.DataSource;
import com.wolfs.models.Post;

import java.util.ArrayList;
import java.sql.*;
import java.util.List;

public class PostService {
    private final Connection connection = DataSource.getInstance().getConnection();

    public void ajouter(Post post) {
        String req = "INSERT INTO post (id_user, forum_id, votes, date_creation, date_modification, chemin_fichier, type, status, survey_question, survey_tags, survey_user_list, announcement_title, announcement_content, announcement_tags, parent_id, comment_content, nbr_signal, UpVoteList, downVoteList , SingalList) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

            // Set fields for Survey, Announcement, or Comment
            if (post instanceof Survey survey) {
                pst.setString(9, survey.getSurveyQuestion());
                pst.setString(10, survey.getSurveyTags());
                pst.setString(11, survey.getSurveyUserList());
                pst.setNull(12, Types.VARCHAR); // announcement_title
                pst.setNull(13, Types.VARCHAR); // announcement_content
                pst.setNull(14, Types.VARCHAR); // announcement_tags
                pst.setNull(15, Types.INTEGER); // parent_id
                pst.setNull(16, Types.VARCHAR); // comment_content
            } else if (post instanceof Announcement announcement) {
                pst.setNull(9, Types.VARCHAR); // survey_question
                pst.setNull(10, Types.VARCHAR); // survey_tags
                pst.setNull(11, Types.VARCHAR); // survey_user_list
                pst.setString(12, announcement.getAnnouncementTitle());
                pst.setString(13, announcement.getAnnouncementContent());
                pst.setString(14, announcement.getAnnouncementTags());
                pst.setNull(15, Types.INTEGER); // parent_id
                pst.setNull(16, Types.VARCHAR); // comment_content
            } else if (post instanceof Comment comment) {
                pst.setNull(9, Types.VARCHAR); // survey_question
                pst.setNull(10, Types.VARCHAR); // survey_tags
                pst.setNull(11, Types.VARCHAR); // survey_user_list
                pst.setNull(12, Types.VARCHAR); // announcement_title
                pst.setNull(13, Types.VARCHAR); // announcement_content
                pst.setNull(14, Types.VARCHAR); // announcement_tags
                pst.setInt(15, comment.getParentId());
                pst.setString(16, comment.getCommentContent());
            } else {
                // For generic Post
                pst.setNull(9, Types.VARCHAR); // survey_question
                pst.setNull(10, Types.VARCHAR); // survey_tags
                pst.setNull(11, Types.VARCHAR); // survey_user_list
                pst.setNull(12, Types.VARCHAR); // announcement_title
                pst.setNull(13, Types.VARCHAR); // announcement_content
                pst.setNull(14, Types.VARCHAR); // announcement_tags
                pst.setNull(15, Types.INTEGER); // parent_id
                pst.setNull(16, Types.VARCHAR); // comment_content
            }

            // Set the new fields
            pst.setInt(17, post.getNbrSignal());
            pst.setString(18, post.getUpVoteList());
            pst.setString(19, post.getDownVoteList());
            pst.setString(20, post.getSignalList());

            pst.executeUpdate();
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                post.setPostId(generatedKeys.getInt(1));
            }
            System.out.println("✅ Post ajouté avec succès!");
            incrementPostCount(post.getForumId());
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout du post: " + e.getMessage());
        }
    }


    public void supprimer(Post post) {
        String req = "DELETE FROM post WHERE post_id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, post.getPostId());
            pst.executeUpdate();
            System.out.println("✅ Post supprimé avec succès!");
            DincrementPostCount(post.getForumId());
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression du post: " + e.getMessage());
        }
    }

    public void modifier(Post post, boolean updateTime) {

        String req = "UPDATE post SET id_user=?, forum_id=?, votes=?, " +
                "chemin_fichier=?, type=?, status=?, survey_question=?, survey_tags=?, survey_user_list=?, " +
                "announcement_title=?, announcement_content=?, announcement_tags=?, parent_id=?, comment_content=?, " +
                "nbr_signal=?, UpVoteList=?, downVoteList=?, SingalList=?";

        if (updateTime) {
            req += ", date_modification=CURRENT_TIMESTAMP";
        }

        req += " WHERE post_id=?";

        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, post.getIdUser());
            pst.setInt(2, post.getForumId());
            pst.setInt(3, post.getVotes());
            pst.setString(4, post.getCheminFichier());
            pst.setString(5, post.getType());
            pst.setString(6, post.getStatus());

            if (post instanceof Survey survey) {
                pst.setString(7, survey.getSurveyQuestion());
                pst.setString(8, survey.getSurveyTags());
                pst.setString(9, survey.getSurveyUserList());
                pst.setNull(10, Types.VARCHAR);
                pst.setNull(11, Types.VARCHAR);
                pst.setNull(12, Types.VARCHAR);
                pst.setNull(13, Types.INTEGER);
                pst.setNull(14, Types.VARCHAR);
            } else if (post instanceof Announcement announcement) {
                pst.setNull(7, Types.VARCHAR);
                pst.setNull(8, Types.VARCHAR);
                pst.setNull(9, Types.VARCHAR);
                pst.setString(10, announcement.getAnnouncementTitle());
                pst.setString(11, announcement.getAnnouncementContent());
                pst.setString(12, announcement.getAnnouncementTags());
                pst.setNull(13, Types.INTEGER);
                pst.setNull(14, Types.VARCHAR);
            } else if (post instanceof Comment comment) {
                pst.setNull(7, Types.VARCHAR);
                pst.setNull(8, Types.VARCHAR);
                pst.setNull(9, Types.VARCHAR);
                pst.setNull(10, Types.VARCHAR);
                pst.setNull(11, Types.VARCHAR);
                pst.setNull(12, Types.VARCHAR);
                pst.setInt(13, comment.getParentId());
                pst.setString(14, comment.getCommentContent());
            } else {

                pst.setNull(7, Types.VARCHAR);
                pst.setNull(8, Types.VARCHAR);
                pst.setNull(9, Types.VARCHAR);
                pst.setNull(10, Types.VARCHAR);
                pst.setNull(11, Types.VARCHAR);
                pst.setNull(12, Types.VARCHAR);
                pst.setNull(13, Types.INTEGER);
                pst.setNull(14, Types.VARCHAR);
            }

            pst.setInt(15, post.getNbrSignal());
            pst.setString(16, post.getUpVoteList());
            pst.setString(17, post.getDownVoteList());
            pst.setString(18, post.getSignalList());
            pst.setInt(19, post.getPostId());

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
                            rs.getString("survey_user_list"),
                            rs.getString("UpVoteList"),
                            rs.getString("downVoteList"),
                            rs.getString("SingalList")
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
                            rs.getString("announcement_tags"),
                            rs.getString("UpVoteList"),
                            rs.getString("downVoteList"),
                            rs.getString("SingalList")
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
                            rs.getInt("parent_id"),
                            rs.getString("UpVoteList"),
                            rs.getString("downVoteList"),
                            rs.getString("SingalList")
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
                            rs.getInt("nbr_signal"),
                            rs.getString("UpVoteList"),
                            rs.getString("downVoteList"),
                            rs.getString("SingalList")
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
            System.out.print("UpVote List: " + post.getUpVoteList() + " ");
            System.out.print("DownVote List: " + post.getDownVoteList() + " ");
            System.out.print("DownVote List: " + post.getSignalList() + " ");


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


    public void incrementPostCount(int forumId) {
        String sql = "UPDATE Forum SET post_count = post_count + 1 WHERE forum_id  = ?";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, forumId);
            int rowsUpdated = pst.executeUpdate();
            System.out.println(rowsUpdated > 0 ? "✅ Post count incremented successfully!" : "❌ Failed to increment post count.");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'incrémentation du post count: " + e.getMessage());
        }
    }
    public void DincrementPostCount(int forumId) {
        String sql = "UPDATE Forum SET post_count = post_count - 1 WHERE forum_id  = ?";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, forumId);
            int rowsUpdated = pst.executeUpdate();
            System.out.println(rowsUpdated > 0 ? "✅ Post count Dincremented successfully!" : "❌ Failed to increment post count.");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'incrémentation du post count: " + e.getMessage());
        }
    }
}
