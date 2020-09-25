package com.example.resturantdlv.Buyers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.resturantdlv.Admin.AdminCategoryActivity;
import com.example.resturantdlv.Model.Users;
import com.example.resturantdlv.Prevalent.Prevalent;
import com.example.resturantdlv.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity
{

    private EditText InputNumber , InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView AdmineLink,NotAdmine,ForgetPasswordLink;

    private String parentDbName = "Users";
    private CheckBox checkBoxRememberMe ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);





        LoginButton = (Button) findViewById(R.id.login);

        InputNumber = (EditText) findViewById(R.id.login_number_login);
        InputPassword = (EditText) findViewById(R.id.login_password_login);
        AdmineLink = (TextView) findViewById(R.id.admin_panel_link);
        NotAdmine = (TextView) findViewById(R.id.not_admin_panel_link);
        ForgetPasswordLink = (TextView) findViewById(R.id.forget_password);
        loadingBar = new ProgressDialog(this);

        checkBoxRememberMe = (CheckBox) findViewById(R.id.remembar_me_chKb);
        Paper.init(this);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                LoginUser();

            }
        });
        AdmineLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login Admin");
                AdmineLink.setVisibility(View.INVISIBLE);
                NotAdmine.setVisibility(View.INVISIBLE);
                parentDbName ="Admins";
            }
        });
        NotAdmine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login");
                AdmineLink.setVisibility(View.VISIBLE);
                NotAdmine.setVisibility(View.INVISIBLE);
                parentDbName ="Users";
            }
        });
    }

    private void LoginUser()
    {

        String phone = InputNumber.getText().toString();
        String password = InputPassword.getText().toString();
        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(LoginActivity.this, "Please write your  phone ...", Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(password))
        {
            Toast.makeText(LoginActivity.this, "Please write your  password ...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login  Account");
            loadingBar.setMessage("Please wait, while we are Checking the credentials. ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone,password);
        }
    }

    private void AllowAccessToAccount(final String phone, final String password)
    {
        if(checkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey , phone);
            Paper.book().write(Prevalent.UserPasswordKey , password);
        }
        final DatabaseReference RootRef ;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()) {
                    Users users = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if (users.getPhone().equals(phone)) {
                        if (users.getPassword().equals(password))
                        {
                            if(parentDbName.equals("Admins"))
                            {
                                Toast.makeText(LoginActivity.this, "Welcome Admin, you are logged in Successfully ...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);

                            }
                            else if (parentDbName.equals("Users"))
                            {
                                Toast.makeText(LoginActivity.this, " logged in Successfully ...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity2.class);
                                Prevalent.currentOnlineUser = users;
                                startActivity(intent);

                            }




                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, " Password is incorrect ...", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    Toast.makeText(LoginActivity.this, " Account with this" + phone + "number do not exist .", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    // Toast.makeText(logiinActivity.this, "You need to create  a new  Account .", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}