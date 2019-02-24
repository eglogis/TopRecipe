/*
 * Realizado por: Samuel Bautista Sanchez
 * DNI: 20227866X
 * Asignatura: Desarrollo de Aplicaciones Multiplataforma
 * */

package com.example.toprecipe;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AnadirReceta extends AppCompatActivity {

    EditText nombre, tiempo, dificultad, pasos;
    ImageView imgReceta;
    Button crear;
    String categoria = "";
    private final int COD_GALERIA=20;
    Bitmap bitmap;
    byte[] imagenbyte;
    Context context = this;

    ArrayList<String> ingredientes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_receta);

        hacerInvisibleStatusBar();

        nombre = (EditText)findViewById(R.id.edtNombre);
        tiempo = (EditText)findViewById(R.id.edtTiempo);
        dificultad = (EditText)findViewById(R.id.edtDificultad);
        pasos = (EditText)findViewById(R.id.edtPasos);
        imgReceta = (ImageView) findViewById(R.id.imgRecetas);
        crear = (Button)findViewById(R.id.btnCrear);


        ingredientes.add("alcachofas");

        Spinner spin = (Spinner)findViewById(R.id.spinnerCrear);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i == 0){

                   categoria = "";
                }
                if(i == 1){

                    categoria = "Comida";
                }
                if(i == 2){

                    categoria = "Postre";
                }
                if(i == 3){

                    categoria = "Carne";
                }
                if(i == 4){

                    categoria = "Pescado";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {




            }
        });

        imgReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkPermission()) {

                    tomarFotoGalreia();
                    Toast.makeText(getApplicationContext(), "Se abrira la galeria", Toast.LENGTH_SHORT).show();
                }

            }
        });

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nombre.getText().toString().isEmpty() || tiempo.getText().toString().isEmpty() || dificultad.getText().toString().isEmpty() || categoria.equals("")){

                    Toast.makeText(getApplicationContext(), "Sedeben de rellenar todos los campos correctamente", Toast.LENGTH_SHORT).show();

                }
                else{

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    imagenbyte = stream.toByteArray();

                    ConectorBaseDeDatos databaseAccess;
                    databaseAccess = ConectorBaseDeDatos.getInstance(getApplicationContext());
                    databaseAccess.AbrirConexion();
                    databaseAccess.insertarReceta(nombre.getText().toString(), tiempo.getText().toString(),dificultad.getText().toString(), imagenbyte, login.UsuarioBuscado.getId(), categoria, generarPdf());
                    databaseAccess.CerrarConexcion();

                    Toast.makeText(context, "Receta creada correctamente", Toast.LENGTH_SHORT).show();

                    finish();


                }
            }
        });
    }

    private void tomarFotoGalreia(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, COD_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == COD_GALERIA) && (resultCode == RESULT_OK)){
            /* Recoge la direccion donde se encuentra la imagen */
            Uri uri = data.getData();
            try {
                /* Recoge del enlace un bitmap y lo redimensiona antes de cargarlo
                 * en el imageView */
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                //bitmap = redimensionar(bitmap);

                /*ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                byte[] b = baos.toByteArray();
                imagenFoto = Base64.encodeToString(b, Base64.DEFAULT);*/

                this.imgReceta.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkPermission() {
        /* Se compureba de que la SDK es superior a marshmallow, pues si es inferior no es necesario
         * pedir permisos */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED) &&
                    (checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                    (checkSelfPermission(READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                /* En caso de no haber cargado correctamente los permisos se avisa con
                 * un Toast y se piden */
                Toast.makeText(getApplicationContext(), "Error al cargar permisos",
                        Toast.LENGTH_LONG).show();
                requestPermissions(new String[]{CAMERA, WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 100);
                return false;
            } else {
                /* En caso de todos los permisos correctos se notifica en el log */
                Log.i("Mensaje", "Todos los permisos se han cargado correctamente.");
                return true;
            }
        }
        return true;
    }

    private String generarPdf(){
        generarPdf pdf = new generarPdf(context);
        pdf.openDocument();
        pdf.addTitulo(nombre.getText().toString());

        //pdf.addIngredientes(ingredientes);

        pdf.addDescripcion(pasos.getText().toString());
        pdf.closeDocument();
        return pdf.verPathPdf();
    }

    private void hacerInvisibleStatusBar(){

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.parseColor("#AC58FA"));
        }
    }

    @Override protected void onDestroy() {

        super.onDestroy();

        Intent intent = new Intent(getApplicationContext(), recetas_ubicacion.class);
        startActivity(intent);
    }
}
