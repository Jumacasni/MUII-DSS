package com.juanmanuelcastillonievas.castillonievasjuanmanuel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Producto> productos;
    private Spinner spinner;

    ProductoAdapter(Context context, ArrayList productos){
        this.context = context;
        this.productos = productos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(String.valueOf(productos.get(position).getId()));
        holder.nombre.setText(String.valueOf(productos.get(position).getNombre()));
        holder.descripcion.setText(String.valueOf(productos.get(position).getDescripcion()));
        holder.precio.setText(String.valueOf(productos.get(position).getPrecio())+"€/unidad");
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener{
        TextView id, nombre, descripcion, precio;
        Button button;
        String cantidad;
        private RequestQueue queue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            final DBHelper db = new DBHelper(context);

            id = itemView.findViewById(R.id.id_producto);
            nombre = itemView.findViewById(R.id.nombre_producto);
            descripcion = itemView.findViewById(R.id.descripcion_producto);
            precio = itemView.findViewById(R.id.precio_producto);
            spinner = itemView.findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.numbers, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
            itemView.findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Carrito carrito = new Carrito();
                    carrito.setId(Integer.parseInt(id.getText().toString()));
                    carrito.setCantidad(Integer.parseInt(cantidad.toString()));

                    if(!db.productoCarritoExists(carrito)) {
                        db.agregarProductosCarrito(carrito);

                        hacerPost(id.getText().toString(), cantidad.toString());

                        Toast.makeText((Activity) context, "Has añadido " + cantidad.toString() + " unidades del producto " + db.getProducto(Integer.parseInt(id.getText().toString())).getNombre() + " al carrito",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText((Activity) context, "ERROR: Ya tienes el producto "+db.getProducto(Integer.parseInt(id.getText().toString())).getNombre() + " en el carrito",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            cantidad = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

        public void hacerPost(String id, String cantidad){
            queue = Volley.newRequestQueue(context);
            String url = "http://"+MainActivity.localhost+":8080/CastilloNievasJuanManuel/rest/catalogo/carrito/"+id;
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {

                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("cantidad", cantidad);
                    return params;
                }
            };

            queue.add(postRequest);
        }
    }
}
