package ku.cs.classroom_2.Controller.Profressor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SyncRequest;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ku.cs.classroom_2.Database.DBHelper;
import ku.cs.classroom_2.Model.CourseModel;
import ku.cs.classroom_2.R;

public class OpenCourse extends AppCompatActivity {

    EditText courseidText, coursenameText;
    Button addcourseBtn;
    ListView subjectListView;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    DBHelper dbHelper;
    String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_course);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        dbHelper = new DBHelper(this);

        courseidText = (EditText) findViewById(R.id.courseidText);
        coursenameText = (EditText) findViewById(R.id.subjectnameText);
        addcourseBtn = (Button) findViewById(R.id.addcourseBtn);
        subjectListView = (ListView) findViewById(R.id.subjectListView);

        arrayList = new ArrayList<>();

        final ArrayAdapter<String>  adapter = new ArrayAdapter<String>(OpenCourse.this, android.R.layout.simple_list_item_1);
        subjectListView.setAdapter(adapter);

        viewData(username);
        addcourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String courseID = courseidText.getText().toString();
                String coursename = coursenameText.getText().toString();

                if (!validatecorseid() | !validatecoursename()) {

                } else {
                    boolean checkCourseID = dbHelper.checkCourseID(courseID);
                    if (checkCourseID == true) {
                        courseidText.setError("Unique Course ID");
                    } else {
                        dbHelper.addCourse(courseID, coursename, username, null, null, null, "OFF");
                        arrayList.clear();
                        viewData(username);
                        Toast.makeText(OpenCourse.this, "Add " + courseID + " Success", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public boolean validatecorseid() {
        String val = courseidText.getText().toString();
        if (val.isEmpty()) {
            courseidText.setError("course ID is empty");
            return false;
        } else {
            courseidText.setError(null);
            return true;
        }
    }

    public boolean validatecoursename() {
        String val = coursenameText.getText().toString();
        if (val.isEmpty()) {
            coursenameText.setError("course name is empty");
            return false;
        } else {
            coursenameText.setError(null);
            return true;
        }
    }

    public void viewData(String email) {
        Cursor cursor = dbHelper.CourseList(email);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                courseID = String.valueOf(arrayList.add(cursor.getString(0)));
            }
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
            subjectListView.setAdapter(arrayAdapter);
        }
    }
}