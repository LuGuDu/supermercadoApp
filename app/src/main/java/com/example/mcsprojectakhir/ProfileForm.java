package com.example.mcsprojectakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mcsprojectakhir.model.UserData;

public class ProfileForm extends AppCompatActivity {

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeForm.class);
        intent.putExtra(SEND_ID, userId);

        startActivity(intent);
        finish();

        super.onBackPressed();
    }

    UserData object_userData;
    TextView tvUserName;

    String userName;
    String userPassword;

    int userId;

    EditText etUserPassword;
    Button buttonConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_form);

        UserDataDBHelper UDdb = new UserDataDBHelper(ProfileForm.this);

        tvUserName = findViewById(R.id.textViewDisplauUserNameProf);
        buttonConfirm = findViewById(R.id.buttonConfirmProf);
        etUserPassword = findViewById(R.id.editTextTextPasswordProf);

        Intent intent = getIntent();
        userId = intent.getIntExtra(HomeForm.SEND_ID, -1);

        userName = MainActivity.UDV.get(userId).getUserName();
        userPassword = MainActivity.UDV.get(userId).getUserPassword();

        tvUserName.setText(userName);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int correct = validation();

                if (correct != 1){

                    Intent intent = new Intent(getApplicationContext(), HomeForm.class);
                    intent.putExtra(SEND_ID, userId);

                    object_userData = new UserData(userId + 1 + "", userName, userPassword);
                    boolean isUpdate = UDdb.updateUD(object_userData);

                    Toast.makeText(getApplicationContext(), "top up sucessful", Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                    finish();
                }

            }
        });

    }


    public int validation(){

        if(TextUtils.isEmpty(etUserPassword.getText())) {
            etUserPassword.setError("input your password");
            etUserPassword.requestFocus();
            return 1;
        } else if(!etUserPassword.getText().toString().contentEquals(userPassword)){
            etUserPassword.setError("password do not match");
            etUserPassword.requestFocus();
            return 1;
        }

        return 0;
    }

}