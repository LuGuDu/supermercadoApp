package com.example.mcsprojectakhir.cart;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mcsprojectakhir.R;
import com.example.mcsprojectakhir.VolleyCallBack;
import com.example.mcsprojectakhir.model.Product;
import com.example.mcsprojectakhir.products.ProductForm;
import com.example.mcsprojectakhir.products.StoreFormRecyclerView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    int userId;

    private Context ctx;
    private Vector<Product> products;
    private Vector<Integer> stocks;
    Button buttonBorrar;

    public CartAdapter(int userId, Context ctx) {
        this.userId = userId;
        this.ctx = ctx;
    }

    public void setProducts(Vector<Product> products) {
        this.products = products;
    }

    public void setStocks(Vector<Integer> stocks) {
        this.stocks = stocks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.cart_component_view, parent, false);
        buttonBorrar = view.findViewById(R.id.deleteCarritoButton);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.productTV.setText(products.get(position).getNombre());
        holder.priceTV.setText("Precio unidad: " + products.get(position).getPrecio() + " €");
        holder.cantidadTV.setText("cantidad: " + stocks.get(position) + " uds.");

        buttonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quitarProduct(new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        System.out.println("hola");
                        Intent intent = new Intent(ctx, CarritoView.class);
                        ctx.startActivity(intent);
                        ((CarritoView) ctx).finish();
                    }
                }, position);
            }
        });
    }

    public void quitarProduct(final VolleyCallBack callBack, int position) {
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        String url = "http://10.0.2.2:8080/GutierrezDuranLucas-p1/catalogo/carritos/203/quitar";

        int productId = products.get(position).getId();

        Map<String, Integer> map = new HashMap<>();
        map.put("productoId", productId);
        map.put("cantidad", 1);
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

    @Override
    public int getItemCount() {
        return products.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productTV, priceTV, cantidadTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productTV = itemView.findViewById(R.id.productNameTV);
            priceTV = itemView.findViewById(R.id.precioTV);
            cantidadTV = itemView.findViewById(R.id.cantidadTV);
        }
    }
}
