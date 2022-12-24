package in.co.okservices.nidanhospitaapp4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
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
    ImageButton select_btn, search_btn, search_by_month_btn;
    EditText selected_date_txt;
    private int mYear, mMonth, mDay;
    Cursor cursor;
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

                                    String date = year + "-" + (sMonthOfYear) + "-" + sDayOfMonth;
                                    selected_date_txt.setText(date);
                                } catch(Exception ex){
                                    Toast.makeText(DayRecordActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initViews(){
        select_btn = (ImageButton)findViewById(R.id.select_btn);
        search_btn = (ImageButton)findViewById(R.id.search_btn);
        search_by_month_btn = (ImageButton)findViewById(R.id.search_by_month_btn);
        selected_date_txt = (EditText)findViewById(R.id.selected_date_txt);
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
}