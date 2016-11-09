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
public class TipoLancamento {

    private int id;
    private String nome;

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


    public static TipoLancamento buscarTipo(Context context, int idtipo) {

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tipo_lancamento  WHERE _id = ? ;",
                new String[]{ idtipo+"" });

        if(cursor.getCount() > 0){

            cursor.moveToFirst();

            TipoLancamento t = new TipoLancamento();
            t.setId(cursor.getInt(0));
            t.setNome(cursor.getString(1));

            cursor.close();

            return t;
        }

        return null;

    }
    public static void preencherSpinnerTipo(Context context, Spinner spinner, ArrayAdapter<TipoLancamento> adapter){
        //Readable database = Banco de leitura
        //para fazer selects
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tipo_lancamento;", null);

        cursor.moveToFirst();

        List<TipoLancamento> lstTipos= new ArrayList<>();
        for(int i = 0; i < cursor.getCount() ; i++){

            TipoLancamento tLancamento = new TipoLancamento();

            tLancamento.setNome(cursor.getString(1));
            tLancamento.setId(cursor.getInt(0));

            lstTipos.add(tLancamento);

            cursor.moveToNext();
        }
        cursor.close();

        if (adapter == null) {
            adapter = new ArrayAdapter<TipoLancamento>(
                    context, //contexto
                    android.R.layout.simple_spinner_item, //layout do spinner
                    lstTipos
            );
        }else{
            adapter.addAll(lstTipos);
        }

        adapter.setDropDownViewResource(android.R.
                layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);
    }

}
