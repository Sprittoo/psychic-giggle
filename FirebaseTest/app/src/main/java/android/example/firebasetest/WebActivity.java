package android.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class WebActivity extends AppCompatActivity {

    WebView web;
    String URL;
    Button polis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        polis = findViewById(R.id.callpolisbtn);


        Intent intent = getIntent();
        String NameAndIP = intent.getStringExtra("NameAndIP");

        urlGetter url = new urlGetter(NameAndIP);
        URL = url.getUrl();


        web =  findViewById(R.id.webView);
//        web.setWebViewClient(new WebViewClient());
        web.loadUrl(URL);




        polis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WebActivity.this, URL,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(WebActivity.this,MainActivity.class));
            }
        });




    }
}