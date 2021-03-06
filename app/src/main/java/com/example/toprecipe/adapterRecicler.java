/*
 * Realizado por: Samuel Bautista Sanchez
 * DNI: 20227866X
 * Asignatura: Desarrollo de Aplicaciones Multiplataforma
 * */


package com.example.toprecipe;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class adapterRecicler extends RecyclerView.Adapter<adapterRecicler.ViewHolder>{

    respuestaAlClick respuesta;

    private ArrayList<receta> recetas = new ArrayList();
    private LayoutInflater mInflater;
    private AdapterView.OnItemClickListener mclicklistener;
    private Context context;

    // data is passed into the constructor
    adapterRecicler(Context context, ArrayList<receta> recetas) {
        respuesta = (respuestaAlClick)context;
        this.mInflater = LayoutInflater.from(context);
        this.recetas = recetas;
        this.context = context;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_reciclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final receta receta = recetas.get(position);
        holder.txvNombre.setText(recetas.get(position).getNombre());
        holder.txvDificultad.setText(recetas.get(position).getDificultad());
        holder.txvTiempo.setText(recetas.get(position).getTiempo());
        holder.fotoReceta.setImageBitmap(recetas.get(position).getImagenBitmap());

        holder.view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), receta.getNombre(), Toast.LENGTH_SHORT).show();
                respuesta.onrespuesAlClick(receta);

            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(view.getContext(), "Estoy dejando pulsado sobre" + receta.getNombre(), Toast.LENGTH_SHORT).show();



                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return recetas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txvNombre, txvDificultad, txvTiempo;
        View view;
        ImageView fotoReceta;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            txvNombre = itemView.findViewById(R.id.txvNombreReceta);
            txvDificultad = itemView.findViewById(R.id.txvDificultad);
            txvTiempo = itemView.findViewById(R.id.txvTiempo);
            fotoReceta = itemView.findViewById(R.id.imgReceta);
            //itemView.setOnClickListener(this);
        }
    }

    receta getItem(int id) {
        return recetas.get(id);
    }

    public interface respuestaAlClick{
        public void onrespuesAlClick(receta receta);
    }
}
