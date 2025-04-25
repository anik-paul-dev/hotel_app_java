package com.example.hotel_booking_app.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.models.Booking;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BookNowActivity extends AppCompatActivity {

    private TextInputEditText etName, etPhone, etAddress, etCheckIn, etCheckOut;
    private TextView tvRoomTitle, tvPrice, tvTotalDays, tvTotalAmount;
    private ImageView ivRoom;
    private Button btnPayNow;
    private int roomId;
    private double pricePerNight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);

        roomId = getIntent().getIntExtra("room_id", -1);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);
        etCheckIn = findViewById(R.id.et_check_in);
        etCheckOut = findViewById(R.id.et_check_out);
        tvRoomTitle = findViewById(R.id.tv_room_title);
        tvPrice = findViewById(R.id.tv_price);
        ivRoom = findViewById(R.id.iv_room);
        tvTotalDays = findViewById(R.id.tv_total_days);
        tvTotalAmount = findViewById(R.id.tv_total_amount);
        btnPayNow = findViewById(R.id.btn_pay_now);

        loadRoomDetails(roomId);

        etCheckIn.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(android.text.Editable s) {
                calculateTotal();
            }
        });

        etCheckOut.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(android.text.Editable s) {
                calculateTotal();
            }
        });

        btnPayNow.setOnClickListener(v -> processPayment());
    }

    private void loadRoomDetails(int roomId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getRoom(roomId).enqueue(new Callback<com.example.hotel_booking_app.models.Room>() {
            @Override
            public void onResponse(Call<com.example.hotel_booking_app.models.Room> call, Response<com.example.hotel_booking_app.models.Room> response) {
                if (response.isSuccessful()) {
                    com.example.hotel_booking_app.models.Room room = response.body();
                    tvRoomTitle.setText(room.getName());
                    tvPrice.setText("Price per Night: $" + room.getPrice());
                    pricePerNight = room.getPrice();
                    Glide.with(BookNowActivity.this).load(room.getImages().get(0)).into(ivRoom);
                }
            }

            @Override
            public void onFailure(Call<com.example.hotel_booking_app.models.Room> call, Throwable t) {}
        });
    }

    private void calculateTotal() {
        String checkIn = etCheckIn.getText().toString();
        String checkOut = etCheckOut.getText().toString();
        if (!checkIn.isEmpty() && !checkOut.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateIn = sdf.parse(checkIn);
                Date dateOut = sdf.parse(checkOut);
                long diff = dateOut.getTime() - dateIn.getTime();
                long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                if (days > 0) {
                    tvTotalDays.setText("Total Days: " + days);
                    double total = days * pricePerNight;
                    tvTotalAmount.setText("Total Amount: $" + total);
                } else {
                    tvTotalDays.setText("Total Days: Invalid");
                    tvTotalAmount.setText("Total Amount: $0");
                }
            } catch (ParseException e) {
                tvTotalDays.setText("Total Days: Invalid");
                tvTotalAmount.setText("Total Amount: $0");
            }
        }
    }

    private void processPayment() {
        Booking booking = new Booking();
        booking.setRoomId(roomId);
        booking.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        booking.setCheckIn(etCheckIn.getText().toString());
        booking.setCheckOut(etCheckOut.getText().toString());
        booking.setTotalPrice(Double.parseDouble(tvTotalAmount.getText().toString().replace("Total Amount: $", "")));
        booking.setStatus("booked");

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.addBooking(booking).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Mock payment processing
                    Toast.makeText(BookNowActivity.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(BookNowActivity.this, "Booking Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(BookNowActivity.this, "Booking Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}