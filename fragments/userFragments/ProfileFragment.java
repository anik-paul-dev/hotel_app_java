package com.example.hotel_booking_app.fragments.userFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.models.Settings;
import com.example.hotel_booking_app.models.User;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ImageView ivProfile;
    private TextInputEditText etName, etPhone, etEmail, etAddress, etPincode, etDob;
    private Button btnUpdate, btnPickImage;
    private TextView tvWebsiteTitle, tvWebsiteDescription;
    private LinearLayout socialLinksLayout;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        ivProfile = view.findViewById(R.id.iv_profile);
        etName = view.findViewById(R.id.et_name);
        etPhone = view.findViewById(R.id.et_phone);
        etEmail = view.findViewById(R.id.et_email);
        etAddress = view.findViewById(R.id.et_address);
        etPincode = view.findViewById(R.id.et_pincode);
        etDob = view.findViewById(R.id.et_dob);
        btnUpdate = view.findViewById(R.id.btn_update);
        btnPickImage = view.findViewById(R.id.btn_pick_image);
        tvWebsiteTitle = view.findViewById(R.id.tv_website_title);
        tvWebsiteDescription = view.findViewById(R.id.tv_website_description);
        socialLinksLayout = view.findViewById(R.id.social_links);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                imageUri = result.getData().getData();
                Glide.with(getContext()).load(imageUri).into(ivProfile);
                btnPickImage.setText("Image Selected");
            }
        });

        btnPickImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnUpdate.setOnClickListener(v -> updateProfile());

        loadProfile();
        loadSettings();

        return view;
    }

    private void loadProfile() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String userId = mAuth.getCurrentUser().getUid();
        apiService.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    Glide.with(getContext()).load(user.getImageUrl()).into(ivProfile);
                    etName.setText(user.getName());
                    etPhone.setText(user.getPhone());
                    etEmail.setText(user.getEmail());
                    etAddress.setText(user.getAddress());
                    etPincode.setText(user.getPincode());
                    etDob.setText(user.getDob());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {}
        });
    }

    private void loadSettings() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getSettings().enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                if (response.isSuccessful()) {
                    Settings settings = response.body();
                    tvWebsiteTitle.setText(settings.getWebsiteTitle());
                    tvWebsiteDescription.setText(settings.getWebsiteDescription());
                    for (String link : settings.getSocialLinks()) {
                        TextView tvLink = new TextView(getContext());
                        tvLink.setText(link);
                        tvLink.setClickable(true);
                        socialLinksLayout.addView(tvLink);
                    }
                }
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {}
        });
    }

    private void updateProfile() {
        String userId = mAuth.getCurrentUser().getUid();
        User user = new User();
        user.setId(userId);
        user.setName(etName.getText().toString().trim());
        user.setPhone(etPhone.getText().toString().trim());
        user.setEmail(etEmail.getText().toString().trim());
        user.setAddress(etAddress.getText().toString().trim());
        user.setPincode(etPincode.getText().toString().trim());
        user.setDob(etDob.getText().toString().trim());

        if (imageUri != null) {
            StorageReference fileRef = storageReference.child("users/" + userId + ".jpg");
            fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    user.setImageUrl(uri.toString());
                    saveUser(user);
                });
            });
        } else {
            saveUser(user);
        }
    }

    private void saveUser(User user) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.updateUser(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}