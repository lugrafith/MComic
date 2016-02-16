package mcomic.com.core;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import mcomic.com.core.iservice.Service;
import mcomic.com.model.Manga;
import mcomic.com.view.activity.MainActivity;

/**
 * Created by lu_gr on 30/01/2016.
 */
public class ServiceTask extends AsyncTask<String, ListView, List<Manga>> {

    private Service service;
    private Activity activity;

    public ServiceTask(Service service, Activity activity){
        this.service = service;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        getViewProgressBar(View.VISIBLE, View.INVISIBLE);
        super.onPreExecute();
    }

    @Override
    protected List<Manga> doInBackground(String... params) {
        try {
            return service.search(params[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(ListView... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<Manga> m) {
        this.cancel(m);
        super.onPostExecute(m);
    }

    @Override
    protected void onCancelled(List<Manga> m) {
        //this.cancel(m);
        super.onCancelled(m);
    }

    private void cancel(List<Manga> m){
        try {
            Aplication.setMangas(m);
            Method method = activity.getClass().getMethod("execute");
            getViewProgressBar(View.INVISIBLE, View.VISIBLE);
            if(method != null){
                method.invoke(activity, null);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private void getViewProgressBar(int p, int g){
        if(activity instanceof MainActivity){
            ((MainActivity) activity).getProgressBar().setVisibility(p);
            ((MainActivity) activity).getGridViewMangas().setVisibility(g);
        }
    }

}
