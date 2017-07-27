package com.example.user.mobcontacts.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.fragments.AddFragment;
import com.example.user.mobcontacts.fragments.ContactsFragment;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContactsFragment fragment =ContactsFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, fragment).commit();

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
                Fragment fragment = AddFragment.newInstance(AddFragment.ADD_MODE);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(TAG).commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
