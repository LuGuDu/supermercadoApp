package com.example.mcsprojectakhir.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class UserForm extends AppCompatActivity {

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    int userId;
    String userIdent, userName, userRole, modificar;

    TextView nameTV, roleTV, userText;
    Button crearUserButton;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), UsersView.class);

        intent.putExtra(SEND_ID, userId);

        startActivity(intent);
        finish();

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_form);

        nameTV = findViewById(R.id.UserNameDetail);
        roleTV = findViewById(R.id.UserRoleDetail);
        userText = findViewById(R.id.textViewUser);
        crearUserButton = findViewById(R.id.userButton);

        Intent intent = getIntent();
        userIdent = intent.getStringExtra("id");
        userName = intent.getStringExtra("nama");
        userRole = intent.getStringExtra("harga");
        modificar = intent.getStringExtra("modificar");

        if(modificar == null) {modificar = "false";}

        if(modificar.equals("true")){
            //MODIFICAR USUARIO
            userText.setText("Modificar Usuario");

            nameTV.setText(userName);
            roleTV.setText(userRole);

            crearUserButton.setText("Modificar usuario");
            crearUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modificateUser(new VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            Intent intent = new Intent(getApplicationContext(), UsersView.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
        } else {
            //CREAR USUARIO
            userText.setText("Nuevo Usuario");
            crearUserButton.setText("Crear usuario");

            crearUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createUser(new VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            Intent intent = new Intent(getApplicationContext(), UsersView.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
        }
    }

    public void createUser(final VolleyCallBack callBack) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/supermercado/catalogo/usuarios/";

        userName = nameTV.getText().toString();
        userRole = roleTV.getText().toString();

        Map<String, String> map = new HashMap<>();
        map.put("nombre", userName);
        map.put("rol", userRole);
        JSONObject jo = new JSONObject(map);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                jo, new Response.Listener<JSONObject>() {
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

    public void modificateUser(final VolleyCallBack callBack) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/supermercado/catalogo/usuarios/"+userIdent;

        userName = nameTV.getText().toString();
        userRole = roleTV.getText().toString();

        Map<String, String> map = new HashMap<>();
        map.put("nombre", userName);
        map.put("rol", userRole);
        JSONObject jo = new JSONObject(map);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,
                jo, new Response.Listener<JSONObject>() {
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