package android.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText pw;
    private Button login_btn;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        pw = findViewById(R.id.password);
        login_btn = findViewById(R.id.login);

        auth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {   //For when the button is clicked, do...
            @Override                                               //Must be View.OnClickListener
            public void onClick(View view) {
                String email_str = email.getText().toString();
                String pw_str = pw.getText().toString();
                
                loginUser(email_str,pw_str);
            }
        });
    }

    private void loginUser(String email, String pw) {
        auth.signInWithEmailAndPassword(email,pw).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));  //Intent from LoginActivity to MainActivity
                finish();
            }
        });
    }

}