package in.co.okservices.nidanhospitaapp4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText search_person_txt;
    ImageButton search_person_btn, see_day_records;
    TextView normal_count_txt, emergency_count_txt, normal_paper_valid_count_txt,
            paper_valid_emergency_txt, discount_count_txt, cancel_txt;
    TextView date_txt, patient_count_txt, total_amount_txt;
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
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
        } catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}