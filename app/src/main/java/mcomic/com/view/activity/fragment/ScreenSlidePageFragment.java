package mcomic.com.view.activity.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import mcomic.com.mcomic.R;
import mcomic.com.model.Capitulo;
import mcomic.com.model.Page;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


@SuppressLint("ValidFragment")
public class ScreenSlidePageFragment extends Fragment {


    private PhotoViewAttacher attacher;

    private ProgressBar progressBarImage;

    private static Capitulo capitulo;

    private ImageView imageViewPage;

    private TextView pageInfo;

    private Page page;

    public ScreenSlidePageFragment(){
        super();
    }

    public ScreenSlidePageFragment(Capitulo capitulo, Page page) {
        this.page = page;
        this.capitulo = capitulo;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page_fragment, container, false);

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
        }

        return rootView;
    }
}
