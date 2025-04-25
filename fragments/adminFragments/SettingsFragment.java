package com.example.hotel_booking_app.fragments.adminFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.models.Settings;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    private EditText etWebsiteTitle, etWebsiteDescription, etAddress, etPhone, etEmail, etGoogleMapIframe;
    private Switch switchShutdown;
    private Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        etWebsiteTitle = view.findViewById(R.id.et_website_title);
        etWebsiteDescription = view.findViewById(R.id.et_website_description);
        etAddress = view.findViewById(R.id.et_address);
        etPhone = view.findViewById(R.id.et_phone);
        etEmail = view.findViewById(R.id.et_email);
        etGoogleMapIframe = view.findViewById(R.id.et_google_map_iframe);
        switchShutdown = view.findViewById(R.id.switch_shutdown);
        btnSave = view.findViewById(R.id.btn_save);

        loadSettings();

        btnSave.setOnClickListener(v -> saveSettings());

        return view;
    }

    private void loadSettings() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getSettings().enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                if (response.isSuccessful()) {
                    Settings settings = response.body();
                    etWebsiteTitle.setText(settings.getWebsiteTitle());
                    etWebsiteDescription.setText(settings.getWebsiteDescription());
                    etAddress.setText(settings.getAddress());
                    etPhone.setText(String.join(", ", settings.getPhoneNumbers()));
                    etEmail.setText(settings.getEmail());
                    etGoogleMapIframe.setText(settings.getGoogleMapIframe());
                    switchShutdown.setChecked(settings.isShutdown());
                }
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {}
        });
    }

    private void saveSettings() {
        Settings settings = new Settings();
        settings.setWebsiteTitle(etWebsiteTitle.getText().toString().trim());
        settings.setWebsiteDescription(etWebsiteDescription.getText().toString().trim());
        settings.setAddress(etAddress.getText().toString().trim());
        settings.setPhoneNumbers(Arrays.asList(etPhone.getText().toString().trim().split(",")));
        settings.setEmail(etEmail.getText().toString().trim());
        settings.setGoogleMapIframe(etGoogleMapIframe.getText().toString().trim());
        settings.setShutdown(switchShutdown.isChecked());

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.updateSettings(settings).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Settings Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to update settings", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}