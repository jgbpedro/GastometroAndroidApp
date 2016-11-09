package br.com.gastometro.app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sn1041520 on 22/09/2016.
 */
public class Categoria {

    private int id;
    private String nome;
    private String icone;

    private double total;

    @Override
    public String toString() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }


    public static Categoria buscarCategoria(Context context, int idCategoria){

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM categoria WHERE _id = ?;",
                new String[]{ idCategoria+"" });

        if(cursor.getCount() > 0){

            cursor.moveToFirst();

            Categoria c = new Categoria();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));

            cursor.close();

            return c;
        }

        return null;
    }

    public static void preencherSpinnerCategoria(Context context, Spinner spinner, ArrayAdapter<Categoria> adapter){

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM categoria;", null);
        cursor.moveToFirst();

        List<Categoria> lstCategorias= new ArrayList<>();
        for(int i = 0; i < cursor.getCount() ; i++){

            Categoria cat = new Categoria();
            cat.setId(cursor.getInt(0));
            cat.setNome(cursor.getString(1));

            lstCategorias.add(cat);

            cursor.moveToNext();
        }
        cursor.close();


        if (adapter == null) {
            adapter = new ArrayAdapter<Categoria>(
                    context, //contexto
                    android.R.layout.simple_spinner_item, //layout do spinner
                    lstCategorias
            );
        }else{
            adapter.addAll(lstCategorias);
        }
        adapter.setDropDownViewResource(android.R.
                layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);
    }


    public static List<Categoria> obterTodos(Context contexto) {
        SQLiteDatabase db = new DatabaseHelper(contexto).getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT c._id, c.nome, c.icone, SUM(l.valor) FROM categoria AS c LEFT JOIN lancamentos l ON c._id = l.idCategoria GROUP BY c._id;", null);
        cursor.moveToFirst();

        List<Categoria> lstCategorias = new ArrayList<>();
        for(int i = 0; i < cursor.getCount() ; i++){

            Categoria cat = new Categoria();
            cat.setId(cursor.getInt(0));
            cat.setNome(cursor.getString(1));
            cat.setIcone( cursor.getString(2) );
            cat.setTotal( cursor.getDouble(3) );

            lstCategorias.add(cat);

            cursor.moveToNext();
        }
        cursor.close();

        return lstCategorias;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
