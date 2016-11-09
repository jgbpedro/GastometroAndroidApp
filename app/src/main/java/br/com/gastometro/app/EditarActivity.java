package br.com.gastometro.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class EditarActivity extends AppCompatActivity {

    Spinner spn_tipo_lancamento, spn_categoria;
    EditText txt_nome;
    EditText txt_valor;
    static EditText txt_data;
    int id_lancamento;

    Context context;

    ArrayAdapter<TipoLancamento> tipoLancamentoAdapter;
    ArrayAdapter<Categoria> categoriaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spn_tipo_lancamento = (Spinner) findViewById(R.id.spn_tipo_lancamento);
        spn_categoria = (Spinner) findViewById(R.id.spn_categoria);
        txt_nome = (EditText) findViewById(R.id.txt_nome);
        txt_valor = (EditText) findViewById(R.id.txt_valor);
        txt_data = (EditText) findViewById(R.id.txt_data);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment().setTxt_data(txt_data);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        txt_data.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    DialogFragment newFragment = new DatePickerFragment().setTxt_data(txt_data);
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            }
        });

        preencherSpinners();

        Intent intent = getIntent();

        id_lancamento = intent.getIntExtra("id_lancamento",0);

        Lancamentos  lancamento = Lancamentos.buscarPorId(this, id_lancamento);

        txt_nome.setText(lancamento.getNome());
        txt_valor.setText(lancamento.getValor()+"");
        txt_data.setText(lancamento.getData());

        for (int i=0; i < tipoLancamentoAdapter.getCount(); i++){

            TipoLancamento t = tipoLancamentoAdapter.getItem(i);

            if (t.getId() == lancamento.getIdTipo()){
                spn_tipo_lancamento.setSelection(i);
            }
        }

        for (int i=0;  i < categoriaAdapter.getCount(); i++){

            Categoria cat = categoriaAdapter.getItem(i);

            if (cat.getId() == lancamento.getIdCategoria()){
                spn_categoria.setSelection(i);
            }
        }

        //Preencher a data atual
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String dataAtual = String.format("%02d/%02d/%d", day, month+1, year);
        txt_data.setText(dataAtual);

    }

    private void atualizar(){


        if(!validacao()) return;

        Categoria catSelecionada=(Categoria)spn_categoria.getSelectedItem();
        TipoLancamento tipoSelecionado = (TipoLancamento) spn_tipo_lancamento.getSelectedItem();

        Lancamentos atualizado = new Lancamentos();

        atualizado.setData(txt_data.getText().toString());
        atualizado.setNome(txt_nome.getText().toString());
        atualizado.setIdCategoria(catSelecionada.getId());
        atualizado.setIdTipo(tipoSelecionado.getId());
        atualizado.setValor(Double.parseDouble(txt_valor.getText().toString()));
        atualizado.setId(id_lancamento);

        long atualizou = Lancamentos.atualizar(this,atualizado);

        if(atualizou != -1 ){
            //chamar a tela principal quando o item for atualizado
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Problemas ao atualizar, tente novamente", Toast.LENGTH_SHORT).show();
        }
    }

    private void preencherSpinners(){
        //Preencher spinner de tipos
        tipoLancamentoAdapter =  new ArrayAdapter<TipoLancamento>(this, android.R.layout.simple_spinner_item, new ArrayList<TipoLancamento>());
        TipoLancamento.preencherSpinnerTipo(this, spn_tipo_lancamento,tipoLancamentoAdapter );

        //Preencher spinner de categorias
        categoriaAdapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, new ArrayList<Categoria>());
        Categoria.preencherSpinnerCategoria(this, spn_categoria, categoriaAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inserir_atualizar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_salvar:
                atualizar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validacao() {

        txt_nome.setError(null);
        txt_valor.setError(null);
        txt_data.setError(null);

        if (txt_nome.getText().toString().isEmpty()) {
            txt_nome.requestFocus();
            txt_nome.setError("Preencha este campo!");
            return false;
        }
        if (txt_valor.getText().toString().isEmpty()) {
            txt_valor.requestFocus();
            txt_valor.setError("Preencha este campo!");
            return false;
        }
        if (txt_data.getText().toString().isEmpty()) {
            txt_data.requestFocus();
            txt_data.setError("Preencha este campo!");
            return false;
        }
        return true;
    }
}
