package com.example.listadecompras.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadecompras.R;
import com.example.listadecompras.interfaces.ListaComprasListener;
import com.example.listadecompras.model.ListaCompras;

import java.util.ArrayList;
import java.util.List;

public class ListaSalvaAdapter extends RecyclerView.Adapter<ListaSalvaAdapter.ViewHolder> {
    private List<ListaCompras> listaComprasList;
    private ListaComprasListener listaComprasListener;

    public ListaSalvaAdapter( ListaComprasListener listaComprasListener){
        this.listaComprasList = new ArrayList<>();
        this.listaComprasListener = listaComprasListener;
    }

    public void atualizarLista(List<ListaCompras> listaComprasList){
        this.listaComprasList = listaComprasList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_salva_celula, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ListaCompras listaCompras = listaComprasList.get(i);
        viewHolder.setupListaCompras(listaCompras);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaComprasListener.onListaDeComprasClicado(listaCompras);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaComprasList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView listaSalvaTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            listaSalvaTextView = itemView.findViewById(R.id.lista_salva_text_view);

        }

        public void setupListaCompras(ListaCompras listaCompras){
            listaSalvaTextView.setText(listaCompras.getNome());
        }
    }
}
