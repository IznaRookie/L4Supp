package com.example.l4supp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class FavouritesFragment extends Fragment {

    private SellerListAdapter adapter;
    private AppDatabase database;
    private TextView tvEmptyList;

    public FavouritesFragment() {
        super(R.layout.fragment_favourites);
    }

    public static FavouritesFragment newInstance() {
        return new FavouritesFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = RoomWrapper.getDatabase(requireActivity());

        tvEmptyList = view.findViewById(R.id.tvEmptyList);

        RecyclerView rvFavouriteList = view.findViewById(R.id.rvFavouriteList);
        adapter = new SellerListAdapter(requireActivity());
        rvFavouriteList.setAdapter(adapter);
        adapter.setFavouriteClickListener(new SellerListAdapter.ItemSellerClickListener() {
            @Override
            public void onItemClick(Seller seller) {
                UserEntity userEntity = database.userDao().getCurrentUser();
                if (userEntity != null) {
                    FavouriteSellerEntity sellerEntity = database.favouriteSellerDao().getBySellerAndUserId(seller.getId(), userEntity.getId());
                    if (sellerEntity != null) {
                        database.favouriteSellerDao().deleteById(sellerEntity.getId());
                        setSellers();
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

        List<Seller> favouriteSellers = new ArrayList<>();

        UserEntity userEntity = database.userDao().getCurrentUser();
        if (userEntity != null) {
            List<FavouriteSellerEntity> favouriteSellerEntities = database.favouriteSellerDao().getAllByUserId(userEntity.getId());
            for (Seller seller: sellers) {
                for (FavouriteSellerEntity favouriteSellerEntity: favouriteSellerEntities) {
                    if (seller.getId() == favouriteSellerEntity.getSellerId()) {
                        seller.setFavourite(true);
                        favouriteSellers.add(seller);
                        break;
                    }
                }
            }
        }

        adapter.setSellers(favouriteSellers);

        if(favouriteSellers.isEmpty()) {
            tvEmptyList.setVisibility(View.VISIBLE);
        } else {
            tvEmptyList.setVisibility(View.GONE);
        }
    }
}