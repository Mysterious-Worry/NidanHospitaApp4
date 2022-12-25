package in.co.okservices.nidanhospitaapp4.data_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import in.co.okservices.nidanhospitaapp4.R;
import in.co.okservices.nidanhospitaapp4.data_models.day_record_madel;

public class day_record_adapter extends RecyclerView.Adapter<day_record_adapter.myViewHolder>{

    ArrayList<day_record_madel> dataHolder;
    Context context;

    public day_record_adapter(ArrayList<day_record_madel> dataHolder, Context context) {
        this.dataHolder = dataHolder;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_detail_row, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.date_txt.setText(dataHolder.get(position).getDate());
        holder.total_patients_txt.setText("TPC : " + dataHolder.get(position).getTotal_patients());
        holder.total_amount_collected_txt.setText("AC : " + dataHolder.get(position).getTotal_amount_collected());
        holder.normal_txt.setText("Normal : " + dataHolder.get(position).getNormal());
        holder.emergency_txt.setText("Emergency : " + dataHolder.get(position).getEmergency());
        holder.paper_valid_txt.setText("Paper Valid : " + dataHolder.get(position).getPaper_valid());
        holder.emergency_paper_valid_txt.setText("Paper Valid\nEmergency : " + dataHolder.get(position).getEmergency_paper_valid());
        holder.discount_txt.setText("Discount : " + dataHolder.get(position).getDiscount());
        holder.cancel_txt.setText("Cancle : " + dataHolder.get(position).getCancel());
        holder.add_amount_txt.setText("Added Amount : " + dataHolder.get(position).getAdd_amount());
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView date_txt, total_patients_txt, total_amount_collected_txt, normal_txt,
                emergency_txt, paper_valid_txt, emergency_paper_valid_txt, discount_txt,
                cancel_txt, add_amount_txt;
        ImageButton see_details_btn;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            date_txt = (TextView)itemView.findViewById(R.id.date);
            total_patients_txt = (TextView)itemView.findViewById(R.id.total_patients);
            total_amount_collected_txt = (TextView)itemView.findViewById(R.id.total_amount_collected);
            normal_txt = (TextView)itemView.findViewById(R.id.normal);
            emergency_txt = (TextView)itemView.findViewById(R.id.emergency);
            paper_valid_txt = (TextView)itemView.findViewById(R.id.paper_valid);
            emergency_paper_valid_txt = (TextView)itemView.findViewById(R.id.emergency_paper_valid);
            discount_txt = (TextView)itemView.findViewById(R.id.discount);
            cancel_txt = (TextView)itemView.findViewById(R.id.cancel);
            add_amount_txt = (TextView)itemView.findViewById(R.id.add_amount);

            see_details_btn = (ImageButton)itemView.findViewById(R.id.see_details_btn);
        }
    }
}
