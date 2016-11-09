package br.com.gastometro.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by sn1041520 on 04/10/2016.
 */
public class LancamentosAdapter extends ArrayAdapter<Lancamentos> {



    int resource;
    public LancamentosAdapter(Context context, int resource,
                              List<Lancamentos> lstLancamentos){

        super(context, resource, lstLancamentos);
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){

            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(resource, null);
        }

        Lancamentos lancamento = getItem(position);

        ImageView img_list_item = (ImageView)v.findViewById(R.id.img_list_item);
        TextView txt_nome_item = (TextView) v.findViewById(R.id.txt_nome_item);
        TextView txt_categoria_item = (TextView) v.findViewById(R.id.txt_categoria_item);
        TextView txt_valor_item = (TextView) v.findViewById(R.id.txt_valor_item);


        txt_nome_item.setText(lancamento.getNome());

        //setar categoria
        txt_categoria_item.setText(lancamento.getCategoria().getNome());

        Locale ptBr = new Locale("pt", "BR");
        NumberFormat f = NumberFormat.getCurrencyInstance(ptBr);

        txt_valor_item.setText( f.format(lancamento.getValor()));

        String icone = lancamento.getCategoria().getIcone();

        int idIcone = getContext().getResources().getIdentifier(icone, "drawable", getContext().getPackageName());

        img_list_item.setImageResource(idIcone);


        return v;
    }

}