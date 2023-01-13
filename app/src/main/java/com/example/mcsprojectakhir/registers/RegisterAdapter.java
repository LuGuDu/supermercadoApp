package com.example.mcsprojectakhir.registers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mcsprojectakhir.R;
import com.example.mcsprojectakhir.model.Product;
import com.example.mcsprojectakhir.model.Register;
import com.example.mcsprojectakhir.products.ProductForm;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.MyViewHolder> {
    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    int userId;

    private Context ctx;
    private Vector<Register> registers;

    public RegisterAdapter(int userId, Context ctx) {
        this.userId = userId;
        this.ctx = ctx;
    }

    public void setRegisters(Vector<Register> registers) {
        this.registers = registers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.register_component_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.usuarioTV.setText("Usuario: " + registers.get(position).getUsuario());
        holder.precioTV.setText("Precio: " + Double.toString(registers.get(position).getPrecio()) + " €");
        holder.carritoTV.setText("Carrito: " + registers.get(position).getCarrito() + " uds.");
        holder.timeStampTV.setText(registers.get(position).getTimeStamp());
    }

    @Override
    public int getItemCount() {
        return registers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView usuarioTV, precioTV, carritoTV, timeStampTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            usuarioTV = itemView.findViewById(R.id.usuarioRegistroTV);
            precioTV = itemView.findViewById(R.id.precioRegistroTV);
            carritoTV = itemView.findViewById(R.id.carritoRegistroTV);
            timeStampTV = itemView.findViewById(R.id.timeStampTV);
        }
    }
}
