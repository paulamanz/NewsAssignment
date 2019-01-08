package com.eit.paulamanz.firstapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import es.upm.hcid.pui.assignment.Article;
import es.upm.hcid.pui.assignment.ModelManager;
import es.upm.hcid.pui.assignment.exceptions.AuthenticationError;
import es.upm.hcid.pui.assignment.exceptions.ServerCommunicationError;

public class AsyncTaskLog extends AsyncTask<String, Integer, String> {

    private ProgressDialog progressDialog;
    Properties prop = new Properties();
    static ModelManager myModelManager = null;


    // primero en ejecutarse
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(MainActivity.myContext);
        progressDialog.setMessage("Loggin ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    // btener el resultado para actualizar la UI
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        // le intentamos dar el valor de lo que ha sacado la tarea
        MainActivity.txt.setText(s);
    }

    //  aqui obtenemos los datos
    @Override
    protected String doInBackground(String... urls) {
        String result = "";

        // user incorrect
        if(MainActivity.username == null || MainActivity.username.length()== 0) {
            MainActivity.errorLoging = "Incorrect username";
        }
        // password incorrect
        else if (MainActivity.password == null || MainActivity.password.length()== 0) {
            MainActivity.errorLoging = "Incorrect password";
        }
        // OK
        else {
            try {
                prop.setProperty(ModelManager.ATTR_LOGIN_USER, MainActivity.username);
                prop.setProperty(ModelManager.ATTR_LOGIN_PASS, MainActivity.password);
                prop.setProperty(ModelManager.ATTR_SERVICE_URL, urls[0]);
                prop.setProperty(ModelManager.ATTR_REQUIRE_SELF_CERT, "TRUE");
                myModelManager = new ModelManager(prop);
                myModelManager.login(MainActivity.username, MainActivity.password);

            } catch (AuthenticationError authenticationError) {
                authenticationError.printStackTrace();
            }

            // Lista de Articulos recibida al hacer el login
            try {
                List<Article> res = myModelManager.getArticles(2,2);
                for (Article article : res) {
                    System.out.println(article);
                }
            } catch (ServerCommunicationError serverCommunicationError) {
                serverCommunicationError.printStackTrace();
            }
        }
      return result;
    }
}
