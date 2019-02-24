/*
 * Realizado por: Samuel Bautista Sanchez
 * DNI: 20227866X
 * Asignatura: Desarrollo de Aplicaciones Multiplataforma
 * */

package com.example.toprecipe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnInciarSesion, btnRegistrate, btnInvitado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
        hacerInvisibleStatusBar();


    }

    //click en un componente
    @Override
    public void onClick(View v) {

        if(v.getId() == findViewById(R.id.btnInicio).getId()){

            clickIniciarSesion();
        }
        if(v.getId() == findViewById(R.id.btnRegistro).getId()){

            clickRegistrar();
        }
        if(v.getId() == findViewById(R.id.btnInvitado).getId()){

            clickInvitado();
        }
    }

    //click en Iniciar Sesion
    private void clickIniciarSesion(){

        Intent intent = new Intent(this, login.class);
        startActivity(intent);


    }

    //click en Registrar
    private void clickRegistrar(){

        Intent intent = new Intent(this, register.class);
        startActivity(intent);


    }

    //click en Invitado
    private void clickInvitado(){

        Intent intent = new Intent(this, recetas_ubicacion.class);
        startActivity(intent);


    }

    //inicializa los componentes de la interfaz
    private void inicializarComponentes(){

        btnInciarSesion = (Button)findViewById(R.id.btnInicio);
        btnInciarSesion.setOnClickListener(this);
        btnRegistrate = (Button)findViewById(R.id.btnRegistro);
        btnRegistrate.setOnClickListener(this);
        btnInvitado = (Button)findViewById(R.id.btnInvitado);
        btnInvitado.setOnClickListener(this);
    }

    //hace invisile la barra de estado dejando los iconos
    private void hacerInvisibleStatusBar(){

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
