package com.example.noteapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import android.content.Context;



public class ProfileFragment extends Fragment {


    private static final int RESULT_OK =2 ;
    private ImageView imageView;
    private  final int Pick_image = 1;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
        imageView =view.findById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent =new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,Pick_image);
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent){
    super.onActivityResult(requestCode,resultCode,imageReturnedIntent);
    switch (requestCode){
        case Pick_image:
            if(resultCode==RESULT_OK){
                try{
                    final Uri imageUri =imageReturnedIntent.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imageView.setImageBitmap(selectedImage);
                                    } catch(FileNotFoundException e){
                    e.printStackTrace();
                }
            }
    }

    }



}