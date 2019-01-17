package com.eit.paulamanz.firstapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import es.upm.hcid.pui.assignment.Article;
import es.upm.hcid.pui.assignment.Image;
import es.upm.hcid.pui.assignment.exceptions.ServerCommunicationError;

public class UploadImgTask extends AsyncTask<String, Void, Boolean> {

    private final NewsDetailsView view;
    private final Context context;
    private ProgressBar progressBar;
    private Image image;
    private int articleId;

    private ModelManagerHandler modelManagerHandler = ModelManagerHandler.getInstance();

    public UploadImgTask(NewsDetailsView view, Context context, Image image) {
        this.view = view;
        this.context = context;
        this.image = image;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = new ProgressBar(context);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean success = false;
        try {
            image.save();
            success = true;
        } catch (ServerCommunicationError serverCommunicationError) {
            Log.e("UPLOAD_IMAGE_TAG", serverCommunicationError.getMessage());
        }


        return success;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        progressBar.setVisibility(View.GONE);

        if (success) {
            String Slecteditem="Image updated";
            Toast.makeText(context, Slecteditem, Toast.LENGTH_SHORT).show();
        }
    }

}
