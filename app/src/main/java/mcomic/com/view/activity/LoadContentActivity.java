package mcomic.com.view.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import mcomic.com.core.service.ServiceCentralManga;
import mcomic.com.core.ServiceTask;
import mcomic.com.mcomic.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoadContentActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textViewInfo;

    private ServiceTask serviceTask;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_content);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_loading);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        ServiceCentralManga serviceCentralManga = new ServiceCentralManga();
        serviceTask = new ServiceTask(serviceCentralManga, this);
        serviceTask.execute("");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void toggle() {
    }

    private void hide() {

    }

    @SuppressLint("InlinedApi")
    private void show() {

    }

    private void delayedHide(int delayMillis) {
    }


    public void execute(){
        System.out.print("FINISH");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
