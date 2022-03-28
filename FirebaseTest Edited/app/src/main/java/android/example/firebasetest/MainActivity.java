package android.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;

import android.os.Bundle;
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
    private Button logout;
    private String currentUser;
    private FirebaseAuth auth;
    private Button addbtn;


    public void loadWeb(View view){
        startActivity(new Intent(MainActivity.this,WebActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = findViewById(R.id.listview);
        logout = findViewById(R.id.logoutbtn);
        auth = FirebaseAuth.getInstance();
        addbtn = findViewById(R.id.addbtn);



        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddRpiActivity.class));
            }
        });







        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.rpi_list,list);
        listview.setAdapter(adapter);

        //-------
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                Toast.makeText(MainActivity.this,"Clicked!" , Toast.LENGTH_SHORT).show();   //<-- Say clicked when any button clicked

            }
        });
        //-------

        DatabaseReference userdata = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Cameras");  // <- the name of branch to retrive data
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {  //snapshot of the "username" branch
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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, currentUser,Toast.LENGTH_SHORT ).show();
                auth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

    }
}