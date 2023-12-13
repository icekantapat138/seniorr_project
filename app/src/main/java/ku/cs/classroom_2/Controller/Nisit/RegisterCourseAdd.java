package ku.cs.classroom_2.Controller.Nisit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ku.cs.classroom_2.Database.DBHelper;
import ku.cs.classroom_2.R;

public class RegisterCourseAdd extends AppCompatActivity {

    TextView courseidText, coursenameText, courseeprofressText;
    Button registerCourseIDBtn;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_course_add);

        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String id = intent.getStringExtra("id");

        courseidText = (TextView) findViewById(R.id.courseidText);
        coursenameText = (TextView) findViewById(R.id.coursenameText);
        courseeprofressText = (TextView) findViewById(R.id.courseeprofressText);
        registerCourseIDBtn = (Button) findViewById(R.id.registerCourseIDBtn);

        Cursor cursor = dbHelper.coursenameIs(id);
        while (cursor.moveToNext()) {
            courseidText.setText(id);
            String cname = cursor.getString(1);
            String cpf = cursor.getString(2);
            coursenameText.setText(cname);
            courseeprofressText.setText(cpf);
        }

        String code = courseNisitRegister(id, username);
        System.out.println(code);

        registerCourseIDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkCcde = dbHelper.checkCode(code);
                if (checkCcde == true) {
                    Toast.makeText(RegisterCourseAdd.this, "Have Regis this Course", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.addRegisNisitCourse(code, id, username);
                    Toast.makeText(RegisterCourseAdd.this, "Add " + id + " Success", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public String courseNisitRegister(String cid, String email) {
        String id = cid + "_" + email;
        return id;
    }
}