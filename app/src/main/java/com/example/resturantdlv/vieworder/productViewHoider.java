package com.example.resturantdlv.vieworder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.resturantdlv.Interface.ItemClickListner;
import com.example.resturantdlv.R;

public class productViewHoider extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName,txtProductPirce;
    public ImageView imageView;
    public ItemClickListner listner;
    public productViewHoider(View itemView)
    {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductPirce = (TextView) itemView.findViewById(R.id.product_Prices);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;

    }

    @Override
    public void onClick(View view) {

        listner.onClick(view ,getAdapterPosition(),false );

    }
}
