package com.example.l4supp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.l4supp.database.AppDatabase;
import com.example.l4supp.database.RoomWrapper;
import com.example.l4supp.database.SellerEntity;
import com.example.l4supp.database.UserEntity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SellerMainActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);
        database = RoomWrapper.getDatabase(this);
        if (savedInstanceState == null) {
            showProfile();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void showProfile() {
        UserEntity userEntity = database.userDao().getCurrentUser();
        if (userEntity != null && userEntity.isSeller()) {
            SellerEntity sellerEntity = database.sellerDao().getByUserId(userEntity.getId());
            if (sellerEntity != null) {
                loadFragment(SellerProfileFragment.newInstance(sellerEntity.getId(), true));
            } else {
                loadFragment(SellerProfileFragment.newInstance(-1, true));
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_profile:
                    showProfile();
                    return true;
                case R.id.action_info:
                    loadFragment(InfoFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_view, fragment);
        ft.commit();
    }
}
