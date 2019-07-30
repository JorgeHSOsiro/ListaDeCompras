package com.example.listadecompras;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.listadecompras.adapter.ListaSalvaAdapter;
import com.example.listadecompras.database.AppDatabase;
import com.example.listadecompras.interfaces.FragmentActionsListener;
import com.example.listadecompras.interfaces.ListaComprasListener;
import com.example.listadecompras.model.ListaCompras;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaSalvaFragment extends Fragment implements ListaComprasListener {

    private RecyclerView recyclerView;
    private FragmentActionsListener fragmentActionsListener;
    private FloatingActionButton floatingActionButton;
    private AppDatabase db;
    private ListaSalvaAdapter listaSalvaAdapter;
    private ProgressBar progressBar;

    public ListaSalvaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_salva, container, false);

        db = Room.databaseBuilder(getContext(),
                AppDatabase.class, AppDatabase.DATABASE_NAME).build();

        recyclerView = view.findViewById(R.id.lista_salva_recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        listaSalvaAdapter = new ListaSalvaAdapter(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(listaSalvaAdapter);
        recyclerView.setLayoutManager(layoutManager);

        floatingActionButton = view.findViewById(R.id.fab_lista_salva);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirDialog();
            }
        });

        exibirListaCompras();

        return view;
    }

    @Override
    public void deletarListaCompras(ListaCompras listaCompras){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Lista compras App");
        builder.setMessage("Deseja deletar a lista" + listaCompras.getNome()+"?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Completable.fromAction(() -> db.listaComprasDao().delete(listaCompras))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe();
                exibirListaCompras();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }



    private void exibirListaCompras() {
        progressBar.setVisibility(View.VISIBLE);
        db.listaComprasDao()
                .getAll()
                .delaySubscription(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(listaComprasList -> {
                    listaSalvaAdapter.atualizarLista(listaComprasList);
                    progressBar.setVisibility(View.GONE);
                }, throwable -> throwable.printStackTrace());

    }

    private void exibirDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment_nova_lista_compras);
        dialog.show();

        Button okDialogButton = dialog.findViewById(R.id.nova_lista_ok_button);
        okDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListaCompras listaCompras = new ListaCompras();

                TextInputEditText nomeTextInputEditText = dialog.findViewById(R.id.nome_lista_edit_text);
                String nomeDigitado = nomeTextInputEditText.getEditableText().toString();

                listaCompras.setNome(nomeDigitado);
                gravarListaCompras(listaCompras);

                dialog.dismiss();
            }
        });
    }

    private void gravarListaCompras(final ListaCompras listaCompras) {
        Completable.fromAction(() -> db.listaComprasDao().insertAll(listaCompras))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(() -> exibirListaCompras());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        fragmentActionsListener =(FragmentActionsListener) context;

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
