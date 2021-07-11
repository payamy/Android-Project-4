package com.example.rhodium;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rhodium.R;
import com.example.rhodium.data.model.Parameter;


import java.util.List;

public class QoeListAdaptor extends RecyclerView.Adapter<QoeListAdaptor.QoeListViewHolder>{
    List<Parameter> myList;

    public QoeListAdaptor(List<Parameter> myList)
    {
        this.myList = myList;
    }

    @NonNull
    @Override
    public QoeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_qoe_list,parent,false);
        return new QoeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QoeListViewHolder holder, int position) {

        Parameter md= myList.get(position);
        holder.TypeQ = md.getType();

        if(holder.TypeQ == 'G'){
            holder.umtsQ.setVisibility(View.GONE);
            holder.lteQ.setVisibility(View.GONE);
            holder.gsmQ.setVisibility(View.VISIBLE);
            holder.type.setText("GSM");
        }
        else
        {
            if(holder.TypeQ == 'U') {
                holder.gsmQ.setVisibility(View.GONE);
                holder.lteQ.setVisibility(View.GONE);
                holder.umtsQ.setVisibility(View.VISIBLE);
                holder.type.setText("UMTS");
            }
            else {
                holder.gsmQ.setVisibility(View.GONE);
                holder.umtsQ.setVisibility(View.GONE);
                holder.lteQ.setVisibility(View.VISIBLE);
                holder.type.setText("LTE");
            }
        }

        holder.up.setText(md.getUpSpeed()+"");
        holder.down.setText(md.getDownSpeed()+"");
        holder.jitter.setText(Math.round(md.getJitter()*100.0)/100.0+"");
        holder.latenc.setText(md.getLatency()+"");
        holder.resp.setText(md.getHttp_response_time()+"");
        holder.aparat.setText(md.getMultimedia_QoE()+"");
        holder.cellID.setText(md.getCell_id()+"");

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class QoeListViewHolder extends RecyclerView.ViewHolder {

        private TextView down,up,latenc,jitter,resp,aparat,type,cellID;
        private LinearLayout gsmQ,umtsQ,lteQ;
        char TypeQ ;

        public QoeListViewHolder(@NonNull View itemView) {
            super(itemView);

            gsmQ = (LinearLayout) itemView.findViewById(R.id.GSMim);
            umtsQ = (LinearLayout) itemView.findViewById(R.id.UMTSim);
            lteQ = (LinearLayout) itemView.findViewById(R.id.LTEim);

            down = (TextView) itemView.findViewById(R.id.download);
            up = (TextView) itemView.findViewById(R.id.upload);
            latenc = (TextView) itemView.findViewById(R.id.latency);
            jitter = (TextView) itemView.findViewById(R.id.jitter);
            resp = (TextView) itemView.findViewById(R.id.response);
            aparat = (TextView) itemView.findViewById(R.id.aparat);
            cellID = (TextView) itemView.findViewById(R.id.cellIDQoe);
            type =  (TextView) itemView.findViewById(R.id.typeQoe);


        }
    }
}
