package android.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listview;
    private Button logoutbtn;
    private Button addbtn;
    FirebaseAuth auth;
    private String URl;
    private WebView web;
    public String name_IP;
    public String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = findViewById(R.id.listview);
        logoutbtn = findViewById(R.id.logoutbtn);
        addbtn = findViewById(R.id.addbtn);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = firebaseUser.getEmail();
        CurrentUser user = new CurrentUser(email);
        currentUser = user.getUserName();



        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddRpiActivity.class));
            }
        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).removeValue();
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                auth.signOut();
            }
        });



        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.rpi_list,list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this,"Clicked!" , Toast.LENGTH_SHORT).show(); //LINK HERE TO START WEBVIEW
//                startActivity(new Intent(MainActivity.this, WebActivity.class));
                name_IP = adapterView.getItemAtPosition(i).toString();  // <-- OMG I GOT IT NOW I KNOW THE IP AND NAME AS  A STRING
                Toast.makeText(MainActivity.this,name_IP , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("NameAndIP",name_IP);
                startActivity(intent);
            }
        });

        DatabaseReference userdata = FirebaseDatabase.getInstance().getReference().child("Users").child("blabla@gmail").child("Cameras");  // <- the name of branch to retrive data
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {  //snapshot of the "blabla@gmail" branch
                list.clear();     //<- clear list to prevent repeated entries when new cam ip is added


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Devices device = new Devices(snapshot.getKey(), snapshot.getValue().toString());   // <-- Created a helper class to get the device name and ip
                    String camName = device.getCamName();
                    String ip = device.getIp();
                    list.add(camName + " : " + ip);

//                    list.add(snapshot.getValue().toString());    <--  getValue() = Getting the ip
//                    list.add(snapshot.getKey());    // <-- getKey() = Getting the name


                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ;
            }
        });

    }
}