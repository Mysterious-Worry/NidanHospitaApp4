package in.co.okservices.nidanhospitaapp4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.co.okservices.nidanhospitaapp4.custom_packages.MyDatabaseHelper;
import in.co.okservices.nidanhospitaapp4.data_adapters.*;
import in.co.okservices.nidanhospitaapp4.data_models.patient_model;

public class EditPreviousPatientDetails extends AppCompatActivity {

    public String date;
    EditText search_person_txt;
    ImageButton search_person_btn, go_back_btn, refresh_btn;
    TextView normal_count_txt, emergency_count_txt, normal_paper_valid_count_txt,
            paper_valid_emergency_txt, discount_count_txt, cancel_txt, add_amount_txt;
    TextView date_txt, patient_count_txt, total_amount_txt;
    RecyclerView recycler_view;
    MyDatabaseHelper myDB;
    ArrayList<patient_model> dataHolder;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_previous_patient_details);
        initViews();

        date = getIntent().getStringExtra("date");

        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        cursor = new MyDatabaseHelper(this).readPatientData(date);
        dataHolder = new ArrayList<>();
        loadDataInDataHolder();

        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycler_view.setLayoutManager(new LinearLayoutManager(EditPreviousPatientDetails.this));
                cursor = new MyDatabaseHelper(EditPreviousPatientDetails.this).readPatientData(date);
                dataHolder = new ArrayList<>();
                loadDataInDataHolder();
            }
        });

        search_person_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor = new MyDatabaseHelper(EditPreviousPatientDetails.this).readOnePatientData(search_person_txt.getText().toString(), date);
                dataHolder = new ArrayList<>();
                loadDataInDataHolder();
            }
        });

        go_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(EditPreviousPatientDetails.this, DayRecordActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void initViews(){
        try {
            search_person_txt = (EditText)findViewById(R.id.search_person_txt);
            search_person_btn = (ImageButton)findViewById(R.id.search_person_btn);
            go_back_btn = (ImageButton)findViewById(R.id.go_back_btn);
            normal_count_txt = (TextView)findViewById(R.id.normal_count_txt);
            emergency_count_txt = (TextView)findViewById(R.id.emergency_count_txt);
            normal_paper_valid_count_txt = (TextView)findViewById(R.id.normal_paper_valid_count_txt);
            paper_valid_emergency_txt = (TextView)findViewById(R.id.paper_valid_emergency_txt);
            discount_count_txt = (TextView)findViewById(R.id.discount_count_txt);
            cancel_txt = (TextView)findViewById(R.id.cancel_txt);
            date_txt = (TextView)findViewById(R.id.date_txt);
            patient_count_txt = (TextView)findViewById(R.id.patient_count_txt);
            total_amount_txt = (TextView)findViewById(R.id.total_amount_txt);
            recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
            refresh_btn = (ImageButton)findViewById(R.id.refresh_btn);
            add_amount_txt = (TextView)findViewById(R.id.add_amount_txt);
        } catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void loadDataInDataHolder() {
        try {
            while (cursor.moveToNext()) {
                patient_model obj = new patient_model(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9)
                );
                dataHolder.add(obj);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage().trim(), Toast.LENGTH_SHORT).show();
        }
        previous_patient_adapter adapter = new previous_patient_adapter(dataHolder, this, date);
        recycler_view.setAdapter(adapter);

        try {
            myDB = new MyDatabaseHelper(getApplicationContext());
            normal_count_txt.setText("N: " + String.valueOf(myDB.senderCell("normal", date)));
            emergency_count_txt.setText("E: " + String.valueOf(myDB.senderCell("emergency", date)));
            normal_paper_valid_count_txt.setText("PV: " + String.valueOf(myDB.senderCell("paper_valid", date)));
            paper_valid_emergency_txt.setText("EPV: " + String.valueOf(myDB.senderCell("emergency_paper_valid", date)));
            discount_count_txt.setText("D: " + String.valueOf(myDB.senderCell("discount", date)));
            cancel_txt.setText("C: " + String.valueOf(myDB.senderCell("cancel", date)));
            add_amount_txt.setText("AA: " + String.valueOf(myDB.senderCell("add_amount", date)));
            total_amount_txt.setText("Rs: " + String.valueOf(myDB.senderCell("total_amount_collected", date)));
            patient_count_txt.setText("TPV: " + String.valueOf(myDB.senderCell("total_patients", date)));
            date_txt.setText(String.valueOf(myDB.getDate()));
        } catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}