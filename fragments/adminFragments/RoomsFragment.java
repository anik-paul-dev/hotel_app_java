package com.example.hotel_booking_app.fragments.adminFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.adapters.RoomAdapter;
import com.example.hotel_booking_app.dialogs.AddRoomDialog;
import com.example.hotel_booking_app.models.Room;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class RoomsFragment extends Fragment {

    private RecyclerView roomsRecycler;
    private FloatingActionButton fabAddRoom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_rooms, container, false);

        roomsRecycler = view.findViewById(R.id.rooms_recycler);
        fabAddRoom = view.findViewById(R.id.fab_add_room);
        roomsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadRooms();

        fabAddRoom.setOnClickListener(v -> {
            AddRoomDialog dialog = new AddRoomDialog();
            dialog.show(getParentFragmentManager(), "add_room");
        });

        return view;
    }

    private void loadRooms() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getRooms(null).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    RoomAdapter adapter = new RoomAdapter(response.body(), getContext(), false);
                    roomsRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {}
        });
    }
}