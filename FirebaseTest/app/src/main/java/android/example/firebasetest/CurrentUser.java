package android.example.firebasetest;

public class CurrentUser {
    String userName;


    public CurrentUser() {

    }

    public CurrentUser(String Email) {
        String[] parts = Email.split(".com");
        this.userName = parts[0];


    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}