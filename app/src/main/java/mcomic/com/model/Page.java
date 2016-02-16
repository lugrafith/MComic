package mcomic.com.model;

import android.graphics.Bitmap;

/**
 * Created by lu_gr on 08/02/2016.
 */
public class Page extends AbstractModel {

    private  transient Bitmap bitmapImage;

    private int pageNumber;

    public Page(String url, Bitmap bitmapImage, int pageNumber) {
        setUrl(url);
        this.bitmapImage = bitmapImage;
        this.pageNumber = pageNumber;
    }

    public Page() {

    }

    public Bitmap getBitmapImage() {
        return bitmapImage;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
