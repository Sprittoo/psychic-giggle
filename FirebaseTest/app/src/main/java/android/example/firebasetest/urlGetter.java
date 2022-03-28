package android.example.firebasetest;

public class urlGetter {

    public String url;

    public urlGetter() {
    }

    public urlGetter(String NameAndIP) {

        String[] parts = NameAndIP.split(" : ");
        this.url = "http://" + parts[1] + ":8082/";;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String ip) {
        this.url = "http://" + ip + ":8082/";
    }
}
