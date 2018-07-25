package com.retailer.user11.onetonineapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.retailer.user11.onetonineapp.model.KickItem;
import com.retailer.user11.onetonineapp.R;

import java.util.List;


/**
 * Created by user on 13-11-2017.
 */

public class KickHistoryAdapter extends RecyclerView.Adapter<KickHistoryAdapter.ContractionViewHolder> {


    Context mContext;
    List<KickItem> kickItems;

    public KickHistoryAdapter(Context mContext, List<KickItem> kickItems) {
        this.mContext = mContext;
        this.kickItems = kickItems;
    }

    @Override
    public ContractionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kick_view, parent, false);
        return new ContractionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContractionViewHolder holder, int position) {

        holder.txDate.setText(kickItems.get(position).getDate());
        holder.txStart.setText(kickItems.get(position).getStart());
        holder.txDuration.setText(kickItems.get(position).getDuration());
        holder.txKicks.setText(kickItems.get(position).getKicks());

    }

    @Override
    public int getItemCount() {
        return kickItems.size();
    }

    public static class ContractionViewHolder extends RecyclerView.ViewHolder {

        protected TextView txDate;
        protected TextView txStart;
        protected TextView txDuration;
        protected TextView txKicks;
        protected LinearLayout livClik;

        public ContractionViewHolder(View itemView) {
            super(itemView);
            txDate = itemView.findViewById(R.id.tx_date);
            txStart = itemView.findViewById(R.id.tx_start);
            txDuration = itemView.findViewById(R.id.tx_duration);
            txKicks = itemView.findViewById(R.id.tx_kicks);
        }
    }
}
