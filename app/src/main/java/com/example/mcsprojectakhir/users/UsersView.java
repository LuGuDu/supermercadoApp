package com.example.mcsprojectakhir.users;

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
import com.example.mcsprojectakhir.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class UsersView extends AppCompatActivity {

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    int userId;

    public static Vector<User> UDV = new Vector<>();

    RecyclerView userRV;
    Button crearUsuarioButton;

    int userIdent;
    String userName;
    String userRole;

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
        setContentView(R.layout.activity_users_form_recycler_view);

        crearUsuarioButton = findViewById(R.id.crearUsuarioButton);

        crearUsuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserForm.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        userId = intent.getIntExtra(HomeForm.SEND_ID, -1);
        userRV = findViewById(R.id.userRV);

        UserAdapter udp = new UserAdapter(userId, this);
        userRV.setLayoutManager(new GridLayoutManager(this, 1));


        getUsers(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                udp.setUsers(UDV);
                userRV.setAdapter(udp);
            }
        });

    }

    public void getUsers(final VolleyCallBack callBack) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/supermercado/catalogo/usuarios";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                UDV.clear();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONArray jsonArray = response;
                        JSONObject item = jsonArray.getJSONObject(i);

                        userIdent = item.getInt("id");
                        userName = item.getString("nombre");
                        userRole = item.getString("rol");

                        User obj = new User(userIdent, userName, userRole);
                        UDV.add(obj);
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