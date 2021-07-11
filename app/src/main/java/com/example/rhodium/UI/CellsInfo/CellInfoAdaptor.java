package com.example.rhodium.UI.CellsInfo;

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

public class CellInfoAdaptor extends RecyclerView.Adapter<CellInfoAdaptor.CellInfoViewHolder> {

    List<Parameter> myList;

     public CellInfoAdaptor(List<Parameter> myList)
     {
         this.myList = myList;
     }

    @NonNull
    @Override
    public CellInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data,parent,false);
        return new CellInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CellInfoViewHolder holder, int position) {

        Parameter md= myList.get(position);
        holder.Type = md.getType();

        if(holder.Type == 'G')
        {
            holder.umts.setVisibility(View.GONE);
            holder.lte.setVisibility(View.GONE);
            holder.gsm.setVisibility(View.VISIBLE);
            holder.txttypeg.setText("GSM");
            holder.txtpowerg.setText(md.getPower()+"");
            holder.txtqualityg.setText(md.getQuality()+"");
            holder.txtextrag.setText(md.getExtera_quality()+"");
            holder.txtCellIDg.setText(md.getCell_id()+"");
            holder.txtplmng.setText(md.getPlmn_id()+"");
            holder.txtlacg.setText(md.getTac()+"");
            holder.txtlatg.setText(Math.round(md.getLatitude()*100.0)/100.0+"");
            holder.txtlongg.setText(Math.round(md.getLongitude()*100.0)/100.0+"");

        }
        else
        {
            if(holder.Type == 'U')
            {
                holder.gsm.setVisibility(View.GONE);
                holder.lte.setVisibility(View.GONE);
                holder.umts.setVisibility(View.VISIBLE);
                holder.txttypeu.setText("UMTS");
                holder.txtpoweru.setText(md.getPower()+"");
                holder.txtqualityu.setText(md.getQuality()+"");
                holder.txtCellIDu.setText(md.getCell_id()+"");
                holder.txtplmnu.setText(md.getPlmn_id()+"");
                holder.txtlacu.setText(md.getLac_rac()+"");
                holder.txtlatu.setText(Math.round(md.getLatitude()*100.0)/100.0+"");
                holder.txtlongu.setText(Math.round(md.getLongitude()*100.0)/100.0+"");
                holder.txtracu.setText(md.getTac()+"");
            }
            else
            {
                holder.gsm.setVisibility(View.GONE);
                holder.umts.setVisibility(View.GONE);
                holder.lte.setVisibility(View.VISIBLE);
                holder.txttypel.setText("LTE");
                holder.txtpowerl.setText(md.getPower()+"");
                holder.txtqualityl.setText(md.getQuality()+"");
                holder.txtextral.setText(md.getExtera_quality()+"");
                holder.txtCellIDl.setText(md.getCell_id()+"");
                holder.txtplmnl.setText(md.getPlmn_id()+"");
                holder.txtlacl.setText(md.getLac_rac()+"");
                holder.txtlatl.setText(Math.round(md.getLatitude()*100.0)/100.0+"");
                holder.txtlongl.setText(Math.round(md.getLongitude()*100.0)/100.0+"");
            }
        }
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class CellInfoViewHolder extends RecyclerView.ViewHolder {
        private TextView txttypeg,txtpowerg,txtqualityg,txtextrag,txtCellIDg,txtplmng,txtlacg
                ,txtlatg,txtlongg,txtracu;
        private TextView txttypeu,txtpoweru,txtqualityu,txtCellIDu,txtplmnu,txtlacu,txtlatu,txtlongu;
        private TextView txttypel,txtpowerl,txtqualityl,txtextral,txtCellIDl,txtplmnl,txtlacl,txtlatl,txtlongl;
        private LinearLayout gsm,umts,lte;
        char Type ;
        public CellInfoViewHolder(@NonNull View itemView) {


            super(itemView);

            gsm = (LinearLayout) itemView.findViewById(R.id.GSM);
            umts = (LinearLayout) itemView.findViewById(R.id.UMTS);
            lte = (LinearLayout) itemView.findViewById(R.id.LTE);

            txttypeg=(TextView)itemView.findViewById(R.id.typeg);
            txtpowerg=(TextView)itemView.findViewById(R.id.powerg);
            txtqualityg=(TextView)itemView.findViewById(R.id.qualityg);
            txtextrag=(TextView)itemView.findViewById(R.id.extrag);
            txtCellIDg=(TextView)itemView.findViewById(R.id.cellIDg);
            txtplmng = (TextView)itemView.findViewById(R.id.plmng);
            txtlacg = (TextView)itemView.findViewById(R.id.lacg);
            txtlatg = (TextView)itemView.findViewById(R.id.latg);
            txtlongg= (TextView)itemView.findViewById(R.id.longg);

            txttypeu=(TextView)itemView.findViewById(R.id.typeu);
            txtpoweru=(TextView)itemView.findViewById(R.id.poweru);
            txtqualityu=(TextView)itemView.findViewById(R.id.qualityu);
            txtCellIDu=(TextView)itemView.findViewById(R.id.cellIDu);
            txtplmnu = (TextView)itemView.findViewById(R.id.plmnu);
            txtlacu = (TextView)itemView.findViewById(R.id.lacu);
            txtlatu = (TextView)itemView.findViewById(R.id.latu);
            txtlongu= (TextView)itemView.findViewById(R.id.longu);
            txtracu= (TextView)itemView.findViewById(R.id.racu);

            txttypel=(TextView)itemView.findViewById(R.id.typel);
            txtpowerl=(TextView)itemView.findViewById(R.id.powerl);
            txtqualityl=(TextView)itemView.findViewById(R.id.qualityl);
            txtextral=(TextView)itemView.findViewById(R.id.extral);
            txtCellIDl=(TextView)itemView.findViewById(R.id.cellIDl);
            txtplmnl = (TextView)itemView.findViewById(R.id.plmnl);
            txtlacl = (TextView)itemView.findViewById(R.id.lacl);
            txtlatl = (TextView)itemView.findViewById(R.id.latl);
            txtlongl= (TextView)itemView.findViewById(R.id.longl);
        }

    }
}
