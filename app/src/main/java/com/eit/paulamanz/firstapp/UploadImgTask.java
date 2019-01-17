package com.eit.paulamanz.firstapp;

import android.app.ProgressDialog;
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
    private ProgressDialog progressDialog;
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
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Login ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
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
        progressDialog.dismiss();

        if (success) {
            String Slecteditem="Image updated";
            Toast.makeText(context, Slecteditem, Toast.LENGTH_SHORT).show();
        }
    }

}
