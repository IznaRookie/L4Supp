package com.example.l4supp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.l4supp.database.AppDatabase;
import com.example.l4supp.database.FavouriteSellerEntity;
import com.example.l4supp.database.RoomWrapper;
import com.example.l4supp.database.SellerEntity;
import com.example.l4supp.database.UserEntity;
import com.example.l4supp.model.Seller;
import com.example.l4supp.model.SellersFactory;

import java.util.ArrayList;
import java.util.List;

public class SellerListFragment extends Fragment {

    private SellerListAdapter adapter;
    private AppDatabase database;
    private ImageButton btnExit;
    private ImageButton btnSearch;
    private EditText etSearch;
    private List<Seller> allSellers = new ArrayList<>();

    public SellerListFragment() {
        super(R.layout.fragment_seller_list);
    }

    public static SellerListFragment newInstance() {
        return new SellerListFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = RoomWrapper.getDatabase(requireActivity());

        RecyclerView rvSellerList = view.findViewById(R.id.rvSellerList);
        adapter = new SellerListAdapter(requireActivity());
        rvSellerList.setAdapter(adapter);
        adapter.setFavouriteClickListener(new SellerListAdapter.ItemSellerClickListener() {
            @Override
            public void onItemClick(Seller seller) {
                UserEntity userEntity = database.userDao().getCurrentUser();
                if (userEntity != null) {
                    FavouriteSellerEntity sellerEntity = database.favouriteSellerDao().getBySellerAndUserId(seller.getId(), userEntity.getId());
                    if (sellerEntity != null) {
                        database.favouriteSellerDao().deleteById(sellerEntity.getId());
                    } else {
                        FavouriteSellerEntity newSellerEntity = new FavouriteSellerEntity(
                                userEntity.getId(),
                                seller.getId()
                        );
                        database.favouriteSellerDao().insert(newSellerEntity);
                    }
                }
            }
        });
        adapter.setClickListener(new SellerListAdapter.ItemSellerClickListener() {
            @Override
            public void onItemClick(Seller seller) {
                Intent intent = new Intent(requireActivity(), SellerProfileActivity.class);
                if (seller.getId() >= 1000) {
                    intent.putExtra("SELLER_TAG", seller);
                } else {
                    intent.putExtra("ID_TAG", seller.getId());
                }
                startActivity(intent);
            }
        });

        etSearch = view.findViewById(R.id.etSearch);

        btnSearch = view.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(etSearch.getText().toString());

            }
        });

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
    public void onStart() {
        super.onStart();
        setSellers();
    }

    private void setSellers() {
        List<Seller> sellers = new ArrayList<>();
        List<SellerEntity> sellerEntities = database.sellerDao().getAll();
        for (SellerEntity sellerEntity: sellerEntities) {
            Seller seller = new Seller(
                    sellerEntity.getId(),
                    sellerEntity.getName(),
                    sellerEntity.getProduct(),
                    false
            );
            sellers.add(seller);
        }
        sellers.addAll(SellersFactory.getInitialSellers());

        UserEntity userEntity = database.userDao().getCurrentUser();
        if (userEntity != null) {
            List<FavouriteSellerEntity> favouriteSellerEntities = database.favouriteSellerDao().getAllByUserId(userEntity.getId());
            for (Seller seller: sellers) {
                for (FavouriteSellerEntity favouriteSellerEntity: favouriteSellerEntities) {
                    if (seller.getId() == favouriteSellerEntity.getSellerId()) {
                        seller.setFavourite(true);
                        break;
                    }
                }
            }
        }
        allSellers = sellers;

        adapter.setSellers(sellers);
    }

    private void search(String query) {
        if(query.isEmpty()) {
            adapter.setSellers(allSellers);
        } else {
            List<Seller> sellers = new ArrayList<>();
            for (Seller seller: allSellers) {
                if(seller.getName().toLowerCase().contains(query.toLowerCase())) {
                    sellers.add(seller);
                }
            }
            adapter.setSellers(sellers);
        }
    }
}