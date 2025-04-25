package com.example.hotel_booking_app.dialogs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.models.Room;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class AddRoomDialog extends DialogFragment {

    private TextInputEditText etName, etArea, etPrice, etQuantity, etMaxAdults, etMaxChildren, etDescription;
    private Button btnAddImages, btnSubmit, btnCancel;
    private List<CheckBox> featureCheckBoxes, facilityCheckBoxes;
    private List<Uri> imageUris = new ArrayList<>();
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_room, container, false);

        etName = view.findViewById(R.id.et_name);
        etArea = view.findViewById(R.id.et_area);
        etPrice = view.findViewById(R.id.et_price);
        etQuantity = view.findViewById(R.id.et_quantity);
        etMaxAdults = view.findViewById(R.id.et_max_adults);
        etMaxChildren = view.findViewById(R.id.et_max_children);
        etDescription = view.findViewById(R.id.et_description);
        btnAddImages = view.findViewById(R.id.btn_add_images);
        btnSubmit = view.findViewById(R.id.btn_submit);
        btnCancel = view.findViewById(R.id.btn_cancel);

        // Initialize feature and facility checkboxes dynamically
        featureCheckBoxes = new ArrayList<>();
        facilityCheckBoxes = new ArrayList<>();
        // Assume fetched from backend
        // Add CheckBoxes programmatically or define in XML

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                if (result.getData().getClipData() != null) {
                    for (int i = 0; i < result.getData().getClipData().getItemCount(); i++) {
                        imageUris.add(result.getData().getClipData().getItemAt(i).getUri());
                    }
                } else {
                    imageUris.add(result.getData().getData());
                }
                btnAddImages.setText(imageUris.size() + " Images Selected");
            }
        });

        btnAddImages.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            imagePickerLauncher.launch(intent);
        });

        btnSubmit.setOnClickListener(v -> submitRoom());
        btnCancel.setOnClickListener(v -> dismiss());

        return view;
    }

    private void submitRoom() {
        Room room = new Room();
        room.setName(etName.getText().toString().trim());
        room.setArea(Integer.parseInt(etArea.getText().toString().trim()));
        room.setPrice(Double.parseDouble(etPrice.getText().toString().trim()));
        room.setQuantity(Integer.parseInt(etQuantity.getText().toString().trim()));
        room.setMaxAdults(Integer.parseInt(etMaxAdults.getText().toString().trim()));
        room.setMaxChildren(Integer.parseInt(etMaxChildren.getText().toString().trim()));
        room.setDescription(etDescription.getText().toString().trim());
        room.setStatus("active");

        List<String> features = new ArrayList<>();
        for (CheckBox cb : featureCheckBoxes) {
            if (cb.isChecked()) features.add(cb.getText().toString());
        }
        room.setFeatures(features);

        List<String> facilities = new ArrayList<>();
        for (CheckBox cb : facilityCheckBoxes) {
            if (cb.isChecked()) facilities.add(cb.getText().toString());
        }
        room.setFacilities(facilities);

        // Upload images and get URLs
        List<String> imageUrls = new ArrayList<>();
        // Implement image upload logic here
        room.setImages(imageUrls);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.addRoom(room).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Room Added", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Failed to add room", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}