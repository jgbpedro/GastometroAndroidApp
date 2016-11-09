package br.com.gastometro.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class DetalhesActivity extends AppCompatActivity {

    TextView textview_nome, textview_data,textView_valor,
            textView_categoria, textView_tipo_lancamento;
    int id_lancamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textview_nome=(TextView) findViewById(R.id.textview_nome);
        textview_data=(TextView) findViewById(R.id.textview_data);
        textView_valor=(TextView) findViewById(R.id.textview_valor);
        textView_categoria=(TextView) findViewById(R.id.textview_categoria);
        textView_tipo_lancamento=(TextView) findViewById(R.id.textview_tipo_lancamento);

        Intent intent = getIntent();

        id_lancamento = intent.getIntExtra("id_lancamento",0);

        buscarLancamento(id_lancamento);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detalhes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_deletar:
                //fazer o codigo de deletar

                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar")
                        .setMessage("Tem certeza que deseja deletar este item?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deletar();
                            }
                        })
                        .setNegativeButton("Não", null).show();

                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    private void deletar(){

        long sucesso = Lancamentos.deletar(this, id_lancamento);

        if (sucesso != -1){
            Toast.makeText(this, "Deletado com sucesso", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, MainActivity.class));
        }else{
            Toast.makeText(this, "Problemas oa deletar, tente novamene", Toast.LENGTH_SHORT).show();
        }


    }

    private void buscarLancamento(int id_lancamento){


        int idCategoria=0;
        int idtipo=0;

        Lancamentos lancamento = Lancamentos.buscarPorId(this, id_lancamento);

        if(lancamento != null) {

            NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("pt","Br"));

            textview_nome.setText( lancamento.getNome()  );
            textview_data.setText( lancamento.getData() );
            textView_valor.setText(f.format(lancamento.getValor()));

            idCategoria = lancamento.getIdCategoria();
            idtipo = lancamento.getIdTipo();

        }




        Categoria c = Categoria.buscarCategoria(this,idCategoria);
        TipoLancamento tipo = TipoLancamento.buscarTipo(this,idtipo );


        if(c !=null){
            textView_categoria.setText(c.getNome());
        }

        if(tipo != null){
            textView_tipo_lancamento.setText(tipo.getNome());
        }


    }

    public void abrirEdicao(View view) {

        Intent intent = new Intent(this, EditarActivity.class);

        intent.putExtra("id_lancamento",id_lancamento);
        startActivity(intent);

    }
}
