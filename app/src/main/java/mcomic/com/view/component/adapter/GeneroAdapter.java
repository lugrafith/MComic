package mcomic.com.view.component.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mcomic.com.mcomic.R;
import mcomic.com.model.Genero;
import mcomic.com.view.component.itemAdapter.ViewGeneroItem;

/**
 * Created by lu_gr on 02/02/2016.
 */
public class GeneroAdapter extends BaseAdapter {

    private Activity activity;
    private List<ViewGeneroItem> itens;

    public GeneroAdapter(Activity activity, List<Genero> generos){
        this.activity = activity;
        itens = new ArrayList<>();
        for(Genero g : generos){
            itens.add(new ViewGeneroItem(g));
        }
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public ViewGeneroItem getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.grid_item_genero, parent, false);
            itens.get(position).setButton((Button) view.findViewById(R.id.button));
        }else {
            view = convertView;
        }

        itens.get(position).getButton().setText(itens.get(position).getGenero().getNome());

        return view;
    }
}
