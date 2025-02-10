public class Client extends User{

    private int status;
    private String photo_profile;

    public Client(String nom, String prenom, String email, String password, int num_tel, int role,int status,String photo_profile) {
        super(nom, prenom, email, password, num_tel, role);
        this.status = status;
        this.photo_profile = photo_profile;
    }

    public Client(int id, String nom, String prenom, String email, String password, int num_tel, int role,int status,String photo_profile) {
        super(id, nom, prenom,email,password,num_tel,role);
        this.status = status;
        this.photo_profile = photo_profile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhoto_profile() {
        return photo_profile;
    }

    public void setPhoto_profile(String photo_profile) {
        this.photo_profile = photo_profile;
    }

    @Override
    public String toString() {
        return "Client{" +
                "status=" + status +
                ", photo_profile='" + photo_profile + '\'' +
                ", id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", num_tel=" + num_tel +
                ", role=" + role +
                '}';
    }
}
