package com.example.hotel_booking_app.fragments.adminFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.adapters.QueryAdapter;
import com.example.hotel_booking_app.models.Query;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class UserQueriesFragment extends Fragment {

    private RecyclerView queriesRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_queries, container, false);

        queriesRecycler = view.findViewById(R.id.queries_recycler);
        queriesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadQueries();

        return view;
    }

    private void loadQueries() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getQueries().enqueue(new Callback<List<Query>>() {
            @Override
            public void onResponse(Call<List<Query>> call, Response<List<Query>> response) {
                if (response.isSuccessful()) {
                    QueryAdapter adapter = new QueryAdapter(getContext(), response.body(), new QueryAdapter.OnQueryClickListener() {
                        @Override
                        public void onMarkReadClick(Query query) {
                            // Handle mark read click (e.g., call apiService.markQueryRead)
                        }

                        @Override
                        public void onDeleteClick(Query query) {
                            // Handle delete click (e.g., call apiService.deleteQuery)
                        }
                    });
                    queriesRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Query>> call, Throwable t) {}
        });
    }
}