package mcomic.com.core.iservice;

import android.os.AsyncTask;

import java.util.List;

import mcomic.com.model.Manga;

/**
 * Created by lu_gr on 30/01/2016.
 */
public interface Service<T extends Manga> {

    List<Manga> search(String title) throws Exception;

}
