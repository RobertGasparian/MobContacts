package com.example.user.mobcontacts.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.helpers.DBHelper;
import com.example.user.mobcontacts.models.ContactImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 7/31/2017.
 */

public class PagerItemFragment extends Fragment {

    private TextView pagerDiscription;
    private ImageView pagerImage;
    private ImageButton edit_pager, delete_pager;
    private final static String ID = "id";
    private final static String CONTACT_ID = "contact_id";
    private final static String PATH = "path";
    private final static String DISCRIPTION = "discription";
    private int contact_id;
    private int id;
    private String path;
    private String discription;


    public static PagerItemFragment newInstance(ContactImage image) {

        Bundle args = new Bundle();
        args.putInt(ID, image.getId());
        args.putInt(CONTACT_ID, image.getContact_id());
        args.putString(PATH, image.getPath());
        args.putString(DISCRIPTION, image.getDiscription());
        PagerItemFragment fragment = new PagerItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_item, container, false);
        if (getArguments() != null) {
            id = getArguments().getInt(ID);
            contact_id = getArguments().getInt(CONTACT_ID);
            path = getArguments().getString(PATH);
            discription = getArguments().getString(DISCRIPTION);
        }
        pagerDiscription = (TextView) view.findViewById(R.id.pager_discription);
        pagerImage = (ImageView) view.findViewById(R.id.pager_image);
        edit_pager = (ImageButton) view.findViewById(R.id.pager_edit);
        delete_pager = (ImageButton) view.findViewById(R.id.pager_delete);
        pagerDiscription.setText(discription);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        pagerImage.setImageBitmap(bitmap);
        //TODO: picasso


        return view;

    }
}
