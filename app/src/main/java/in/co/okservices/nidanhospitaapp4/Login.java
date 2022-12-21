package in.co.okservices.nidanhospitaapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    TextView username_txt, pass_txt;
    Button login_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_txt = (Button)findViewById(R.id.login_txt);
        username_txt = (TextView)findViewById(R.id.username_txt);
        pass_txt = (TextView)findViewById(R.id.pass_txt);


        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username_txt.getText().toString().trim().equals("admin") &&
                        pass_txt.getText().toString().trim().equals("admin") ){
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Login credentials are incorrect, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}