package com.example.listadecompras;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.listadecompras.interfaces.NovaListaListener;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class NovaListaComprasFragment extends Fragment {

    private Button okButton;
    private TextInputEditText nomeListaEditText;
    private NovaListaListener novaListaListener;


    public NovaListaComprasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        novaListaListener = (NovaListaListener)context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nova_lista_compras, container, false);

        okButton = view.findViewById(R.id.nova_lista_ok_button);
        nomeListaEditText = view.findViewById(R.id.nome_lista_edit_text);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = nomeListaEditText.getEditableText().toString();
                novaListaListener.criarNovaLista(nome);
            }
        });

        return view;
    }

}
