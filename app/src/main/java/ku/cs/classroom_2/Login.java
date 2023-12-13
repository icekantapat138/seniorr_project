package ku.cs.classroom_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import ku.cs.classroom_2.Controller.Nisit.HomeNisit;
import ku.cs.classroom_2.Controller.Profressor.HomeProfressor;
import ku.cs.classroom_2.Database.DBHelper;

public class Login extends AppCompatActivity {

    EditText usernameTxt, passwordTxt;
    Button loginBtn;
    TextView registerBtn;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        usernameTxt = (EditText) findViewById(R.id.usernameTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        loginBtn = (Button) findViewById(R.id.btnLogin);
        registerBtn = (TextView) findViewById(R.id.signupBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmail() | !validatePassword()) {

                } else {
                    String username = usernameTxt.getText().toString();
                    String password = passwordTxt.getText().toString();

                    if (username.equals("") | password.equals("")) {

                    } else {
                        Boolean checkuserpass = dbHelper.checkEmailPassword(username,password);
                        if (checkuserpass==true) {
                            Cursor cursor = dbHelper.checkRole(username);
                            StringBuffer str = new StringBuffer();
                            while (cursor.moveToNext()) {
                                str.append(cursor.getString(3));
                                String roles = str.toString();
                                if (roles.equals("Professor")) {
                                    Intent intent = new Intent(getApplicationContext(), HomeProfressor.class);
                                    intent.putExtra("username", username);
                                    startActivity(intent);
                                } else if (roles.equals("Nisit")) {
                                    Intent intent = new Intent(getApplicationContext(), HomeNisit.class);
                                    intent.putExtra("username", username);
                                    startActivity(intent);
                                } else {
                                    System.out.println("Error");
                                }
                            }
                        } else {
                            System.out.println("Error2");
                        }
                    }
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }

    public boolean validateEmail() {
        String val = usernameTxt.getText().toString();
        if (val.isEmpty()) {
            usernameTxt.setError("email is empty");
            return false;
        } else {
            usernameTxt.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String val = passwordTxt.getText().toString();
        if (val.isEmpty()) {
            passwordTxt.setError("password is empty");
            return false;
        } else {
            passwordTxt.setError(null);
            return true;
        }
    }
}