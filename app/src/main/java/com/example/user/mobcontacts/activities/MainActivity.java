package com.example.user.mobcontacts.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.fragments.AddEditFragment;
import com.example.user.mobcontacts.fragments.ContactsFragment;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final int STORAGE_ACCESS = 1;
    private boolean PERMISSION_GRANTED = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_ACCESS);
            } else {
                if (savedInstanceState == null) {
                    ContactsFragment fragment = ContactsFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).commit();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PERMISSION_GRANTED) {


            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() == 0) {
                ContactsFragment fragment = ContactsFragment.newInstance();
                fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).commit();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Fragment fragment = AddEditFragment.newInstance(AddEditFragment.ADD_MODE);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(TAG).commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case STORAGE_ACCESS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    PERMISSION_GRANTED = true;

                } else {
                    Toast.makeText(this, getString(R.string.read_permission), Toast.LENGTH_LONG).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 4500);
                }

        }

    }
}
