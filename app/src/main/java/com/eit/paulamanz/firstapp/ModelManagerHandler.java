package com.eit.paulamanz.firstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;

import java.util.List;
import java.util.Properties;

import es.upm.hcid.pui.assignment.Article;
import es.upm.hcid.pui.assignment.Image;
import es.upm.hcid.pui.assignment.ModelManager;
import es.upm.hcid.pui.assignment.exceptions.AuthenticationError;
import es.upm.hcid.pui.assignment.exceptions.ServerCommunicationError;

import static es.upm.hcid.pui.assignment.Utils.imgToBase64String;

public class ModelManagerHandler {

    private static ModelManagerHandler INSTANCE = null;

    private static final String USER_INFO_SHARED_PREFERENCES_KEY = "USER_INFO_PREFS";

    private static final String USERNAME_KEY = "userInfo:username";
    private static final String PASSWORD_KEY = "userInfo:password";

    private ModelManager modelManager;
    private Properties properties;

    private ModelManagerHandler() {

    }

    public static ModelManagerHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModelManagerHandler();
        }

        return INSTANCE;
    }

    public void initialize(Properties properties) throws AuthenticationError {
        this.properties = properties;
        modelManager = new ModelManager(properties);
    }

    public List<Article> getList() throws ServerCommunicationError {
        return modelManager.getArticles(/*2,2*/);

    }

    public Article getArticle(int articleId) throws ServerCommunicationError {
        return modelManager.getArticle(articleId);
    }

    public Image createImage (Bitmap bitmap, int articleId){
        Image image = new Image(this.modelManager,0,"",articleId,imgToBase64String(bitmap));
        return image;
    }


    public void storeUserInfo(@NonNull Context context) {
        String username = properties.getProperty(ModelManager.ATTR_LOGIN_USER);
        String password = properties.getProperty(ModelManager.ATTR_LOGIN_PASS);

        SharedPreferences prefs = context.getSharedPreferences(USER_INFO_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(USERNAME_KEY, username);
        prefsEditor.putString(PASSWORD_KEY, password);
        prefsEditor.commit();
    }

    @Nullable
    public UserInfo getUserInfo(@NonNull Context context) {
        SharedPreferences prefs = context.getSharedPreferences(USER_INFO_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String username = prefs.getString(USERNAME_KEY, null);
        String password = prefs.getString(PASSWORD_KEY, null);

        if (username == null || password == null) {
            return null;
        }

        return new UserInfo(username, password);
    }
}
