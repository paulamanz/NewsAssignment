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
    //public static String errorLoging = null;

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

    // recogida de datos de los usuarios
    private void setUpViews() {
        usernameTextField = findViewById(R.id.usernameInput);
        passwordTextField = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
    }

    // Obtencion de los datos proporcionados por el usuario
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

    // Funcion para detectar errores previos al login,
    // generar feedback correspondiente al usuario
    // y en caso de error, evitar lanzar la tarea
    private void onLoginButtonClicked() {
        Editable usernameEditable = usernameTextField.getText();
        Editable passwordEditable = passwordTextField.getText();

        if (usernameEditable.length() == 0 && passwordEditable.length() == 0){
            showLoginError(3);
            return;
        } else{
            if (usernameEditable.length() == 0){
                showLoginError(1);
                return;
            }
            if (passwordEditable.length() == 0){
                showLoginError(2);
                return;
            }
        }
        doLogin(usernameEditable.toString(), passwordEditable.toString());
    }


    // Funcion para establecer las propiedades necesarias para el login
    // y ejecutar su correspondiente tarea asincrona
    private void doLogin(String username, String password) {
        Properties properties = new Properties();
        properties.setProperty(ModelManager.ATTR_LOGIN_USER, username);
        properties.setProperty(ModelManager.ATTR_LOGIN_PASS, password);
        properties.setProperty(ModelManager.ATTR_SERVICE_URL, LOGIN_URL);
        properties.setProperty(ModelManager.ATTR_REQUIRE_SELF_CERT, "TRUE");

        LoginTask loginTask = new LoginTask(this, properties);
        loginTask.execute();
    }


    // Funcion dedicada a mostrar errores segun que campo sea erroneo
    private void showLoginError(int code) {
        switch (code){
            case 1: Toast.makeText(this, getString(R.string.login_error_message_1), Toast.LENGTH_SHORT).show();
                    break;
            case 2: Toast.makeText(this, getString(R.string.login_error_message_2), Toast.LENGTH_SHORT).show();
                    break;
            case 3: Toast.makeText(this, getString(R.string.login_error_message_3), Toast.LENGTH_SHORT).show();
                    break;
        }
    }
}
