package mcomic.com.view.activity.fragment;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mcomic.com.core.Aplication;
import mcomic.com.core.service.ServiceCentralManga;
import mcomic.com.mcomic.R;
import mcomic.com.model.Capitulo;
import mcomic.com.model.Manga;
import mcomic.com.model.Page;

/**
 * Created by lu_gr on 08/02/2016.
 */
public class ScreenSlidePagerActivity extends FragmentActivity {

    private PagerAdapter mPagerAdapter;

    private ViewPager mPager;

    private Capitulo capitulo;

    private Manga manga;


    private ProgressBar progressBarLoad;

    private TextView textViewPageInfo;

    public ScreenSlidePagerActivity() {
        super();
    }

    SearchPages searchPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        manga = (Manga) getIntent().getSerializableExtra(Aplication.MANGA);
        capitulo = (Capitulo)getIntent().getSerializableExtra(Aplication.CAPTIULO);

        progressBarLoad = (ProgressBar) findViewById(R.id.progressBar_loading);
        textViewPageInfo = (TextView) findViewById(R.id.textView_pageInfo);
        progressBarLoad.getIndeterminateDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

        textViewPageInfo.setText("aguarde...");
        searchPages = new SearchPages();
        searchPages.execute();
    }

    @Override
    public void onBackPressed() {
        searchPages.cancel(true);
        super.onBackPressed();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(capitulo.getPages().size() > position){
                return new ScreenSlidePageFragment(capitulo, capitulo.getPages().get((position)));
            }
            return new ScreenSlidePageFragment(capitulo, null);
        }

        @Override
        public int getCount() {
            return capitulo.getTotalPaginas() + 1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

    }


    private class SearchPages extends AsyncTask<Capitulo, Page, Void>{

        //private int pageNumber;

        public SearchPages(){

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Capitulo... params) {
            ServiceCentralManga serviceCentralManga = new ServiceCentralManga();
            try {
                if(capitulo.getPages().size() == 0){
                    capitulo.setTotalPaginas(serviceCentralManga.getMaxPages(manga, capitulo));
                }
                for(int p = 1; p <= capitulo.getTotalPaginas(); p++){
                    if(!this.isCancelled()){
                        publishProgress(serviceCentralManga.getPage(manga, capitulo, p));
                    } else {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Page... values) {
            capitulo.getPages().add(values[0]);
            if(progressBarLoad.getVisibility() == View.VISIBLE){
                progressBarLoad.setVisibility(View.INVISIBLE);
                textViewPageInfo.setVisibility(View.INVISIBLE);
            }
            if(capitulo.getPages().size() == 2){
                mPager = (ViewPager) findViewById(R.id.pager);
                mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
                mPager.setAdapter(mPagerAdapter);
            }

            if(mPagerAdapter != null){
                mPagerAdapter.notifyDataSetChanged();
            }
        }
    }
}
