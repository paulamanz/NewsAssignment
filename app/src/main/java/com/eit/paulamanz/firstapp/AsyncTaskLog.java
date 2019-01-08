package com.eit.paulamanz.firstapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class AsyncTaskLog extends AsyncTask<String, Integer, String> {

    Context context;
    public AsyncTaskLog(Context context) {
        this.context = context.getApplicationContext();
    }
    private ProgressDialog progressDialog;

    // primero en ejecutarse
    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
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

        //ListView.txt.setText(s);
        Intent myIntent = new Intent(this.context, ListView.class);
        myIntent.putExtra("key", s); //Optional parameters
        context.startActivity(myIntent);
    }

    //  aqui obtenemos los datos
    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        try {
            if(MainActivity.username == null || MainActivity.username.length()== 0) {
                MainActivity.errorLoging = "Incorrect username";
            }
            else if (MainActivity.password == null || MainActivity.password.length()== 0) {
                MainActivity.errorLoging = "Incorrect password";
            }
            else {
                //ModelManager.login(MainActivity.username, MainActivity.password);
            }

            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader isw = new InputStreamReader(in);
                int data = isw.read();
                int cont = 0;
                while (data != -1 && cont <1000) {
                    result += (char) data;
                    data = isw.read();
                    cont++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "Exception: " + e.getMessage();
        }
        return result;
    }
}
