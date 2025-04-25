package com.example.hotel_booking_app.dialogs;

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
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordDialog extends DialogFragment {

    private TextInputEditText etEmail;
    private Button btnSendLink, btnCancel;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_forget_password, container, false);

        mAuth = FirebaseAuth.getInstance();
        etEmail = view.findViewById(R.id.et_email);
        btnSendLink = view.findViewById(R.id.btn_send_link);
        btnCancel = view.findViewById(R.id.btn_cancel);

        btnSendLink.setOnClickListener(v -> sendResetLink());
        btnCancel.setOnClickListener(v -> dismiss());

        return view;
    }

    private void sendResetLink() {
        String email = etEmail.getText().toString().trim();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                        dismiss();
                    } else {
                        // Check if admin email
                        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
                        apiService.forgetPassword(email).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                } else {
                                    Toast.makeText(getActivity(), "Failed to send reset link", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }
}