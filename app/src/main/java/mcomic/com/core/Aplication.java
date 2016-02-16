package mcomic.com.core;

import java.util.List;

import mcomic.com.model.Manga;
import mcomic.com.model.MangaCentral;

/**
 * Created by lu_gr on 30/01/2016.
 */
public class Aplication {

    public  static final String CAPTIULO = "capitulo";

    public  static final String MANGA = "MANGA";

    //public static boolean cancelSearch;

    private static List<Manga> mangas;

    //public  static ServiceTask serviceTask;

    public static List<Manga> getMangas() {
       return mangas;
    }

    public static void setMangas(List<Manga> mangas) {
        Aplication.mangas = mangas;
    }
}
