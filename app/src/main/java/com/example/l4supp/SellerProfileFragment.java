package com.example.l4supp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.l4supp.database.AppDatabase;
import com.example.l4supp.database.RoomWrapper;
import com.example.l4supp.database.SellerEntity;
import com.example.l4supp.database.UserEntity;
import com.example.l4supp.model.Seller;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SellerProfileFragment extends Fragment {

    private static final String ID_ARG = "ID_ARG";
    private static final String IS_SELLER_ARG = "IS_SELLER_ARG";
    private static final String SELLER_ARG = "SELLER_ARG";
    private TextView tvTitle, tvDescription;
    private FloatingActionButton fabEdit;
    private Button btnExit;
    private AppDatabase database;

    public SellerProfileFragment() {
        super(R.layout.fragment_seller_profile);
    }

    public static SellerProfileFragment newInstance(int id, boolean isSeller) {
        SellerProfileFragment fragment = new SellerProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ID_ARG, id);
        bundle.putBoolean(IS_SELLER_ARG, isSeller);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static SellerProfileFragment newInstance(Seller seller) {
        SellerProfileFragment fragment = new SellerProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SELLER_ARG, seller);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = RoomWrapper.getDatabase(requireActivity());
        tvTitle = view.findViewById(R.id.tvTitle);
        tvDescription = view.findViewById(R.id.tvDescription);
        fabEdit = view.findViewById(R.id.fabEdit);
        btnExit = view.findViewById(R.id.btnExit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEntity userEntity = database.userDao().getCurrentUser();
                userEntity.setLoggedIn(false);
                database.userDao().update(userEntity);
                startActivity(new Intent(requireActivity(),LoginActivity.class));
                requireActivity().finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Seller seller = getSeller();
        int id = getSellerId();
        if (seller != null) {
            tvTitle.setText(seller.getName());
            tvDescription.setText(seller.getDescription());
            fabEdit.setVisibility(View.GONE);
            btnExit.setVisibility(View.GONE);
        } else if (id >= 0) {
            SellerEntity sellerEntity = database.sellerDao().getById(id);
            if (sellerEntity != null) {
                if (sellerEntity.getName() == null || sellerEntity.getName().isEmpty()) {
                    tvTitle.setText(R.string.no_name);
                } else {
                    tvTitle.setText(sellerEntity.getName());
                }
                if (sellerEntity.getProduct() == null || sellerEntity.getProduct().isEmpty()) {
                    tvDescription.setText(R.string.no_description);
                } else {
                    tvDescription.setText(sellerEntity.getProduct());
                }
                if (getIsSeller()) {
                    fabEdit.setVisibility(View.VISIBLE);
                    btnExit.setVisibility(View.VISIBLE);
                    fabEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(requireActivity(), SellerEditorActivity.class);
                            intent.putExtra("ID_TAG", id);
                            startActivity(intent);
                        }
                    });
                } else {
                    fabEdit.setVisibility(View.GONE);
                    btnExit.setVisibility(View.GONE);
                }
            } else {
                Toast.makeText(requireActivity(), "Wrong id", Toast.LENGTH_LONG).show();
                requireActivity().finish();
            }
        }
    }

    private int getSellerId() {
        if (getArguments() != null) {
            return getArguments().getInt(ID_ARG, -1);
        }
        return -1;
    }

    private boolean getIsSeller() {
        if (getArguments() != null) {
            return getArguments().getBoolean(IS_SELLER_ARG, false);
        }
        return false;
    }
    private Seller getSeller() {
        if (getArguments() != null) {
            return getArguments().getParcelable(SELLER_ARG);
        }
        return null;
    }
}
