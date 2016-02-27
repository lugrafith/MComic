package mcomic.com.core.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import mcomic.com.core.db.OrmliteOpenHelper;
import mcomic.com.model.Manga;

/**
 * Created by lu_gr on 25/02/2016.
 */
public class MangaDao extends AbstractDao<Manga> {

    public MangaDao(OrmliteOpenHelper helper) {
        super(helper, Manga.class);
    }

}
