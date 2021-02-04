package com.juanmanuelcastillonievas.castillonievasjuanmanuel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ThirdFragment extends Fragment implements CarritoCallback{

    RecyclerView recyclerView;
    TextView total;
    CarritoAdapter carritoAdapter;
    ArrayList<Carrito> carrito;

    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        final DBHelper db = new DBHelper(getContext());

        carrito = db.getProductosCarrito();
        ArrayList<Producto> productos = db.getProductos();

        recyclerView = view.findViewById(R.id.recyclerViewCarrito);
        total = view.findViewById(R.id.total);

        double totalPrecio = 0;
        for(Carrito c: carrito){
            for(Producto p: productos){
                if(p.getId() == c.getId()){
                    totalPrecio+= c.getCantidad()*productos.get(productos.indexOf(p)).getPrecio();
                    break;
                }
            }
        }

        total.setText("Total a pagar: "+String.valueOf(totalPrecio)+"€");

        carritoAdapter = new CarritoAdapter(getContext(), carrito, this);
        recyclerView.setAdapter(carritoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        return view;
    }

    @Override
    public void recargaCarrito() {
        final DBHelper db = new DBHelper(getContext());

        carrito = db.getProductosCarrito();
        ArrayList<Producto> productos = db.getProductos();

        double totalPrecio = 0;
        for(Carrito c: carrito){
            for(Producto p: productos){
                if(p.getId() == c.getId()){
                    totalPrecio+= c.getCantidad()*productos.get(productos.indexOf(p)).getPrecio();
                    break;
                }
            }
        }

        total.setText("Total a pagar: "+String.valueOf(totalPrecio)+"€");

    }
}