package ku.cs.classroom_2.Controller.Profressor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ku.cs.classroom_2.Database.DBHelper;
import ku.cs.classroom_2.R;

public class OpenCheckname extends AppCompatActivity {

    EditText courseidText;
    Button searchBtn;
    ListView courseListview;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    DBHelper dbHelper;
    String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_checkname);

        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        courseidText = (EditText) findViewById(R.id.coursidText);
        courseListview = (ListView) findViewById(R.id.courseListview);
        searchBtn = (Button) findViewById(R.id.searchBtn);

        arrayList = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(OpenCheckname.this, android.R.layout.simple_list_item_1);
        courseListview.setAdapter(adapter);

        viewData(username);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseid = courseidText.getText().toString().trim();

                if (courseid.isEmpty()) {
                    courseidText.setError("course id is empty");
                } else {
                    arrayList.clear();
                    viewData2(username, courseid);
                }
            }
        });

        courseListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String iid = courseListview.getItemAtPosition(position).toString();
                Toast.makeText(OpenCheckname.this, ""+ iid, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SystemOpen.class);
                intent.putExtra("id", iid);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
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
            courseListview.setAdapter(arrayAdapter);
        }
    }

    public void viewData2(String email, String id) {
        Cursor cursor = dbHelper.CourseList2(email, id);

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