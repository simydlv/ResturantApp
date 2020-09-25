package com.example.resturantdlv.Buyers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.resturantdlv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegActivity extends AppCompatActivity {
    private Button CreateAccountButton;
    private EditText intputName,inputPhone,inputPassword;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        CreateAccountButton= (Button) findViewById(R.id.Sign_Up);
        intputName = (EditText) findViewById(R.id.full_name);
        inputPhone = (EditText) findViewById(R.id.Email);
        inputPassword = (EditText) findViewById(R.id.login_password_login);
        loadingBar = new ProgressDialog(this);


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }
    private void CreateAccount()
    {
        String name = intputName.getText().toString();
        String phone = inputPhone.getText().toString();
        String password = inputPassword.getText().toString();


        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your  name ...", Toast.LENGTH_SHORT).show();
        }
             else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your  phone ...", Toast.LENGTH_SHORT).show();
        }
            else  if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your  password ...", Toast.LENGTH_SHORT).show();
        }
            else
                {
                    loadingBar.setTitle("Create Account");
                    loadingBar.setMessage("Please wait, while we are Checking the credentials. ");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    validatephoneNumber(name,phone,password);

                }


    }
     private void validatephoneNumber(final String name, final String phone, final String password)
     {
         final DatabaseReference RootRef ;
         RootRef = FirebaseDatabase.getInstance().getReference();
         RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot)
             {
                 if(!(dataSnapshot.child("Users").child(phone).exists()))
                 {
                     HashMap<String,Object> userdataMap =new HashMap<>();
                     userdataMap.put("phone",phone);
                     userdataMap.put("password",password);
                     userdataMap.put("name", name);

                     RootRef.child("Users").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()) {
                                 Toast.makeText(RegActivity.this, " Congratulation , your account has been create.", Toast.LENGTH_SHORT).show();
                                 loadingBar.dismiss();
                                 Intent intent = new Intent(RegActivity.this, logiinActivity.class);
                                 startActivity(intent);
                             } else
                             {
                                 loadingBar.dismiss();
                                 Toast.makeText(RegActivity.this, " Network Error : Please try again after some time ...", Toast.LENGTH_SHORT).show();
                             }
                         }
                     });

                 }
                 else
                 {
                     Toast.makeText(RegActivity.this, "This"+ phone + "already exists .", Toast.LENGTH_SHORT).show();
                     loadingBar.dismiss();
                     Toast.makeText(RegActivity.this, " Please try again using another phone number .", Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent(RegActivity.this, MainActivity.class);
                     startActivity(intent);


                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });



     }
}
