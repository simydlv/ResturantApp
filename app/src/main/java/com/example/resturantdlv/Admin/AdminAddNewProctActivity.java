package com.example.resturantdlv.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.resturantdlv.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProctActivity extends AppCompatActivity {

    private String CategoryName,PriceProduct,pname,saveCurrentTime,saveCurrentData ;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private EditText InputProdectName, InputProductPrice;
    private static final int GalleryPick =1;
    private Uri ImageUri;
    private String productRandomKey,downloadImageUrl;
    private StorageReference ProductImageRefe;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_proct);

        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImageRefe = FirebaseStorage.getInstance().getReference().child("Product Image");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        AddNewProductButton = (Button) findViewById(R.id.add_new_product);
        InputProdectName = (EditText) findViewById(R.id.product_name);
        InputProductPrice = (EditText) findViewById(R.id.product_price);
        InputProductImage = (ImageView) findViewById(R.id.select_product_image);
        loadingBar = new ProgressDialog(this);

        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });


        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidataProductData();

            }
        });


        //Toast.makeText(this, " Welcome Admin ....", Toast.LENGTH_SHORT).show();
    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);




    }

   private void ValidataProductData()
   {
       PriceProduct = InputProductPrice.getText().toString();
       pname = InputProdectName.getText().toString();
       
       
       if(ImageUri==null)
       {
           Toast.makeText(this, "Product image is mandatory ...", Toast.LENGTH_SHORT).show();
       }
       else if(TextUtils.isEmpty(PriceProduct))
       {
           Toast.makeText(this, "Please write product Pirce ...", Toast.LENGTH_SHORT).show();
       }
       else if(TextUtils.isEmpty(pname))
       {
           Toast.makeText(this, "Please write product Name ...", Toast.LENGTH_SHORT).show();
       }
       else
       {
           storeProductInformation();
       }

       

   }
   private void storeProductInformation()
   {
       loadingBar.setTitle("Add New Product");
       loadingBar.setMessage(" Dear Admin, Please wait, while we are adding the new Product. ");
       loadingBar.setCanceledOnTouchOutside(false);
       loadingBar.show();

       Calendar calendar = Calendar.getInstance();

       SimpleDateFormat currentData = new SimpleDateFormat("MMM dd,yyyy");
       saveCurrentData = currentData.format(calendar.getTime());
       SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
       saveCurrentTime = currentData.format(calendar.getTime());
       productRandomKey =  saveCurrentData + saveCurrentTime;
       final StorageReference filePath = ProductImageRefe.child(ImageUri.getLastPathSegment() + productRandomKey +".jpg");

       final UploadTask uploadTask = filePath.putFile(ImageUri);


       uploadTask.addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e)
           {
               String message = e.toString();
               Toast.makeText(AdminAddNewProctActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();
               loadingBar.dismiss();

           }
       }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
           {
               Toast.makeText(AdminAddNewProctActivity.this, "Image uploaded Successfully ...", Toast.LENGTH_SHORT).show();
               Task<Uri> uriTask =uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                   @Override
                   public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                   {
                       if (!task.isSuccessful())
                       {
                           throw task.getException();
                       }
                       downloadImageUrl = filePath.getDownloadUrl().toString();
                       return filePath.getDownloadUrl();

                   }

               }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                   @Override
                   public void onComplete(@NonNull Task<Uri> task)
                   {
                       if (task.isSuccessful())
                       {
                           downloadImageUrl = task.getResult().toString();
                           Toast.makeText(AdminAddNewProctActivity.this, "got the Product image save to Database Successfully ..", Toast.LENGTH_SHORT).show();
                           savaProductInformationToDatabase();
                           
                       }

                   }
               });
               

           }
       });


   }

   private void savaProductInformationToDatabase()
   {
       HashMap<String , Object> productMap = new HashMap<>();
       productMap.put("pid",productRandomKey);
       productMap.put("data",saveCurrentData);
       productMap.put("time",saveCurrentTime);
       productMap.put("price",PriceProduct);
       productMap.put("name",pname);
       productMap.put("Category",CategoryName);
       productMap.put("Image",downloadImageUrl);
       
       ProductsRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task)
           {
               if(task.isSuccessful())
               {
                   Intent intent = new Intent(AdminAddNewProctActivity.this, AdminCategoryActivity.class);
                   startActivity(intent);
                   loadingBar.dismiss();
                   Toast.makeText(AdminAddNewProctActivity.this, "Product is added successfully ....", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   loadingBar.dismiss();
                   String message = task.getException().toString();
                   Toast.makeText(AdminAddNewProctActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
               }
           }
       });


   }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
           ImageUri = data.getData();
           InputProductImage.setImageURI(ImageUri);

        }
    }
}