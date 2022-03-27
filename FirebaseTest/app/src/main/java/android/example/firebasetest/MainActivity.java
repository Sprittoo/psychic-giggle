package android.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listview;
    private Button listbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = findViewById(R.id.listview);
        listbtn = findViewById(R.id.listbtn);



        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.rpi_list,list);
        listview.setAdapter(adapter);

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

        listbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, list.toString(),Toast.LENGTH_SHORT ).show();
            }
        });

    }
}