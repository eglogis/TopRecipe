package com.example.toprecipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class register extends AppCompatActivity implements View.OnClickListener{

    private EditText edtNombre, edtApellido, edtFechaNacimiento, edtNumeroTelefono, edtObservaciones, edtNombreUsuario, edtCorreoElectronico, edtContrasena, edtContrasenados;
    private Button btnRegistrar;
    private double latitud, altitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inicializarComponentes();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == findViewById(R.id.btnRegistro).getId()){

        }

    }


    //inicializa los componentes de la interfaz
    private void inicializarComponentes() {

        //edittext
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtApellido = (EditText) findViewById(R.id.edtApellido);
        edtFechaNacimiento = (EditText) findViewById(R.id.edtFechaNacimiento);
        edtNumeroTelefono = (EditText) findViewById(R.id.edtNumeroTelefono);
        edtObservaciones = (EditText) findViewById(R.id.edtObservaciones);
        edtNombreUsuario = (EditText) findViewById(R.id.edtNombreUsuario);
        edtCorreoElectronico = (EditText) findViewById(R.id.edtCorreoElectronico);
        edtContrasena = (EditText) findViewById(R.id.edtContrasena);
        edtContrasenados = (EditText) findViewById(R.id.edtContrasenados);

        //button
        btnRegistrar = (Button) findViewById(R.id.btnRegistro);
        btnRegistrar.setOnClickListener(this);

    }

    private void clickBotonRegistrar(){


    }
}
