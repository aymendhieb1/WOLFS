package com.wolfs.tests;

import com.wolfs.models.*;
import com.wolfs.services.*;
import com.wolfs.models.Contrat;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MainProg {
    public MainProg() {
    }

    /* ------------------------------------------- Gestion Users ------------------------------------ */

    public static void main(String[] args) throws ParseException {
        Client clientajout1 = new Client( "youssef", "Ben Ali", "ahmed.benali@example.com", "password123", 98765432, 2, 1, "ahmed_profile.jpg");

        Client clientajout2 = new Client( "aymen", "Dupont", "sophie.dupont@example.com", "securePass!", 92345678, 2, 1, "sophie_avatar.png");

        Client clientajout3 = new Client( "Karim", "Jlassi","karim.jlassi@example.com", "karim2024", 96543210, 2, 1, "karim_profile.png");

        Client clientupdate1 = new Client( 2,"talel", "Ben Ali", "ahmed.benali@example.com", "password123", 98765432, 2, 1, "ahmed_profile.jpg");

        ClientServices s1=new ClientServices();
        //add client

        // s1.ajouterUser( clientajout1);
        // s1.ajouterUser( clientajout2);
        //s1.ajouterUser( client3);

        //update client

        //s1.modifierUser(clientupdate1);

        //delete client
        //s1.supprimerUser(clientupdate1);
        /********************************************/
        Admin adminAjout1 = new Admin("Dupont", "Jean", "jean.dupont@email.com", 98765432, "securePass123", 0);

        Admin adminUpdate1 = new Admin(6,"Dupont", "Jean", "jean.dupont@esprit.tn", 98765432, "securePass123", 0);

        AdminServices s2=new AdminServices();


        //add admin
        //s2.ajouterUser(adminAjout1);

        //update admin
        //s2.modifierUser(adminUpdate1);

        //supprimer admin
        //s2.supprimerUser(adminUpdate1);
        /********************************************/
        Moderator moderatorAjout1 = new Moderator("Dhib", "Youssef", "youssef.dhib@email.com", 98765432, "securePass123", 1);

        Moderator moderatorUpdate1 = new Moderator(9,"Dhib", "Youssef", "youssef.dhib@esprit.tn", 98765432, "securePass123", 1);

        ModeratorServices s3=new ModeratorServices();

        //add moderator
        // s3.ajouterUser(moderatorAjout1);

        //update moderator
        //s3.modifierUser(moderatorUpdate1);

        //supprimer moderator
        //s3.supprimerUser(moderatorUpdate1);



        //recherche Users
        System.out.println("Clients:");
        System.out.println(s1.rechercherUser());
        System.out.println("Admins:");
        System.out.println(s2.rechercherUser());
        System.out.println("Moderators:");
        System.out.println(s3.rechercherUser());

        /* ------------------------------------------- Gestion Contrat ------------------------------------ */


        ContratService contratService = new ContratService();
        VehiculeService vehiculeService = new VehiculeService(); // Vehicle service

        // Test: Add a contract
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Contrat contratToAdd = new Contrat();
        contratToAdd.setDateD(sdf.parse("2024-03-01")); // Setting dateD
        contratToAdd.setDateF(sdf.parse("2024-03-06")); //
        contratToAdd.setCinLocateur(11234);
        contratToAdd.setPhotoPermit("photoPermit.jpg");

        // Create a Bus and assign it to the contract (You can also use Voiture if needed)
        Bus bus = new Bus(1, "BBB123", "indisponible", 30); // Bus with 40 seats
        contratToAdd.setVehicule(bus);

        //contratService.ajouter(contratToAdd); // Add contract
        System.out.println("--------------------------!\n");

        // Test: Modify a contract
        Contrat contratToUpdate = new Contrat();
        contratToUpdate.setIdLocation(38); // Assuming contract ID to modify is 7
        contratToUpdate.setDateD(sdf.parse("2024-03-1")); // New Start Date
        contratToUpdate.setDateF(sdf.parse("2024-03-3")); // New End Date
        contratToUpdate.setCinLocateur(11234);
        contratToUpdate.setPhotoPermit("newPhotoPermit1.jpg");

        // Assuming you want to update the bus vehicle or replace it with a new one
        Bus updatedBus = new Bus(1, "BBB123", "indisponible", 30); // Update Bus with new number of seats
        contratToUpdate.setVehicule(updatedBus);

        //contratService.modifier(contratToUpdate); // Modify the contract
        System.out.println("------------------------------\n");

        // Test: Delete a contract
        Contrat contratToDelete = new Contrat();
        contratToDelete.setIdLocation(38);
        //contratService.supprimer(contratToDelete); // Delete contract
        System.out.println("-------------------------------------\n");

        // Test: Get all contracts and display them along with vehicle info
        System.out.println("📜 Listing all contracts...\n");

        List<Contrat> contratsList = contratService.getAllContrats(); // Get all contracts
        for (Contrat contrat : contratsList) {
            Vehicule vehicule = contrat.getVehicule();
            System.out.println("Contract ID: " + contrat.getIdLocation());
            System.out.println("Contract Date Start: " + contrat.getDateD());
            System.out.println("Contract Date End: " + contrat.getDateF());
            System.out.println("Locataire CIN: " + contrat.getCinLocateur());
            System.out.println("Photo Permit: " + contrat.getPhotoPermit());

            System.out.println("Vehicule Matricule: " + vehicule.getMatricule());
            if (vehicule instanceof Voiture) {
                System.out.println("Vehicule Type: Voiture");
                System.out.println("Vehicule Cylinder: " + ((Voiture) vehicule).getCylinder());
            } else if (vehicule instanceof Bus) {
                System.out.println("Vehicule Type: Bus");
                System.out.println("Vehicule nbPlace: " + ((Bus) vehicule).getNbPlace());
            }
            System.out.println("---------------------------------");
        }

        // Example to add, modify, and delete a vehicle
        // Add a vehicle
        Vehicule newVehicule = new Voiture(0, "AAA123", "Available", 1600);
        // vehiculeService.ajouter(newVehicule);

        // Modify a vehicle (Assuming the vehicle ID is 1)
        Vehicule modifiedVehicule = new Bus(1, "BBB123", "available", 30);
        //vehiculeService.modifier(modifiedVehicule);

        // Delete a vehicle
        Vehicule vehiculeToDelete = new Voiture(2 ,"221TN1000" , "disponible", 5); // Create a dummy bus with the ID
        //vehiculeService.supprimer(vehiculeToDelete);

        List<Vehicule> vehiculesList = vehiculeService.getAllVehicules();  // Get all vehicles

        // Display each vehicle in the list
        System.out.println("🚗 Listing all vehicles...\n");
        for (Vehicule vehicule : vehiculesList) {
            System.out.println("Vehicule ID: " + vehicule.getIdVehicule());
            System.out.println("Matricule: " + vehicule.getMatricule());
            System.out.println("Status: " + vehicule.getStatus());
            if (vehicule instanceof Voiture) {
                System.out.println("Vehicule Type: Voiture");
                System.out.println("Cylinder: " + ((Voiture) vehicule).getCylinder());
            } else if (vehicule instanceof Bus) {
                System.out.println("Vehicule Type: Bus");
                System.out.println("Seats: " + ((Bus) vehicule).getNbPlace());
            }
            System.out.println("---------------------------------");
        }
        /* ------------------------------------------- Gestion vol ------------------------------------ */

        // Initialize VolService
        VolService volService = new VolService();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                                                /*Vol volToAdd = new Vol(
                                                        "Tunis",
                                                        "Morocco",
                                                        LocalDateTime.parse("12/12/2025 10:00", formatter),
                                                        LocalDateTime.parse("12/12/2025 14:00", formatter),
                                                        ClasseChaise.ECONOMY,  // Use the Enum here
                                                        "Air Morocco",
                                                        1800,
                                                        24,
                                                        "Test flight"
                                                );
                                                volService.ajouterVol(volToAdd);
                                                System.out.println("Vol ajouté: " + volToAdd);
                                        */

                                               /*Vol volToModify = new Vol(
                                                        1,
                                                        "Tunis",
                                                        "Italy",
                                                        LocalDateTime.parse("12/12/2025 15:00", formatter),
                                                        LocalDateTime.parse("12/12/2025 17:00", formatter),
                                                        ClasseChaise.BUSINESS,  // Use the Enum here
                                                        "Tunisair",
                                                        2000,
                                                        10,
                                                        "Modified flight"
                                                );*/
        //volService.modifierVol(volToModify);
        //System.out.println("Vol modifié: " + volToModify);


        Vol volToDelete = new Vol(1, "", "", null, null, null, null, 0, 0, "");
        volService.supprimerVol(volToDelete);
        System.out.println("Vol supprimé: " + volToDelete);

        List<Vol> vols = volService.rechercherVol();
        System.out.println("-----------------------------------");
        System.out.println("List of Flights");
        System.out.println("-----------------------------------");
        for (Vol vol : vols) {
            System.out.println("Flight ID: " + vol.getFlightID());
            System.out.println("Departure: " + vol.getDeparture());
            System.out.println("Destination: " + vol.getDestination());
            System.out.println("Departure Time: " + vol.getDepartureTime());
            System.out.println("Arrival Time: " + vol.getArrivalTime());
            System.out.println("Classe Chaise: " + vol.getClasseChaise());
            System.out.println("Airline: " + vol.getAirline());
            System.out.println("Price: " + vol.getFlightPrice());
            System.out.println("Available Seats: " + vol.getAvailableSeats());
            System.out.println("Description: " + vol.getDescription());
            System.out.println("-----------------------------------");
        }


        /* ------------------------------------------- Gestion Hotels ------------------------------------ */

        //HotelService hot = new HotelService();
        // hot.ajouter(new Hotel("youssef", "xx","23456789","mouradi.@gmail.com","url",""));
        //hot.modifier(new Hotel(4,"Mouradi", "xx","23456789","mouradi.@gmail.com","url",""));
        // hot.supprimer(new Hotel(4,"","","","","","" ));
        //System.out.println(hot.rechercher());

        // ChambreService ch = new ChambreService();
        //ch.ajouter(new Chambre(1,"exclusive",300,true,1));
        // System.out.println(ch.afficher());

        /* ------------------------------------------- Gestion Circuis ------------------------------------ */

        ActiviteService2 activiteService = new ActiviteService2();
        SessionService sessionService = new SessionService();

        Activite activite1 = new Activite("sp", "A relaxing activity", "Beach", "Fitness", 20);
        // activiteService.ajouter(activite1);
        //Activite activite2= new Activite(7,"Yoga", "A relaxing activity", "Beach", "Fitness", 20);


        //activite2.setNom_act("hello");
        //activiteService.modifier(activite2);


        //  activiteService.supprimer(activite2);


        LocalDate date = LocalDate.of(2026,10,10);
        LocalTime time = LocalTime.of(4,20,20);
        Session session1 = new Session(date, time, 50, 100);
        Session session2 = new Session(1,date, time, 50, 100);

        //sessionService.ajouter(session1);



        //session2.setNbr_places_sess(220);
        //sessionService.modifier(session2);

        //    sessionService.supprimer(session2);


        System.out.println(activiteService.rechercher());

        System.out.println(sessionService.rechercher());

        /* ------------------------------------------- Gestion Forum ------------------------------------ */



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

                    Forum newForum = new Forum(0, name, createdBy, 0, 0, description, LocalDateTime.now(), isPrivate);
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

                    Forum updatedForum = new Forum(modId, newName, newCreatedBy, 0, 0, newDesc, LocalDateTime.now(), newIsPrivate);
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
