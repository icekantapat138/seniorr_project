package ku.cs.classroom_2.Controller.Nisit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ku.cs.classroom_2.Database.DBHelper;
import ku.cs.classroom_2.R;

public class SendStudyLeaveDetail extends AppCompatActivity {

    TextView studyleaveText, courseidText;
    EditText detail, linkText;
    Button submitBtn;
    DBHelper dbHelper;
    String ccode, grcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_study_leave_detail);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String id = intent.getStringExtra("id");

        dbHelper = new DBHelper(this);

        studyleaveText = (TextView) findViewById(R.id.studyleaveText);
        courseidText = (TextView) findViewById(R.id.courseidText);
        detail = (EditText) findViewById(R.id.detail);
        linkText = (EditText) findViewById(R.id.linkText);
        submitBtn = (Button) findViewById(R.id.submitBtn);

        Cursor cursor1 = dbHelper.courseRe(email, id);
        while (cursor1.moveToNext()){
            ccode = cursor1.getString(0);
            System.out.println(ccode);
        }

        grcode = generateID(ccode);
        System.out.println(grcode);
        studyleaveText.setText(grcode);
        courseidText.setText(id);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = detail.getText().toString();
                String link = linkText.getText().toString();
                if (content.isEmpty() | link.isEmpty()) {
                    detail.setError("please enter email");
                    linkText.setError("Please enter link");
                } else {
                    dbHelper.addStudyLeave(studyleaveText.getText().toString(), ccode, email, id, content, link);
                    Toast.makeText(SendStudyLeaveDetail.this, "Make Study Leave Success", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public String generateID(String idcode){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String id = idcode + "_" + dtf.format(now);
        return id;
    }
}