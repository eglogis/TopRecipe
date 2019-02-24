/*
 * Realizado por: Samuel Bautista Sanchez
 * DNI: 20227866X
 * Asignatura: Desarrollo de Aplicaciones Multiplataforma
 * */

package com.example.toprecipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class usuario {

    @Expose
    @SerializedName("id")
    int id;
    @Expose
    @SerializedName("login")
    String login;
    @Expose
    @SerializedName("contrasena")
    String contrasena;
    @Expose
    @SerializedName("nombre")
    String nombre;
    @Expose
    @SerializedName("apellido")
    String apellido;
    @Expose
    @SerializedName("nacimiento")
    String nacimiento;
    @Expose
    @SerializedName("correo")
    String correo;
    @Expose
    @SerializedName("latitud")
    Double latitud;
    @Expose
    @SerializedName("altitud")
    Double altitud;
    @Expose
    @SerializedName("telefono")
    String telefono;
    @Expose
    @SerializedName("foto")
    String foto;
    @Expose
    @SerializedName("fechaAlta")
    String fechaAlta;
    @SerializedName("comentarios")
    String comentarios;


    public usuario(int id, String login, String contrasena, String nombre, String apellido, String nacimiento, String correo, Double latitud, Double altitud, String telefono, String foto, String fechaAlta, String comentarios) {
        this.id = id;
        this.login = login;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacimiento = nacimiento;
        this.correo = correo;
        this.latitud = latitud;
        this.altitud = altitud;
        this.telefono = telefono;
        this.foto = foto;
        this.fechaAlta = fechaAlta;
        this.comentarios = comentarios;
    }

    public usuario(int id, String login, String contrasena, String nombre, String apellido, String nacimiento, String correo, Double latitud, Double altitud, String telefono, String foto, String comentarios) {
        this.id = id;
        this.login = login;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacimiento = nacimiento;
        this.correo = correo;
        this.latitud = latitud;
        this.altitud = altitud;
        this.telefono = telefono;
        this.foto = foto;
        this.comentarios = comentarios;
    }
    public usuario() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getAltitud() {
        return altitud;
    }

    public void setAltitud(Double altitud) {
        this.altitud = altitud;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
