package com.example.mcsprojectakhir.model;

public class Register {

    private int id;
    private String timeStamp;
    private String carrito;
    private double precio;
    private String usuario;

    public Register(int id, String timeStamp, String carrito, double precio, String usuario) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.carrito = carrito;
        this.precio = precio;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCarrito() {
        return carrito;
    }

    public void setCarrito(String carrito) {
        this.carrito = carrito;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
