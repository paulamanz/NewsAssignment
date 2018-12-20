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
    public static TextView txt; // temporal, para ver los resultados
    SharedPreferences preferencia = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myContext = this;
        preferencia = getSharedPreferences("MiPreferencia",Context.MODE_PRIVATE);
        // para bajar datos de la pagina web
        Button b = findViewById(R.id.loginButton);
        txt = findViewById(R.id.textView);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AsyncTaskLog task = new AsyncTaskLog();
                task.execute(new String[]{"https://www.marca.com/"});
            }
        });
    }
}
