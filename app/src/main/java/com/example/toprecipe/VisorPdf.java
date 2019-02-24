/*
 * Realizado por: Samuel Bautista Sanchez
 * DNI: 20227866X
 * Asignatura: Desarrollo de Aplicaciones Multiplataforma
 * */

package com.example.toprecipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class VisorPdf extends AppCompatActivity {

    String nombrePdf;
    PDFView pdfView;
    int defecto;
    //private String urlPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_pdf);



        nombrePdf = getIntent().getStringExtra("pdf");
        defecto = getIntent().getIntExtra("defecto", 0);

        pdfView = (PDFView)findViewById(R.id.pdfView);

        if(defecto == 1) {

            pdfView.fromAsset("pdf/" + nombrePdf)
                    .enableSwipe(true)
                    .enableDoubletap(true)
                    .enableAntialiasing(true)
                    .load();
        }
        else {

            this.pdfView.fromFile(new File(this.nombrePdf)) //FROM FILE
                    .enableSwipe(true)
                    .enableDoubletap(true)
                    .enableAntialiasing(true)
                    .load();
        }
    }
}
