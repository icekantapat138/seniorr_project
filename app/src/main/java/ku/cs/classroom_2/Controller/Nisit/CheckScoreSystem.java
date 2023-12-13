package ku.cs.classroom_2.Controller.Nisit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import ku.cs.classroom_2.Database.DBHelper;
import ku.cs.classroom_2.R;

public class CheckScoreSystem extends AppCompatActivity {

    DBHelper dbHelper;
    TextView courseidText, coursenameText, linkText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_score_system);

        dbHelper = new DBHelper(this);

        courseidText = (TextView) findViewById(R.id.courseidText);
        coursenameText = (TextView) findViewById(R.id.coursenameText);
        linkText = (TextView) findViewById(R.id.statusText);
        linkText.setMovementMethod(LinkMovementMethod.getInstance());

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        System.out.println(id +  "Checkcou");
        courseidText.setText(id);

        Cursor cursor = dbHelper.scoreList(id);
        while (cursor.moveToNext()) {
            String content = cursor.getString(1);
            String linkk = cursor.getString(2);
            System.out.println(content);
            System.out.println(linkk);
            coursenameText.setText(content);
            linkText.setText(linkk);
        }
    }
}