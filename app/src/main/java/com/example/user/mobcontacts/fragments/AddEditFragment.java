package com.example.user.mobcontacts.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.helpers.DBHelper;
import com.example.user.mobcontacts.models.ContactImage;
import com.example.user.mobcontacts.utilities.ValidationUtility;
import com.example.user.mobcontacts.models.Contact;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by User on 7/26/2017.
 */

public class AddEditFragment extends Fragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();

    private Button add_edit;
    private EditText nameEdit, phoneEdit, ageEdit;
    private Spinner genderSpinner;
    private ArrayAdapter spinnerAdapter;
    public final static int ADD_MODE = 0;
    public final static int EDIT_MODE = 1;
    public final static int REQUEST_CODE = 3;
    public final static int CAMERA_ACCESS = 7;
    public final static int CAMERA_RESULT = 8;
    public final static int GALLERY_RESULT = 9;
    private int DEFAULT_MODE = ADD_MODE;
    private int id;
    public final static String ID = "id", MODE = "mode";
    private String name, phone;
    private int age, gender;
    private ImageButton cameraBtn, urlBtn, galleryBtn;
    private List<ContactImage> imageList;
    private ImageView imageView;//test
    private String urlImagePath;
    private TextView path, disc, size;//test


    public static AddEditFragment newInstance(int id, int mode) {
        AddEditFragment addEditFragment = new AddEditFragment();
        Bundle args = new Bundle();
        args.putInt(ID, id);
        args.putInt(MODE, mode);
        addEditFragment.setArguments(args);
        return addEditFragment;
    }

    public static AddEditFragment newInstance(int mode) {
        AddEditFragment addEditFragment = new AddEditFragment();
        Bundle args = new Bundle();
        args.putInt(MODE, mode);
        addEditFragment.setArguments(args);
        return addEditFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_edit_fragment, container, false);

        init(view);
        editFill();
        setUpClicks();

        return view;

    }

    private void init(View view) {

        cameraBtn = (ImageButton) view.findViewById(R.id.camera_button);
        urlBtn = (ImageButton) view.findViewById(R.id.url_button);
        galleryBtn = (ImageButton) view.findViewById(R.id.gallery_button);

        add_edit = (Button) view.findViewById(R.id.add_edit_button);
        Bundle bundle = getArguments();

        if (bundle != null) {

            DEFAULT_MODE = bundle.getInt(MODE);
            id = bundle.getInt(ID);


            if (DEFAULT_MODE != 0) {

                cameraBtn.setVisibility(View.GONE);
                urlBtn.setVisibility(View.GONE);
                galleryBtn.setVisibility(View.GONE);
                add_edit.setText(R.string.Edit);
                DBHelper dbHelper = new DBHelper(getContext());
                Contact contact = dbHelper.getContact(id);
                name = contact.getName();
                phone = contact.getPhone();
                age = contact.getAge();
                gender = contact.getGender();

            }
        } else {

            Log.e(TAG, "Args are null");

        }

        nameEdit = (EditText) view.findViewById(R.id.name_edit);
        phoneEdit = (EditText) view.findViewById(R.id.phone_edit);
        ageEdit = (EditText) view.findViewById(R.id.age_edit);
        genderSpinner = (Spinner) view.findViewById(R.id.gender_spinner);
        spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gender, R.layout.spinner_item);
        genderSpinner.setAdapter(spinnerAdapter);
        imageList = new ArrayList<>();
        imageView = (ImageView) view.findViewById(R.id.image_test);//test
        path = (TextView) view.findViewById(R.id.path_text);//test
        disc = (TextView) view.findViewById(R.id.disc_text);//test
        size = (TextView) view.findViewById(R.id.size);//test
        size.setText(String.valueOf(imageList.size()));

    }

    private void editFill() {

        if (DEFAULT_MODE != 0) {

            nameEdit.setText(name);
            phoneEdit.setText(phone);
            ageEdit.setText(String.valueOf(age));
            genderSpinner.setSelection(gender);

        }
    }

    private void setUpClicks() {
        cameraBtn.setOnClickListener(this);
        urlBtn.setOnClickListener(this);
        galleryBtn.setOnClickListener(this);
        add_edit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.add_edit_button:
                if (!ageEdit.getText().toString().isEmpty()) {

                    if (ValidationUtility.checkValidation(nameEdit.getText().toString(),
                            phoneEdit.getText().toString(),
                            genderSpinner.getSelectedItemPosition())) {

                        DBHelper dbHelper = new DBHelper(getContext());


                        if (DEFAULT_MODE == ADD_MODE) {
                            if (imageList.size() != 0) {
                                dbHelper.addContact(new Contact(nameEdit.getText().toString(),
                                        phoneEdit.getText().toString(),
                                        Integer.parseInt(ageEdit.getText().toString()),
                                        genderSpinner.getSelectedItemPosition()));
                                int contact_id = dbHelper.getLastContactID();

                                for (ContactImage image :
                                        imageList) {
                                    image.setContact_id(contact_id);
                                    dbHelper.addImage(image);
                                }

                                FragmentManager fragmentManager = getFragmentManager();
                                ContactsFragment contactsFragment = ContactsFragment.newInstance();
                                fragmentManager.beginTransaction().replace(R.id.main_fragment, contactsFragment).commit();

                            } else {

                                Toast.makeText(getContext(), R.string.picture_requierment, Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            Contact contact = new Contact(nameEdit.getText().toString(),
                                    phoneEdit.getText().toString(),
                                    Integer.parseInt(ageEdit.getText().toString()),
                                    genderSpinner.getSelectedItemPosition());
                            contact.setId(id);
                            dbHelper.updateContact(contact);

                            FragmentManager fragmentManager = getFragmentManager();
                            ContactsFragment contactsFragment = ContactsFragment.newInstance();
                            fragmentManager.beginTransaction().replace(R.id.main_fragment, contactsFragment).commit();

                        }


                    } else {
                        Toast.makeText(getContext(), R.string.field_empty_toast, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.field_empty_toast, Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.camera_button:
                if (imageList.size() >= 5) {
                    Toast.makeText(getContext(), R.string.more_than_size, Toast.LENGTH_SHORT).show();
                } else {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_ACCESS);
                        } else {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_RESULT);
                        }
                    }
                }
                break;
            case R.id.url_button:
                if (imageList.size() >= 5) {
                    Toast.makeText(getContext(), R.string.more_than_size, Toast.LENGTH_SHORT).show();
                } else {
                    FragmentManager fragmentManager = getFragmentManager();
                    DownloadDialogFragment dialogFragment = DownloadDialogFragment.newInstance();
                    dialogFragment.setTargetFragment(this, REQUEST_CODE);
                    dialogFragment.show(fragmentManager, TAG);
                }
                break;
            case R.id.gallery_button:
                if (imageList.size() >= 5) {
                    Toast.makeText(getContext(), R.string.more_than_size, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY_RESULT);
                }
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case CAMERA_RESULT: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_RESULT);

                } else {
                    Toast.makeText(getContext(), R.string.camera_permission_deny, Toast.LENGTH_SHORT).show();
                }

                return;
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {

        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(getContext(), photo);
            String imagePath = getRealPathFromURI(tempUri);

            //Text
            imageView.setImageBitmap(photo);
            ContactImage contactImage = new ContactImage(imagePath);
            Log.d(TAG, "onActivityResult");
            imageList.add(contactImage);
            size.setText(String.valueOf(imageList.size()));
            Log.d(TAG, "list size " + imageList.size());
            FragmentManager fragmentManager = getFragmentManager();
            DiscDialogFragment dialogFragment = DiscDialogFragment.newInstance();
            dialogFragment.setTargetFragment(AddEditFragment.this, REQUEST_CODE);
            dialogFragment.show(fragmentManager, TAG);


            //Test
            path.setText(imagePath);


        } else if (requestCode == GALLERY_RESULT && resultCode == RESULT_OK) {

            Uri pickedImage = data.getData();
            String imagePath = getRealPathFromURI(pickedImage);
            // Test
            Bitmap photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), pickedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(photo);

            ContactImage contactImage = new ContactImage(imagePath);
            Log.d(TAG, "onActivityResult");
            imageList.add(contactImage);
            size.setText(String.valueOf(imageList.size()));
            Log.d(TAG, "list size " + imageList.size());
            FragmentManager fragmentManager = getFragmentManager();
            DiscDialogFragment dialogFragment = DiscDialogFragment.newInstance();
            dialogFragment.setTargetFragment(AddEditFragment.this, REQUEST_CODE);
            dialogFragment.show(fragmentManager, TAG);

            //Test
            path.setText(imagePath);

        }
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        String realPath=cursor.getString(idx);
        cursor.close();
        return realPath;
    }

    public void setURLtoPath(String path, String disc) {
        urlImagePath = path;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        imageView.setImageBitmap(bitmap);
        this.disc.setText(disc);
        ContactImage contactImage = new ContactImage(path, disc);
        Log.d(TAG, "onActivityResult");
        imageList.add(contactImage);
        size.setText(String.valueOf(imageList.size()));
        Log.d(TAG, "list size " + imageList.size());
        //text
        this.path.setText(urlImagePath);
    }

    public void setDiscription(String disc) {
        imageList.get((imageList.size() - 1)).setDiscription(disc);
        this.disc.setText(imageList.get((imageList.size() - 1)).getDiscription());
    }
}