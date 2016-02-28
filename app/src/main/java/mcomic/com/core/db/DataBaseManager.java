package mcomic.com.core.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

/**
 * Created by lu_gr on 28/02/2016.
 */
public class DataBaseManager {

    private static DataBaseManager instance;
    private OrmLiteSqliteOpenHelper helper;

    private DataBaseManager(Context  context){
        helper = new OrmliteOpenHelper(context);
    }

    public static void init(Context context){
        if(instance == null){
            instance = new DataBaseManager(context);
        }
    }

    public static DataBaseManager getInstance(){
        return instance;
    }

    public OrmLiteSqliteOpenHelper getHelper(){
        return helper;
    }
}
