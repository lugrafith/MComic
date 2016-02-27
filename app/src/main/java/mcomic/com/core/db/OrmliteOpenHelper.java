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
import mcomic.com.model.Page;

/**
 * Created by lu_gr on 25/02/2016.
 */
public class OrmliteOpenHelper extends OrmLiteSqliteOpenHelper{

    private static final String databaseName = "MComic";
    private static final int databaseVersion = 4;

    public OrmliteOpenHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
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
            TableUtils.dropTable(connectionSource, Manga.class, true);
            TableUtils.dropTable(connectionSource, Capitulo.class, true);
            TableUtils.dropTable(connectionSource, Page.class, true);
            TableUtils.dropTable(connectionSource, Favorito.class, true);
            TableUtils.dropTable(connectionSource, Arte.class, true);
            TableUtils.dropTable(connectionSource, Autor.class, true);
            TableUtils.dropTable(connectionSource, Genero.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCreate(database, connectionSource);
    }

}
