package com.example.mcsprojectakhir.users;

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
import com.example.mcsprojectakhir.model.User;

import java.util.Vector;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    int userId;

    private Context ctx;
    private Vector<User> users = new Vector<>();
    Button buttonBorrar;

    public UserAdapter(int userId, Context ctx) {
        this.userId = userId;
        this.ctx = ctx;
    }

    public void setUsers(Vector<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.user_component_view, parent, false);
        buttonBorrar = view.findViewById(R.id.deleteButton);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.nameTV.setText(users.get(position).getName());
        holder.roleTV.setText("Rol: " + users.get(position).getRole());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, UserForm.class);
                intent.putExtra("id", Integer.toString(users.get(position).getId()));
                intent.putExtra("nama", users.get(position).getName());
                intent.putExtra("harga", users.get(position).getRole());
                intent.putExtra("modificar", "true");

                ctx.startActivity(intent);
                ((UsersView)ctx).finish();
            }
        });

        buttonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(ctx);
                String userIdent = Integer.toString(users.get(position).getId());
                String url = "http://10.0.2.2:8080/GutierrezDuranLucas-p1/catalogo/usuarios/"+userIdent;
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                requestQueue.add(request);

                Intent intent = new Intent(ctx, UsersView.class);
                ctx.startActivity(intent);
                ((UsersView)ctx).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV, roleTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.userNameTV);
            roleTV = itemView.findViewById(R.id.roleTV);
        }
    }
}
