package com.eit.paulamanz.firstapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AboutUs extends AppCompatActivity {

    /** The default code for the request */
    public static final int REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Intent originalIntent = getIntent();
        Button bImage = findViewById(R.id.buttonImage);

        int n1 = originalIntent.getIntExtra("parameter_1",0);
        String s1 = String.valueOf(n1);
        String s2= originalIntent.getStringExtra("parameter_2");


        TextView text1 = findViewById(R.id.textView1);
        text1.setText(s1);

        TextView text2 = findViewById(R.id.textView2);
        text2.setText(s2);

        bImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = findViewById(R.id.imageView1);

        if (requestCode == REQUEST_CODE &&
                resultCode == Activity.RESULT_OK){
                InputStream stream = null;
        try {
                 stream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (stream != null){
                    try{
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }
    }
}
