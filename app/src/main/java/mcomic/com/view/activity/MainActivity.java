package mcomic.com.view.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import mcomic.com.core.Aplication;
import mcomic.com.core.ServiceTask;
import mcomic.com.core.ServiceTaskMangaInfo;
import mcomic.com.core.service.ServiceCentralManga;
import mcomic.com.mcomic.R;
import mcomic.com.view.component.adapter.CapituloAdapter;
import mcomic.com.view.component.adapter.GeneroAdapter;
import mcomic.com.view.component.adapter.MangaAdapter;
import mcomic.com.view.component.itemAdapter.ViewCapituloItem;
import mcomic.com.view.component.itemAdapter.ViewMangaItem;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabHost.OnTabChangeListener, AdapterView.OnItemClickListener {

    private GridView gridViewMangas;
    private EditText editTextSearch;
    private ProgressBar progressBar;
    private ServiceTask serviceTask;
    private TabHost tabHost;
    private ServiceTaskMangaInfo serviceTaskMangaInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceTask = new ServiceTask(new ServiceCentralManga(), this);
        serviceTaskMangaInfo = new ServiceTaskMangaInfo(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_filter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
            }
        });

        fab.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{Color.BLACK}));
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //
        gridViewMangas = (GridView) findViewById(R.id.gridView_mangas);
        gridViewMangas.setAdapter(new MangaAdapter(this, Aplication.getMangas()));
        gridViewMangas.setOnItemClickListener(this);
        setInfoManga();

        progressBar = (ProgressBar) findViewById(R.id.progressBar_loading);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        editTextSearch = (EditText) findViewById(R.id.editText_search);

        editTextSearch.setOnEditorActionListener(getOnKeyListener());


        //Tabs
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tab = tabHost.newTabSpec("Mangas");
        tab.setContent(R.id.linearLayout);
        tab.setIndicator("");
        tabHost.addTab(tab);

        tab = tabHost.newTabSpec("");
        tab.setContent(R.id.linearLayout2);
        tab.setIndicator("");
        tabHost.addTab(tab);

        tab = tabHost.newTabSpec("");
        tab.setContent(R.id.linearLayout3);
        tab.setIndicator("");
        tabHost.addTab(tab);

        TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); ++i){
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.BLACK);
        tabHost.setOnTabChangedListener(this);


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

        if (id == R.id.nav_exit) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public TextView.OnEditorActionListener getOnKeyListener(){
        final Activity activity = this;
        return new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(!editTextSearch.getEditableText().toString().equals("") && event != null){
                    progressBar.setVisibility(View.VISIBLE);
                    if(serviceTask != null){
                        serviceTask.cancel(true);
                    }
                    serviceTaskMangaInfo.cancel(true);
                    serviceTaskMangaInfo = new ServiceTaskMangaInfo(activity);
                    serviceTask = new ServiceTask(new ServiceCentralManga(), activity);
                    serviceTask.execute(editTextSearch.getEditableText().toString());
                    tabHost.setCurrentTab(0);
                }
                return false;
            }

        };
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
    }

    private void setInfoManga(){
        serviceTaskMangaInfo.execute((MangaAdapter)gridViewMangas.getAdapter());
    }

    @Override
    public void onTabChanged(String tabId) {
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++){
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.TRANSPARENT); //unselected
        }
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.BLACK); // selected
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

        ViewMangaItem item = (ViewMangaItem) parent.getAdapter().getItem(position);

        if (item.getManga().getImageCover() != null) {
            tabHost.setCurrentTab(1);
            Drawable backgroundF = new BitmapDrawable(getResources(), item.getManga().getImageCover());
            ProgressBar progressBarLoadImage = (ProgressBar) findViewById(R.id.progressBar_loadImage);
            progressBarLoadImage.getIndeterminateDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

            TextView textViewTitle = (TextView) findViewById(R.id.textView_title);
            ImageButton cover = (ImageButton) findViewById(R.id.imageButton_cover);
            ImageButton ler = (ImageButton) findViewById(R.id.imageButton_ler);
            TextView textViewAutor = (TextView) findViewById(R.id.textView_autor);
            TextView textViewArte = (TextView) findViewById(R.id.textView_arte);
            GridView gridViewCategorias = (GridView) findViewById(R.id.gridView_generos);
            TextView textViewSinopse = (TextView) findViewById(R.id.textView_sinopse);
            textViewTitle.setText(item.getManga().getTitle() + " - " + item.getManga().getAno());
            cover.setBackground(backgroundF);
            textViewAutor.setText("autor " + item.getManga().getAutor().getNome());
            textViewArte.setText("arte " + item.getManga().getArte().getNome());
            if (item.getManga().getGeneros().size() > 0) {
                GeneroAdapter generoAdapter = new GeneroAdapter(this, item.getManga().getGeneros());
                gridViewCategorias.setAdapter(generoAdapter);
            }
            textViewSinopse.setText(item.getManga().getSinopse());
            progressBarLoadImage.setVisibility(View.INVISIBLE);

            cover.setOnClickListener(getClickCapitulos(item));
            ler.setOnClickListener(getClickCapitulos(item));

        } else {
            Toast.makeText(MainActivity.this, "Aguarde..", Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener getClickCapitulos(final ViewMangaItem item){
        Activity activity = this;
        return  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                tabHost.setCurrentTab(2);
                progressBar.setVisibility(View.VISIBLE);
                CapituloAdapter capituloAdapter = new CapituloAdapter(MainActivity.this, item.getManga().getCapitulos(), item.getManga());
                ((ListView) findViewById(R.id.listView_capitulos)).setAdapter(capituloAdapter);
                progressBar.setVisibility(View.INVISIBLE);
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
