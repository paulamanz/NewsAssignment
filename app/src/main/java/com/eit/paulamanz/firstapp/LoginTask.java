package com.eit.paulamanz.firstapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import es.upm.hcid.pui.assignment.Article;
import es.upm.hcid.pui.assignment.ModelManager;
import es.upm.hcid.pui.assignment.exceptions.AuthenticationError;
import es.upm.hcid.pui.assignment.exceptions.ServerCommunicationError;

public class LoginTask extends AsyncTask<String, Void, Boolean> {

    private final Context context;
    private final Properties properties;
    private ProgressDialog progressDialog;

    private ModelManagerHandler modelManagerHandler = ModelManagerHandler.getInstance();

    public LoginTask(Context context, Properties properties) {
        this.context = context;
        this.properties = properties;
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
            modelManagerHandler.initialize(properties);

            success = true;
        } catch (AuthenticationError authenticationError) {
            Log.e("LOGIN_LOG_TAG", authenticationError.getMessage());
        }

        return success;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        progressDialog.dismiss();

        if (success) {
            modelManagerHandler.storeUserInfo(context);

            Intent articlesIntent = new Intent(context, NewsListActivity.class);
            context.startActivity(articlesIntent);
        } else {
            showError();
        }
    }

    private void showError() {
        Toast.makeText(context, context.getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }
}
