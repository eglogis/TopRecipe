package com.example.toprecipe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.MaskFilter;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class adapterUsuario extends RecyclerView.Adapter<adapterUsuario.ViewHolder> implements Serializable {

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<usuario> usuarios = new ArrayList();

    adapterUsuario(Context context, ArrayList<usuario> usuarios) {

        this.mInflater = LayoutInflater.from(context);
        this.usuarios = usuarios;
        this.context = context;
    }
    @Override
    @NonNull
    public adapterUsuario.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_recicle_usuarios, parent, false);
        return new adapterUsuario.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull adapterUsuario.ViewHolder holder, int position) {

        final usuario usuario = usuarios.get(position);

        holder.txvNombre.setText(usuarios.get(position).getNombre());
        holder.observaciones.setText(usuarios.get(position).getComentarios());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, usuario.getNombre(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Accion")
                        .setItems(R.array.acciones, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                if(which == 0){

                                    Intent intent = new Intent(context, MapsActivity.class);
                                    intent.putExtra("latitud", usuario.getLatitud());
                                    intent.putExtra("longitud", usuario.getAltitud());
                                    intent.putExtra("nombre", usuario.getNombre());
                                    context.startActivity(intent);
                                    Toast.makeText(context, usuario.getNombre(), Toast.LENGTH_SHORT).show();
                                }
                                if(which == 2 || which == 3){

                                    Intent intent = new Intent(context, EnviarCorreoSms.class);
                                    intent.putExtra("correo", usuario.getCorreo());
                                    intent.putExtra("numeroTelf", usuario.getTelefono());
                                    context.startActivity(intent);
                                }
                                if(which == 4){

                                    String telefono = "tel:" + usuario.getTelefono();
                                    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(telefono)));
                                }
                            }
                        });
                builder.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txvNombre, observaciones;
        View view;
        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            observaciones = itemView.findViewById(R.id.txvObservaciones);
            txvNombre = itemView.findViewById(R.id.txvNombre);

        }
    }
    usuario getItem(int id) {
        return usuarios.get(id);
    }
}
