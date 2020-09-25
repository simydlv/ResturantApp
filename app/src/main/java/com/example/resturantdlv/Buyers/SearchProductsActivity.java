package com.example.resturantdlv.Buyers;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resturantdlv.R;

public class SearchProductsActivity extends AppCompatActivity
{
    private Button SearchBtn;
    private EditText inputText;
    private RecyclerView searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);


        inputText = findViewById(R.id.search_product_name);
        searchList = findViewById(R.id.search_list);
        SearchBtn = findViewById(R.id.search_btn);
        searchList.setLayoutManager(new LinearLayoutManager(SearchProductsActivity.this));

    }
}