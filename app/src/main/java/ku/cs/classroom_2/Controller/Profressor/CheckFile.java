package ku.cs.classroom_2.Controller.Profressor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ku.cs.classroom_2.Controller.Nisit.RegisterCourse;
import ku.cs.classroom_2.Controller.Nisit.RegisterCourseAdd;
import ku.cs.classroom_2.Database.DBHelper;
import ku.cs.classroom_2.R;

public class CheckFile extends AppCompatActivity {

    TextView courseidText;
    ListView courseListview;
    DBHelper dbHelper;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_file);

        dbHelper = new DBHelper(this);

        courseidText = (TextView) findViewById(R.id.courseidText);
        courseListview = (ListView) findViewById(R.id.courseListview);

        arrayList = new ArrayList<>();
        final ArrayAdapter<String>  adapter = new ArrayAdapter<String>(CheckFile.this, android.R.layout.simple_list_item_1);
        courseListview.setAdapter(adapter);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String email = intent.getStringExtra("email");

        courseidText.setText(id);
        viewData(id);

        courseListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String iid = courseListview.getItemAtPosition(position).toString();
                Toast.makeText(CheckFile.this, ""+ iid, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ReviewStudyLeave.class);
                intent.putExtra("id", iid);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }

    public void viewData(String id) {
        Cursor cursor = dbHelper.studyLeaveID(id);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                courseID = String.valueOf(arrayList.add(cursor.getString(0)));
            }
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
            courseListview.setAdapter(arrayAdapter);
        }
    }
}