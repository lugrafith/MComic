package mcomic.com.view.component.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mcomic.com.core.Aplication;
import mcomic.com.mcomic.R;
import mcomic.com.model.Capitulo;
import mcomic.com.model.Manga;
import mcomic.com.view.activity.MainActivity;
import mcomic.com.view.activity.fragment.ScreenSlidePagerActivity;
import mcomic.com.view.component.itemAdapter.ViewCapituloItem;

/**
 * Created by lu_gr on 07/02/2016.
 */
public class CapituloAdapter extends BaseAdapter {

    private MainActivity activity;

    private List<ViewCapituloItem> itens;

    private Manga manga;

    public CapituloAdapter(MainActivity activity, List<Capitulo> capitulos, Manga manga){
        this.activity = activity;
        this.manga = manga;
        itens = new ArrayList<>();
        for(int x = 0; x <= capitulos.size() -1; x++){
            itens.add(new ViewCapituloItem(capitulos.get(x)));
        }
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public ViewCapituloItem getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listview_item_capitulo, parent, false);
        }
        itens.get(position).setCheckBoxLido((CheckBox) view.findViewById(R.id.checkBox_lido));
        itens.get(position).setTextViewCapitulo((TextView) view.findViewById(R.id.textView_capitulo));
        itens.get(position).setButtomLer((ImageView) view.findViewById(R.id.imageButton_ler));
        itens.get(position).setProgressBar((ProgressBar) view.findViewById(R.id.progressBar));
        itens.get(position).getTextViewCapitulo().setText(itens.get(position).getCapitulo().getNome());
        itens.get(position).getButtomLer().setOnClickListener(getPagesCapitulo(itens.get(position)));

        itens.get(position).getProgressBar().getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        itens.get(position).getProgressBar().getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        itens.get(position).getProgressBar().setProgress(1);
        return view;
    }


    private View.OnClickListener getPagesCapitulo(final ViewCapituloItem viewCapituloItem){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ScreenSlidePagerActivity.class);
                intent.putExtra(Aplication.MANGA, manga);
                intent.putExtra(Aplication.CAPTIULO, viewCapituloItem.getCapitulo());
                activity.cancelServices();
                activity.startActivity(intent);
            }
        };
    }

}
