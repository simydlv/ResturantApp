package com.example.resturantdlv.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.resturantdlv.HomeActivity;
import com.example.resturantdlv.Buyers.HomeActivity2;
import com.example.resturantdlv.Buyers.MainActivity;
import com.example.resturantdlv.R;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView c2,c3,c4,c5,c6,c7,c8,c9,c10,c12,c13,c11;
    private Button LogoutBtn,CheckOrdersBtn,mintaianProductsBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        CheckOrdersBtn = (Button) findViewById(R.id.check_orders_btn);
        mintaianProductsBtn =(Button) findViewById(R.id.mintain_btn);


        mintaianProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, MainActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);
                finish();

            }
        });


        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();


            }
        });

        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminNewOrdersActivity.class);

                startActivity(intent);


            }
        });

        c2 = (ImageView) findViewById(R.id.c2);
        c3 = (ImageView) findViewById(R.id.c3);
        c4 = (ImageView) findViewById(R.id.c4);
        c5 = (ImageView) findViewById(R.id.c5);
        c6 = (ImageView) findViewById(R.id.c6);
        c7 = (ImageView) findViewById(R.id.c7);
        c8 = (ImageView) findViewById(R.id.c8);
       // c9 = (ImageView) findViewById(R.id.c9);
//        c10 = (ImageView) findViewById(R.id.c10);
//        c11 = (ImageView) findViewById(R.id.c11);
//        c12 = (ImageView) findViewById(R.id.c12);
//        c13 = (ImageView) findViewById(R.id.c13);


         c2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                 intent.putExtra("category","c2");
                 startActivity(intent);
             }
         });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("category","c3");
                startActivity(intent);
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("category","c4");
                startActivity(intent);
            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("category","c5");
                startActivity(intent);
            }
        });
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("category","c6");
                startActivity(intent);
            }
        });
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("category","c7");
                startActivity(intent);
            }
        });
        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("category","c8");
                startActivity(intent);
            }
        });
        c9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("category","c9");
                startActivity(intent);
            }
        });
        c10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("category","c10");
                startActivity(intent);
            }
        });
        c12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("category","c11");
                startActivity(intent);
            }
        });
        c13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("category","c13");
                startActivity(intent);
            }
        });
        c11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, HomeActivity.class);
                intent.putExtra("category","c11");
                startActivity(intent);
            }
        });

    }
}
