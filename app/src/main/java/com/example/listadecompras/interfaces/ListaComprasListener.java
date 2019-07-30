package com.example.listadecompras.interfaces;

import com.example.listadecompras.model.ListaCompras;

public interface ListaComprasListener {

    void deletarListaCompras(ListaCompras listaCompras);

    void onListaDeComprasClicado(ListaCompras listaCompras);
}
