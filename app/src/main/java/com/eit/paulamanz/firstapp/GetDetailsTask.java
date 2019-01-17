package com.eit.paulamanz.firstapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import es.upm.hcid.pui.assignment.Article;
import es.upm.hcid.pui.assignment.exceptions.ServerCommunicationError;

public class GetDetailsTask extends AsyncTask<String, Void, Boolean> {

    private final NewsDetailsView view;
    private final Context context;
    private ProgressDialog progressDialog;
    private Article article;
    private int articleId;

    private ModelManagerHandler modelManagerHandler = ModelManagerHandler.getInstance();

    public GetDetailsTask(NewsDetailsView view, Context context, int articleId) {
        this.view = view;
        this.context = context;
        this.articleId = articleId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting details ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean success = false;
        try {
            article = modelManagerHandler.getArticle(articleId);
            success = true;
        } catch (ServerCommunicationError serverCommunicationError) {
            Log.e("GET_ARTICLE_TAG", serverCommunicationError.getMessage());
        }


        return success;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        progressDialog.dismiss();

        if (success) {
            view.showArticleInfo(article);
        }
    }

}
