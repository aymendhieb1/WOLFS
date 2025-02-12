package com.wolfs.tests;

import com.wolfs.models.Forum;
import com.wolfs.models.Post;
import com.wolfs.models.Survey;
import com.wolfs.models.Announcement;
import com.wolfs.models.Comment;

import com.wolfs.services.ForumService;
import com.wolfs.services.PostService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MainProg {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ForumService forumService = new ForumService();
        PostService postService = new PostService();

        while (true) {
            System.out.println("\n===== 📌 Management Menu 📌 =====");
            System.out.println("📂 FORUM MANAGEMENT");
            System.out.println("1️⃣  Add Forum");
            System.out.println("2️⃣  Modify Forum");
            System.out.println("3️⃣  Delete Forum");
            System.out.println("4️⃣  List Forums");

            System.out.println("\n📝 POST MANAGEMENT");
            System.out.println("5️⃣  Add Post");
            System.out.println("6️⃣  Modify Post");
            System.out.println("7️⃣  Delete Post");
            System.out.println("8️⃣  List Posts");
            System.out.println("9️⃣  Exit");

            System.out.print("👉 Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter forum name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter creator ID: ");
                    int createdBy = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Is it private? (true/false): ");
                    boolean isPrivate = scanner.nextBoolean();

                    Forum newForum = new Forum(0, name, createdBy, 15, 65, description, LocalDateTime.now(), isPrivate,"ajoute");
                    forumService.ajouter(newForum);
                    break;

                case 2:
                    System.out.print("Enter forum ID to modify: ");
                    int modId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new creator ID: ");
                    int newCreatedBy = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new description: ");
                    String newDesc = scanner.nextLine();
                    System.out.print("Is it private? (true/false): ");
                    boolean newIsPrivate = scanner.nextBoolean();

                    Forum updatedForum = new Forum(modId, newName, newCreatedBy, 14, 12, newDesc, LocalDateTime.now(), newIsPrivate,"test");
                    forumService.modifier(updatedForum);
                    break;

                case 3:
                    System.out.print("Enter forum ID to delete: ");
                    int delId = scanner.nextInt();
                    Forum forumToDelete = new Forum();
                    forumToDelete.setForumId(delId);
                    forumService.supprimer(forumToDelete);
                    break;

                case 4:
                     forumService.afficher();
                    break;

                case 5:
                    System.out.print("Enter forum ID: ");
                    int forumId = scanner.nextInt();
                    System.out.print("Enter user ID: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter post status: ");
                    String status = scanner.nextLine();
                    System.out.print("Enter file path (or leave empty): ");
                    String filePath = scanner.nextLine();
                    System.out.print("Enter post Signal Count: ");
                    int signalCount = scanner.nextInt();
                    scanner.nextLine(); 

                    System.out.println("Select post type:");
                    System.out.println("1️⃣ Survey");
                    System.out.println("2️⃣ Announcement");
                    System.out.println("3️⃣ Comment");
                    System.out.print("👉 Choose post type: ");
                    int postTypeChoice = scanner.nextInt();
                    scanner.nextLine(); 

                    Post newPost = null;
                    if (postTypeChoice == 1) {
                        System.out.print("Enter survey question: ");
                        String surveyQuestion = scanner.nextLine();
                        System.out.print("Enter survey tags: ");
                        String surveyTags = scanner.nextLine();
                        System.out.print("Enter user list for survey (comma-separated): ");
                        String surveyUserList = scanner.nextLine();

                        newPost = new Survey(0, forumId, userId, 0, LocalDateTime.now(), LocalDateTime.now(), filePath, status, signalCount, surveyQuestion, surveyTags, surveyUserList);
                    } else if (postTypeChoice == 2) {
                        System.out.print("Enter announcement title: ");
                        String announcementTitle = scanner.nextLine();
                        System.out.print("Enter announcement content: ");
                        String announcementContent = scanner.nextLine();
                        System.out.print("Enter announcement tags: ");
                        String announcementTags = scanner.nextLine();

                        newPost = new Announcement(0, forumId, userId, 0, LocalDateTime.now(), LocalDateTime.now(), filePath,  status, signalCount, announcementTitle, announcementContent, announcementTags);
                    } else if (postTypeChoice == 3) {
                        System.out.print("Enter parent post ID (0 if not applicable): ");
                        int parentId = scanner.nextInt();
                        scanner.nextLine(); 
                        System.out.print("Enter comment content: ");
                        String commentContent = scanner.nextLine();

                        newPost = new Comment(0, forumId, userId, 0, LocalDateTime.now(), LocalDateTime.now(), filePath, status, signalCount, commentContent, parentId);
                    }

                    if (newPost != null) {
                        postService.ajouter(newPost);
                    }
                    break;

                case 6:
                    System.out.print("Enter post ID to modify: ");
                    int postId = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter new forum ID: ");
                    int newForumId = scanner.nextInt();
                    System.out.print("Enter new user ID: ");
                    int newUserId = scanner.nextInt();
                    scanner.nextLine(); 

                    System.out.println("Select post type:");
                    System.out.println("1️⃣ Survey");
                    System.out.println("2️⃣ Announcement");
                    System.out.println("3️⃣ Comment");
                    System.out.print("👉 Choose post type: ");
                    int postTypeChoice2 = scanner.nextInt();
                    scanner.nextLine(); 

                    System.out.print("Enter new status: ");
                    String newStatus = scanner.nextLine();
                    System.out.print("Enter new file path: ");
                    String newFilePath = scanner.nextLine();
                    System.out.print("Enter post Signal Count: ");
                    int newSignal_count = scanner.nextInt();
                    scanner.nextLine(); 

                    String postType = "";
                    String surveyQuestion = null, surveyTags = null, surveyUserList = null;
                    String announcementTitle = null, announcementContent = null, announcementTags = null;
                    int parentId = 0;
                    String commentContent = null;

                    switch (postTypeChoice2) {
                        case 1:
                            postType = "survey";
                            System.out.print("Enter survey question: ");
                            surveyQuestion = scanner.nextLine();
                            System.out.print("Enter survey tags: ");
                            surveyTags = scanner.nextLine();
                            System.out.print("Enter survey user list: ");
                            surveyUserList = scanner.nextLine();
                            break;
                        case 2:
                            postType = "announcement";
                            System.out.print("Enter announcement title: ");
                            announcementTitle = scanner.nextLine();
                            System.out.print("Enter announcement content: ");
                            announcementContent = scanner.nextLine();
                            System.out.print("Enter announcement tags: ");
                            announcementTags = scanner.nextLine();
                            break;
                        case 3:
                            postType = "comment";
                            System.out.print("Enter parent post ID: ");
                            parentId = scanner.nextInt();
                            scanner.nextLine(); 
                            System.out.print("Enter comment content: ");
                            commentContent = scanner.nextLine();
                            break;
                        default:
                            System.out.println("❌ Invalid post type selected.");
                            break;
                    }

                    Post updatedPost = null;
                    switch (postType) {
                        case "survey":
                            updatedPost = new Survey(postId, newForumId, newUserId, 0, LocalDateTime.now(), LocalDateTime.now(),
                                    newFilePath, newStatus, newSignal_count, surveyQuestion, surveyTags, surveyUserList);
                            break;
                        case "announcement":
                            updatedPost = new Announcement(postId, newForumId, newUserId, 0, LocalDateTime.now(), LocalDateTime.now(),
                                    newFilePath, newStatus, newSignal_count, announcementTitle, announcementContent, announcementTags);
                            break;
                        case "comment":
                            updatedPost = new Comment(postId, newForumId, newUserId, 0, LocalDateTime.now(), LocalDateTime.now(),
                                    newFilePath, newStatus, newSignal_count, commentContent, parentId);
                            break;
                        default:
                            System.out.println("❌ Invalid post type.");
                            break;
                    }

                    if (updatedPost != null) {
                        postService.modifier(updatedPost);
                    }
                    break;


                case 7:
                    System.out.print("Enter post ID to delete: ");
                    int deletePostId = scanner.nextInt();
                    Post PostToDelete = new Post();
                    PostToDelete.setPostId(deletePostId);
                    postService.supprimer(PostToDelete);
                    break;
                case 8:
                    postService.afficher();
                    break;

                case 9:
                    System.out.println("🚪 Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("❌ Invalid choice! Try again.");
            }
        }
    }
}
