package mcomic.com.core.dao;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mcomic.com.core.db.OrmliteOpenHelper;
import mcomic.com.model.AbstractModel;

/**
 * Created by lu_gr on 25/02/2016.
 */
public abstract class AbstractDao<T extends AbstractModel> {

    private OrmliteOpenHelper helper;
    private  Class classe;

    public AbstractDao(OrmliteOpenHelper helper, Class classe){
        this.classe = classe;
        this.helper = helper;
    }

    public T insert(T persist) throws SQLException {
        if(persist.getId() != 0){
            int id =  helper.getDao(classe).update(persist);
            persist.setId(id);
        }else {
            //busca pela URL
            T t = getForUrl(persist.getUrl());
            if(t != null){
                persist.setId(t.getId());
                helper.getDao(classe).update(persist);
            }else {
                int id = helper.getDao(classe).create(persist);
            }
        }
        System.out.println("Salvando : " + getClasse() + " -> " + persist);
        return persist;
    }

    public List<T> listAll() throws SQLException {
        return  getHelper().getDao(getClasse()).queryForAll();
    }

    protected List<T> getList(HashMap<String, Object> mapValues) throws SQLException {
        return  getHelper().getDao(getClasse()).queryForFieldValuesArgs(mapValues);
    }
    public T get(long id) throws SQLException {
        return (T) getHelper().getDao(getClasse()).queryForId(id);
    }

    public T getForUrl(String url) throws SQLException {
        HashMap<String, String> map = new HashMap<>();
        map.put("url", url);
        List<T> list = getHelper().getDao(getClasse()).queryForFieldValues(map);
        if(list != null && list.size() > 0){
            return  list.get(0);
        }
        return null;
    }

    protected OrmliteOpenHelper getHelper() {
        return helper;
    }

    protected Class getClasse() {
        return classe;
    }
}
