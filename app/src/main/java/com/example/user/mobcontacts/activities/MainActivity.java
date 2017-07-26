package com.example.user.mobcontacts.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.user.mobcontacts.R;
import com.example.user.mobcontacts.fragments.AddFragment;
import com.example.user.mobcontacts.fragments.ContactsFragment;

public class MainActivity extends AppCompatActivity {

    private int fragmetnIndex=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment=new ContactsFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_fragment,fragment).commit();
        fragmetnIndex=1;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.add,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                Fragment fragment=new AddFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_fragment,fragment).commit();
                fragmetnIndex=2;
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if(fragmetnIndex==2){
            Fragment fragment=new ContactsFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_fragment,fragment).commit();
            fragmetnIndex=1;
        }else{
            super.onBackPressed();
        }

    }
}
