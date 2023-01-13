package com.example.mcsprojectakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mcsprojectakhir.cart.CarritoView;
import com.example.mcsprojectakhir.model.Product;
import com.example.mcsprojectakhir.model.UserData;
import com.example.mcsprojectakhir.products.StoreFormRecyclerView;
import com.example.mcsprojectakhir.registers.RegisterView;
import com.example.mcsprojectakhir.users.UsersView;

import java.util.Vector;

public class HomeForm extends AppCompatActivity {

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    int userId;

    Button productoButton, usuarioButton, carritoButton, historialButton, tiendasButton;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeForm.class);
        startActivity(intent);
        finish();

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_form);

        productoButton = findViewById(R.id.buttonProducts);
        usuarioButton = findViewById(R.id.buttonUsuarios);
        carritoButton = findViewById(R.id.buttonCarrito);
        historialButton = findViewById(R.id.buttonRegisters);
        tiendasButton = findViewById(R.id.buttonShops);

        productoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreFormRecyclerView.class);
                startActivity(intent);
                finish();
            }
        });

        usuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UsersView.class);
                startActivity(intent);
                finish();
            }
        });

        carritoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CarritoView.class);
                startActivity(intent);
                finish();
            }
        });

        historialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterView.class);
                startActivity(intent);
                finish();
            }
        });

        tiendasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GoogleMapsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        MainActivity.UDV.clear();

        Intent intent = getIntent();
        userId = intent.getIntExtra(MainActivity.SEND_ID, -1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case R.id.menuHome:
                return true;
            case R.id.menuStore:

                intent = new Intent(getApplicationContext(), StoreFormRecyclerView.class);
                intent.putExtra(SEND_ID, userId);

                startActivity(intent);
                finish();

                return true;

            case R.id.menuUsers:
                intent = new Intent(getApplicationContext(), UsersView.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.menuCart:
                intent = new Intent(getApplicationContext(), CarritoView.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.menuRegister:
                intent = new Intent(getApplicationContext(), RegisterView.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.menuShops:
                intent = new Intent(getApplicationContext(), GoogleMapsActivity.class);
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}