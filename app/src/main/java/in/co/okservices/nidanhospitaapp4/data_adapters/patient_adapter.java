package in.co.okservices.nidanhospitaapp4.data_adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

import in.co.okservices.nidanhospitaapp4.R;
import in.co.okservices.nidanhospitaapp4.custom_packages.MyDatabaseHelper;
import in.co.okservices.nidanhospitaapp4.data_models.patient_model;

public class patient_adapter extends RecyclerView.Adapter<patient_adapter.myViewHolder> {

    ArrayList<patient_model> dataHolder;
    Context context;
    MyDatabaseHelper myDb;

    public patient_adapter() {
    }

    public patient_adapter(ArrayList<patient_model> dataHolder) {
        this.dataHolder = dataHolder;
    }

    public patient_adapter(Context context) {
        this.context = context;
        myDb = new MyDatabaseHelper(context);
    }

    public patient_adapter(ArrayList<patient_model> dataHolder, Context context) {
        this.dataHolder = dataHolder;
        this.context = context;
        myDb = new MyDatabaseHelper(context);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_detail_layout, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        String type = "";
        int pos = position;
        holder.id_txt.setText(dataHolder.get(position).get_id());
        holder.sr_no_txt.setText(dataHolder.get(position).getSr_no());
        holder.name_txt.setText(dataHolder.get(position).getName());
        holder.age_txt.setText(dataHolder.get(position).getAge());
        holder.color_txt.setText(dataHolder.get(position).getColor());
        holder.amount_txt.setText(dataHolder.get(position).getAmount());
//        holder.type_txt.setText(dataHolder.get(position).getType());
        if(dataHolder.get(position).getColor().equals("2131100236")) {
            holder.type_txt.setText("Normal");
        } else if(dataHolder.get(position).getColor().equals("2131099742")) {
            holder.type_txt.setText("Emergency");
        } else if(dataHolder.get(position).getColor().equals("2131100240")) {
            holder.type_txt.setText("Paper Valid");
        } else if(dataHolder.get(position).getColor().equals("2131100241")) {
            holder.type_txt.setText("Paper Valid\nEmergency");
        } else if(dataHolder.get(position).getColor().equals("2131099740")) {
            holder.type_txt.setText("Discount");
        } else if(dataHolder.get(position).getColor().equals("2131099692")) {
            holder.type_txt.setText("Cancel");
        } else if(dataHolder.get(position).getColor().equals("2131099675")) {
            holder.type_txt.setText("Amount");
        } else {
            holder.type_txt.setText("Not\nDefined");
        }

        try{
            holder.color_txt.setTextColor(context.getResources().getColor(Integer.parseInt(dataHolder.get(position).getColor())));
            holder.bg.setCardBackgroundColor(holder.color_txt.getTextColors());
        } catch (Exception ex){
            if(!Objects.equals(ex.getMessage(), "For input string: \"ffffff\""))
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        {
            holder.name_txt.setEnabled(false);
            holder.age_txt.setEnabled(false);
            holder.percent_txt.setEnabled(false);
            holder.amount_edt_txt.setEnabled(false);
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
                // holder.percent_txt.setEnabled(true);
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

        holder.normal_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.normal_cb.isChecked()){
                    holder.emergency_cb.setChecked(false);
                    holder.paper_valid_db.setChecked(false);
                    holder.paper_valid_emergency_cb.setChecked(false);
                    holder.discount_cb.setChecked(false);
                    holder.cancel_cb.setChecked(false);
                    holder.add_amount_cb.setChecked(false);
                }
            }
        });

        holder.emergency_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.emergency_cb.isChecked()){
                    holder.normal_cb.setChecked(false);
                    holder.paper_valid_db.setChecked(false);
                    holder.paper_valid_emergency_cb.setChecked(false);
                    holder.discount_cb.setChecked(false);
                    holder.cancel_cb.setChecked(false);
                    holder.add_amount_cb.setChecked(false);
                }
            }
        });

        holder.paper_valid_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.paper_valid_db.isChecked()){
                    holder.normal_cb.setChecked(false);
                    holder.emergency_cb.setChecked(false);
                    holder.paper_valid_emergency_cb.setChecked(false);
                    holder.discount_cb.setChecked(false);
                    holder.cancel_cb.setChecked(false);
                    holder.add_amount_cb.setChecked(false);
                }
            }
        });

        holder.paper_valid_emergency_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.paper_valid_emergency_cb.isChecked()){
                    holder.normal_cb.setChecked(false);
                    holder.emergency_cb.setChecked(false);
                    holder.paper_valid_db.setChecked(false);
                    holder.discount_cb.setChecked(false);
                    holder.cancel_cb.setChecked(false);
                    holder.add_amount_cb.setChecked(false);
                }
            }
        });

        holder.discount_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.discount_cb.isChecked()){
                    holder.normal_cb.setChecked(false);
                    holder.emergency_cb.setChecked(false);
                    holder.paper_valid_db.setChecked(false);
                    holder.paper_valid_emergency_cb.setChecked(false);
                    holder.cancel_cb.setChecked(false);
                    holder.add_amount_cb.setChecked(false);

                    holder.percent_txt.setEnabled(true);
                }
            }
        });

        holder.cancel_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.cancel_cb.isChecked()){
                    holder.normal_cb.setChecked(false);
                    holder.emergency_cb.setChecked(false);
                    holder.paper_valid_db.setChecked(false);
                    holder.paper_valid_emergency_cb.setChecked(false);
                    holder.discount_cb.setChecked(false);
                    holder.add_amount_cb.setChecked(false);
                }
            }
        });

        holder.add_amount_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.add_amount_cb.isChecked()){
                    holder.normal_cb.setChecked(false);
                    holder.emergency_cb.setChecked(false);
                    holder.paper_valid_db.setChecked(false);
                    holder.paper_valid_emergency_cb.setChecked(false);
                    holder.discount_cb.setChecked(false);
                    holder.cancel_cb.setChecked(false);

                    holder.amount_edt_txt.setEnabled(true);
                }
            }
        });

        holder.check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.type_txt.getText().toString().equals("Not\nDefined"))
                {
                    int amount = 0;
                    String type = "";
                    String dtype = "";
                    int hexColor = 0;
                    if (holder.normal_cb.isChecked()) {
                        amount = 200;
                        dtype = type = "normal";
                        hexColor = Integer.parseInt(String.valueOf(R.color.normal));
                    } else if (holder.emergency_cb.isChecked()) {
                        amount = 400;
                        dtype = type = "emergency";
                        hexColor = Integer.parseInt(String.valueOf(R.color.emergency));
                    } else if (holder.paper_valid_db.isChecked()) {
                        dtype = type = "paper_valid";
                        hexColor = Integer.parseInt(String.valueOf(R.color.paper_valid));
                    } else if (holder.paper_valid_emergency_cb.isChecked()) {
                        amount = 200;
                        type = "paper_valid_emergency";
                        dtype = "emergency_paper_valid";
                        hexColor = Integer.parseInt(String.valueOf(R.color.paper_valid_emergency));
                    } else if (holder.discount_cb.isChecked()) {
                        try {
                            if (Integer.parseInt(holder.percent_txt.getText().toString()) > 100) {
                                Toast.makeText(context, "The discounted amount should not be more than 100%.", Toast.LENGTH_SHORT).show();
                            } else {
                                amount = 200 - Integer.parseInt(holder.percent_txt.getText().toString()) * 2;
                                dtype = type = "discount";
                                hexColor = Integer.parseInt(String.valueOf(R.color.discount));
                            }
                        } catch (Exception ex) {
                            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else if (holder.cancel_cb.isChecked()) {
                        dtype = type = "cancel";
                        hexColor = Integer.parseInt(String.valueOf(R.color.cancel));
                    } else if (holder.add_amount_cb.isChecked()) {
                        amount = Integer.parseInt(holder.amount_edt_txt.getText().toString());
                        dtype = type = "add_amount";
                        hexColor = Integer.parseInt(String.valueOf(R.color.add_amount));
                    }

                    myDb.updatePatientDetail(Integer.parseInt(holder.id_txt.getText().toString()),
                            Integer.parseInt(holder.sr_no_txt.getText().toString()),
                            type,
                            hexColor,
                            holder.name_txt.getText().toString(),
                            holder.age_txt.getText().toString(),
                            amount);

                    try {
                        myDb.updateDayRecord(dtype, amount);
                    } catch (Exception ex) {
                        Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(context, "Please refresh to reload the data.", Toast.LENGTH_SHORT).show();
                    {
                        holder.name_txt.setEnabled(false);
                        holder.age_txt.setEnabled(false);
                        holder.percent_txt.setEnabled(false);
                        holder.amount_edt_txt.setEnabled(false);
                        holder.normal_cb.setEnabled(false);
                        holder.emergency_cb.setEnabled(false);
                        holder.paper_valid_db.setEnabled(false);
                        holder.paper_valid_emergency_cb.setEnabled(false);
                        holder.discount_cb.setEnabled(false);
                        holder.cancel_cb.setEnabled(false);
                        holder.add_amount_cb.setEnabled(false);
                        holder.check_btn.setEnabled(false);
                    }
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to clean this record?");
                    builder.setCancelable(false);

                    builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                        String type = null;
                        if(holder.type_txt.getText().toString().equals("Normal")){
                            type = "normal";
                        } else if(holder.type_txt.getText().toString().equals("Emergency")){
                            type = "emergency";
                        } else if(holder.type_txt.getText().toString().equals("Paper Valid")){
                            type = "paper_valid";
                        } else if(holder.type_txt.getText().toString().equals("Paper Valid\nEmergency") ||
                                holder.type_txt.getText().toString().equals("Paper Valid Emergency")){
                            type = "emergency_paper_valid";
                        } else if(holder.type_txt.getText().toString().equals("Discount")){
                            type = "discount";
                        } else if(holder.type_txt.getText().toString().equals("Cancel")){
                            type = "cancel";
                        } else if(holder.type_txt.getText().toString().equals("Amount")){
                            type = "add_amount";
                        }

                        String deleteMethod = myDb.deletePatientRow(holder.id_txt.getText().toString(), myDb.getDate(), holder.sr_no_txt.getText().toString());
                        Toast.makeText(context, deleteMethod, Toast.LENGTH_SHORT).show();
                        myDb.deleteDayRecord(type, holder.amount_txt.getText().toString());

                        {
                            int amount = 0;
                            String ptype = "";
                            String dtype = "";
                            int hexColor = 0;
                            if (holder.normal_cb.isChecked()) {
                                amount = 200;
                                dtype = ptype = "normal";
                                hexColor = Integer.parseInt(String.valueOf(R.color.normal));
                            } else if (holder.emergency_cb.isChecked()) {
                                amount = 400;
                                dtype = ptype = "emergency";
                                hexColor = Integer.parseInt(String.valueOf(R.color.emergency));
                            } else if (holder.paper_valid_db.isChecked()) {
                                dtype = ptype = "paper_valid";
                                hexColor = Integer.parseInt(String.valueOf(R.color.paper_valid));
                            } else if (holder.paper_valid_emergency_cb.isChecked()) {
                                amount = 200;
                                ptype = "paper_valid_emergency";
                                dtype = "emergency_paper_valid";
                                hexColor = Integer.parseInt(String.valueOf(R.color.paper_valid_emergency));
                            } else if (holder.discount_cb.isChecked()) {
                                try {
                                    if (Integer.parseInt(holder.percent_txt.getText().toString()) > 100) {
                                        Toast.makeText(context, "The discounted amount should not be more than 100%.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        amount = 200 - Integer.parseInt(holder.percent_txt.getText().toString()) * 2;
                                        dtype = ptype = "discount";
                                        hexColor = Integer.parseInt(String.valueOf(R.color.discount));
                                    }
                                } catch (Exception ex) {
                                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else if (holder.cancel_cb.isChecked()) {
                                dtype = ptype = "cancel";
                                hexColor = Integer.parseInt(String.valueOf(R.color.cancel));
                            } else if (holder.add_amount_cb.isChecked()) {
                                amount = Integer.parseInt(holder.amount_edt_txt.getText().toString());
                                dtype = ptype = "add_amount";
                                hexColor = Integer.parseInt(String.valueOf(R.color.add_amount));
                            }

                            myDb.updatePatientDetail(Integer.parseInt(holder.id_txt.getText().toString()),
                                    Integer.parseInt(holder.sr_no_txt.getText().toString()),
                                    ptype,
                                    hexColor,
                                    holder.name_txt.getText().toString(),
                                    holder.age_txt.getText().toString(),
                                    amount);

                            try {
                                myDb.updateDayRecord(dtype, amount);
                            } catch (Exception ex) {
                                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            Toast.makeText(context, "Please refresh to reload the data.", Toast.LENGTH_SHORT).show();
                            {
                                holder.name_txt.setEnabled(false);
                                holder.age_txt.setEnabled(false);
                                holder.percent_txt.setEnabled(false);
                                holder.amount_edt_txt.setEnabled(false);
                                holder.normal_cb.setEnabled(false);
                                holder.emergency_cb.setEnabled(false);
                                holder.paper_valid_db.setEnabled(false);
                                holder.paper_valid_emergency_cb.setEnabled(false);
                                holder.discount_cb.setEnabled(false);
                                holder.cancel_cb.setEnabled(false);
                                holder.add_amount_cb.setEnabled(false);
                                holder.check_btn.setEnabled(false);
                            }
                        }
                    });

                    builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to clean this record?");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    String type = null;
                    if(holder.type_txt.getText().toString().equals("Normal")){
                        type = "normal";
                    } else if(holder.type_txt.getText().toString().equals("Emergency")){
                        type = "emergency";
                    } else if(holder.type_txt.getText().toString().equals("Paper Valid")){
                        type = "paper_valid";
                    } else if(holder.type_txt.getText().toString().equals("Paper Valid\nEmergency") ||
                            holder.type_txt.getText().toString().equals("Paper Valid Emergency")){
                        type = "emergency_paper_valid";
                    } else if(holder.type_txt.getText().toString().equals("Discount")){
                        type = "discount";
                    } else if(holder.type_txt.getText().toString().equals("Cancel")){
                        type = "cancel";
                    } else if(holder.type_txt.getText().toString().equals("Amount")){
                        type = "add_amount";
                    }

                    String deleteMethod = myDb.deletePatientRow(holder.id_txt.getText().toString(), myDb.getDate(), holder.sr_no_txt.getText().toString());
                    Toast.makeText(context, deleteMethod, Toast.LENGTH_SHORT).show();
                    myDb.deleteDayRecord(type, holder.amount_txt.getText().toString());
                });

                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        CardView bg;
        TextView id_txt, sr_no_txt, color_txt, type_txt, amount_txt;
        EditText name_txt, age_txt, percent_txt, amount_edt_txt;
        CheckBox normal_cb, emergency_cb, paper_valid_db,
                paper_valid_emergency_cb, discount_cb, cancel_cb, add_amount_cb;
        ImageButton edit_btn, check_btn, delete_btn;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            bg = (CardView)itemView.findViewById(R.id.bg);
            id_txt = (TextView)itemView.findViewById(R.id.id_txt);
            type_txt = (TextView)itemView.findViewById(R.id.type_txt);
            sr_no_txt = (TextView)itemView.findViewById(R.id.sr_no_txt);
            amount_txt = (TextView)itemView.findViewById(R.id.amount_txt);
            name_txt = (EditText)itemView.findViewById(R.id.name_txt);
            age_txt = (EditText)itemView.findViewById(R.id.age_txt);
            percent_txt = (EditText)itemView.findViewById(R.id.percent_txt);
            amount_edt_txt = (EditText)itemView.findViewById(R.id.amount_edt_txt);
            normal_cb = (CheckBox)itemView.findViewById(R.id.normal_cb);
            emergency_cb = (CheckBox)itemView.findViewById(R.id.emergency_cb);
            paper_valid_db = (CheckBox)itemView.findViewById(R.id.paper_valid_db);
            paper_valid_emergency_cb = (CheckBox)itemView.findViewById(R.id.paper_valid_emergency_cb);
            discount_cb = (CheckBox)itemView.findViewById(R.id.discount_cb);
            cancel_cb = (CheckBox)itemView.findViewById(R.id.cancel_cb);
            add_amount_cb = (CheckBox)itemView.findViewById(R.id.add_amount_cb);
            edit_btn = (ImageButton)itemView.findViewById(R.id.edit_btn);
            check_btn = (ImageButton)itemView.findViewById(R.id.check_btn);
            color_txt = (TextView)itemView.findViewById(R.id.color_txt);
            delete_btn = (ImageButton)itemView.findViewById(R.id.delete_btn);
        }
    }
}
