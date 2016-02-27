package mcomic.com.core.dao;

import mcomic.com.core.db.OrmliteOpenHelper;
import mcomic.com.model.Page;

/**
 * Created by lu_gr on 26/02/2016.
 */
public class PageDao extends AbstractDao<Page> {

    public PageDao(OrmliteOpenHelper helper) {
        super(helper, Page.class);
    }
}
