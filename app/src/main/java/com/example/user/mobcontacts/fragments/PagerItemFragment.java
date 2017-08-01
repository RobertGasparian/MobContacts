package com.example.user.mobcontacts.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.helpers.DBHelper;
import com.example.user.mobcontacts.models.ContactImage;
import com.squareup.picasso.Picasso;

/**
 * Created by User on 7/31/2017.
 */

public class PagerItemFragment extends Fragment{


    private final String TAG = getClass().getSimpleName();

    private TextView pagerDiscription;
    private ImageView pagerImage;
    private ImageButton edit_pager, delete_pager;
    private final static int REQUEST_CODE=55;
    private final static String ID = "id";
    private final static String CONTACT_ID = "contact_id";
    private final static String PATH = "path";
    private final static String DISCRIPTION = "discription";
    private int id;



    public static PagerItemFragment newInstance(int image_id) {

        Bundle args = new Bundle();
        args.putInt(ID, image_id);
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
        }

        DBHelper dbHelper=new DBHelper(getContext());
        ContactImage image=dbHelper.getImage(id);

        pagerDiscription = (TextView) view.findViewById(R.id.pager_discription);
        pagerImage = (ImageView) view.findViewById(R.id.pager_image);
        edit_pager = (ImageButton) view.findViewById(R.id.pager_edit);
        delete_pager = (ImageButton) view.findViewById(R.id.pager_delete);
        pagerDiscription.setText(image.getDiscription());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        Bitmap bitmap = BitmapFactory.decodeFile(image.getPath(), options);
        pagerImage.setImageBitmap(bitmap);
//        Picasso.with(getContext())
//                .load(path)
//                .into(pagerImage);

        edit_pager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                EditImageDialogFragment dialogFragment = EditImageDialogFragment.newInstance(id);
                dialogFragment.setTargetFragment(PagerItemFragment.this,REQUEST_CODE);
                dialogFragment.show(fragmentManager, TAG);
            }
        });

        delete_pager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(getContext());
                dbHelper.deleteImage(id);
                updateDetailedFragment();
            }
        });

        return view;

    }

    public void updateDetailedFragment(){

        ((DetailedFragment)getFragmentManager().findFragmentByTag("ContactsFragment")).updateFragment();
    }



}
