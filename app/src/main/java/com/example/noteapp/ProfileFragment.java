package com.example.noteapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.content.Context;
import android.widget.TextView;


public class ProfileFragment extends Fragment {


    private ImageView imageFromGallery;
    private SharedPreferences sharedPreferences;
    private ActivityResultLauncher<String> content;
    private TextView name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageFromGallery = view.findViewById(R.id.imageGallery);
        name = view.findViewById(R.id.nameProfile);
        sharedPreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String a = sharedPreferences.getString(EditProfileFragment.keyName, "");
        name.setText(sharedPreferences.getString("name", a));
        name.setOnLongClickListener(v -> {
            clear();
            return true;
        });
        imageFromGallery.setOnClickListener(v -> {
            ProfileFragment.this.content.launch("image/*");
        });

        content = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageFromGallery.setImageURI(result);
            }
        });
        getParentFragmentManager().setFragmentResultListener(EditProfileFragment.key, getViewLifecycleOwner(), (requestKey, result) -> {
            name.setText(result.getString(EditProfileFragment.keyProfile));
        });
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.popap_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.editProfilePopap) {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.editProfileFragment);
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }


    private void clear() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
        name.setText("");
    }
}