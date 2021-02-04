package com.juanmanuelcastillonievas.castillonievasjuanmanuel;

public class Carrito {
    private int id;
    private int cantidad;

    public Carrito(){
    }

    public Carrito(int id_producto, int cantidad){
        this.id = id;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
