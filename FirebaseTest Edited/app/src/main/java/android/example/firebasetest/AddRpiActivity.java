package android.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

//FOR NOW MAIN IS ADD PRINTER PAGE, LTR CAN REFACTOR

public class AddRpiActivity extends AppCompatActivity {


    Button addCamera;
    EditText nameInput;
    EditText ipInput;
    String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrpi);
        addCamera = (Button) findViewById(R.id.addcamera);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserDotCom = user.getEmail();
        CurrentUser temp = new CurrentUser(currentUserDotCom);
        currentUser = temp.getUserEmail();

        addCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                nameInput = findViewById(R.id.nameinput);
                ipInput = findViewById(R.id.ipinput);

                if (nameInput.toString().matches("") || ipInput.toString().matches("")){
                    Toast.makeText(AddRpiActivity.this, "Entry can't be empty!",Toast.LENGTH_SHORT).show();
                }

                else {
                    IpHashMap ipmap = new IpHashMap(nameInput.getText().toString(),ipInput.getText().toString());
                    HashMap<String,Object> map = ipmap.getMap();

                    FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Cameras").updateChildren(map);  // <- Firebase path cannot got symbols, so need to create a hash table
                    Toast.makeText(AddRpiActivity.this, ("Camera " + nameInput.getText().toString() + " is added to the list"),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddRpiActivity.this,MainActivity.class));
                }


            }
        });





//        FirebaseDatabase.getInstance().getReference().child("CameraList").child("IP").setValue("100.72.73.128");
    }
}