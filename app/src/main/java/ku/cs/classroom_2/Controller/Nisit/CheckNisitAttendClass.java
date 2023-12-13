package ku.cs.classroom_2.Controller.Nisit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ku.cs.classroom_2.Controller.Profressor.CheckknameList;
import ku.cs.classroom_2.Database.DBHelper;
import ku.cs.classroom_2.R;

public class CheckNisitAttendClass extends AppCompatActivity {

    EditText editTextText;
    Spinner spinner;
    ListView listView;
    Button button;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    DBHelper dbHelper;
    String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_nisit_attend_class);

        editTextText = (EditText) findViewById(R.id.editTextText);
        spinner = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.listview);
        button = (Button) findViewById(R.id.button);

        dbHelper = new DBHelper(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CheckNisitAttendClass.this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Intent intent = getIntent();
        String email = intent.getStringExtra("username");
        String id = intent.getStringExtra("id");

        System.out.println(id);
        arrayList = new ArrayList<>();


        viewData(email);
    }

    private void viewData(String email1) {
        Cursor cursor = dbHelper.courseCheckEmail(email1);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                courseID = String.valueOf(arrayList.add(cursor.getString(0)));
            }
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);
        }
    }
}