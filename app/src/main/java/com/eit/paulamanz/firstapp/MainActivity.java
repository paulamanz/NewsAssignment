package com.eit.paulamanz.firstapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button b = findViewById(R.id.firstbutton);
        Button bnext = findViewById(R.id.nextbutton);
        final EditText tel = findViewById(R.id.firsttext);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Context context = getApplicationContext();
              //  CharSequence text = txt.getText();
               // int duration = Toast.LENGTH_SHORT;

               // Toast toast = Toast.makeText(context, text, duration);
               // toast.show();

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tel));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });


       bnext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent myIntent = new Intent(getApplicationContext(),
                       AboutUs.class);
               myIntent.putExtra("parameter_1", 34);
               myIntent.putExtra("parameter_2", "Hello World");
               MainActivity.this.startActivity(myIntent);
           }
       });

    }
}
