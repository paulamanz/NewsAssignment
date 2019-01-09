package com.eit.paulamanz.firstapp;


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
    private ProgressBar progressBar;
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
        progressBar = new ProgressBar(context);
        progressBar.setVisibility(View.VISIBLE);
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
        progressBar.setVisibility(View.GONE);

        if (success) {
            view.showArticleInfo(article);
        }
    }

}
