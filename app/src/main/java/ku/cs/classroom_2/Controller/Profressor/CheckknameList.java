package ku.cs.classroom_2.Controller.Profressor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ku.cs.classroom_2.Controller.Nisit.ChecknameNisit;
import ku.cs.classroom_2.Database.DBHelper;
import ku.cs.classroom_2.R;

public class CheckknameList extends AppCompatActivity {

    ListView listview;
    Spinner spinner;
    Button button;
    DBHelper dbHelper;
    String courseID;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkkname_list);

        dbHelper = new DBHelper(this);
        listview = (ListView) findViewById(R.id.listview);
        spinner = (Spinner) findViewById(R.id.spinner2);
        button = (Button) findViewById(R.id.button2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CheckknameList.this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Intent intent = getIntent();
        String email = intent.getStringExtra("username");
        String id = intent.getStringExtra("id");

        System.out.println(id);
        arrayList = new ArrayList<>();


        viewData(id);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = (String) spinner.getSelectedItem();
                arrayList.clear();
                viewData2(id, status);
            }
        });
    }

    private void viewData(String iid) {
        Cursor cursor = dbHelper.courseCheck(iid);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                courseID = String.valueOf(arrayList.add(cursor.getString(0)));
            }
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
            listview.setAdapter(arrayAdapter);
        }
    }

    private void viewData2(String iid, String status) {
        Cursor cursor = dbHelper.courseCheckStatus(iid, status);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                courseID = String.valueOf(arrayList.add(cursor.getString(0)));
            }
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
            listview.setAdapter(arrayAdapter);
        }
    }


}