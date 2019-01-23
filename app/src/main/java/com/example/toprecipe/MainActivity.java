package com.example.toprecipe;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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


        }
        if(v.getId() == findViewById(R.id.btnRegistro).getId()){


        }
        if(v.getId() == findViewById(R.id.btnInvitado).getId()){


        }
    }

    //click en Iniciar Sesion
    private void clickIniciarSesion(){


    }

    //click en Registrar
    private void clickRegistrar(){


    }

    //click en Invitado
    private void clickInvitado(){


    }

    //inicializa los componentes de la interfaz
    private void inicializarComponentes(){

        btnInciarSesion = (Button)findViewById(R.id.btnInicio);
        btnInciarSesion.setOnClickListener((View.OnClickListener) getApplicationContext());
        btnRegistrate = (Button)findViewById(R.id.btnRegistro);
        btnRegistrate.setOnClickListener((View.OnClickListener) getApplicationContext());
        btnInvitado = (Button)findViewById(R.id.btnInvitado);
        btnInvitado.setOnClickListener((View.OnClickListener) getApplicationContext());
    }

    //hace invisile la barra de estado dejando los iconos
    private void hacerInvisibleStatusBar(){

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }


}
