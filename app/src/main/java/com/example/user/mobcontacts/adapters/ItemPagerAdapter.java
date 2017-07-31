package com.example.user.mobcontacts.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.models.ContactImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 7/31/2017.
 */

public class ItemPagerAdapter extends FragmentPagerAdapter {


    //TODO: adapter

    public ItemPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
