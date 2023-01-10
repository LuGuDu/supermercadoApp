package com.example.mcsprojectakhir.products;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mcsprojectakhir.R;
import com.example.mcsprojectakhir.VolleyCallBack;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductForm extends AppCompatActivity {

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    int userId;

    TextView nameTV, priceTV, cantidadTV, textViewCreateProduct;
    Button productoButton;
    String productId, productName, productPrice, productCantidad, modificar;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), StoreFormRecyclerView.class);

        intent.putExtra(SEND_ID, userId);

        startActivity(intent);
        finish();

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_form);

        nameTV = findViewById(R.id.productNameDetail);
        priceTV = findViewById(R.id.priceProductDetail);
        cantidadTV = findViewById(R.id.editTNCantidad);
        productoButton = findViewById(R.id.productButton);
        textViewCreateProduct = findViewById(R.id.textViewCreateProduct);

        Intent intent = getIntent();
        productId = intent.getStringExtra("id");
        productName = intent.getStringExtra("nama");
        productPrice = intent.getStringExtra("harga");
        productCantidad = intent.getStringExtra("cantidad");
        modificar = intent.getStringExtra("modificar");

        if(modificar == null) {modificar = "false";}

        if(modificar.equals("true")){
            //MODIFICAR PRODUCTO
            textViewCreateProduct.setText("Modificar Producto");
            productoButton.setText("Modificar Producto");

            nameTV.setText(productName);
            //System.out.println(priceTV + ", " + cantidadTV);
            priceTV.setText(productPrice);
            cantidadTV.setText(productCantidad);

            productoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modificateProduct(new VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            Intent intent = new Intent(getApplicationContext(), StoreFormRecyclerView.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
        } else {
            //CREAR PRODUCTO
            textViewCreateProduct.setText("Crear Producto");
            productoButton.setText("Crear Prroducto");

            productoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    createProduct(new VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            Intent intent = new Intent(getApplicationContext(), StoreFormRecyclerView.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
        }
    }

    public void modificateProduct(final VolleyCallBack callBack) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/supermercado/catalogo/productos/"+productId;

        productName = nameTV.getText().toString();
        productPrice = priceTV.getText().toString();
        productCantidad = cantidadTV.getText().toString();

        Map<String, String> map = new HashMap<>();
        map.put("nombre", productName);
        map.put("precio", productPrice);
        map.put("cantidad", productCantidad);
        JSONObject jo = new JSONObject(map);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jo, new Response.Listener<JSONObject>() {
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

    public void createProduct(final VolleyCallBack callBack) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/supermercado/catalogo/productos/";

        productName = nameTV.getText().toString();
        productPrice = priceTV.getText().toString();
        productCantidad = cantidadTV.getText().toString();

        Map<String, String> map = new HashMap<>();
        map.put("nombre", productName);
        map.put("precio", productPrice);
        map.put("cantidad", productCantidad);
        JSONObject jo = new JSONObject(map);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jo, new Response.Listener<JSONObject>() {
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
}