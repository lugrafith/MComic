package mcomic.com.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import mcomic.com.model.Arte;
import mcomic.com.model.Autor;
import mcomic.com.model.Capitulo;
import mcomic.com.model.Favorito;
import mcomic.com.model.Genero;
import mcomic.com.model.Manga;
import mcomic.com.model.MangaCentral;
import mcomic.com.model.Page;

/**
 * Created by lu_gr on 25/02/2016.
 */
class OrmliteOpenHelper extends OrmLiteSqliteOpenHelper{

    private static final String DATABASE_NAME = "MComic";
    private static final int DATABASE_VERSION = 3;

    public OrmliteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Arte.class);
            TableUtils.createTable(connectionSource, Autor.class);
            TableUtils.createTable(connectionSource, Genero.class);
            TableUtils.createTable(connectionSource, Manga.class);

            TableUtils.createTable(connectionSource, Capitulo.class);
            TableUtils.createTable(connectionSource, Page.class);
            TableUtils.createTable(connectionSource, Favorito.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Capitulo.class, false);
            TableUtils.dropTable(connectionSource, Page.class, false);
            TableUtils.dropTable(connectionSource, Favorito.class, false);
            TableUtils.dropTable(connectionSource, Arte.class, false);
            TableUtils.dropTable(connectionSource, Autor.class, false);
            TableUtils.dropTable(connectionSource, Genero.class, false);
            TableUtils.dropTable(connectionSource, Manga.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCreate(database, connectionSource);
    }

}
