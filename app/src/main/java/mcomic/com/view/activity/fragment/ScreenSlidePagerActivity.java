package mcomic.com.view.activity.fragment;

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
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;

import mcomic.com.core.Aplication;
import mcomic.com.core.dao.FavoritoDao;
import mcomic.com.core.dao.MangaDao;
import mcomic.com.core.service.ServiceCentralManga;
import mcomic.com.mcomic.R;
import mcomic.com.model.Capitulo;
import mcomic.com.model.Favorito;
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
        progressBarLoad.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        textViewPageInfo.setText("Aguarde...");

        try {
            if(manga.getId() == 0){
                Manga m = new MangaDao().getForUrl(manga.getUrl());
                if(m != null){
                    manga.setId(m.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        searchPages = new SearchPages(this);
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

        private ScreenSlidePagerActivity activity;

        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm, ScreenSlidePagerActivity activity) {
            super(fm);
            this.activity = activity;
        }

        @Override
        public Fragment getItem(int position) {
            if(capitulo.getPages().size() > position){
                //salva como favorito e grava a pagina atual
                try {
                    FavoritoDao favoritoDao = new FavoritoDao();
                    Favorito favorito = favoritoDao.getForUrl(manga.getUrl());
                    if(favorito == null){
                        favorito = new Favorito(manga, capitulo,capitulo.getPages().get((position)), false);
                    }else {
                        manga.setId(favorito.getManga().getId());
                        favorito.setManga(manga);
                        if(favorito.getCapituloAtual() != null){
                            capitulo.setId(favorito.getCapituloAtual().getId());
                        }
                        favorito.setCapituloAtual(capitulo);
                        if(favorito.getPaginaAtual() != null){
                            capitulo.getPages().get((position)).setId(favorito.getPaginaAtual().getId());
                        }
                        favorito.setPaginaAtual(capitulo.getPages().get((position)));
                        favorito.setUrl(manga.getUrl());
                    }
                    favoritoDao.insert(favorito);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "ERRO : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                return new ScreenSlidePageFragment(capitulo, capitulo.getPages().get((position)));
            }else if(capitulo.getPages().size() == position && capitulo.getTotalPaginas() == capitulo.getPages().get((position - 1)).getPageNumber()){
                //recupero os mangas até achar o atual
                for (int i = 0 ; i < manga.getCapitulos().size();i++){
                    //verifica se é o atual
                    if(capitulo.getUrl().equals(manga.getCapitulos().get(i).getUrl())){
                        //verifica se existe um próximo
                        if(i + 1 < manga.getCapitulos().size()){
                            return new ScreenSlidePageFragment(activity, manga, manga.getCapitulos().get(i + 1), true, false);
                        }else {
                            return new ScreenSlidePageFragment(activity, manga, manga.getCapitulos().get(0), true, true);
                        }
                    }
                }
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

        private ScreenSlidePagerActivity activity;

        public SearchPages(ScreenSlidePagerActivity activity){
            this.activity = activity;
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
                mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), activity);
                mPager.setAdapter(mPagerAdapter);
            }

            if(mPagerAdapter != null){
                mPagerAdapter.notifyDataSetChanged();
            }
        }
    }
}
