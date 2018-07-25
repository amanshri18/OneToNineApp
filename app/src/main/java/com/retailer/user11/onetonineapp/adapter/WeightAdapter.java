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
import com.retailer.user11.onetonineapp.model.WeightItem;
/**
 * Created by user11 on 4/27/2018.
 */
public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.ViewHolder> {
    private Context context;
    private ArrayList<WeightItem> user_data;

    public WeightAdapter(Context context, ArrayList<WeightItem> data) {
        this.context = context;
        this.user_data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weight_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(user_data.get(position));
        WeightItem pu = user_data.get(position);
        holder.tx_date.setText(pu.getGet_date());
        holder.tx_weight.setText(pu.getGet_weight());
        holder.tx_change.setText(pu.getGet_difference());
        holder.tx_week.setText(pu.getWeek());
    }

    @Override
    public int getItemCount() {
        return user_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tx_date;
        public TextView tx_week;
        public TextView tx_weight;
        public TextView tx_change;

        public ViewHolder(View itemView) {
            super(itemView);
            tx_date = itemView.findViewById(R.id.tx_date);
            tx_week = itemView.findViewById(R.id.tx_week);
            tx_weight = itemView.findViewById(R.id.tx_weight);
            tx_change = itemView.findViewById(R.id.tx_change);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                }
            });
        }
    }

   /* public void setFilter(ArrayList<UserModel> userModel) {
        user_data = new ArrayList<>();
        user_data.addAll(userModel);
        notifyDataSetChanged();
    }*/
}



/*
package com.retailer.user11.onetonineapp.adapter;

*/
/*
public class WeightAdapter {
}
*//*

//package com.retailer.user11.onetonineapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.retailer.user11.onetonineapp.activity.WeightActivity;
import com.retailer.user11.onetonineapp.model.KickItem;
import com.retailer.user11.onetonineapp.model.LapItem;
import com.retailer.user11.onetonineapp.R;
import com.retailer.user11.onetonineapp.model.WeightItem;
import com.retailer.user11.onetonineapp.utils.ContractionUtil;

import java.text.SimpleDateFormat;
import java.util.List;

*/
/**
 * Created by user on 13-11-2017.
 *//*


public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.ViewHolder> {
    Context mContext;
    List<WeightItem> WeightItem;

    public WeightAdapter(Context mContext, List<WeightItem> WeightItem) {
        this.mContext = mContext;
        this.WeightItem = WeightItem;
    }
    */
/*public WeightAdapter(WeightActivity weightActivity, List<WeightItem> lapItems) {

    }*//*

    @NonNull
    @Override
    public WeightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weight_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightAdapter.ViewHolder holder, int position) {
        holder.txDate.setText(WeightItem.get(position).getGet_date());
        holder.txStart.setText(WeightItem.get(position).getGet_weight());
        holder.txDuration.setText(WeightItem.get(position).getWeek());
        holder.txDuration.setText(WeightItem.get(position).getGet_difference());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView txDate;
        protected TextView txStart;
        protected TextView txDuration;
        protected TextView txKicks;
        protected LinearLayout livClik;

        public ViewHolder(View itemView) {
            super(itemView);
            txDate = itemView.findViewById(R.id.tx_date);
            txStart = itemView.findViewById(R.id.tx_start);
            txDuration = itemView.findViewById(R.id.tx_duration);
            txKicks = itemView.findViewById(R.id.tx_kicks);
        }
    }
}
*/
