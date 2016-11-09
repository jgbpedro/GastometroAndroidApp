//exibição do item da lista
//Qualquer posição da lista, quanso clicada da um get no ID, que por sua vz será usado na tela principal para exibição
package br.com.gastometro.app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Lancamento {
    private int id;
    private String nome;
    private String data;
    private double valor;
    private Categoria categoria;
    private TipoLancamento tipoLancamento;

    private int idCategoria;
    private int idTipo;

    @Override
    public String toString() {
        NumberFormat f = NumberFormat.getCurrencyInstance();
        return String.format("%s - R$%f", nome, f.format(valor));
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
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

    public static Lancamento buscarPorId(Context context, int id){

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        //select * from lancamentos where _id = " + id_lancamento

        Cursor cursor = db.rawQuery("SELECT * FROM lancamentos WHERE _id = ? " , new String[] { String.valueOf(id)});

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            Lancamento lanca = new Lancamento();

            lanca.setId(cursor.getInt(0));
            lanca.setNome(cursor.getString(2));
            lanca.setValor(cursor.getDouble(4));
            lanca.setIdCategoria( cursor.getInt(3));
            lanca.setIdTipo(cursor.getInt(1));

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

    public static List<Lancamento> obterTodos(Context context) {
        List<Lancamento> lstLancamentos = new ArrayList<>();

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();//retorna un cursor

        Cursor cursor = db.rawQuery("SELECT * FROM lancamentos l INNER JOIN categoria c ON c._id = l.idCategoria ORDER BY l.data;", null);//cursor ue irá selecionar todos os itens já adicionados a lista lancamentos
        cursor.moveToFirst();

        for(int i=0; i<cursor.getCount(); i++){//loop enquanto houver item o cursor contará e moverá para o próximo
            Lancamento lan = new Lancamento();
            lan.setId(cursor.getInt(0));
            lan.setNome(cursor.getString(2));
            lan.setData(cursor.getString(5));
            lan.setValor(cursor.getDouble(4));
            lan.setIdCategoria(cursor.getInt(3));
            lan.setIdTipo(cursor.getInt(1));

            Categoria cat = new Categoria();
            cat.setId(cursor.getInt(6));//6 e 7 por causa do INNER JOIN [
            cat.setNome(cursor.getString(7));

            lan.setCategoria(cat);//setando a categoria do lancamento

            lstLancamentos.add(lan);//irá adicionar a lista 'lstLancamento'

            cursor.moveToNext();
        }
        cursor.close();//quando acabar cursor para

        return lstLancamentos;
    }
}