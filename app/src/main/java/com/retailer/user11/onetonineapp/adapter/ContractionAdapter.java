package com.retailer.user11.onetonineapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.retailer.user11.onetonineapp.model.LapItem;
import com.retailer.user11.onetonineapp.R;
import com.retailer.user11.onetonineapp.utils.ContractionUtil;

import java.text.SimpleDateFormat;
import java.util.List;
/**
 * Created by user on 13-11-2017.
 */
public class ContractionAdapter extends RecyclerView.Adapter<ContractionAdapter.ContractionViewHolder> {

    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat sdfHourMin = new SimpleDateFormat("H:mm");
    SimpleDateFormat sdfSec = new SimpleDateFormat(":ss");
    Context mContext;
    List<LapItem> lapItems;
    long interval;
    String duration;

    public ContractionAdapter(Context mContext, List<LapItem> lapItems) {
        this.mContext = mContext;
        this.lapItems = lapItems;
    }

    @Override
    public ContractionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lap_view, parent, false);
        return new ContractionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContractionViewHolder holder, int position) {

        holder.lapDate.setText(this.sdfDate.format(Long.valueOf(lapItems.get(position).getDate())));
        holder.lapHourMin.setText(this.sdfHourMin.format(Long.valueOf(lapItems.get(position).getDate())));
        holder.lapSec.setText(this.sdfSec.format(Long.valueOf(lapItems.get(position).getDate())));

        interval = lapItems.get(position).getInterval();
        duration = lapItems.get(position).getDuration();
        String intervalTime;

        if (interval != 0) {
            intervalTime = ContractionUtil.stringForTime(mContext, (int) interval);
        } else {
            intervalTime = "--:--";
        }
        holder.lapInterval.setText(intervalTime);
        holder.lapDuration.setText(lapItems.get(position).getDuration());
    }

    @Override
    public int getItemCount() {
        return lapItems.size();
    }


    public class ContractionViewHolder extends RecyclerView.ViewHolder {

        protected TextView lapDate;
        protected TextView lapHourMin;
        protected TextView lapSec;
        protected TextView lapInterval;
        protected TextView lapDuration;
        protected LinearLayout livClik;

        public ContractionViewHolder(View itemView) {
            super(itemView);
            lapDate = itemView.findViewById(R.id.lap_date);
            lapHourMin = itemView.findViewById(R.id.lap_hour_min);
            lapSec = itemView.findViewById(R.id.lap_sec);
            lapInterval = itemView.findViewById(R.id.lap_interval);
            lapDuration = itemView.findViewById(R.id.lap_duration);
            livClik = itemView.findViewById(R.id.liv_clik);
        }
    }
}
