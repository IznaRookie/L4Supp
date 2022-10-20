package com.example.l4supp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.l4supp.model.Seller;

public class SellerProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);
        if (savedInstanceState == null) {
            int id = getIntent().getIntExtra("ID_TAG", -1);
            Seller seller = getIntent().getParcelableExtra("SELLER_TAG");
            if (seller != null) {
                loadFragment(SellerProfileFragment.newInstance(seller));
            } else if (id >= 0) {
                loadFragment(SellerProfileFragment.newInstance(id, false));
            } else {
                Toast.makeText(this, "Неверный идентификатор продавца", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_view, fragment);
        ft.commit();
    }
}
