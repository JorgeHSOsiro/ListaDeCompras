package com.example.listadecompras;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.listadecompras.adapter.ProdutoAdapter;
import com.example.listadecompras.model.ListaCompras;
import com.example.listadecompras.model.Produto;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ComprasFragment extends Fragment {


    public ComprasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_compras, container, false);

        List<Produto> listaDeProdutos = getListaDeProdutos();

        Bundle bundle = getArguments();

        if(bundle != null) {
            ListaCompras listaCompras = (ListaCompras) bundle.getSerializable("LISTA");
            listaDeProdutos = listaCompras.getListaProdutos();
        }


        ProdutoAdapter produtoAdapter = new ProdutoAdapter(listaDeProdutos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());

        RecyclerView recyclerView = view.findViewById(R.id.compras_recycler_view);

        recyclerView.setAdapter(produtoAdapter);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    private List<Produto> getListaDeProdutos(){
        List<Produto> listaDeProdutos = new ArrayList<>();
        Produto produto1 = new Produto();
        produto1.setQuantidade(2);
        produto1.setUnidade("kg");
        produto1.setDescricao("Cebola");
        produto1.setComprado(false);
        listaDeProdutos.add(produto1);

        Produto produto2 = new Produto();
        produto2.setQuantidade(1);
        produto2.setUnidade("un.");
        produto2.setDescricao("sabão");
        produto2.setComprado(false);
        listaDeProdutos.add(produto2);

        Produto produto3 = new Produto();
        produto3.setQuantidade(1);
        produto3.setUnidade("kg");
        produto3.setDescricao("Alcatra");
        produto3.setComprado(false);
        listaDeProdutos.add(produto3);

        return  listaDeProdutos;

    }

}
