package com.example.resturantdlv.Buyers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resturantdlv.Admin.AdminMaintainProductsActivity;
import com.example.resturantdlv.Model.Products;
import com.example.resturantdlv.Prevalent.Prevalent;
import com.example.resturantdlv.R;
import com.example.resturantdlv.vieworder.productViewHoider;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity2 extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private  String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            type = getIntent().getExtras().get("Admin").toString();
        }




        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        Paper.init(this);

       // productViewHoider ProductViewHoider = new productViewHoider();









        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Home");

        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();


                if(!type.equals("Admin"))
                {
                    Intent intent = new Intent(HomeActivity2.this, CartActivity.class);
                    startActivity(intent);



                }

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView =(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.username_profile_image);


        if (!type.equals("Admin"))
        {
            userNameTextView.setText(Prevalent.currentOnlineUser.getName());


            Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
        }




        recyclerView = findViewById(R.id.recycler_meanu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);









        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(ProductsRef, Products.class).build();
        FirebaseRecyclerAdapter<Products, productViewHoider> adapter = new FirebaseRecyclerAdapter<Products, productViewHoider>(options) {
            @Override
            protected void onBindViewHolder(@NonNull productViewHoider holder, int position, @NonNull final Products model)
            {

               holder.txtProductName.setText(model.getPname());
                holder.txtProductPirce.setText("Pirce ="+model.getPrice() + "RS");
                holder.txtProductName.setText(model.getPname());
                Picasso.get().load(model.getImage()).into(holder.imageView);




                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {


                        if(type.equals("Admin"))
                        {
                            Intent intent = new Intent(HomeActivity2.this, AdminMaintainProductsActivity.class);
                            intent.putExtra("pid",model.getPid());
                            startActivity(intent);



                        }
                        else
                        {
                            Intent intent = new Intent(HomeActivity2.this, ProductDetailsActivity.class);
                            intent.putExtra("pid",model.getPid());
                            startActivity(intent);

                        }


                    }
                });



            }

            @NonNull
            @Override
            public productViewHoider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item_layout , parent,false);
                productViewHoider hoider = new productViewHoider(view);
                return hoider;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_activity2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

//        if (id==R.id.action_settings)
//        {
//            return true;
//
//d
//        }
        return super.onOptionsItemSelected(item);

    }
    public boolean onNavigationtionItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.nav_cart)
        {
            if(!type.equals("Admin"))
            {


                Intent intent = new Intent(HomeActivity2.this,CartActivity.class);
                startActivity(intent);

            }


        }
        else if (id == R.id.nav_search)
        {
            if(!type.equals("Admin"))
            {

                Intent intent = new Intent(HomeActivity2.this, SearchProductsActivity.class);
                startActivity(intent);


            }


        }
        else if (id == R.id.nav_set)
        {
            if(!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity2.this, SettingActivity.class);
                startActivity(intent);



            }


        }
        else if (id == R.id.nav_logout)
        {
            if(!type.equals("Admin"))
            {

                Paper.book().destroy();

                Intent intent = new Intent(HomeActivity2.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();



            }


        }
        DrawerLayout drawer =(DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    

}