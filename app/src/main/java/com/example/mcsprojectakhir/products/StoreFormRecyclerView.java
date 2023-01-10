package com.example.mcsprojectakhir.products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mcsprojectakhir.HomeForm;
import com.example.mcsprojectakhir.R;
import com.example.mcsprojectakhir.VolleyCallBack;
import com.example.mcsprojectakhir.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class StoreFormRecyclerView extends AppCompatActivity {

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    int userId;

    public static Vector<Product> PDV = new Vector<>();

    RecyclerView productRV;
    Button agregarProductoButton;

    int productId;
    String productName;
    double productPrice;
    int productCantidad;

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
        setContentView(R.layout.activity_store_form_recycler_view);

        agregarProductoButton = findViewById(R.id.crearUsuarioButton);

        agregarProductoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductForm.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        userId = intent.getIntExtra(HomeForm.SEND_ID, -1);
        productRV = findViewById(R.id.userRV);

        ProductAdapter adp = new ProductAdapter(userId, this);
        productRV.setLayoutManager(new GridLayoutManager(this, 1));

        getProducts(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                adp.setProducts(PDV);
                productRV.setAdapter(adp);
            }
        });
    }

    public void getProducts(final VolleyCallBack callBack) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/supermercado/catalogo/productos";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                PDV.clear();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONArray jsonArray = response;
                        JSONObject item = jsonArray.getJSONObject(i);

                        productId = item.getInt("id");
                        productName = item.getString("nombre");
                        productPrice = item.getDouble("precio");
                        productCantidad = item.getInt("cantidad");

                        Product obj = new Product(productId, productName, productPrice, productCantidad);
                        PDV.add(obj);
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

}