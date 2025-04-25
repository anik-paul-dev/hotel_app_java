package com.example.hotel_booking_app.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.adapters.ViewPagerAdapter;
import com.example.hotel_booking_app.dialogs.LoginDialog;
import com.example.hotel_booking_app.dialogs.RegisterDialog;
import com.example.hotel_booking_app.fragments.publicFragments.AboutFragment;
import com.example.hotel_booking_app.fragments.publicFragments.ContactUsFragment;
import com.example.hotel_booking_app.fragments.publicFragments.FacilitiesFragment;
import com.example.hotel_booking_app.fragments.publicFragments.HomeFragment;
import com.example.hotel_booking_app.fragments.publicFragments.RoomsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new RoomsFragment(), "Rooms");
        adapter.addFragment(new FacilitiesFragment(), "Facilities");
        adapter.addFragment(new ContactUsFragment(), "Contact Us");
        adapter.addFragment(new AboutFragment(), "About");
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(adapter.getPageTitle(position))).attach();

        findViewById(R.id.btn_login).setOnClickListener(v -> new LoginDialog().show(getSupportFragmentManager(), "login"));
        findViewById(R.id.btn_register).setOnClickListener(v -> new RegisterDialog().show(getSupportFragmentManager(), "register"));
    }
}