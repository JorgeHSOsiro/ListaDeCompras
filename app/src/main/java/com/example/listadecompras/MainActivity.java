package com.example.listadecompras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pegar a intent
        Intent intent = getIntent();

        // Pegar o bundle da intent
        Bundle bundle = intent.getExtras();

        // Pegar no bundle o valor que está guardado na chave EMAIL
        String email = bundle.getString("EMAIL");

        // Buscar elemento na tela por id
       // TextView emailText = findViewById(R.id.email_text);

        // Alterar o texto
        //emailText.setText("Usuário: "+email);

        Button comprasButton = findViewById(R.id.compras_button);
        comprasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirComprasFragment();
            }
        });

        Button listaSalvaButton = findViewById(R.id.lista_button);
        listaSalvaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exbirListasFragment();
            }
        });

        Button promocaoButton = findViewById(R.id.promocoes_button);
        promocaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirPromocoesFragment();
            }
        });
    }

    private void exibirPromocoesFragment() {
        setupFragment(new PromocaoFragment());
    }

    private void exbirListasFragment() {
        setupFragment(new ListaSalvaFragment());

    }

    private void exibirComprasFragment() {
        setupFragment(new ComprasFragment());
    }

    private void setupFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_id, fragment);
        transaction.commit();
    }
}
