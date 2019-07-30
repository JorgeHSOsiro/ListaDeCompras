package com.example.listadecompras.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.listadecompras.database.dao.ListaComprasDao;
import com.example.listadecompras.database.dao.ProdutoDao;
import com.example.listadecompras.model.ListaCompras;
import com.example.listadecompras.model.Produto;

@Database(entities = {ListaCompras.class, Produto.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static String DATABASE_NAME = "digitalgrocerydb";

    public abstract ListaComprasDao listaComprasDao();
    public abstract ProdutoDao produtoDao();
}
