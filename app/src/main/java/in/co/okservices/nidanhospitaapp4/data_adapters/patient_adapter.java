package in.co.okservices.nidanhospitaapp4.data_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import in.co.okservices.nidanhospitaapp4.R;
import in.co.okservices.nidanhospitaapp4.data_models.patient_model;

public class patient_adapter extends RecyclerView.Adapter<patient_adapter.myViewHolder> {

    ArrayList<patient_model> dataHolder;
    Context context;

    public patient_adapter() {
    }

    public patient_adapter(ArrayList<patient_model> dataHolder) {
        this.dataHolder = dataHolder;
    }

    public patient_adapter(Context context) {
        this.context = context;
    }

    public patient_adapter(ArrayList<patient_model> dataHolder, Context context) {
        this.dataHolder = dataHolder;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_detail_layout, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        int pos = position;
        holder.id_txt.setText(dataHolder.get(position).get_id());
        holder.sr_no_txt.setText(dataHolder.get(position).getSr_no());
        {
            holder.name_txt.setEnabled(false);
            holder.age_txt.setEnabled(false);
            holder.percent_txt.setEnabled(false);
            holder.amount_txt.setEnabled(false);
            holder.normal_cb.setEnabled(false);
            holder.emergency_cb.setEnabled(false);
            holder.paper_valid_db.setEnabled(false);
            holder.paper_valid_emergency_cb.setEnabled(false);
            holder.discount_cb.setEnabled(false);
            holder.cancel_cb.setEnabled(false);
            holder.add_amount_cb.setEnabled(false);
            holder.check_btn.setEnabled(false);
        }
        holder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.name_txt.setEnabled(true);
                holder.age_txt.setEnabled(true);
                holder.percent_txt.setEnabled(true);
                holder.amount_txt.setEnabled(true);
                holder.normal_cb.setEnabled(true);
                holder.emergency_cb.setEnabled(true);
                holder.paper_valid_db.setEnabled(true);
                holder.paper_valid_emergency_cb.setEnabled(true);
                holder.discount_cb.setEnabled(true);
                holder.cancel_cb.setEnabled(true);
                holder.add_amount_cb.setEnabled(true);
                holder.check_btn.setEnabled(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        CardView bg;
        TextView id_txt, sr_no_txt;
        EditText name_txt, age_txt, percent_txt, amount_txt;
        CheckBox normal_cb, emergency_cb, paper_valid_db,
                paper_valid_emergency_cb, discount_cb, cancel_cb, add_amount_cb;
        ImageButton edit_btn, check_btn;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            bg = (CardView)itemView.findViewById(R.id.bg);
            id_txt = (TextView)itemView.findViewById(R.id.id_txt);
            sr_no_txt = (TextView)itemView.findViewById(R.id.sr_no_txt);
            name_txt = (EditText)itemView.findViewById(R.id.name_txt);
            age_txt = (EditText)itemView.findViewById(R.id.age_txt);
            percent_txt = (EditText)itemView.findViewById(R.id.percent_txt);
            amount_txt = (EditText)itemView.findViewById(R.id.amount_txt);
            normal_cb = (CheckBox)itemView.findViewById(R.id.normal_cb);
            emergency_cb = (CheckBox)itemView.findViewById(R.id.emergency_cb);
            paper_valid_db = (CheckBox)itemView.findViewById(R.id.paper_valid_db);
            paper_valid_emergency_cb = (CheckBox)itemView.findViewById(R.id.paper_valid_emergency_cb);
            discount_cb = (CheckBox)itemView.findViewById(R.id.discount_cb);
            cancel_cb = (CheckBox)itemView.findViewById(R.id.cancel_cb);
            add_amount_cb = (CheckBox)itemView.findViewById(R.id.add_amount_cb);
            edit_btn = (ImageButton)itemView.findViewById(R.id.edit_btn);
            check_btn = (ImageButton)itemView.findViewById(R.id.check_btn);
        }
    }
}
