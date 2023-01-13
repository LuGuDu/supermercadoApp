package com.example.mcsprojectakhir.products;

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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    int userId;

    private Context ctx;
    private Vector<Product> products;
    Button buttonBorrar;
    Button buttonAddCarrito;

    public ProductAdapter(int userId, Context ctx) {
        this.userId = userId;
        this.ctx = ctx;
    }

    public void setProducts(Vector<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.product_component_view, parent, false);
        buttonBorrar = view.findViewById(R.id.deleteButton);
        buttonAddCarrito = view.findViewById(R.id.addCarritoButton);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.productTV.setText(products.get(position).getNombre());
        holder.priceTV.setText("Precio: " + products.get(position).getPrecio() + " €");
        holder.cantidadTV.setText("cantidad: " + products.get(position).getCantidad() + " uds.");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ProductForm.class);

                intent.putExtra("id", Integer.toString(products.get(position).getId()));
                intent.putExtra("nama", products.get(position).getNombre());
                intent.putExtra("harga", Double.toString(products.get(position).getPrecio()));
                intent.putExtra("cantidad", Integer.toString(products.get(position).getCantidad()));
                intent.putExtra("modificar", "true");

                ctx.startActivity(intent);
                ((StoreFormRecyclerView)ctx).finish();
            }
        });

        buttonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(ctx);
                String productId = Integer.toString(products.get(position).getId());
                String url = "http://10.0.2.2:8080/GutierrezDuranLucas-p1/catalogo/productos/"+productId;
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                requestQueue.add(request);

                Intent intent = new Intent(ctx, StoreFormRecyclerView.class);
                ctx.startActivity(intent);
                ((StoreFormRecyclerView)ctx).finish();
            }
        });

        buttonAddCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(ctx);
                String url = "http://10.0.2.2:8080/GutierrezDuranLucas-p1/catalogo/carritos/203";
                int productId = products.get(position).getId();

                Map<String, Integer> map = new HashMap<>();
                map.put("productoId", productId);
                map.put("cantidad", 1);
                JSONObject jo = new JSONObject(map);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jo, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                requestQueue.add(request);
            }
        });
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
