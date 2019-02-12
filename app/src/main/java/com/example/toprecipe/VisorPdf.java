package com.example.toprecipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class VisorPdf extends AppCompatActivity {

    String nombrePdf;
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_pdf);

        nombrePdf = getIntent().getStringExtra("pdf");

        pdfView = (PDFView)findViewById(R.id.pdfView);

        pdfView.fromAsset(nombrePdf)
                .enableSwipe(true)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .load();
    }
}
