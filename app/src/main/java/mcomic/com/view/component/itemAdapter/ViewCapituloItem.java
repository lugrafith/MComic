package mcomic.com.view.component.itemAdapter;

import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import mcomic.com.model.Capitulo;

/**
 * Created by lu_gr on 07/02/2016.
 */
public class ViewCapituloItem {
    private CheckBox checkBoxLido;
    private TextView textViewCapitulo;
    private ProgressBar progressBar;
    private ImageView buttomLer;
    private Capitulo capitulo;


    public ViewCapituloItem(CheckBox checkBoxLido, TextView textViewCapitulo, ImageButton buttomLer, ProgressBar progressBar, Capitulo capitulo) {
        this.checkBoxLido = checkBoxLido;
        this.textViewCapitulo = textViewCapitulo;
        this.progressBar = progressBar;
        this.buttomLer = buttomLer;
        this.capitulo = capitulo;
    }
    public ViewCapituloItem(Capitulo capitulo) {
        this.capitulo = capitulo;
    }

    public CheckBox getCheckBoxLido() {
        return checkBoxLido;
    }

    public void setCheckBoxLido(CheckBox checkBoxLido) {
        this.checkBoxLido = checkBoxLido;
    }

    public TextView getTextViewCapitulo() {
        return textViewCapitulo;
    }

    public void setTextViewCapitulo(TextView textViewCapitulo) {
        this.textViewCapitulo = textViewCapitulo;
    }

    public ImageView getButtomLer() {
        return buttomLer;
    }

    public void setButtomLer(ImageView buttomLer) {
        this.buttomLer = buttomLer;
    }

    public Capitulo getCapitulo() {
        return capitulo;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setCapitulo(Capitulo capitulo) {
        this.capitulo = capitulo;
    }
}
