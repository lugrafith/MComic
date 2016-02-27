package mcomic.com.view.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mcomic.com.core.Aplication;
import mcomic.com.core.ServiceTask;
import mcomic.com.core.ServiceTaskMangaInfo;
import mcomic.com.core.dao.FavoritoDao;
import mcomic.com.core.dao.MangaDao;
import mcomic.com.core.db.OrmliteOpenHelper;
import mcomic.com.core.service.ServiceCentralManga;
import mcomic.com.mcomic.R;
import mcomic.com.model.Favorito;
import mcomic.com.model.Manga;
import mcomic.com.view.activity.fragment.ScreenSlidePagerActivity;
import mcomic.com.view.component.adapter.CapituloAdapter;
import mcomic.com.view.component.adapter.GeneroAdapter;
import mcomic.com.view.component.adapter.MangaAdapter;
import mcomic.com.view.component.itemAdapter.ViewMangaItem;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabHost.OnTabChangeListener, AdapterView.OnItemClickListener, TextView.OnEditorActionListener {

    private TextView info;
    private GridView gridViewMangas;
    private EditText editTextSearch;
    private ProgressBar progressBar;
    private ServiceTask serviceTask;
    private TabHost tabHost;
    private ServiceTaskMangaInfo serviceTaskMangaInfo;
    private ViewStub stubContainerDetalheManga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceTask = new ServiceTask(new ServiceCentralManga(), this);
        serviceTaskMangaInfo = new ServiceTaskMangaInfo(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        info = (TextView) findViewById(R.id.textView_info);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //
        gridViewMangas = (GridView) findViewById(R.id.gridView_mangas);
        gridViewMangas.setAdapter(new MangaAdapter(this, Aplication.getMangas()));
        gridViewMangas.setOnItemClickListener(this);
        setInfoManga();

        progressBar = (ProgressBar) findViewById(R.id.progressBar_loading);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        editTextSearch = (EditText) findViewById(R.id.editText_search);

        //busca
        editTextSearch.addTextChangedListener(getTextWatcher());
        editTextSearch.setOnEditorActionListener(this);

        //Tabs
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tab = tabHost.newTabSpec("Favoritos");
        tab.setContent(R.id.linearLayout_favoritos);
        tab.setIndicator("");
        tabHost.addTab(tab);

        tab = tabHost.newTabSpec("Mangas");
        tab.setContent(R.id.linearLayout_mangas);
        tab.setIndicator("");
        tabHost.addTab(tab);

        tab = tabHost.newTabSpec("Detalhe");
        tab.setContent(R.id.linearLayout_detalhe_manga);
        tab.setIndicator("");
        tabHost.addTab(tab);


        TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); ++i){
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }
        tabHost.setCurrentTab(1);
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.RED);
        tabHost.setOnTabChangedListener(this);

        stubContainerDetalheManga = (ViewStub) findViewById(R.id.linearLayout_containerDetalheManga);
        stubContainerDetalheManga.setLayoutResource(R.layout.layout_detalhe_manga_portrait_landscape);
        stubContainerDetalheManga.inflate();
    }



    private TextWatcher getTextWatcher(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (filterLongEnough()) {
                    search(editTextSearch);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            private boolean filterLongEnough() {
                return editTextSearch.getText().toString().trim().length() > 1;
            }
        };
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(event != null){
            search(v);
        }
        return false;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        }
        //TODO
        super.onConfigurationChanged(newConfig);
    }


    public int getScreenOrientation()
    {
        Display getOrient = getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if(getOrient.getWidth()==getOrient.getHeight()){
            orientation = Configuration.ORIENTATION_SQUARE;
        } else{
            if(getOrient.getWidth() < getOrient.getHeight()){
                orientation = Configuration.ORIENTATION_PORTRAIT;
            }else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        return orientation;
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(tabHost.getCurrentTab() > 0){
                tabHost.setCurrentTab(tabHost.getCurrentTab() - 1);
            }else {
                cancelServices();
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_search){
            tabHost.setCurrentTab(1);
        }else if (id == R.id.nav_favorites) {
            tabHost.setCurrentTab(0);
            FavoritoDao favoritoDao = new FavoritoDao(new OrmliteOpenHelper(this));
            try {
                List<Favorito> favoritos = favoritoDao.listAll();
                if(favoritos != null || favoritos.size() != 0){
                    //((RelativeLayout) findViewById(R.id.container_favoritos)).setVisibility(View.INVISIBLE);
                    List<Manga> mangasLidos = new ArrayList();
                    List<Manga> mangasFavoritos = new ArrayList();
                    for (Favorito f : favoritos){
                        if(f.isFavorito()){
                            mangasFavoritos.add(f.getManga());
                        }else {
                            mangasLidos.add(f.getManga());
                        }
                    }
                    MangaAdapter mangaAdapterFavoritos = new MangaAdapter(this, mangasFavoritos);
                    MangaAdapter mangaAdapterLidos = new MangaAdapter(this, mangasLidos);
                    GridView gridViewFavoritos = (GridView) findViewById(R.id.gridView_mangas_favoritos);
                    GridView gridViewLidos = (GridView) findViewById(R.id.gridView_mangas_lidos);
                    gridViewFavoritos.setAdapter(mangaAdapterFavoritos);
                    gridViewFavoritos.setOnItemClickListener(this);
                    gridViewLidos.setAdapter(mangaAdapterLidos);
                    gridViewLidos.setOnItemClickListener(this);
                    cancelServices();
                    //serviceTaskMangaInfo.cancel(true);
                    serviceTaskMangaInfo = new ServiceTaskMangaInfo(this);
                    serviceTaskMangaInfo.execute((MangaAdapter) gridViewFavoritos.getAdapter());
                    serviceTaskMangaInfo = new ServiceTaskMangaInfo(this);
                    serviceTaskMangaInfo.execute((MangaAdapter) gridViewLidos.getAdapter());
                }else {
                    //((RelativeLayout) findViewById(R.id.container_favoritos)).setVisibility(View.VISIBLE);
                    //TODO
                }
            } catch (SQLException e) {
                Toast.makeText(MainActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else if (id == R.id.nav_exit) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void search(TextView v){
        if (!v.getEditableText().toString().equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            if (serviceTask != null) {
                serviceTask.cancel(true);
            }
            serviceTaskMangaInfo.cancel(true);
            serviceTaskMangaInfo = new ServiceTaskMangaInfo(this);
            serviceTask = new ServiceTask(new ServiceCentralManga(), this);
            serviceTask.execute(v.getEditableText().toString());
            if (tabHost.getCurrentTab() != 1) {
                tabHost.setCurrentTab(1);
            }
        }
    }

    public void cancelServices(){
        if(serviceTaskMangaInfo != null && !serviceTaskMangaInfo.isCancelled()){
            serviceTaskMangaInfo.cancel(true);
        }
        if(serviceTask != null && !serviceTask.isCancelled()){
            serviceTask.cancel(true);
        }
    }

    public void execute(){
        System.out.print("FINISH");
        gridViewMangas.setAdapter(new MangaAdapter(this, Aplication.getMangas()));
        setInfoManga();
        if(Aplication.getMangas() == null || Aplication.getMangas().size() == 0){
            info.setText("Nenhum manga encontrado com o titulo " + editTextSearch.getText());
        }else {
            info.setText("");
        }
    }

    private void setInfoManga(){
        serviceTaskMangaInfo.execute((MangaAdapter) gridViewMangas.getAdapter());
    }

    @Override
    public void onTabChanged(String tabId) {
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++){
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.TRANSPARENT); //unselected
        }
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.RED); // selected
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

        ViewMangaItem item = (ViewMangaItem) parent.getAdapter().getItem(position);

        if (item.getManga().getImageCover() != null) {
            ((ScrollView) findViewById(R.id.scrollView_container)).setScrollX(0);
            ((ScrollView) findViewById(R.id.scrollView_container)).setScrollY(0);
            tabHost.setCurrentTab(2);

            LinearLayout linearLayoutContainer = (LinearLayout) findViewById(R.id.linearLayout_containerDetalheManga);
            ImageButton imageButtonIconRead = (ImageButton) findViewById(R.id.imageButton_icon_read);
            ImageButton imageButtonShare = (ImageButton) findViewById(R.id.imageButton_share);
            ImageButton imageButtonBack = (ImageButton) findViewById(R.id.imageButton_back);
            TextView textViewMangaTitle = (TextView) findViewById(R.id.textView_title);
            TextView textViewAutor= (TextView) findViewById(R.id.textView_autor);
            TextView textViewArte = (TextView) findViewById(R.id.textView_arte);
            TextView textViewSinopse = (TextView) findViewById(R.id.textView_sinopse);
            GridView gridViewGenero = (GridView) findViewById(R.id.gridView_generos);
            ListView listViewCapitulos = (ListView) findViewById(R.id.listView_capitulos);
            Button buttonFavoritar = (Button) findViewById(R.id.button_favoritar);

            Drawable backgroundF = new BitmapDrawable(getResources(), item.getManga().getImageCover());
            linearLayoutContainer.setBackground(backgroundF);

            textViewMangaTitle.setText(item.getManga().getTitle() + " - " + item.getManga().getAno());
            textViewAutor.setText("Autor: " + item.getManga().getAutor().getNome());
            textViewArte.setText("Arte: " + item.getManga().getArte().getNome());
            if (item.getManga().getGeneros().size() > 0) {
                GeneroAdapter generoAdapter = new GeneroAdapter(this, item.getManga().getGeneros());
                gridViewGenero.setAdapter(generoAdapter);
            }
            textViewSinopse.setText(item.getManga().getSinopse());
            imageButtonIconRead.setOnClickListener(getClickCapitulos(item));

            //capitulos
            CapituloAdapter capituloAdapter = new CapituloAdapter(MainActivity.this, item.getManga().getCapitulos(), item.getManga());
            listViewCapitulos.setAdapter(capituloAdapter);
            //voltar
            imageButtonBack.setOnClickListener(voltar());
            //size ListView
            ListAdapter adapter = listViewCapitulos.getAdapter();
            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, listViewCapitulos);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams par = listViewCapitulos.getLayoutParams();
            par.height = totalHeight + (listViewCapitulos.getDividerHeight() * (adapter.getCount()));
            listViewCapitulos.setLayoutParams(par);
            listViewCapitulos.requestLayout();


            alterarButtonfavorito(item, buttonFavoritar);
            buttonFavoritar.setOnClickListener(getClick(item    ));
        } else {
            Toast.makeText(MainActivity.this, "Aguarde..", Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener getClick(final ViewMangaItem item){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterarButtonfavorito(item, (Button)v);
            }
        };
    }

    private void alterarButtonfavorito(ViewMangaItem item, Button buttonFavoritar){
        FavoritoDao favoritoDao = new FavoritoDao(new OrmliteOpenHelper(this));
        MangaDao mangaDao = new MangaDao(new OrmliteOpenHelper(this));
        try {
            Manga m = mangaDao.getForUrl(item.getManga().getUrl());
            if(m != null){
                item.getManga().setId(m.getId());
                Favorito f = favoritoDao.getForManga(item.getManga());
                if(f != null){
                    buttonFavoritar.setBackgroundColor(Color.RED);
                    buttonFavoritar.setText("- Favorito");
                }else {
                    buttonFavoritar.setBackgroundColor(Color.BLACK);
                    buttonFavoritar.setText("+ Favorito");
                }
            }else {
                buttonFavoritar.setBackgroundColor(Color.BLACK);
                buttonFavoritar.setText("+ Favorito");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener voltar(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        };
    }

    private View.OnClickListener getClickCapitulos(final ViewMangaItem item){
        final MainActivity activity = this;
        return  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ScreenSlidePagerActivity.class);
                intent.putExtra(Aplication.MANGA, item.getManga());
                intent.putExtra(Aplication.CAPTIULO, item.getManga().getCapitulos().get(0));
                activity.cancelServices();
                activity.startActivity(intent);
            }
        };
    }

    public GridView getGridViewMangas() {
        return gridViewMangas;
    }

    public EditText getEditTextSearch() {
        return editTextSearch;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public ServiceTask getServiceTask() {
        return serviceTask;
    }

}
