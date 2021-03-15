package com.example.noteapp.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.App;
import com.example.noteapp.R;
import com.example.noteapp.interfaces.OnItemClickListener;
import com.example.noteapp.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class HomeFragment extends Fragment implements OnItemClickListener {
    public static final String CheckData = "CheckData";
    public static final String CheckKEY = "CheckKEY";
    private boolean chekAddData = false;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter();
        adapter.setOnItemClickListener(this);
        setHasOptionsMenu(true);
        ImputData();
    }

    private void ImputData() {
        List<Note> list = App.getDataBase().noteDao().getAll();
        adapter.setList(list);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(v -> {
            chekAddData = false;
            openNote(null);

        });
        getParentFragmentManager().setFragmentResultListener(CheckKEY,
                getViewLifecycleOwner(), (requestKey, result) -> {
                    Note note = (Note) result.getSerializable(CheckData);
                    if (chekAddData) adapter.getList(position, note);
                    else adapter.addItem(note);

                });
    }



    public void onItemClick(int position) {
        this.position = position;
        chekAddData = true;
        Note note = adapter.getItem(position);
        openNote(note);
    }

    @Override
    public void onLongClick(int position) {
        new AlertDialog.Builder(getContext()).setTitle("Are sure about that?").setMessage("Are you sure??")
                .setPositiveButton("Yes!", (dialog, which) -> {
                    App.getDataBase().noteDao().delete(adapter.getItem(position));
                    App.getDataBase().noteDao().update(adapter.getItem(position));
                    adapter.remove(position);
                }).setNegativeButton("No...", null).show();
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.popup_menu_for_home_fragment, menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sortForHomeFragment) {
            adapter.sortList(App.getDataBase().noteDao().sortAll());
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    private void openNote(Note note) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CheckData, note);
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.noteFragment, bundle);

    }
}

