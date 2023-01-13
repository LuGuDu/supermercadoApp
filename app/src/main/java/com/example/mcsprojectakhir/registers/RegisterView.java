package com.example.mcsprojectakhir.registers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.mcsprojectakhir.model.Register;
import com.example.mcsprojectakhir.products.ProductForm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class RegisterView extends AppCompatActivity {

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    int userId;

    public static Vector<Register> RDV = new Vector<>();

    RecyclerView registerRV;

    String timestamp, cart, user;
    int registerId;
    double price;

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
        setContentView(R.layout.activity_registers_view);

        Intent intent = getIntent();
        userId = intent.getIntExtra(HomeForm.SEND_ID, -1);
        registerRV = findViewById(R.id.registerRV);

        RegisterAdapter rdp = new RegisterAdapter(userId, this);
        registerRV.setLayoutManager(new GridLayoutManager(this, 1));

        getRegisters(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                rdp.setRegisters(RDV);
                registerRV.setAdapter(rdp);
            }
        });
    }

    public void getRegisters(final VolleyCallBack callBack) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/GutierrezDuranLucas-p1/catalogo/registros";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                RDV.clear();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONArray jsonArray = response;
                        JSONObject item = jsonArray.getJSONObject(i);

                        registerId = item.getInt("id");
                        timestamp = item.getString("timeStamp");
                        cart = item.getString("carrito");
                        price = item.getDouble("precio");
                        user = item.getString("usuario");

                        Register obj = new Register(registerId, timestamp, cart, price, user);
                        System.out.println(item);
                        RDV.add(obj);
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