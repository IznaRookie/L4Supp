package com.example.l4supp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.l4supp.database.AppDatabase;
import com.example.l4supp.database.RoomWrapper;
import com.example.l4supp.database.SellerEntity;
import com.example.l4supp.database.UserEntity;

public class SellerEditorFragment extends Fragment {

    private static final String ID_ARG = "ID_ARG";
    private EditText etName, etDescription;
    private Button btnSave, btnExit;
    private AppDatabase database;

    public SellerEditorFragment() {
        super(R.layout.fragment_seller_editor);
    }

    public static SellerEditorFragment newInstance(int id) {
        SellerEditorFragment fragment = new SellerEditorFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ID_ARG, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = RoomWrapper.getDatabase(requireActivity());

        etName = view.findViewById(R.id.etName);
        etDescription = view.findViewById(R.id.etDescription);
        btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEntity userEntity = database.userDao().getCurrentUser();
                SellerEntity sellerEntity = new SellerEntity(
                        userEntity.getId(),
                        etName.getText().toString(),
                        etDescription.getText().toString()
                );
                SellerEntity existingSeller = database.sellerDao().getByUserId(userEntity.getId());
                if (existingSeller != null) {
                    sellerEntity.setId(existingSeller.getId());
                    database.sellerDao().update(sellerEntity);
                } else {
                    database.sellerDao().insert(sellerEntity);
                }
                requireActivity().finish();
            }
        });

        UserEntity userEntity = database.userDao().getCurrentUser();

        SellerEntity existingSeller = database.sellerDao().getByUserId(userEntity.getId());
        if (existingSeller != null) {
            etName.setText(existingSeller.getName());
            etDescription.setText(existingSeller.getProduct());
        }
    }
}