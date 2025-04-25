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
import com.example.hotel_booking_app.models.Feature;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFeatureDialog extends DialogFragment {

    private TextInputEditText etName;
    private Button btnSubmit, btnCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_feature, container, false);

        etName = view.findViewById(R.id.et_name);
        btnSubmit = view.findViewById(R.id.btn_submit);
        btnCancel = view.findViewById(R.id.btn_cancel);

        btnSubmit.setOnClickListener(v -> submitFeature());
        btnCancel.setOnClickListener(v -> dismiss());

        return view;
    }

    private void submitFeature() {
        String name = etName.getText().toString().trim();
        Feature feature = new Feature();
        feature.setName(name);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.addFeature(feature).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Feature Added", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Failed to add feature", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}