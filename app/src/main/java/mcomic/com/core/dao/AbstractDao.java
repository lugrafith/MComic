package mcomic.com.core.dao;

import android.annotation.SuppressLint;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import mcomic.com.core.db.DataBaseManager;
import mcomic.com.model.AbstractModel;

/**
 * Created by lu_gr on 25/02/2016.
 */
public abstract class AbstractDao<T extends AbstractModel> {

    private DataBaseManager dataBase;
    private  Class classe;

    @SuppressLint("NewApi")
    public AbstractDao(Class classe){
        this.classe = classe;
        this.dataBase = DataBaseManager.getInstance();
        this.dataBase.getHelper().setWriteAheadLoggingEnabled(true);
    }

    public T insert(T persist) throws SQLException {
        if(persist.getId() != 0){
            int id =  this.dataBase.getHelper().getDao(classe).update(persist);
            persist.setId(id);
        }else {
            //busca pela URL
            T t = getForUrl(persist.getUrl());
            if(t != null){
                persist.setId(t.getId());
                this.dataBase.getHelper().getDao(classe).update(persist);
            }else {
                int id = this.dataBase.getHelper().getDao(classe).create(persist);
            }
        }
        System.out.println("Salvando : " + getClasse() + " -> " + persist);
        return persist;
    }

    public List<T> listAll() throws SQLException {
        return  this.dataBase.getHelper().getDao(getClasse()).queryForAll();
    }

    protected List<T> getList(HashMap<String, Object> mapValues) throws SQLException {
        return  this.dataBase.getHelper().getDao(getClasse()).queryForFieldValuesArgs(mapValues);
    }
    public T get(long id) throws SQLException {
        return (T) this.dataBase.getHelper().getDao(getClasse()).queryForId(id);
    }

    public T getForUrl(String url) throws SQLException {
        if(url != null){
            HashMap<String, String> map = new HashMap<>();
            map.put("url", url);
            List<T> list = this.dataBase.getHelper().getDao(getClasse()).queryForFieldValues(map);
            if(list != null && list.size() > 0){
                return  list.get(0);
            }
        }
        return null;
    }


    public void delete(T persist) throws SQLException {
        this.dataBase.getHelper().getDao(getClasse()).delete(persist);
    }

    protected Class getClasse() {
        return classe;
    }

    public DataBaseManager getDataBase() {
        return dataBase;
    }
}
