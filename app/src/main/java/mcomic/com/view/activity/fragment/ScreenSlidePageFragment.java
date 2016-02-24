package mcomic.com.view.activity.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mcomic.com.core.Aplication;
import mcomic.com.mcomic.R;
import mcomic.com.model.Capitulo;
import mcomic.com.model.Manga;
import mcomic.com.model.Page;
import uk.co.senab.photoview.PhotoViewAttacher;


@SuppressLint("ValidFragment")
public class ScreenSlidePageFragment extends Fragment implements View.OnClickListener {


    private PhotoViewAttacher attacher;

    private ProgressBar progressBarImage;

    private Capitulo capitulo;

    private ImageView imageViewPage;

    private TextView pageInfo;

    private Page page;

    private ImageButton imageButtonNext;
    
    private TextView textViewInfoNext;

    private RelativeLayout relativeLayoutBoxNext;

    private boolean isLastPage;

    private boolean isLastCapter;


    private Manga manga;

    private ScreenSlidePagerActivity  activity;

    public ScreenSlidePageFragment(){
        super();
    }

    public ScreenSlidePageFragment(Capitulo capitulo, Page page) {
        this.page = page;
        this.capitulo = capitulo;
    }


    public ScreenSlidePageFragment(ScreenSlidePagerActivity activity, Manga manga, Capitulo capitulo, boolean isLastPage, boolean isLastCapter) {
        this.activity = activity;
        this.manga = manga;
        this.capitulo = capitulo;
        this.isLastPage = isLastPage;
        this.isLastCapter = isLastCapter;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page_fragment, container, false);
        relativeLayoutBoxNext = (RelativeLayout) rootView.findViewById(R.id.relativeLayout_container_next);
        imageButtonNext = (ImageButton) rootView.findViewById(R.id.button_next);
        textViewInfoNext = (TextView) rootView.findViewById(R.id.textView_next);
        progressBarImage = (ProgressBar) rootView.findViewById(R.id.progressBar_loadImage);
        progressBarImage.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        imageViewPage = (ImageView) rootView.findViewById(R.id.imageView_page);
        pageInfo = (TextView) rootView.findViewById(R.id.textView_pageInfo);
        if (page != null && page.getBitmapImage() != null){
            Display display = ((WindowManager) this.getContext().getSystemService(this.getContext().WINDOW_SERVICE)).getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            Bitmap btm;
            int larguraAD = 0;
            larguraAD =  metrics.widthPixels - page.getBitmapImage().getWidth();
            btm = Bitmap.createScaledBitmap(page.getBitmapImage(), page.getBitmapImage().getWidth() + larguraAD, page.getBitmapImage().getHeight() + larguraAD, true);
            pageInfo.setText(page.getPageNumber() + " / " + capitulo.getTotalPaginas());

            imageViewPage.setMinimumHeight(metrics.heightPixels);
            imageViewPage.setMinimumWidth(metrics.widthPixels);
            imageViewPage.setImageBitmap(btm);
            imageViewPage.setVisibility(View.VISIBLE);
            progressBarImage.setVisibility(View.INVISIBLE);

            attacher = new PhotoViewAttacher(imageViewPage);
        } else if(isLastPage){
            progressBarImage.setVisibility(View.INVISIBLE);
            relativeLayoutBoxNext.setVisibility(View.VISIBLE);
            if(isLastCapter){
                imageButtonNext.setVisibility(View.INVISIBLE);
                textViewInfoNext.setText("Parabéns! Acabou de ler o último capitulo de " + manga.getTitle());
            }else {
                imageButtonNext.setOnClickListener(this);
            }
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, ScreenSlidePagerActivity.class);
        intent.putExtra(Aplication.MANGA, manga);
        intent.putExtra(Aplication.CAPTIULO, capitulo);
        activity.startActivity(intent);
        activity.finish();
    }
}
