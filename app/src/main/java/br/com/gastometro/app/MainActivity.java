package br.com.gastometro.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Context context;


    ListView list_view_lancamentos;
    TextView text_saldo;

    LancamentosAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //colocar o icone na barra de ferramentas
        toolbar.setNavigationIcon(R.mipmap.ic_launcher_gastometro);

        setSupportActionBar(toolbar);
        context = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Abrir tela de inserir
                Intent intent = new Intent(context, InserirActivity.class);
                startActivity(intent);

            }
        });

        text_saldo = (TextView) findViewById(R.id.text_saldo);
        list_view_lancamentos = (ListView) findViewById(R.id.list_view_lancamentos);
        preencherLista();

        list_view_lancamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Lancamentos item = adapter.getItem(i);

                Intent intent = new Intent(context, DetalhesActivity.class);
                intent.putExtra("id_lancamento", item.getId());

                startActivity(intent);

                //Toast.makeText(context, "Cliquei no "+ item.getNome(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_resumo, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()){
            case R.id.action_deletar:
                //fazer o codigo de deletar
                //Toast.makeText(context, "Cliquei no resumo", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, ResumoActivity.class);
                startActivity(intent);
                break;
            case R.id.action_sobre:
                intent = new Intent(this, SobreActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void preencherLista(){

        List<Lancamentos> lstLancamentos= Lancamentos.obterTodos(context);

        adapter =
                new LancamentosAdapter(
                context,
                R.layout.list_view_item,
                lstLancamentos);


        list_view_lancamentos.setAdapter(adapter);


        double entrada=0, saida=0;
        for (Lancamentos item : lstLancamentos){

            if(item.getIdTipo() ==1 ){
                //despesa
                saida += item.getValor();
            }else{
                entrada += item.getValor();
            }
        }

        double saldo = entrada - saida;

        Locale ptBr = new Locale("pt", "BR");
        NumberFormat f = NumberFormat.getCurrencyInstance(ptBr);

        text_saldo.setText( f.format(saldo));
    }
}