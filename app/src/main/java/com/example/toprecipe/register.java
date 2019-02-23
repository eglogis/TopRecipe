package com.example.toprecipe;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class register extends AppCompatActivity implements View.OnClickListener{

    private EditText edtNombre, edtApellido, edtDia, edtMes, edtAno, edtNumeroTelefono, edtObservaciones, edtNombreUsuario, edtCorreoElectronico, edtContrasena, edtContrasenados;
    private Button btnRegistrar;
    private ImageButton foto, galeria;
    public static usuario UsuarioBuscado;
    private double latitud, altitud;
    private ImageView imagenUsusario;
    private final int COD_CAMARA=10;
    private final int COD_GALERIA=20;
    private Bitmap bitmap;
    private String imagenFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inicializarComponentes();
        hacerInvisibleStatusBar();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == findViewById(R.id.btnRegistrar).getId()) {

            clickBotonRegistrar();

        }
    }

    //inicializa los componentes de la interfaz
    private void inicializarComponentes() {

        //edittext
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtApellido = (EditText) findViewById(R.id.edtApellido);
        edtDia = (EditText) findViewById(R.id.edtDia);
        edtMes = (EditText) findViewById(R.id.edtMes);
        edtAno = (EditText) findViewById(R.id.edtAno);
        edtNumeroTelefono = (EditText) findViewById(R.id.edtNumeroTelefono);
        edtObservaciones = (EditText) findViewById(R.id.edtObservaciones);
        edtNombreUsuario = (EditText) findViewById(R.id.edtNombreUsuario);
        edtCorreoElectronico = (EditText) findViewById(R.id.edtCorreoElectronico);
        edtContrasena = (EditText) findViewById(R.id.edtContrasena);
        edtContrasenados = (EditText) findViewById(R.id.edtContrasenados);
        galeria = (ImageButton)findViewById(R.id.btnGaleria);
        foto = (ImageButton)findViewById(R.id.btnCamara);

        //button
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);

        imagenUsusario = (ImageView) findViewById(R.id.imgUsuario);

        //evento para imagenButton
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tomarfoto();
                ToastExito("Se abrira la camara");
            }
        });

        //evento para ImgenButon
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkPermission()) {

                    tomarFotoGalreia();
                    ToastExito("Se abrira la galeria");
                }

            }
        });

    }

    //METODOS PARA CONTROLAR LA CAMARA Y LA GALERIA

    //metodo para tomar una foto de la galeria
    private void tomarFotoGalreia(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, COD_GALERIA);
    }

    //metodo que no utilizo para redimensionar una imagen
    private Bitmap redimensionar(Bitmap source) {
        int sizeWidth = source.getWidth();
        int sizeHeight = sizeWidth / 2 + 20;
        int x = 0;
        int y = 0;
        return Bitmap.createBitmap(source, x, y, sizeWidth, sizeHeight);
    }

    //metodo para tomar una foto de la camara
    private void tomarfoto() {

        if (checkPermission()) {

            Intent intentCamara=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intentCamara, COD_CAMARA);
        }


    }

    //para saber que hacer si coger la imagen de la galeria o hacer mediante la camara segun el COD
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == COD_CAMARA) && (resultCode == RESULT_OK)){

                bitmap = (Bitmap) data.getExtras().get("data");
                imagenUsusario.setImageBitmap(bitmap);

            ByteArrayOutputStream baos=new  ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
            byte[] b = baos.toByteArray();
            imagenFoto = Base64.encodeToString(b, Base64.DEFAULT);

        }

        if ((requestCode == COD_GALERIA) && (resultCode == RESULT_OK)){
            /* Recoge la direccion donde se encuentra la imagen */
            Uri uri = data.getData();
            try {
                /* Recoge del enlace un bitmap y lo redimensiona antes de cargarlo
                 * en el imageView */
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                //bitmap = redimensionar(bitmap);

                ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                byte[] b = baos.toByteArray();
                imagenFoto = Base64.encodeToString(b, Base64.DEFAULT);

                this.imagenUsusario.setImageBitmap(bitmap);

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

    //METODOS PARA EL CONTROL DE LOS DATOS QUE VIENEN DE LA API Y EL CONTROL DE ERRORES DE LA FECHA...

    //metodo para ver si est vacio o no un editText
    private boolean estaVacioEditText(){

        if((edtNombre.getText().toString().isEmpty()) || (edtApellido.getText().toString().isEmpty()) || (edtDia.getText().toString().isEmpty()) || (edtMes.getText().toString().isEmpty()) || (edtAno.getText().toString().isEmpty()) || (edtNumeroTelefono.getText().toString().isEmpty()) || (edtObservaciones.getText().toString().isEmpty()) || (edtNombreUsuario.getText().toString().isEmpty()) || (edtCorreoElectronico.getText().toString().isEmpty()) || (edtContrasena.getText().toString().isEmpty()) || (edtContrasenados.getText().toString().isEmpty())){

            return true;

        }else{

            return false;
        }
    }

    //metodo utilizado para cuando haga click en el boton registrar
    private void clickBotonRegistrar(){

        if(estaVacioEditText()){

            ToastError("Son obligatorios los dos campos");
        }else{

            /*if((edtDia.getText().toString().length() != 2) || (edtMes.getText().toString().length() != 2) || (edtAno.getText().toString().length() != 4)){

                ToastError("Los campos de la fecha tienen que ser DD MM AAAA");
            }*/
            /*else{*/

                if((Integer.parseInt(edtMes.getText().toString())) > 12 || (Integer.parseInt(edtMes.getText().toString()) <= 0) || (Integer.parseInt(edtAno.getText().toString()) < 1000) || (Integer.parseInt(edtAno.getText().toString()) > 2019)){

                    ToastError("La fecha esta mal puesta");
                }else {

                    if ((Integer.parseInt(edtMes.getText().toString())) == 2 && (Integer.parseInt(edtDia.getText().toString()) > 28) || (Integer.parseInt(edtDia.getText().toString()) <= 0)) {

                        ToastError("No has puesto bien la fecha, Febrero tiene 28 dias");
                    } else {

                        if (((Integer.parseInt(edtMes.getText().toString()) == 4) || (Integer.parseInt(edtMes.getText().toString()) == 6) || (Integer.parseInt(edtMes.getText().toString()) == 9) || (Integer.parseInt(edtMes.getText().toString()) == 11)) && (Integer.parseInt(edtDia.getText().toString()) > 30) || (Integer.parseInt(edtDia.getText().toString()) <= 0)) {

                            ToastError("No has puesto bien la fecha, ese mes no tiene mas de 30 dias ni menos de 1");
                        } else {

                            if (((Integer.parseInt(edtMes.getText().toString()) == 1) || (Integer.parseInt(edtMes.getText().toString()) == 3) || (Integer.parseInt(edtMes.getText().toString()) == 5) || (Integer.parseInt(edtMes.getText().toString()) == 7) || (Integer.parseInt(edtMes.getText().toString()) == 8) || (Integer.parseInt(edtMes.getText().toString()) == 10) || (Integer.parseInt(edtMes.getText().toString()) == 12)) && (Integer.parseInt(edtDia.getText().toString()) > 31) || (Integer.parseInt(edtDia.getText().toString()) <= 0)) {

                                ToastError("No has puesto bien la fecha, ese mes no tiene mas de 31 dias ni menos de 1");

                            } else {

                                if (!edtContrasena.getText().toString().equals(edtContrasenados.getText().toString())) {

                                    ToastError("Las contraseñas no coinciden");

                                } else {

                                    InstanciaRetrofit.GetDataService service = InstanciaRetrofit.getRetrofitInstance().create(InstanciaRetrofit.GetDataService.class);
                                    Call<ArrayList<usuario>> call = service.getAllPhotos();

                                    call.enqueue(new Callback<ArrayList<usuario>>() {
                                        @Override
                                        public void onResponse(Call<ArrayList<usuario>> call, retrofit2.Response<ArrayList<usuario>> response) {

                                            UsuarioBuscado = generateDataList(response.body());

                                            if (UsuarioBuscado.getId() == 0) {

                                                final String fecha = edtAno.getText().toString() + "-" + edtMes.getText().toString() + "-" + edtDia.getText().toString();

                                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                StringRequest sr = new StringRequest(Request.Method.POST, "http://192.168.1.140/topRecipes/anadir.php", new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {

                                                    }
                                                }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        if (error.networkResponse.statusCode == 400) {
                                                            //TODO: Colocar lo que hará cuando el usuario no se autentique correctamente
                                                        }
                                                    }
                                                }) {
                                                    @Override
                                                    protected Map<String, String> getParams() {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("login", edtNombreUsuario.getText().toString());
                                                        params.put("contrasena", edtContrasena.getText().toString());
                                                        params.put("nombre", edtNombre.getText().toString());
                                                        params.put("apellido", edtApellido.getText().toString());
                                                        params.put("nacimiento", fecha);
                                                        params.put("correo", edtCorreoElectronico.getText().toString());
                                                        params.put("latitud", "40.7127837");
                                                        params.put("altitud", "40.7127837");
                                                        params.put("telefono", edtNumeroTelefono.getText().toString());
                                                        params.put("foto", "imagenFoto");
                                                        params.put("comentarios", edtObservaciones.getText().toString());

                                                        return params;
                                                    }

                                                    @Override
                                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                                        Map<String, String> params = new HashMap<String, String>();
                                                        params.put("Content-Type", "application/x-www-form-urlencoded");
                                                        return params;
                                                    }
                                                };
                                                queue.add(sr);

                                                ToastExito("Usuario registrado correctamente");
                                                Intent intent = new Intent(getApplicationContext(), login.class);
                                                startActivity(intent);

                                            } else {

                                                ToastError("Ya existe un usuario con ese nombre de usuario");

                                            }
                                        }


                                        @Override
                                        public void onFailure(Call<ArrayList<usuario>> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                                            Log.e("error", t.toString());
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
    //}


    //metodo para ver si se esta repitiendo o un el nombre de usuario en el registro
    private usuario generateDataList(ArrayList<usuario> photoList) {

        boolean encontrado = false;
        int contador = 0;
        usuario usuarioEncontrado = null;

        while ((encontrado == false) && (contador < photoList.size())){

            if((photoList.get(contador).getLogin().equals(edtNombreUsuario.getText().toString()))){

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

    //METODOS PARA HACER EL FEEDBACK DEL USUARIO: MENSAJES DE ERROR QUE LE SALEN ECT...

    //metodo para hacer invisible statusbar
    private void hacerInvisibleStatusBar(){

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }


    //muestra toast cuando hay un error
    private void ToastError(String texto){

        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    //muestra un toast cuando algo sale bien
    private void ToastExito(String texto){

        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }
}
