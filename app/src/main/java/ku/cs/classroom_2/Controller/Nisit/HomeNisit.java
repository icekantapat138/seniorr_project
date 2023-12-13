package ku.cs.classroom_2.Controller.Nisit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ku.cs.classroom_2.R;

public class HomeNisit extends AppCompatActivity {

    Button registerCourseBtn, checknameBtn, studyleaveBtn, scoreBtn, checkNisitattendClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_nisit);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        registerCourseBtn = (Button) findViewById(R.id.registerCourseBtn);
        checknameBtn = (Button) findViewById(R.id.checknameBtn);
        studyleaveBtn = (Button) findViewById(R.id.studyleaveBtn);
        scoreBtn = (Button) findViewById(R.id.scoreBtn);
        checkNisitattendClass = (Button) findViewById(R.id.checkNisitattendClassBtn);

        registerCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), RegisterCourse.class);
                intent1.putExtra("username", username);
                startActivity(intent1);
            }
        });

        checknameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ChecknameNisit.class);
                intent1.putExtra("username", username);
                startActivity(intent1);
            }
        });

        studyleaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), SendStudyLeave.class);
                intent1.putExtra("username", username);
                startActivity(intent1);
            }
        });

        scoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), CheckScore.class);
                intent1.putExtra("username", username);
                startActivity(intent1);
            }
        });

        checkNisitattendClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), CheckNisitAttendClass.class);
                intent1.putExtra("username", username);
                startActivity(intent1);
            }
        });
    }

}