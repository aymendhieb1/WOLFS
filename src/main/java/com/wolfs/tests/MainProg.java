public class MainProg {
    public MainProg() {
    }
    public static void main(String[] args) {
        Client clientajout1 = new Client( "yaakoubi", "Ben Ali", "ahmed.benali@example.com", "password123", 98765432, 2, 1, "ahmed_profile.jpg");

        Client clientajout2 = new Client( "Sophie", "Dupont", "sophie.dupont@example.com", "securePass!", 92345678, 2, 1, "sophie_avatar.png");

        Client clientajout3 = new Client( "Karim", "Jlassi", "karim.jlassi@example.com", "karim2024", 96543210, 2, 1, "karim_profile.png");

        Client clientupdate1 = new Client( 1,"yaakoubi", "Ben Ali", "ahmed.benali@example.com", "password123", 98765432, 2, 1, "ahmed_profile.jpg");

        ClientServices s1=new ClientServices();
        //add client

        //s1.ajouterUser( client1);
        //s1.ajouterUser( client2);
        //s1.ajouterUser( client3);

        //update client

        //s1.modifierUser(clientupdate1);

        //delete client
        //s1.supprimerUser(clientupdate1);
/********************************************/
        Admin adminAjout1 = new Admin("Dupont", "Jean", "jean.dupont@email.com", 98765432, "securePass123", 0);

        Admin adminUpdate1 = new Admin(6,"Dupont", "Jean", "jean.dupont@esprit.tn", 98765432, "securePass123", 0);

        AdminServices  s2=new AdminServices();


        //add admin
        //s2.ajouterUser(adminAjout1);

        //update admin
        //s2.modifierUser(adminUpdate1);

        //supprimer admin
        //s2.supprimerUser(adminUpdate1);
        /********************************************/
        Moderator moderatorAjout1 = new Moderator("Dhib", "Youssef", "youssef.dhib@email.com", 98765432, "securePass123", 1);

        Moderator moderatorUpdate1 = new Moderator(9,"Dhib", "Youssef", "youssef.dhib@esprit.tn", 98765432, "securePass123", 1);

        ModeratorServices  s3=new ModeratorServices();

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








    }
}
