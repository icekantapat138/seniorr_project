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

public class CheckStudyLeave extends AppCompatActivity {

    EditText coursidText;
    Button searchBtn;
    ListView courseListview;
    DBHelper dbHelper;
    String courseID;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_study_leave);

        dbHelper = new DBHelper(this);

        coursidText = (EditText) findViewById(R.id.coursidText);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        courseListview = (ListView) findViewById(R.id.courseListview);

        Intent intent = getIntent();
        String email = intent.getStringExtra("username");

        arrayList = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(CheckStudyLeave.this, android.R.layout.simple_list_item_1);
        courseListview.setAdapter(adapter);

        viewData(email);
        courseListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String iid = courseListview.getItemAtPosition(position).toString();
                Toast.makeText(CheckStudyLeave.this, ""+ iid, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CheckFile.class);
                intent.putExtra("id", iid);
                intent.putExtra("email", email);
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
}