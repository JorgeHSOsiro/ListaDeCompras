package com.example.listadecompras;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.listadecompras.adapter.ListaSalvaAdapter;
import com.example.listadecompras.interfaces.FragmentActionsListener;
import com.example.listadecompras.interfaces.ListaComprasListener;
import com.example.listadecompras.interfaces.NovaListaListener;
import com.example.listadecompras.model.ListaCompras;
import com.example.listadecompras.model.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaSalvaFragment extends Fragment implements ListaComprasListener {

    private RecyclerView recyclerView;
    private FragmentActionsListener fragmentActionsListener;
    private FloatingActionButton floatingActionButton;

    public ListaSalvaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_salva, container, false);
        recyclerView = view.findViewById(R.id.lista_salva_recycler_view);
        floatingActionButton = view.findViewById(R.id.fab_lista_salva);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container_lista_salva_id, new NovaListaComprasFragment(),"POPUP NOVA LISTA");
                fragmentTransaction.commit();
            }
        });

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

        Produto produto1 = new Produto();
        produto1.setDescricao("Cebola");
        produto1.setUnidade("kg");
        produto1.setQuantidade(3);
        listaCompras.getListaProdutos().add(produto1);

        Produto produto2 = new Produto();
        produto2.setDescricao("Sabão em pó");
        produto2.setQuantidade(1);
        produto2.setUnidade("caixa");
        listaCompras.getListaProdutos().add(produto2);

        ListaCompras listaCompras1 = new ListaCompras();
        listaCompras1.setNome("Churrasco");
        listaComprasList.add(listaCompras1);

        Produto produto3 = new Produto();
        produto3.setDescricao("Carvão");
        produto3.setQuantidade(2);
        produto3.setUnidade("pacote");

        listaCompras1.getListaProdutos().add(produto3);

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

        Bundle bundle = new Bundle();
        bundle.putSerializable("LISTA", listaCompras);

        comprasFragment.setArguments(bundle);

        fragmentActionsListener.subsituirFragment(comprasFragment);
    }


}
