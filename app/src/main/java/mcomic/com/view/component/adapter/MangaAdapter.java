package mcomic.com.view.component.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mcomic.com.core.Aplication;
import mcomic.com.core.ServiceTaskMangaInfo;
import mcomic.com.mcomic.R;
import mcomic.com.model.Manga;
import mcomic.com.view.component.itemAdapter.ViewMangaItem;

/**
 * Created by lu_gr on 31/01/2016.
 */
public class MangaAdapter extends BaseAdapter {


    private Activity activity;
    private List<ViewMangaItem> viewMangaItems;
    private boolean cancelSearch;

    public MangaAdapter(Activity activity, List<Manga> mangaList){
        this.activity = activity;
        viewMangaItems = new ArrayList<>();
        if(mangaList != null){
            for (Manga manga : mangaList ){
                ViewMangaItem viewMangaItem = new ViewMangaItem(null, null, null);
                viewMangaItem.setManga(manga);
                viewMangaItems.add(viewMangaItem);
            }
        }
    }


    @Override
    public int getCount() {
        return viewMangaItems.size();
    }

    @Override
    public ViewMangaItem getItem(int position) {
        return viewMangaItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return R.layout.grid_item;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.grid_item, parent, false);

        viewMangaItems.get(position).setBackground((RelativeLayout) view.findViewById(R.id.background));
        viewMangaItems.get(position).setTextView((TextView) view.findViewById(R.id.textView_mangaTitle));
        viewMangaItems.get(position).setProgressBar((ProgressBar) view.findViewById(R.id.progressBar_loadInfo));
        viewMangaItems.get(position).getProgressBar().getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        viewMangaItems.get(position).getTextView().setText(viewMangaItems.get(position).getManga().getTitle());

        if(viewMangaItems.get(position).getManga().getImageCover() != null){
            Drawable backgroundF = new BitmapDrawable(activity.getResources(),  viewMangaItems.get(position).getManga().getImageCover());
            viewMangaItems.get(position).getBackground().setBackground(backgroundF);
            viewMangaItems.get(position).getProgressBar().setVisibility(View.INVISIBLE);
        }

        return view;
    }
}
