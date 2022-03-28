package android.example.firebasetest;

public class urlGetter {

    public String url;

    public urlGetter() {
    }

    public urlGetter(String ip) {
        this.url = "http://" + ip + ":8082/";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String ip) {
        this.url = "http://" + ip + ":8082/";
    }
}
