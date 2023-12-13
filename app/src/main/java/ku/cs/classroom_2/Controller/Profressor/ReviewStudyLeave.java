package ku.cs.classroom_2.Controller.Profressor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.StringReader;

import ku.cs.classroom_2.Controller.Nisit.CheckScoreSystem;
import ku.cs.classroom_2.Database.DBHelper;
import ku.cs.classroom_2.R;

public class ReviewStudyLeave extends AppCompatActivity {

    DBHelper dbHelper;
    TextView courseidText, Content, linkText;
    Button passBtn, notpassBtn;
    String cregis, cem, cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_study_leave);

        dbHelper = new DBHelper(this);

        courseidText = (TextView) findViewById(R.id.courseidText);
        Content = (TextView) findViewById(R.id.coursenameText);
        linkText = (TextView) findViewById(R.id.statusText);
        passBtn = (Button) findViewById(R.id.passBtn);
        notpassBtn = (Button) findViewById(R.id.notpassBtn);
        linkText.setMovementMethod(LinkMovementMethod.getInstance());

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        courseidText.setText(id);
        Cursor cursor = dbHelper.studyLeaveIDD(id);
        while (cursor.moveToNext()) {
            cregis = cursor.getString(1);
            cem = cursor.getString(2);
            cid = cursor.getString(3);
            String dtail = cursor.getString(4);
            String lnk = cursor.getString(5);
            Content.setText(dtail);
            linkText.setText(lnk);
        }

        passBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = dbHelper.addCheckID(id, cregis, cem, cid, "Check");
                if (isSuccess){
                    Toast.makeText(ReviewStudyLeave.this, "Add " + id + " Success Pass", Toast.LENGTH_SHORT).show();
                    dbHelper.delete(id);
                    Intent intent = new Intent(getApplicationContext(), HomeProfressor.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });

        notpassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addCheckID(id, cregis, cem, cid, "Not-Check");
                Toast.makeText(ReviewStudyLeave.this, "Add " + id + " Success Not Pass", Toast.LENGTH_SHORT).show();
                dbHelper.delete(id);
                Intent intent = new Intent(getApplicationContext(), HomeProfressor.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }
}