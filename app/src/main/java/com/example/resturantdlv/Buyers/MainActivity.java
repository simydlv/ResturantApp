package com.example.resturantdlv.Buyers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.resturantdlv.Model.Users;
import com.example.resturantdlv.Prevalent.Prevalent;
import com.example.resturantdlv.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

     private Button JoinNowButton , loginButton;
     private ProgressDialog loadingBar;
     private TextView sellerBegin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JoinNowButton =(Button) findViewById(R.id.login);
        loginButton =(Button) findViewById(R.id.singin);
        sellerBegin =(TextView) findViewById(R.id.seller_begin);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);


        JoinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
//        sellerBegin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, SellerRegistrationActivity.class);
//                startActivity(intent);
//
//            }
//        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegActivity.class);
                startActivity(intent);

            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPhoneKey != "" && UserPasswordKey != "")
        {
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAccess(UserPhoneKey,UserPasswordKey);


                loadingBar.setTitle("Aleady Logged in ");
                loadingBar.setMessage("Please wait ...... ");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }




    }
     private void AllowAccess( final String phone, final String password)
     {
         final DatabaseReference RootRef ;
         RootRef = FirebaseDatabase.getInstance().getReference();

         RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot)
             {
                 if(dataSnapshot.child("Users").child(phone).exists())
                 {
                     Users users =dataSnapshot.child("Users").child(phone).getValue(Users.class);
                     if (users.getPhone().equals(phone))
                     {
                         if (users.getPassword().equals(password))
                         {
                             Toast.makeText(MainActivity.this, " please wait, are already logged in ...", Toast.LENGTH_SHORT).show();
                             loadingBar.dismiss();
                             Intent intent = new Intent(MainActivity.this, HomeActivity2.class);
                             Prevalent.currentOnlineUser=users;
                             startActivity(intent);

                         }
                         else
                         {
                             loadingBar.dismiss();
                             Toast.makeText(MainActivity.this, " Password is incorrect ...", Toast.LENGTH_SHORT).show();
                         }

                     }

                 }
                 else
                 {
                     Toast.makeText(MainActivity.this, " Account with this" + phone+ "number do not exist .", Toast.LENGTH_SHORT).show();
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
