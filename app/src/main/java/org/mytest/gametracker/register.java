package org.mytest.gametracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    EditText fullname, email, password;
    Button regButton;
    TextView loginButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname = findViewById(R.id.fullName);
        email = findViewById(R.id.userMail);
        password = findViewById(R.id.password);
        regButton = findViewById(R.id.registerBtn);
        loginButton = findViewById(R.id.loginDirectly);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

//        if(fAuth.getCurrentUser()!=null){
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), login.class));
                finish();
            }
        });


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getmail = email.getText().toString().trim();
                String getPass = password.getText().toString().trim();

                if(TextUtils.isEmpty(getmail)){
                    email.setError("Email is required");
                }

                if(TextUtils.isEmpty(getPass)){
                    password.setError("password is required");
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(getmail, getPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this, "user created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(register.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
