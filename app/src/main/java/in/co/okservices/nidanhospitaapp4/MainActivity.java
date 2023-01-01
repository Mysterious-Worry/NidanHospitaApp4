package in.co.okservices.nidanhospitaapp4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

import in.co.okservices.nidanhospitaapp4.custom_packages.MyDatabaseHelper;
import in.co.okservices.nidanhospitaapp4.data_models.*;
import in.co.okservices.nidanhospitaapp4.data_adapters.*;

public class MainActivity extends AppCompatActivity {

    EditText search_person_txt;
    ImageButton search_person_btn, see_day_records, refresh_btn;
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
        setContentView(R.layout.activity_main);
        myDB = new MyDatabaseHelper(MainActivity.this);
        initViews();
        insertDayData();

        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        cursor = new MyDatabaseHelper(this).readPatientData();
        dataHolder = new ArrayList<>();
        loadDataInDataHolder();

        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recycler_view.getLayoutManager();
                int currentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                float visibleFraction;

                RecyclerView.ViewHolder viewHolder = recycler_view.findViewHolderForAdapterPosition(currentPosition);

                recycler_view.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                cursor = new MyDatabaseHelper(MainActivity.this).readPatientData();
                dataHolder = new ArrayList<>();
                loadDataInDataHolder();

                recycler_view.getLayoutManager().scrollToPosition(currentPosition);
            }
        });

        search_person_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor = new MyDatabaseHelper(MainActivity.this).readOnePatientData(search_person_txt.getText().toString());
                dataHolder = new ArrayList<>();
                loadDataInDataHolder();
            }
        });

        see_day_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DayRecordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews(){
        try {
            search_person_txt = (EditText)findViewById(R.id.search_person_txt);
            search_person_btn = (ImageButton)findViewById(R.id.search_person_btn);
            see_day_records = (ImageButton)findViewById(R.id.see_day_records);
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

    private void insertDayData(){
        boolean ifRecordExists = myDB.checkIfRecordExist(myDB.getDate());
        if(!ifRecordExists){
            String dataAdded = myDB.addRawData();
            if(Objects.equals(dataAdded, "Failed")){
                Toast.makeText(this, "Failed, adding patient data.", Toast.LENGTH_SHORT).show();
            } else if(Objects.equals(dataAdded, "Successfully inserted")){
                Toast.makeText(this, "Data added successfully.", Toast.LENGTH_SHORT).show();
            }

            String dayDataAdded = myDB.addDayData();
            if(Objects.equals(dayDataAdded, "Failed")){
                Toast.makeText(this, "Failed, adding patient data.", Toast.LENGTH_SHORT).show();
            } else if(Objects.equals(dayDataAdded, "Successfully inserted")){
                Toast.makeText(this, "Data added successfully.", Toast.LENGTH_SHORT).show();
            }
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
        patient_adapter adapter = new patient_adapter(dataHolder, this);
        recycler_view.setAdapter(adapter);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

        try {
            normal_count_txt.setText("N: " + String.valueOf(myDB.senderCell("normal")));
            emergency_count_txt.setText("E: " + String.valueOf(myDB.senderCell("emergency")));
            normal_paper_valid_count_txt.setText("PV: " + String.valueOf(myDB.senderCell("paper_valid")));
            paper_valid_emergency_txt.setText("EPV: " + String.valueOf(myDB.senderCell("emergency_paper_valid")));
            discount_count_txt.setText("D: " + String.valueOf(myDB.senderCell("discount")));
            cancel_txt.setText("C: " + String.valueOf(myDB.senderCell("cancel")));
            add_amount_txt.setText("AA: " + String.valueOf(myDB.senderCell("add_amount")));
            total_amount_txt.setText("Rs: " + String.valueOf(myDB.senderCell("total_amount_collected")));
            patient_count_txt.setText("TPV: " + String.valueOf(myDB.senderCell("total_patients")));
            date_txt.setText(String.valueOf(myDB.getDate()));
        } catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}