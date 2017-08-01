package com.example.user.mobcontacts.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.mobcontacts.fragments.PagerItemFragment;
import com.example.user.mobcontacts.models.ContactImage;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 7/31/2017.
 */

public class ItemPagerAdapter extends FragmentStatePagerAdapter {


    //TODO: adapter
    private List<PagerItemFragment> itemList;

    public ItemPagerAdapter(FragmentManager fm, List<PagerItemFragment> itemList) {

        super(fm);
        this.itemList = new ArrayList<>();
        if (itemList.size() != 0 ) {
            this.itemList = itemList;
        }
    }

    @Override
    public Fragment getItem(int position) {

        return itemList.get(position);
    }

    @Override
    public int getCount() {

        return itemList.size();
    }
}
