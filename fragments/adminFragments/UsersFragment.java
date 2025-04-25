package com.example.hotel_booking_app.fragments.adminFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.adapters.UserAdapter;
import com.example.hotel_booking_app.models.User;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class UsersFragment extends Fragment {

    private RecyclerView usersRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        usersRecycler = view.findViewById(R.id.users_recycler);
        usersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadUsers();

        return view;
    }

    private void loadUsers() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    UserAdapter adapter = new UserAdapter(getContext(), response.body(), new UserAdapter.OnUserClickListener() {
                        @Override
                        public void onUpdateClick(User user) {
                            // Handle update click (e.g., show update dialog)
                        }

                        @Override
                        public void onDeleteClick(User user) {
                            // Handle delete click (e.g., call apiService.updateUser to deactivate)
                        }
                    });
                    usersRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {}
        });
    }
}