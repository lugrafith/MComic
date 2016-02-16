package mcomic.com.core.exceptions;

/**
 * Created by lu_gr on 30/01/2016.
 */
public class AplicationException extends Exception {

    public AplicationException(String s) {
        super(s);
    }

    public AplicationException(Exception e){
        super(e);
    }
}
