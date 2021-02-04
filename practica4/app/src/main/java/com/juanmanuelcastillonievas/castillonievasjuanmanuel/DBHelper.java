package com.juanmanuelcastillonievas.castillonievasjuanmanuel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_BD="practica4";
    private static final int VERSION_BD=1;
    private static final String TABLA_PRODUCTOS = "CREATE TABLE Productos(id INT PRIMARY KEY," +
            "nombre STRING, descripcion STRING, precio DOUBLE)";
    private static final String TABLA_CARRITO = "CREATE TABLE Carrito(id INT PRIMARY KEY REFERENCES Productos(id)," +
            "cantidad INT)";

    public DBHelper(@Nullable Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_PRODUCTOS);
        db.execSQL(TABLA_CARRITO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Productos");
        db.execSQL(TABLA_PRODUCTOS);
        db.execSQL("DROP TABLE IF EXISTS Carrito");
        db.execSQL(TABLA_CARRITO);
    }

    public void agregarProductos(Producto p){
        SQLiteDatabase bd = getWritableDatabase();

        if(bd != null){
            bd.execSQL("INSERT INTO Productos VALUES('"+String.valueOf(p.getId())+"','"+p.getNombre()+"','"+p.getDescripcion()+"','"+String.valueOf(p.getPrecio())+"')");
            bd.close();
        }
    }

    public void agregarProductosCarrito(Carrito c){
        SQLiteDatabase bd = getWritableDatabase();

        if(bd != null){
            bd.execSQL("INSERT INTO Carrito VALUES('"+String.valueOf(c.getId())+"','"+String.valueOf(c.getCantidad())+"')");
            bd.close();
        }
    }

    public ArrayList<Producto> getProductos(){
        SQLiteDatabase bd = getWritableDatabase();
        ArrayList<Producto> resultado = new ArrayList<>();

        if(bd != null){
            Cursor c = bd.rawQuery("SELECT * FROM Productos ", null);
            if (c.moveToFirst()){
                do {
                    Producto p = new Producto();
                    p.setId(Integer.parseInt(c.getString(0)));
                    p.setNombre(c.getString(1));
                    p.setDescripcion(c.getString(2));
                    p.setPrecio(Double.parseDouble(c.getString(3)));

                    resultado.add(p);

                } while(c.moveToNext());
            }
            c.close();
            bd.close();
        }

        return resultado;
    }

    public Producto getProducto(int id){
        SQLiteDatabase bd = getWritableDatabase();
        Producto p = new Producto();

        if(bd != null) {
            Cursor c = bd.rawQuery("SELECT * FROM Productos WHERE id="+String.valueOf(id)+";", null);
            if (c.moveToFirst()) {
                p.setId(Integer.parseInt(c.getString(0)));
                p.setNombre(c.getString(1));
                p.setDescripcion(c.getString(2));
                p.setPrecio(Double.parseDouble(c.getString(3)));
            }
            c.close();
        }
        return p;
    }

    public boolean productExists(Producto p){
        SQLiteDatabase bd = getWritableDatabase();

        if(bd != null) {
            Cursor c = bd.rawQuery("SELECT * FROM Productos WHERE id="+String.valueOf(p.getId())+";", null);
            if(c.getCount() <= 0){
                c.close();
                return false;
            }
            c.close();
            return true;
        }
        return false;
    }

    public boolean productoCarritoExists(Carrito carrito){
        SQLiteDatabase bd = getWritableDatabase();

        if(bd != null) {
            Cursor c = bd.rawQuery("SELECT * FROM Carrito WHERE id="+String.valueOf(carrito.getId())+";", null);
            if(c.getCount() <= 0){
                c.close();
                return false;
            }
            c.close();
            return true;
        }
        return false;
    }

    public ArrayList<Carrito> getProductosCarrito(){
        SQLiteDatabase bd = getWritableDatabase();
        ArrayList<Carrito> resultado = new ArrayList<>();

        if(bd != null){
            Cursor c = bd.rawQuery("SELECT * FROM Carrito ", null);
            if (c.moveToFirst()){
                do {
                    Carrito carrito = new Carrito();
                    carrito.setId(Integer.parseInt(c.getString(0)));
                    carrito.setCantidad(Integer.parseInt(c.getString(1)));

                    resultado.add(carrito);

                } while(c.moveToNext());
            }
            c.close();
            bd.close();
        }

        return resultado;
    }

    public void eliminarProductoCarrito(int id){
        SQLiteDatabase bd = getWritableDatabase();
        if(bd != null) {
            bd.execSQL("DELETE FROM Carrito WHERE id='"+String.valueOf(id)+"'");
        }
    }
}
