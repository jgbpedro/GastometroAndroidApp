package br.com.gastometro.app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.Calendar;

public class InserirActivity extends AppCompatActivity {

    Spinner spn_tipo_lancamento, spn_categoria;
    EditText txt_nome;
    EditText txt_valor;
    static EditText txt_data;

    ArrayAdapter<TipoLancamento> tipoLancamentoAdapter;
    ArrayAdapter<Categoria> categoriaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spn_tipo_lancamento = (Spinner) findViewById(R.id.spn_tipo_lancamento);
        spn_categoria = (Spinner) findViewById(R.id.spn_categoria);
        txt_nome = (EditText) findViewById(R.id.txt_nome);
        txt_valor = (EditText) findViewById(R.id.txt_valor);
        txt_data = (EditText) findViewById(R.id.txt_data);


        txt_data.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    DialogFragment newFragment =
                            new DatePickerFragment().setTxt_data(txt_data);
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            }
        });


        txt_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment =
                        new DatePickerFragment().setTxt_data(txt_data);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });


        preencherSpinners();


        //Setando a data atual
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String dataAtual = String.format("%02d/%02d/%d", day, month+1, year);
        txt_data.setText(dataAtual);


    }

    public void inserirDados() {

        if (!validacao()) return;
        //banco de dados de escrita porque quero fazer um insert
        SQLiteDatabase db = new DatabaseHelper(this).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", txt_nome.getText().toString());
        valores.put("valor", txt_valor.getText().toString());
        valores.put("data", txt_data.getText().toString());


        //
        TipoLancamento tipo = (TipoLancamento) spn_tipo_lancamento.getSelectedItem();
        valores.put("idTipo", tipo.getId());

        //
        Categoria categoria = (Categoria) spn_categoria.getSelectedItem();
        valores.put("idCategoria", categoria.getId());

        //retorna -1 se deu algum erro
        long resultado = db.insert("lancamentos", null, valores);

        if (resultado != -1) {
            //chamar a tela principal quando o item for atualizado
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            Toast.makeText(this, "Inserido com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erro ao inserir", Toast.LENGTH_SHORT).show();
        }
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


    private void preencherSpinners() {

        TipoLancamento.preencherSpinnerTipo(this, spn_tipo_lancamento,tipoLancamentoAdapter );

        //Preencher spinner de categorias
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
                inserirDados();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}