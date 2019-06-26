package com.example.listadecompras;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.listadecompras.adapter.ListaSalvaAdapter;
import com.example.listadecompras.interfaces.FragmentActionsListener;
import com.example.listadecompras.interfaces.ListaComprasListener;
import com.example.listadecompras.model.ListaCompras;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaSalvaFragment extends Fragment implements ListaComprasListener {

    private RecyclerView recyclerView;
    private FragmentActionsListener fragmentActionsListener;

    public ListaSalvaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_salva, container, false);
        recyclerView = view.findViewById(R.id.lista_salva_recycler_view);

        ListaSalvaAdapter listaSalvaAdapter = new ListaSalvaAdapter(getListaComprasList(),this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setAdapter(listaSalvaAdapter);
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        fragmentActionsListener =(FragmentActionsListener) context;

    }

    private List<ListaCompras> getListaComprasList(){
        List<ListaCompras> listaComprasList = new ArrayList<>();
        ListaCompras listaCompras = new ListaCompras();

        listaCompras.setNome("Compras Supermercado");
        listaComprasList.add(listaCompras);

        ListaCompras listaCompras1 = new ListaCompras();
        listaCompras1.setNome("Churrasco");
        listaComprasList.add(listaCompras1);

        ListaCompras listaCompras2 = new ListaCompras();
        listaCompras2.setNome("Açougue");
        listaComprasList.add(listaCompras2);

        ListaCompras listaCompras3 = new ListaCompras();
        listaCompras3.setNome("Feira");
        listaComprasList.add(listaCompras3);

        ListaCompras listaCompras4 = new ListaCompras();
        listaCompras4.setNome("Pet Shop");
        listaComprasList.add(listaCompras4);

        return listaComprasList;
    }

    @Override
    public void onListaDeComprasClicado(ListaCompras listaCompras) {
        Fragment comprasFragment = new ComprasFragment();
        fragmentActionsListener.subsituirFragment(comprasFragment);
    }
}
