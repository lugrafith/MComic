package mcomic.com.view.component.itemAdapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mcomic.com.model.Manga;

/**
 * Created by lu_gr on 30/01/2016.
 */
public class ViewMangaItem {
    private RelativeLayout background;
    private TextView textView;
    private ProgressBar progressBar;

    private Manga manga;

    public ViewMangaItem(RelativeLayout background, TextView textView, ProgressBar progressBar) {
        this.background = background;
        this.textView = textView;
        this.progressBar = progressBar;
    }

    public RelativeLayout getBackground() {
        return background;
    }

    public void setBackground(RelativeLayout background) {
        this.background = background;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
