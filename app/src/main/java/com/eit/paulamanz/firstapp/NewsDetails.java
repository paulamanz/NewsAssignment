package com.eit.paulamanz.firstapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import es.upm.hcid.pui.assignment.Article;
import es.upm.hcid.pui.assignment.Utils;

public class NewsDetails extends AppCompatActivity implements NewsDetailsView {

    public static final int REQUEST_CODE = 5;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private int articleId;
    TextView title;
    // TextView subtitle;
    TextView category;
    TextView articleAbstract;
    TextView body;
    TextView footer;
    Button edit;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Intent intent = getIntent();
        articleId = intent.getIntExtra("key", 0);

        edit = findViewById(R.id.btn_edit);
        title = findViewById(R.id.txt_title);
        imageView = findViewById(R.id.img_view);
        category = findViewById(R.id.txt_category);
        articleAbstract = findViewById(R.id.txt_abstract);
        body = findViewById(R.id.txt_body);
        footer = findViewById(R.id.txt_footer);

        GetDetailsTask getDetailsTask = new GetDetailsTask(this, this, articleId);
        getDetailsTask.execute();

        //TODO upload images to the API
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }

        if (requestCode == REQUEST_CODE &&
                resultCode == Activity.RESULT_OK) {
            InputStream stream = null;
            try {
                stream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                startActivityForResult(intent, REQUEST_CODE);
                                break;
                            case 1:
                                dispatchTakePictureIntent();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    @Override
    public void showArticleInfo(Article article) {
        title.setText(article.getTitleText());
        articleAbstract.setText(article.getAbstractText());
        category.setText(article.getCategory());
        body.setText(article.getBodyText());
        footer.setText(article.getFooterText());
        imageView.setImageBitmap(Utils.base64StringToImg(article.getImage().getImage()));
    }
}
