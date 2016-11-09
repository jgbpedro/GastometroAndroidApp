package br.com.gastometro.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ResumoActivity extends AppCompatActivity {
    ListView lst_todasCategorias;
    TextView text_despesa, text_entrada, text_saldo;


    CategoriasAdapter categorias_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//mostra o botão voltar de acordo com a hierarquia no arquivo 'manifest'

        text_entrada = (TextView) findViewById(R.id.text_entrada);
        text_despesa = (TextView) findViewById(R.id.text_despesa);
        text_saldo = (TextView) findViewById(R.id.text_saldo);
        lst_todasCategorias = (ListView) findViewById(R.id.lst_todasCategorias);



        List<Lancamento> lancamentos = Lancamento.obterTodos(this);

        double totalEntradas = 0;
        double totalSaidas = 0;
        for( int i = 0; i < lancamentos.size(); ++i ) {
            Log.i("tipo id: ", String.valueOf(lancamentos.get(i).getIdTipo()));

            if( lancamentos.get(i).getIdTipo() == 1 ) {
                totalSaidas += lancamentos.get(i).getValor();
            } else {
                totalEntradas += lancamentos.get(i).getValor();
            }
        }

        NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));


        text_entrada.setText( f.format( totalEntradas ) );
        text_despesa.setText( "(" + f.format( totalSaidas ) + ")" );
        text_saldo.setText( f.format( totalEntradas - totalSaidas ) );

        categorias_adapter = new CategoriasAdapter(this, R.layout.list_view_item_resumo, Categoria.obterTodos(this));
        lst_todasCategorias.setAdapter(categorias_adapter);

    }
}