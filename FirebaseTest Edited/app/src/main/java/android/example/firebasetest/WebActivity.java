package android.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WebActivity extends AppCompatActivity {

    WebView web;
    String URL;
    Button resetbtn;
    Button polis;
    String ip;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        polis = findViewById(R.id.callpolisbtn);
        resetbtn = findViewById(R.id.resetbtn);

        //Get data base ref for each ip and extract the ip
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserDotCom = user.getEmail();                           //<-- This chunk of codes get user email without .com as id
        CurrentUser temp = new CurrentUser(currentUserDotCom);
        currentUser = temp.getUserEmail();

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear firebase data here
                FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).removeValue();
                startActivity(new Intent(WebActivity.this,LoginActivity.class));
            }
        });

        polis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WebActivity.this,MainActivity.class));
            }
        });


        DatabaseReference userData = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Cameras");
        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Devices device = new Devices(snapshot.getKey(), snapshot.getValue().toString());
                    ip = device.getIp();
                    urlGetter urlgetter = new urlGetter(ip);
                    URL = urlgetter.getUrl();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        web = findViewById(R.id.webView);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(URL);


    }
}