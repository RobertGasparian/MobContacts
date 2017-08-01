package com.example.user.mobcontacts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.adapters.ItemPagerAdapter;
import com.example.user.mobcontacts.helpers.DBHelper;
import com.example.user.mobcontacts.models.ContactImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 7/31/2017.
 */

public class DetailedFragment extends Fragment  {


    private List<ContactImage> itemList;
    private final static String CONTACT_ID = "contact_id";
    private int contact_id;
    private ItemPagerAdapter adapter;
    private ViewPager viewPager;
    private TextView name_detailded, phone_detailed;


    public static DetailedFragment newInstance(int contact_id) {

        Bundle args = new Bundle();

        args.putInt(CONTACT_ID, contact_id);
        DetailedFragment fragment = new DetailedFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailed_fragment, container, false);
        contact_id = getArguments().getInt(CONTACT_ID);
        itemList = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(getContext());
        itemList = dbHelper.getAllContactImages(contact_id);
        List<PagerItemFragment> pagerFragments = new ArrayList<>();

        for (ContactImage image : itemList
                ) {

            pagerFragments.add(PagerItemFragment.newInstance(image.getId()));

        }

        adapter=new ItemPagerAdapter(this.getFragmentManager(),pagerFragments);
        viewPager=(ViewPager)view.findViewById(R.id.image_pager);
        name_detailded=(TextView)view.findViewById(R.id.detailed_name);
        phone_detailed=(TextView)view.findViewById(R.id.detailed_phone);
        viewPager.setAdapter(adapter);
        name_detailded.setText(dbHelper.getContact(contact_id).getName());
        phone_detailed.setText(dbHelper.getContact(contact_id).getPhone());

        return view;
    }



    public void updateFragment() {
        DBHelper dbHelper = new DBHelper(getContext());
        itemList = dbHelper.getAllContactImages(contact_id);
        List<PagerItemFragment> pagerFragments = new ArrayList<>();

        for (ContactImage image : itemList
                ) {

            pagerFragments.add(PagerItemFragment.newInstance(image.getId()));

        }

        adapter=new ItemPagerAdapter(this.getFragmentManager(),pagerFragments);
        viewPager.setAdapter(adapter);
    }
}
