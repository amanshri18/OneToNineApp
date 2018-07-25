package com.retailer.user11.onetonineapp.adapter;
/**
 * Created by user11 on 6/1/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import android.support.v7.widget.RecyclerView;
import com.retailer.user11.onetonineapp.activity.CustomerDetailsActivity;
import com.retailer.user11.onetonineapp.R;
import com.retailer.user11.onetonineapp.model.UserModel;

/**
 * Created by user11 on 4/27/2018.
 */
public class CustomerRecyclerAdapter extends RecyclerView.Adapter<CustomerRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<UserModel> user_data;

    public CustomerRecyclerAdapter(Context context, ArrayList<UserModel> data) {
        this.context = context;
        this.user_data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(user_data.get(position));
        UserModel pu = user_data.get(position);
        holder.tx_name.setText(pu.getName());
        holder.tx_email_id.setText(pu.getEmail());
    }

    @Override
    public int getItemCount() {
        return user_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tx_name;
        public TextView tx_email_id;


        public ViewHolder(View itemView) {
            super(itemView);
            tx_name = itemView.findViewById(R.id.tx_name);
            tx_email_id = itemView.findViewById(R.id.tx_email_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String dob = user_data.get(position).getDob();
                    String age = user_data.get(position).getAge();
                    String blood_group = user_data.get(position).getBloodgroup();
                    String name = user_data.get(position).getName();
                    String email = user_data.get(position).getEmail();
                    String id = user_data.get(position).getId();
                    String status = user_data.get(position).getStatus();
                    String mobilenumber = user_data.get(position).getmobile();
                    Intent i = new Intent(view.getContext(), CustomerDetailsActivity.class);
                    i.putExtra("dob", dob);
                    i.putExtra("age", age);
                    i.putExtra("blood_group", blood_group);
                    i.putExtra("name", name);
                    i.putExtra("email", email);
                    i.putExtra("id", id);
                    i.putExtra("status", status);
                    i.putExtra("mobile_number", mobilenumber);
                    context.startActivity(i);
                }
            });
        }
    }

    public void setFilter(ArrayList<UserModel> userModel) {
        user_data = new ArrayList<>();
        user_data.addAll(userModel);
        notifyDataSetChanged();
    }
}
