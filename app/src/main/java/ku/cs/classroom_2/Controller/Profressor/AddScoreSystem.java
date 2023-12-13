package ku.cs.classroom_2.Controller.Profressor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ku.cs.classroom_2.Database.DBHelper;
import ku.cs.classroom_2.R;

public class AddScoreSystem extends AppCompatActivity {

    TextView courseidText;
    EditText detail, link;
    Button submitBtn;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score_system);

        dbHelper = new DBHelper(this);

        courseidText = (TextView) findViewById(R.id.courseidText);
        detail = (EditText) findViewById(R.id.detail);
        link = (EditText) findViewById(R.id.linkText);
        submitBtn = (Button) findViewById(R.id.submitBtn);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        courseidText.setText(id);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dtail = detail.getText().toString();
                String lnk = link.getText().toString();
                dbHelper.addScore(id, dtail, lnk);
            }
        });
    }
}