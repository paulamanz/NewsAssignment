package com.eit.paulamanz.firstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import es.upm.hcid.pui.assignment.ModelManager;

public class MainActivity extends AppCompatActivity {

    private static final String LOGIN_URL = "https://sanger.dia.fi.upm.es/pui-rest-news/";

    public static Context myContext = null;
    public static String errorLoging = null;

    private EditText usernameTextField;
    private EditText passwordTextField;
    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();
        fillUserInfoFieldsIfPossible();
        setUpClickListeners();
    }

    private void setUpViews() {
        usernameTextField = findViewById(R.id.usernameInput);
        passwordTextField = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
    }

    private void fillUserInfoFieldsIfPossible() {
        ModelManagerHandler modelManagerHandler = ModelManagerHandler.getInstance();
        UserInfo userInfo = modelManagerHandler.getUserInfo(this);

        if (userInfo != null) {
            usernameTextField.setText(userInfo.getUsername());
            passwordTextField.setText(userInfo.getPassword());
        }
    }

    private void setUpClickListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginButtonClicked();
            }
        });
    }

    private void onLoginButtonClicked() {
        Editable usernameEditable = usernameTextField.getText();
        Editable passwordEditable = passwordTextField.getText();

        if (usernameEditable == null || passwordEditable == null) {
            showLoginError();
            return;
        }

        doLogin(usernameEditable.toString(), passwordEditable.toString());
    }

    private void doLogin(String username, String password) {
        Properties properties = new Properties();
        properties.setProperty(ModelManager.ATTR_LOGIN_USER, username);
        properties.setProperty(ModelManager.ATTR_LOGIN_PASS, password);
        properties.setProperty(ModelManager.ATTR_SERVICE_URL, LOGIN_URL);
        properties.setProperty(ModelManager.ATTR_REQUIRE_SELF_CERT, "TRUE");

        LoginTask loginTask = new LoginTask(this, properties);
        loginTask.execute();
    }

    private void showLoginError() {
        Toast.makeText(this, getString(R.string.login_error_message), Toast.LENGTH_SHORT).show();
    }
}
