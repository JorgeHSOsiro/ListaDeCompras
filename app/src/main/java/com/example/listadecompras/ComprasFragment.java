package com.example.listadecompras;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.listadecompras.adapter.ProdutoAdapter;
import com.example.listadecompras.database.AppDatabase;
import com.example.listadecompras.interfaces.ProdutoListener;
import com.example.listadecompras.model.Produto;
import com.example.listadecompras.util.Constantes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class ComprasFragment extends Fragment implements ProdutoListener {

    private FloatingActionButton fab;
    private ProdutoAdapter produtoAdapter;
    private int listaComprasId;
    private AppDatabase db;


    public ComprasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_compras, container, false);

        fab = view.findViewById(R.id.fab_produto);
        db = Room.databaseBuilder(getContext(), AppDatabase.class, AppDatabase.DATABASE_NAME).build();

        Bundle bundle = getArguments();

        if (bundle != null){
            listaComprasId = bundle.getInt(Constantes.LISTA_ID);
        }

        produtoAdapter = new ProdutoAdapter(this);

        setupRecyclerView(view);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirDialog();
            }
        });

        exibirProdutos();
        return view;
    }

    private void exibirProdutos() {
        db.produtoDao()
                .getListaProdutosByListaCompras(listaComprasId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(listaProdutos -> produtoAdapter.atualizarProdutos(listaProdutos),
                        throwable -> throwable.printStackTrace());

    }

    private void exibirDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_novo_produto);

        Button button = dialog.findViewById(R.id.ok_produto_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Produto produto = new Produto();

                TextInputEditText descricaoEditText = dialog.findViewById(R.id.descricao_produto_edit_text);
                String descricao = descricaoEditText.getEditableText().toString();
                produto.setDescricao(descricao);

                TextInputEditText quantidadeEditText = dialog.findViewById(R.id.quantidade_produto_edit_text);
                String quantidadeString = quantidadeEditText.getEditableText().toString();
                float quantidade = Float.parseFloat(quantidadeString);
                produto.setQuantidade(quantidade);

                TextInputEditText unidadeEdiText = dialog.findViewById(R.id.unidade_produto_edit_text);
                String unidade = unidadeEdiText.getEditableText().toString();
                produto.setUnidade(unidade);

                produto.setComprado(false);

                produto.setListaComprasId(listaComprasId);

                gravarProduto(produto);

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void gravarProduto(Produto produto) {
        Completable.fromAction(() -> db.produtoDao().inserir(produto))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(() -> exibirProdutos());

    }

    private void setupRecyclerView(View view) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());

        RecyclerView recyclerView = view.findViewById(R.id.produtos_recycler_view);

        recyclerView.setAdapter(produtoAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void atualizarProduto(Produto produto){
        Completable.fromAction(()-> db.produtoDao().update(produto))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(() -> exibirProdutos());
    }

    @Override
    public void atualizarProdutoComprado(Produto produto) {
        atualizarProduto(produto);
    }
}
