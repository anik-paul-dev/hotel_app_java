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
import com.example.hotel_booking_app.models.Facility;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFacilityDialog extends DialogFragment {

    private TextInputEditText etName, etDescription;
    private Button btnAddImage, btnSubmit, btnCancel;
    private Uri imageUri;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_facility, container, false);

        etName = view.findViewById(R.id.et_name);
        etDescription = view.findViewById(R.id.et_description);
        btnAddImage = view.findViewById(R.id.btn_add_image);
        btnSubmit = view.findViewById(R.id.btn_submit);
        btnCancel = view.findViewById(R.id.btn_cancel);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                imageUri = result.getData().getData();
                btnAddImage.setText("Image Selected");
            }
        });

        btnAddImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnSubmit.setOnClickListener(v -> submitFacility());
        btnCancel.setOnClickListener(v -> dismiss());

        return view;
    }

    private void submitFacility() {
        Facility facility = new Facility();
        facility.setName(etName.getText().toString().trim());
        facility.setDescription(etDescription.getText().toString().trim());
        // Upload image and set URL
        facility.setImage(imageUri != null ? imageUri.toString() : "");

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.addFacility(facility).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Facility Added", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Failed to add facility", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}