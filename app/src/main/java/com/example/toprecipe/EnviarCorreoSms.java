/*
 * Realizado por: Samuel Bautista Sanchez
 * DNI: 20227866X
 * Asignatura: Desarrollo de Aplicaciones Multiplataforma
 * */

package com.example.toprecipe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class EnviarCorreoSms extends AppCompatActivity {

    Button btnEnviar;
    EditText edtMensaje;
    Context contexto = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_correo_sms);

        hacerInvisibleStatusBar();



        edtMensaje = (EditText)findViewById(R.id.edtMensaje);
        btnEnviar = (Button)findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                builder.setTitle("Enviar por: ")
                        .setItems(R.array.correo_sms, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                if(which == 0){

                                    String phone = getIntent().getExtras().getString("numeroTelf");
                                    String text = edtMensaje.getText().toString();
                                    SmsManager sms = SmsManager.getDefault();
                                    sms.sendTextMessage(phone, null, text , null, null);
                                    edtMensaje.setText("");
                                }

                                if(which == 1){



                                    String correo = getIntent().getExtras().getString("correo");

                                    String[] TO = {correo};
                                    //String[] CC = {""};

                                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                                    emailIntent.setData(Uri.parse("mailto:"));
                                    emailIntent.setType("text/plain");

                                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                                    emailIntent.putExtra(Intent.EXTRA_CC, TO);
                                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Mensaje para usuario");
                                    emailIntent.putExtra(Intent.EXTRA_TEXT, edtMensaje.getText().toString());
                                    startActivity(Intent.createChooser(emailIntent, "Mensaje para usuario"));
                                    edtMensaje.setText("");
                                }
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    private void hacerInvisibleStatusBar(){

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.parseColor("#AC58FA"));
        }
    }


}
