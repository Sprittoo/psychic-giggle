package android.example.firebasetest;

public class CurrentUser {
    String userEmail;


    public CurrentUser() {

    }

    public CurrentUser(String Email) {
       String[] parts = Email.split(".com");
       this.userEmail = parts[0];


    }

    public String getUserEmail() {

        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
