package in.co.okservices.nidanhospitaapp4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import in.co.okservices.nidanhospitaapp4.custom_packages.MyDatabaseHelper;
import in.co.okservices.nidanhospitaapp4.data_adapters.day_record_adapter;
import in.co.okservices.nidanhospitaapp4.data_models.day_record_madel;

public class DayRecordActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton select_btn, search_btn, search_by_month_btn, refresh_btn, get_pdf_btn, select_date_1_btn, select_date_2_btn, search_between_2_dates_btn;
    EditText selected_date_txt, selected_date_1_txt, selected_date_2_txt;
    private int mYear, mMonth, mDay;
    Cursor cursor;
    private static final int REQUEST_WRITE_STORAGE = 112;
    ArrayList<day_record_madel> dataHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_record);
        initViews();

        cursor = new MyDatabaseHelper(this).fetchDayData();
        loadData(cursor);

        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateInEditText(selected_date_txt);
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor = new MyDatabaseHelper(DayRecordActivity.this).fetchOneDayData(selected_date_txt.getText().toString());
                loadData(cursor);
            }
        });

        search_by_month_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String month = selected_date_txt.getText().toString().trim();
                    StringBuilder rmv = new StringBuilder(month);
                    rmv = rmv.delete(0, 3);
                    selected_date_txt.setText(rmv.toString());
                    Cursor cursor = new MyDatabaseHelper(DayRecordActivity.this).fetchMonthDayData(rmv.toString());
                    loadData(cursor);
                } catch (Exception ex){
                    Toast.makeText(DayRecordActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        refresh_btn.setOnClickListener(view -> {
            cursor = new MyDatabaseHelper(DayRecordActivity.this).fetchDayData();
            loadData(cursor);
        });

        get_pdf_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(DayRecordActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Request the necessary permissions
                    ActivityCompat.requestPermissions(DayRecordActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);

                }
                try{
                    String str = new MyDatabaseHelper(DayRecordActivity.this).makePDF(cursor);
                    Toast.makeText(DayRecordActivity.this, str, Toast.LENGTH_SHORT).show();
                }catch(Exception ex){
                    Toast.makeText(DayRecordActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        select_date_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateInEditText(selected_date_1_txt);
            }
        });

        select_date_2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateInEditText(selected_date_2_txt);
            }
        });

        search_between_2_dates_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id1 = new MyDatabaseHelper(DayRecordActivity.this).senderCell("_id", selected_date_1_txt.getText().toString());
                int id2 = new MyDatabaseHelper(DayRecordActivity.this).senderCell("_id", selected_date_2_txt.getText().toString());

                if(id1 > id2){
                    // swapped
                    int p = id1;
                    id1 = id2;
                    id2 = p;
                }

                cursor = new MyDatabaseHelper(DayRecordActivity.this).fetchBetweenDayData(String.valueOf(id1), String.valueOf(id2));
                loadData(cursor);
            }
        });
    }

    private void initViews(){
        select_btn = (ImageButton)findViewById(R.id.select_btn);
        search_btn = (ImageButton)findViewById(R.id.search_btn);
        search_by_month_btn = (ImageButton)findViewById(R.id.search_by_month_btn);
        refresh_btn = (ImageButton)findViewById(R.id.refresh_btn);
        get_pdf_btn = (ImageButton)findViewById(R.id.get_pdf_btn);
        select_date_1_btn = (ImageButton)findViewById(R.id.select_date_1_btn);
        select_date_2_btn = (ImageButton)findViewById(R.id.select_date_2_btn);
        search_between_2_dates_btn = (ImageButton)findViewById(R.id.search_between_2_dates_btn);
        selected_date_txt = (EditText)findViewById(R.id.selected_date_txt);
        selected_date_1_txt = (EditText)findViewById(R.id.selected_date_1_txt);
        selected_date_2_txt = (EditText)findViewById(R.id.selected_date_2_txt);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private void loadData(Cursor cursor){
        try{
            recyclerView.setLayoutManager(new LinearLayoutManager(DayRecordActivity.this));
            dataHolder = new ArrayList<>();
            while (cursor.moveToNext()){
                day_record_madel madel = new day_record_madel(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10)
                );
                dataHolder.add(madel);
            }
            day_record_adapter adapter = new day_record_adapter(dataHolder, DayRecordActivity.this);
            recyclerView.setAdapter(adapter);
        } catch(Exception ex) {
            Toast.makeText(DayRecordActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setDateInEditText(EditText editText){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog( DayRecordActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        try {
                            String sMonthOfYear, sDayOfMonth;
                            sMonthOfYear = Integer.toString(monthOfYear+1);
                            sDayOfMonth = Integer.toString(dayOfMonth);
                            if (sMonthOfYear.length() == 1 ) {
                                sMonthOfYear = "0" + String.valueOf(sMonthOfYear);
                            }
                            if (sDayOfMonth.length() < 2) {
                                sDayOfMonth = "0" + dayOfMonth;
                            }

                            String date = sDayOfMonth + "-" + (sMonthOfYear) + "-" + year;
                            editText.setText(date);
                        } catch(Exception ex){
                            Toast.makeText(DayRecordActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}