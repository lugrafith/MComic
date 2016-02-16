package mcomic.com.core;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;

import mcomic.com.core.exceptions.AplicationException;
import mcomic.com.core.service.ServiceCentralManga;
import mcomic.com.model.Manga;
import mcomic.com.view.component.adapter.MangaAdapter;
import mcomic.com.view.component.itemAdapter.ViewMangaItem;

/**
 * Created by lu_gr on 31/01/2016.
 */
public class ServiceTaskMangaInfo extends AsyncTask<MangaAdapter, ViewMangaItem, Void> {

    private Activity activity;

    public ServiceTaskMangaInfo(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(MangaAdapter... params) {
        MangaAdapter mangaAdapter = params[0];

        for (int x = 0; x < mangaAdapter.getCount(); x++){
            if(!this.isCancelled()){
                ViewMangaItem item = mangaAdapter.getItem(x);
                try {
                    new ServiceCentralManga().completeInfoManga(item.getManga());
                    publishProgress(item);
                } catch (AplicationException e) {
                    e.printStackTrace();
                }
            }else {
                break;
            }
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onProgressUpdate(ViewMangaItem... values) {
        ViewMangaItem item = values[0];
        if(item.getBackground() != null){
            Drawable backgroundF = new BitmapDrawable(
                    activity.getResources(),
                    item.getManga().getImageCover());
            item.getBackground().setBackground(backgroundF);
        }else {
            System.out.println("Erro!");
        }
        if(item.getProgressBar() != null){
            item.getProgressBar().setVisibility(View.INVISIBLE);
        }
    }
}
