package com.juanmanuelcastillonievas.castillonievasjuanmanuel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondFragment extends Fragment {

    RecyclerView recyclerView;
    ProductoAdapter productoAdapter;
    ArrayList<Producto> productos;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        final DBHelper db = new DBHelper(getContext());

        productos = db.getProductos();

        recyclerView = view.findViewById(R.id.recyclerView);

        productoAdapter = new ProductoAdapter(getContext(), productos);
        recyclerView.setAdapter(productoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}