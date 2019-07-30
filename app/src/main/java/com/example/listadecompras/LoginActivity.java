package com.example.listadecompras;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listadecompras.util.Constantes;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEDitText;
    private TextInputEditText senhaEditText;
    private Button loginButton;
    private TextView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEDitText = findViewById(R.id.login_email_edit_text);
        senhaEditText = findViewById(R.id.login_senha_edit_text);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.login_register_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logar();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irParaCadastro();

            }
        });
    }

    private void logar(){

        String emailDigitado = emailEDitText.getEditableText().toString();
        String senhaDigitada = senhaEditText.getEditableText().toString();

        emailEDitText.setError(null);
        senhaEditText.setError(null);

//        if(emailDigitado.equals("joji@gmail.com")&& senhaDigitada.equals("12345")){
            Intent intent = new Intent(this, HomeActivity.class);
        SharedPreferences preferences = getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constantes.EMAIL, emailDigitado);
        editor.commit();

            startActivity(intent);
//        }else{
//            emailEDitText.setError("Usuário e/ou senha incorreto(s)");
//            senhaEditText.setError("Usuário e/ou senha incorreto(s)");
//        }
    }
    private void irParaCadastro(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
