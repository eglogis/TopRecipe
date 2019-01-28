package com.example.toprecipe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity implements View.OnClickListener{

    private EditText edtNombre;
    private EditText edtContrasena;
    private Button btnIniciar;
    public static usuario UsuarioBuscado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        hacerInvisibleStatusBar();
        inicializarComponentes();





    }

    @Override
    public void onClick(View v) {

        if(v.getId() == findViewById(R.id.btnInicioSesion).getId()){

            clickEnIniciarSesion();
        }

    }

    //hace invisile la barra de estado dejando los iconos
    private void hacerInvisibleStatusBar(){

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //inicializa los componentes de la interfaz
    private void inicializarComponentes(){

        edtNombre = (EditText)findViewById(R.id.edtNombre);
        edtContrasena = (EditText)findViewById(R.id.edtContraseña);
        btnIniciar = (Button)findViewById(R.id.btnInicioSesion);
        btnIniciar.setOnClickListener(this);
    }

    //comprueba si el editText esta vacio
    private boolean estaVacioEditText(){

        if((edtNombre.getText().toString().isEmpty()) || (edtContrasena.getText().toString().isEmpty())){

            return true;

        }else{

            return false;
        }
    }

    //lo que hace al clicar el boton
    private void clickEnIniciarSesion(){

        if(estaVacioEditText()){

            Toast.makeText(this, "Son obligatorios los dos campos", Toast.LENGTH_SHORT).show();


        }else{

            instanciaRetrofit();
        }
    }

    private void instanciaRetrofit(){

        InstanciaRetrofit.GetDataService service = InstanciaRetrofit.getRetrofitInstance().create(InstanciaRetrofit.GetDataService.class);
        Call<List<usuario>> call = service.getAllPhotos();

        call.enqueue(new Callback<List<usuario>>() {
            @Override
            public void onResponse(Call<List<usuario>> call, Response<List<usuario>> response) {

                UsuarioBuscado = generateDataList(response.body());

                if(UsuarioBuscado.getId() == 0){

                    Toast.makeText(getApplicationContext(), "El usuario o la contraseña no es correcto", Toast.LENGTH_SHORT).show();

                }
                else {

                    Intent intent = new Intent(getApplicationContext(), recipes.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<usuario>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e("error", t.toString());
            }
        });
    }

    private usuario generateDataList(List<usuario> photoList) {

        boolean encontrado = false;
        int contador = 0;
        usuario usuarioEncontrado = null;

        while ((encontrado == false) && (contador < photoList.size())){

            if((photoList.get(contador).getLogin().equals(edtNombre.getText().toString())) && (photoList.get(contador).getContrasena().equals(edtContrasena.getText().toString()))){

                usuarioEncontrado = photoList.get(contador);
                encontrado = true;
            }
            else {

                usuarioEncontrado = new usuario(0, null, null, null, null, null, null, null, null, null, null, null, null);
            }

            contador++;
        }

        return usuarioEncontrado;
    }
}
