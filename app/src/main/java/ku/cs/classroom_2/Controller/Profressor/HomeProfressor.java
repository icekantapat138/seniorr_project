package ku.cs.classroom_2.Controller.Profressor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import ku.cs.classroom_2.R;

public class HomeProfressor extends AppCompatActivity {

    ImageButton imageButton;
    Button openCourseBtn, checkcourseBtn, openchecknameBtn, checknisitregisterBtn, checkstudyLeaveBtn, scoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_profressor);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        openCourseBtn = (Button) findViewById(R.id.openCourseBtn);
        checkcourseBtn = (Button) findViewById(R.id.checkcourseBtn);
        openchecknameBtn = (Button) findViewById(R.id.openchecknameBtn);
        checknisitregisterBtn = (Button) findViewById(R.id.checknisitregisterBtn);
        checkstudyLeaveBtn = (Button) findViewById(R.id.checkstudyLeaveBtn);
        scoreBtn = (Button) findViewById(R.id.scoreBtn);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfessorEdit.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        openCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OpenCourse.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        checkcourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckCourseOpen.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        openchecknameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OpenCheckname.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        checknisitregisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckNisitCheckname.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        checkstudyLeaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckStudyLeave.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        scoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddScore.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}