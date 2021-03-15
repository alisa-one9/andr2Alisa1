package com.example.noteapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultOwner;
import androidx.navigation.Navigation;

public class EditProfileFragment extends Fragment {

    public static String keyProfile = "keyProfile";
    public static String keyName = "keyName";
    public static String key = "key";

    private Button btnSave;
    private EditText editText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSave = view.findViewById(R.id.btnSaveProf);
        editText = view.findViewById(R.id.editTextProf);
        btnSave.setOnClickListener(v -> {
            save();
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
        });
    }



    public void save() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(keyName, editText.getText().toString()).apply();
        Bundle bundle = new Bundle();
        bundle.putSerializable(keyProfile, editText.getText().toString());
        getParentFragmentManager().setFragmentResult(key, bundle);
    }


}
