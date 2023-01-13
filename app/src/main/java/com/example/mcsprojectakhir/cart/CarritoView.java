package com.example.mcsprojectakhir.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mcsprojectakhir.HomeForm;
import com.example.mcsprojectakhir.R;
import com.example.mcsprojectakhir.VolleyCallBack;
import com.example.mcsprojectakhir.model.Product;
import com.example.mcsprojectakhir.products.ProductAdapter;
import com.example.mcsprojectakhir.products.ProductForm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CarritoView extends AppCompatActivity {

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    int userId;

    public static Vector<Product> PDV = new Vector<>();
    public static Vector<Integer> SDV = new Vector<>();

    RecyclerView cartRV;
    TextView precioFinalTV;
    Button comprarButton;

    int productId;
    String productName;
    double productPrice;
    int productCantidad;
    String precioFinal = "0.0";

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeForm.class);
        intent.putExtra(SEND_ID, userId);

        startActivity(intent);
        finish();

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);

        Intent intent = getIntent();
        userId = intent.getIntExtra(HomeForm.SEND_ID, -1);
        cartRV = findViewById(R.id.userRV);

        CartAdapter cdp = new CartAdapter(userId, this);
        cartRV.setLayoutManager(new GridLayoutManager(this, 1));

        comprarButton = findViewById(R.id.comprarButton);

        comprarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comprar(new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(getApplicationContext(), CarritoView.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        getCarrito(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                cdp.setProducts(PDV);
                cdp.setStocks(SDV);
                cartRV.setAdapter(cdp);
            }
        });

        precioFinalTV = findViewById(R.id.precioFinalTV);

        getPrecio(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                precioFinalTV.setText("Precio final: " + precioFinal + " €");
            }
        });
    }

    public void comprar(final VolleyCallBack callBack) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/GutierrezDuranLucas-p1/catalogo/carritos/203";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callBack.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    public void getCarrito(final VolleyCallBack callBack) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/GutierrezDuranLucas-p1/catalogo/carritos/203";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                PDV.clear();
                SDV.clear();

                try {
                    JSONArray productlist = (JSONArray) response.get("productos");
                    JSONArray cantidadlist = (JSONArray) response.get("cantidades");

                    for (int i = 0; i < productlist.length(); i++) {
                        JSONObject item = (JSONObject) productlist.get(i);

                        productId = item.getInt("id");
                        productName = item.getString("nombre");
                        productPrice = item.getDouble("precio");
                        productCantidad = item.getInt("cantidad");

                        Product obj = new Product(productId, productName, productPrice, productCantidad);
                        PDV.add(obj);
                        SDV.add((Integer) cantidadlist.get(i));
                        callBack.onSuccess();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    public void getPrecio(final VolleyCallBack callBack) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/GutierrezDuranLucas-p1/catalogo/carritos/203/precio";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    precioFinal = response.getString("precio");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callBack.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

}