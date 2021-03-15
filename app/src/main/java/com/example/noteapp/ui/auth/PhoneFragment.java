package com.example.noteapp.ui.auth;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noteapp.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class PhoneFragment extends Fragment {
    private EditText editPhone, editCode;
    private Button btnContinue, btnCheck;
    private String id;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editPhone = view.findViewById(R.id.editPhone);
        editCode = view.findViewById(R.id.editCode);
        editCode.setVisibility(View.INVISIBLE);
        btnContinue = view.findViewById(R.id.btnContinue);
        btnCheck = view.findViewById(R.id.btnCheck);
        btnCheck.setVisibility(View.INVISIBLE);
        btnContinue.setOnClickListener(v -> {
            requestSms();
            btnContinue.setVisibility(View.INVISIBLE);
            btnCheck.setVisibility(View.VISIBLE);
            editPhone.setVisibility(View.INVISIBLE);
            editCode.setVisibility(View.VISIBLE);
            btnCheck.setOnClickListener(v1 -> {
                confirm();
            });
        });
        setCallback();
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    // метод при нажатии назад приложение закрывается
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });

    }

    private void confirm() {
        String codeInSms = editCode.getText().toString().trim();
        if (codeInSms.length() == 6 && TextUtils.isDigitsOnly(codeInSms))
            signIn(PhoneAuthProvider.getCredential(id, codeInSms));
    }

    public void setCallback() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("Phone", "onVerificationCompleted");
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("Phone", "onVerificationFailed" + e.getMessage());
            }

            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                id = s;
            }

        };
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
            else
                Toast.makeText(requireContext(), "Ошибка" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
    private void requestSms() {
        String phone = editPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            editPhone.setError("Напишите телефон номера!");
            editPhone.requestFocus();
            return;
        }
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance()).setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS).setActivity(requireActivity()).setCallbacks(mCallbacks).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}