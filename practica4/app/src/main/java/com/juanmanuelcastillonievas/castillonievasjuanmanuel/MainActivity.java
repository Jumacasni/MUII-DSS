package com.juanmanuelcastillonievas.castillonievasjuanmanuel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;



public class MainActivity extends AppCompatActivity {
    static String localhost = "192.168.1.130";
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DBHelper db = new DBHelper(getApplicationContext());
        db.onUpgrade(db.getWritableDatabase(),0,0);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);

        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.firstFragment);
        topLevelDestinations.add(R.id.secondFragment);
        topLevelDestinations.add(R.id.thirdFragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        actualizarDB(db);

        // Se actualiza la base de datos si el servidor está activo
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://"+localhost+":8080/CastilloNievasJuanManuel/";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        db.onUpgrade(db.getWritableDatabase(),0,0);
                        try {
                            obtenerProductos(db);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            obtenerCarrito(db);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("No hay conexión con el servidor");

            }
        });
        queue.add(stringRequest);
    }

    private void actualizarDB(DBHelper db) {
        if(db.getProductos().size() == 0){
            Producto p1 = new Producto(1, "Móvil", 700.0);
            p1.setDescripcion("Esta es la descripción del móvil");

            Producto p2 = new Producto(2, "Ordenador", 1000.0);
            p2.setDescripcion("Esta es la descripción del ordenador");

            Producto p3 = new Producto(3, "Gafas", 100.0);
            p3.setDescripcion("Esta es la descripción de las gafas");

            db.agregarProductos(p1);
            db.agregarProductos(p2);
            db.agregarProductos(p3);
        }
    }

    private void obtenerProductos(DBHelper db) throws IOException {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://"+localhost+":8080/CastilloNievasJuanManuel/rest/catalogo/xml";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseXMLProductos(response, db);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("No se puede obtener el catálogo");
            }
        });
        queue.add(stringRequest);
    }

    public void parseXMLProductos(String response, DBHelper db) {
        try {
            Producto p = new Producto();
            String text = "";
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput( new StringReader( response) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = xpp.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("producto")) {
                            p = new Producto();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("producto")) {
                            if(!db.productExists(p)) {
                                db.agregarProductos(p);
                            }
                        }else if (tagname.equalsIgnoreCase("id")) {
                            p.setId(Integer.parseInt(text));
                        }  else if (tagname.equalsIgnoreCase("nombre")) {
                            p.setNombre(text);
                        } else if (tagname.equalsIgnoreCase("descripcion")) {
                            p.setDescripcion(text);
                        } else if (tagname.equalsIgnoreCase("precio")) {
                            p.setPrecio(Double.parseDouble(text));
                        }
                        break;

                    default:
                        break;
                }
                eventType = xpp.next();
            }
            
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void obtenerCarrito(DBHelper db) throws IOException {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://"+localhost+":8080/CastilloNievasJuanManuel/rest/catalogo/carrito/xml";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseXMLCarrito(response, db);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("No se puede obtener el carrito");
            }
        });
        queue.add(stringRequest);
    }

    public void parseXMLCarrito(String response, DBHelper db) {
        try {
            Carrito c = new Carrito();
            String text = "";
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput( new StringReader( response) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = xpp.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("carrito")) {
                            c = new Carrito();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("carrito")) {
                            if(!db.productoCarritoExists(c)) {
                                db.agregarProductosCarrito(c);
                            }
                        }else if (tagname.equalsIgnoreCase("id")) {
                            c.setId(Integer.parseInt(text));
                        }else if (tagname.equalsIgnoreCase("cantidad")) {
                            c.setCantidad(Integer.parseInt(text));
                        }
                        break;

                    default:
                        break;
                }
                eventType = xpp.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}