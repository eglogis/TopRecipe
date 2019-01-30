package com.example.toprecipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

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

        if(v.getId() == findViewById(R.id.btnRegistrar).getId()){

            clickBotonRegistrar();

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
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);

    }

    private void clickBotonRegistrar(){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST,"http://192.168.1.32/topRecipes/anadir.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta:",response);
                //Gson g = new Gson();
                //UserToken p = g.fromJson(response, UserToken.class);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse.statusCode == 400)
                {
                    //TODO: Colocar lo que hará cuando el usuario no se autentique correctamente
                }
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("login", "alberto");
                params.put("contrasena", "123");
                params.put("nombre", "Alberto");
                params.put("apellido", "Lucena");
                params.put("nacimiento", "2000-03-04");
                params.put("correo", "alberto@gmail.com");
                params.put("latitud", "40.7127837");
                params.put("altitud", "40.7127837");
                params.put("telefono","957650935");
                params.put("foto","fotaso");
                params.put("comentarios", "Profesional");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);


    }
}
