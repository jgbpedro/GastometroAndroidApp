package br.com.gastometro.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO ="gastometro.db";
    private static final int VERSAO = 2;


    public DatabaseHelper(Context context){
        super(context,NOME_BANCO, null, VERSAO );
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table categoria( _id INTEGER PRIMARY KEY,nome TEXT );");
        db.execSQL("create table tipo_lancamento( _id INTEGER PRIMARY KEY, nome TEXT);");

        db.execSQL("create table lancamentos( _id INTEGER PRIMARY KEY," +
                "idTipo INTEGER NOT NULL ," +
                "nome TEXT ," +
                "idCategoria INTEGER NOT NULL ," +
                "valor DOUBLE ," +
                "data DATE, " +
                "FOREIGN KEY(idTipo) REFERENCES tipo_lancamento(_id)," +
                "FOREIGN KEY(idCategoria) REFERENCES categoria(_id));");

        db.execSQL("insert into tipo_lancamento(nome) values('Despesa');");
        db.execSQL("insert into tipo_lancamento(nome) values('Entrada');");

        db.execSQL("insert into categoria(nome) values('Alimentação');");
        db.execSQL("insert into categoria(nome) values('Lazer');");
        db.execSQL("insert into categoria(nome) values('Moradia');");
        db.execSQL("insert into categoria(nome) values('Transporte');");
        db.execSQL("insert into categoria(nome) values('Saúde');");
        db.execSQL("insert into categoria(nome) values('Outros');");

        atualizacaoV2(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion <2) {
            atualizacaoV2(db);
        }
    }

    private void atualizacaoV2 (SQLiteDatabase db){
        //Atualização da versão 2 do banco
        db.execSQL("ALTER TABLE categoria ADD COLUMN icone TEXT;");

        db.execSQL("UPDATE categoria set icone='ic_alimentacao' where nome='Alimentação';");
        db.execSQL("UPDATE categoria set icone='ic_lazer' where nome='Lazer';");
        db.execSQL("UPDATE categoria set icone='ic_moradia' where nome='Moradia';");
        db.execSQL("UPDATE categoria set icone='ic_transporte' where nome='Transporte';");
        db.execSQL("UPDATE categoria set icone='ic_saude' where nome='Saúde';");
        db.execSQL("UPDATE categoria set icone='ic_outros' where nome='Outros';");
    }
}