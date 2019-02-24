/*
 * Realizado por: Samuel Bautista Sanchez
 * DNI: 20227866X
 * Asignatura: Desarrollo de Aplicaciones Multiplataforma
 * */

package com.example.toprecipe;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Ficha_usuario extends AppCompatActivity implements adapterRecicler.respuestaAlClick {

    adapterRecicler miAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_usuario);

        hacerInvisibleStatusBar();

        String login = getIntent().getExtras().getString("login");
        String nombre = getIntent().getExtras().getString("nombre");
        String fecha = getIntent().getExtras().getString("fechaNac");
        String telefono = getIntent().getExtras().getString("telefono");
        String correo = getIntent().getExtras().getString("correo");
        String comentarios = getIntent().getExtras().getString("comentarios");
        int id = getIntent().getExtras().getInt("id");
        String imagen = getIntent().getExtras().getString("iamgen");

        RecyclerView recicleRecetas = (RecyclerView)findViewById(R.id.rclrecetasUsuario);
        recicleRecetas.setLayoutManager(new GridLayoutManager(this, 1));

        ArrayList<receta> arrayRecetas = new ArrayList();
        ConectorBaseDeDatos databaseAccess;
        databaseAccess = ConectorBaseDeDatos.getInstance(this);
        databaseAccess.AbrirConexion();
        arrayRecetas = databaseAccess.recetasPorUsuario(id);
        databaseAccess.CerrarConexcion();
        miAdaptador = new adapterRecicler(this, arrayRecetas);
        recicleRecetas.setAdapter(miAdaptador);

        CircleImageView fotoredonda = (CircleImageView)findViewById(R.id.fotoCirculo);
        TextView txvNombre = (TextView)findViewById(R.id.txvNombrePerfil);
        TextView txvNombreCompleto = (TextView)findViewById(R.id.txvNombreCompleto);
        TextView txvFecha = (TextView)findViewById(R.id.txvFechaNac);
        TextView txvTelefono = (TextView)findViewById(R.id.txvNumeroTel);
        TextView txvCorreo = (TextView)findViewById(R.id.txvCorreoEl);
        TextView txvComentarios = (TextView)findViewById(R.id.txvComentarios);

        Picasso.with(this).load("http://192.168.1.140/topRecipes/imagenes/" + imagen).error(R.drawable.ic_account_circle_black_24dp).into(fotoredonda);

        txvNombre.setText(login);
        txvNombreCompleto.setText(nombre);
        txvFecha.setText(fecha);
        txvTelefono.setText(telefono);
        txvCorreo.setText(correo);
        txvComentarios.setText(comentarios);
    }

    private void hacerInvisibleStatusBar(){

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onrespuesAlClick(receta receta) {

    }
}
