package ku.cs.classroom_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ku.cs.classroom_2.Database.DBHelper;
import ku.cs.classroom_2.Model.UserModel;

public class Register extends AppCompatActivity {

    EditText fullnameText, passwordText, emailText;
    Button btnSignup;
    TextView loginBtn;
    Spinner roleSpinner;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(this);

        fullnameText = (EditText) findViewById(R.id.fullnameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        emailText = (EditText) findViewById(R.id.emailText);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        loginBtn = (TextView) findViewById(R.id.loginBtn);
        roleSpinner = (Spinner) findViewById(R.id.roleSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    public void addUser() {
        String fullname = fullnameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String role = roleSpinner.getSelectedItem().toString();

        if (!validateFullname() | !validateEmail() | !validatePassword() | !validateRRole()) {

        } else {
            boolean uniqueuser = dbHelper.checkEmail(email);
            if (uniqueuser == true) {
                emailText.setError("Unique Email");
            } else {
                dbHelper.adduser(email, fullname, password, role);
                Toast.makeText(this, "Add " + email + " Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);

            }

        }
    }

    public boolean validateFullname() {
        String val = fullnameText.getText().toString();
        if (val.isEmpty()) {
            fullnameText.setError("Fullname is empty");
            return false;
        } else {
            fullnameText.setError(null);
            return true;
        }
    }

    public boolean validateEmail() {
        String val = fullnameText.getText().toString();
        if (val.isEmpty()) {
            fullnameText.setError("Email is empty");
            return false;
        } else {
            fullnameText.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String val = fullnameText.getText().toString();
        if (val.isEmpty()) {
            fullnameText.setError("Password is empty");
            return false;
        } else {
            fullnameText.setError(null);
            return true;
        }
    }

    public boolean validateRRole() {
        String role = roleSpinner.getSelectedItem().toString();
        if (role.equals("Choose Role")) {
            TextView errorText = (TextView) roleSpinner.getSelectedView();
            errorText.setText("Please Choose Role");
            return false;
        } else {
            return true;
        }
    }


}