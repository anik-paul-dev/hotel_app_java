package com.example.hotel_booking_app.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();

    public ViewPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    public void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public String getPageTitle(int position) {
        return titles.get(position);
    }
}