package com.example.hotel_booking_app.dialogs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.activities.UserActivity;
import com.example.hotel_booking_app.models.User;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterDialog extends DialogFragment {

    private TextInputEditText etName, etPhone, etEmail, etAddress, etPincode, etDob, etPassword, etConfirmPassword;
    private Button btnRegister, btnPickImage;
    private Uri imageUri;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_register, container, false);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        etName = view.findViewById(R.id.et_name);
        etPhone = view.findViewById(R.id.et_phone);
        etEmail = view.findViewById(R.id.et_email);
        etAddress = view.findViewById(R.id.et_address);
        etPincode = view.findViewById(R.id.et_pincode);
        etDob = view.findViewById(R.id.et_dob);
        etPassword = view.findViewById(R.id.et_password);
        etConfirmPassword = view.findViewById(R.id.et_confirm_password);
        btnRegister = view.findViewById(R.id.btn_register);
        btnPickImage = view.findViewById(R.id.btn_pick_image);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                imageUri = result.getData().getData();
                btnPickImage.setText("Image Selected");
            }
        });

        btnPickImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnRegister.setOnClickListener(v -> registerUser());

        return view;
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String pincode = etPincode.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        if (imageUri != null) {
                            StorageReference fileRef = storageReference.child("users/" + userId + ".jpg");
                            fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                    saveUserToBackend(userId, name, phone, email, address, pincode, dob, uri.toString());
                                });
                            });
                        } else {
                            saveUserToBackend(userId, name, phone, email, address, pincode, dob, "");
                        }
                    } else {
                        Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserToBackend(String userId, String name, String phone, String email, String address, String pincode, String dob, String imageUrl) {
        User user = new User(userId, name, phone, email, address, pincode, dob, imageUrl, "active", true);
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.registerUser(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(getActivity(), UserActivity.class));
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Failed to save user data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}