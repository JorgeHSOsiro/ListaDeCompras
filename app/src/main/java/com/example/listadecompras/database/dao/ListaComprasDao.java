package com.example.listadecompras.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.listadecompras.model.ListaCompras;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ListaComprasDao {

    @Query("SELECT * FROM listacompras")
    Flowable<List<ListaCompras>> getAll();

    @Insert
    void  insertAll(ListaCompras... listaCompras);

    @Delete
    void delete(ListaCompras listaCompras);
}
