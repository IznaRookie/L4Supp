package com.example.l4supp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.l4supp.model.Seller;

import java.util.ArrayList;
import java.util.List;

public class SellerListAdapter extends RecyclerView.Adapter<SellerListAdapter.SellerViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<Seller> sellers = new ArrayList<>();
    private ItemSellerClickListener clickListener;
    private ItemSellerClickListener favouriteClickListener;

    public SellerListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public ItemSellerClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(ItemSellerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setFavouriteClickListener(ItemSellerClickListener favouriteClickListener) {
        this.favouriteClickListener = favouriteClickListener;
    }

    public void setSellers(List<Seller> newSellers) {
        sellers.clear();
        sellers.addAll(newSellers);
        sellers.trimToSize();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_seller,parent,false);
        return new SellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerViewHolder holder, int position) {
        holder.bind(sellers.get(position));
    }

    @Override
    public int getItemCount() {
        return sellers.size();
    }

    class SellerViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvDescription;
        private ImageView ivFavourite;

        SellerViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvDescription = view.findViewById(R.id.tvDescription);
            ivFavourite = view.findViewById(R.id.ivFavourite);
        }

        public void bind(Seller seller) {
            tvName.setText(seller.getName());
            tvDescription.setText(seller.getDescription());

            setIconColor(seller.isFavourite());

            ivFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(favouriteClickListener != null) {
                        seller.setFavourite(!seller.isFavourite());
                        setIconColor(seller.isFavourite());
                        favouriteClickListener.onItemClick(seller);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onItemClick(seller);
                    }
                }
            });
        }

        private void setIconColor(boolean isFavourite){
            if (isFavourite) {
                ivFavourite.setImageResource(R.drawable.ic_baseline_favorite_item_pressed);
            } else {
                ivFavourite.setImageResource(R.drawable.ic_baseline_favorite_item_24);
            }
        }
    }

    public interface ItemSellerClickListener {
        void onItemClick(Seller seller);
    }
}
