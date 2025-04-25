package com.example.hotel_booking_app.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.activities.AdminActivity;
import com.example.hotel_booking_app.activities.UserActivity;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginDialog extends DialogFragment {

    private TextInputEditText etEmail, etPassword;
    private Button btnLogin, btnForgetPassword;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);
        btnForgetPassword = view.findViewById(R.id.btn_forget_password);

        btnLogin.setOnClickListener(v -> loginUser());
        btnForgetPassword.setOnClickListener(v -> {
            ForgetPasswordDialog dialog = new ForgetPasswordDialog();
            dialog.show(getParentFragmentManager(), "forget_password");
            dismiss();
        });

        return view;
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Check if admin login
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.adminLogin(email, password).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(getActivity(), AdminActivity.class));
                    dismiss();
                } else {
                    // Try user login
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getActivity(), UserActivity.class));
                                    dismiss();
                                } else {
                                    Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}