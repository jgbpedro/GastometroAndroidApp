package br.com.gastometro.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by 15251399 on 20/10/2016.
 */
public class CategoriasAdapter extends ArrayAdapter<Categoria> {

    private int resource;
    public CategoriasAdapter(Context context, int resource, List<Categoria> list) {
        super(context, resource, list);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if( v == null ) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(resource, null);
        }

        TextView titulo_categoria = (TextView) v.findViewById(R.id.txt_categoria_item);
        TextView valor_categoria = (TextView) v.findViewById(R.id.txt_total_categoria);

        Categoria c = getItem(position);
        titulo_categoria.setText( c.getNome() );
        NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        valor_categoria.setText( f.format(c.getTotal()) );

        return v;
    }
}
