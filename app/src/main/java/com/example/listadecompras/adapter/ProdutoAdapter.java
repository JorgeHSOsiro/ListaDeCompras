package com.example.listadecompras.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadecompras.R;
import com.example.listadecompras.interfaces.ProdutoListener;
import com.example.listadecompras.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder>  {

    private List<Produto> listaDeProdutos;
    private ProdutoListener produtoListener;

    public ProdutoAdapter(ProdutoListener produtoListener) {
        this.listaDeProdutos = new ArrayList<>();
        this.produtoListener = produtoListener;
    }

    public void atualizarProdutos(List<Produto> listaDeProdutos){
        this.listaDeProdutos = listaDeProdutos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.produto_celula, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    Produto produto = listaDeProdutos.get(i);
    viewHolder.setupProduto(produto);
    }

    @Override
    public int getItemCount() {
        return listaDeProdutos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView descricaoTextView;
        private TextView quantidadeTextView;
        private TextView unidadeTextView;
        private CheckBox compradoCheckbox;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            descricaoTextView = itemView.findViewById(R.id.descricao_text_view);
            quantidadeTextView = itemView.findViewById(R.id.quantidade_text_view);
            unidadeTextView = itemView.findViewById(R.id.unidade_text_view);
            compradoCheckbox = itemView.findViewById(R.id.comprado_checkbox);
        }
        private void setupProduto(final Produto produto){
            descricaoTextView.setText(produto.getDescricao());
            unidadeTextView.setText(produto.getUnidade());
            quantidadeTextView.setText(""+produto.getQuantidade());
            compradoCheckbox.setChecked(produto.isComprado());

            compradoCheckbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                    produto.setComprado(isChecked);
                    produtoListener.atualizarProdutoComprado(produto);
                }
            });
        }
    }
}
