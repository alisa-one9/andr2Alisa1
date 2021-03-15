package com.example.noteapp.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noteapp.App;
import com.example.noteapp.R;
import com.example.noteapp.models.Note;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class NoteFragment extends Fragment {
    private Note note;
    private EditText editText;
    private Button btnSave;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        btnSave = view.findViewById(R.id.btnSaveProf);
        note = (Note) requireArguments().getSerializable(HomeFragment.CheckData);
        if (note != null) editText.setText(note.getTitle());
        btnSave.setOnClickListener(v -> {
            String text = editText.getText().toString().trim();
            if (note == null) {
                note = new Note(text);
                App.getDataBase().noteDao().insert(note);
            } else {
                note.setTitle(text);
                App.getDataBase().noteDao().update(note);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable(HomeFragment.CheckData, note);
            getParentFragmentManager().setFragmentResult(HomeFragment.CheckKEY, bundle);
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
        });
    }
}




 //   private void saveToFireStore() {
//        FirebaseFirestore.getInstance()
 //               .collection("notes")
 //               .add(note)
  //              .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
  //                  @Override
 //                   public void onComplete(@NonNull Task<DocumentReference> task) {
  //                      if (task.isSuccessful()) {
  //                          App.getDataBase().noteDao().insert(note);
  //                          Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show();
  //                          close();
  //                      }
 //                   }
  //              });




