package com.eit.paulamanz.firstapp;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;


import es.upm.hcid.pui.assignment.Article;
import es.upm.hcid.pui.assignment.exceptions.ServerCommunicationError;

public class GetArticlesTask extends AsyncTask<String, Void, Boolean> {

    private final Context context;
    private ProgressBar progressBar;
    private List<Article> articlesList;
    private NewsListActivity activity;

    private ModelManagerHandler modelManagerHandler = ModelManagerHandler.getInstance();

    public GetArticlesTask(NewsListActivity activity,Context context) {
        this.context = context;
        this.activity= activity;
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
            articlesList = modelManagerHandler.getList();

            success = true;
        } catch (ServerCommunicationError serverCommunicationError) {
            Log.e("GET_LIST_TAG", serverCommunicationError.getMessage());
        }


        return success;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        progressBar.setVisibility(View.GONE);

        if (success) {
            activity.updateList(articlesList);
        }
    }

}
