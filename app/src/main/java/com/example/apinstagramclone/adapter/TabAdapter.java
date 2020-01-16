package com.example.apinstagramclone.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.apinstagramclone.fragment.ProfileTabFragment;
import com.example.apinstagramclone.fragment.SharePictureTabFragment;
import com.example.apinstagramclone.fragment.UserTabFragment;

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int tabPosition) {
        switch (tabPosition) {
            case 0:
                return new ProfileTabFragment();
            case 1:
                return new UserTabFragment();
            case 2:
                return new SharePictureTabFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Profile";
            case 1:
                return "User";
            case 2:
                return "Share Picture";
            default:
                return null;
        }
    }
}
