package com.example.noteapp.ui.onBoard;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.noteapp.R;
import com.example.noteapp.ui.boardAdapter;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import ActivityResultContracts.Prefs;


public class BoardFragment extends Fragment {
    private SpringDotsIndicator springDotsIndicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 viewPager;
        viewPager = view.findViewById(R.id.viewPager);
        Button btnSkip = view.findViewById(R.id.btnSkip);
        boardAdapter adapter = new boardAdapter();
        SpringDotsIndicator springDotsIndicator = viewPager.findViewById(R.id.spring_dots_indicator);
        viewPager.setAdapter(adapter);
        springDotsIndicator.setViewPager2(viewPager);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        adapter.setOnStartClickListener(new boardAdapter.OnStartClickListener() {
            @Override
            public void onClick() {
                close();
            }

        });
        requireActivity()
                .getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(),
                        new OnBackPressedCallback(true) {
                            @Override
                            public void handleOnBackPressed() {
                                requireActivity().finish();
                            }
                        });


    }

    private void close() {
        Prefs prefs = new Prefs(requireContext());
        NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_controller_view_tag);
        navController.navigateUp();

    }
}