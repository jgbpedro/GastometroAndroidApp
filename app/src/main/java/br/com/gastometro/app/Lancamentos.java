package br.com.gastometro.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Lancamentos {

    private int id;
    private String nome;
    private String data;
    private Double valor;
    private Categoria categoria;
    private TipoLancamento tipoLancamento;

    private int idCategoria;
    private int idTipo;


    @Override
    public String toString() {

        NumberFormat f = NumberFormat.getCurrencyInstance();
        return String.format("%s - %s", nome, f.format(valor));
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public static Lancamentos buscarPorId(Context context, int id){

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        //select * from lancamentos where _id = " + id_lancamento

        Cursor cursor = db.rawQuery("SELECT * FROM lancamentos  WHERE _id = ?;" ,
                new String[] { String.valueOf(id)});



        if(cursor.getCount() > 0){

            cursor.moveToFirst();

            Lancamentos lanca = new Lancamentos();

            lanca.setId(cursor.getInt(0));
            lanca.setIdTipo(cursor.getInt(1));
            lanca.setNome(cursor.getString(2));
            lanca.setIdCategoria( cursor.getInt(3));
            lanca.setValor(cursor.getDouble(4));
            lanca.setData(cursor.getString(5));

            cursor.close();

            return lanca;
        }

        return null;

    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    /*public static List<Lancamentos> resumo(Context context){
        List<Lancamentos> lstResumo = new ArrayList<>();
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM lancamentos l inner join categoria c " +  " on c._id = l.idCategoria", null);

        cursor.moveToFirst();

        for(int i=0; i<cursor.getCount();i++){
            Lancamentos lres = new Lancamentos();
            lres.set
        }
    }*/

    public static List<Lancamentos> obterTodos(Context context) {

        List<Lancamentos> lstLancamentos = new ArrayList<>();

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM lancamentos l inner join categoria c on c._id = l.idCategoria ORDER BY data;", null);

        cursor.moveToFirst();


        for(int i=0 ; i < cursor.getCount(); i++){

            Lancamentos lan = new Lancamentos();
            lan.setId(cursor.getInt(0));
            lan.setIdTipo(cursor.getInt(1));
            lan.setNome(cursor.getString(2));
            lan.setIdCategoria(cursor.getInt(3));
            lan.setValor(cursor.getDouble(4));
            lan.setData(cursor.getString(5));

            Categoria cat = new Categoria();
            cat.setId(cursor.getInt(6));
            cat.setNome(cursor.getString(7));
            cat.setIcone(cursor.getString(8));

            lan.setCategoria(cat);//setando a categoria do lancamento

            lstLancamentos.add(lan);

            cursor.moveToNext();
        }

        cursor.close();

        return lstLancamentos;
    }

    public static long atualizar(Context context, Lancamentos atualizado) {

        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();

        ContentValues v = new ContentValues();

        v.put("nome", atualizado.getNome());
        v.put("valor", atualizado.getValor());
        v.put("data", atualizado.getData());
        v.put("idCategoria", atualizado.getIdCategoria());
        v.put("idTipo", atualizado.getIdTipo());

       return db.update("lancamentos",v," _id = ?", new String[] {atualizado.getId() +""});
    }

    public static long deletar( Context context, int id_lancamento) {

        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();

       return  db.delete("lancamentos","_id=?", new String[]{id_lancamento + ""});
    }
}