package com.juanmanuelcastillonievas.castillonievasjuanmanuel;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Carrito> carrito;
    private CarritoCallback carritoCallback;

    CarritoAdapter(Context context, ArrayList carrito, CarritoCallback carritoCallback){
        this.context = context;
        this.carrito = carrito;
        this.carritoCallback = carritoCallback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_carrito, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DBHelper db = new DBHelper(context);
        int id_producto = carrito.get(position).getId();
        Producto p = db.getProducto(id_producto);

        holder.id.setText(String.valueOf(p.getId()));
        holder.nombre.setText(String.valueOf(p.getNombre()));
        holder.descripcion.setText(String.valueOf(p.getDescripcion()));
        holder.precio.setText(String.valueOf(p.getPrecio())+"€/unidad");
        holder.cantidad.setText("Cantidad: "+String.valueOf(carrito.get(position).getCantidad()));
    }

    @Override
    public int getItemCount() {
        return carrito.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id, nombre, descripcion, precio, cantidad;
        Button button;
        private RequestQueue queue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            final DBHelper db = new DBHelper(context);

            id = itemView.findViewById(R.id.id_producto_carrito);
            nombre = itemView.findViewById(R.id.nombre_producto_carrito);
            descripcion = itemView.findViewById(R.id.descripcion_producto_carrito);
            precio = itemView.findViewById(R.id.precio_producto_carrito);
            cantidad = itemView.findViewById(R.id.cantidad_producto);

            itemView.findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.eliminarProductoCarrito(Integer.parseInt(id.getText().toString()));

                    hacerPost(id.getText().toString());

                    Toast.makeText((Activity) context, "Has eliminado el producto " + db.getProducto(Integer.parseInt(id.getText().toString())).getNombre() + " del carrito",
                            Toast.LENGTH_SHORT).show();

                    for(Carrito ca : carrito){
                        if(ca.getId() == Integer.parseInt(id.getText().toString())){
                            carrito.remove(carrito.indexOf(ca));
                            break;
                        }
                    }

                    notifyDataSetChanged();
                    carritoCallback.recargaCarrito();
                }
            });
        }

        public void hacerPost(String id){
            queue = Volley.newRequestQueue(context);
            String url = "http://"+MainActivity.localhost+":8080/CastilloNievasJuanManuel/rest/catalogo/carrito/eliminar/"+id;
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
            };

            queue.add(postRequest);
        }

    }
}
