package com.eit.paulamanz.firstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static Context myContext = null;
    public static String username = null;
    public static String password = null;
    public static String errorLoging = null;
//    public static TextView txt; // temporal, para ver los resultados
    SharedPreferences preferencia = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myContext = this;
        preferencia = getSharedPreferences("MiPreferencia",Context.MODE_PRIVATE);
        // para bajar datos de la pagina web
        Button b = findViewById(R.id.loginButton);
        errorLoging = findViewById(R.id.errorMessage).toString();
//        txt = findViewById(R.id.textView);
        username = findViewById(R.id.usernameInput).toString();
        password = findViewById(R.id.passwordInput).toString();

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AsyncTaskLog task = new AsyncTaskLog(myContext);
                task.execute(new String[]{"https://www.google.com"});
            }
        });

    }
}
