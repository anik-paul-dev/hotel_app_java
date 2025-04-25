package com.example.hotel_booking_app.fragments.publicFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.models.Query;
import com.example.hotel_booking_app.models.Settings;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsFragment extends Fragment {

    private WebView webViewMap;
    private TextView tvAddress, tvPhone, tvEmail;
    private LinearLayout socialLinksLayout;
    private TextInputEditText etName, etEmail, etSubject, etMessage;
    private Button btnSend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        webViewMap = view.findViewById(R.id.webview_map);
        tvAddress = view.findViewById(R.id.tv_address);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvEmail = view.findViewById(R.id.tv_email);
        socialLinksLayout = view.findViewById(R.id.social_links);
        etName = view.findViewById(R.id.et_name);
        etEmail = view.findViewById(R.id.et_email);
        etSubject = view.findViewById(R.id.et_subject);
        etMessage = view.findViewById(R.id.et_message);
        btnSend = view.findViewById(R.id.btn_send);

        loadSettings();

        btnSend.setOnClickListener(v -> sendQuery());

        return view;
    }

    private void loadSettings() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getSettings().enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                if (response.isSuccessful()) {
                    Settings settings = response.body();
                    webViewMap.loadData(settings.getGoogleMapIframe(), "text/html", "utf-8");
                    tvAddress.setText(settings.getAddress());
                    tvPhone.setText(String.join(", ", settings.getPhoneNumbers()));
                    tvEmail.setText(settings.getEmail());
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

    private void sendQuery() {
        Query query = new Query();
        query.setName(etName.getText().toString().trim());
        query.setEmail(etEmail.getText().toString().trim());
        query.setSubject(etSubject.getText().toString().trim());
        query.setMessage(etMessage.getText().toString().trim());
        query.setDate(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.addQuery(query).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Query Sent", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etEmail.setText("");
                    etSubject.setText("");
                    etMessage.setText("");
                } else {
                    Toast.makeText(getActivity(), "Failed to send query", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}