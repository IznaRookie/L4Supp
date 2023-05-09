package com.example.l4supp.model;

import java.util.ArrayList;
import java.util.List;

public class SellersFactory {

    public static List<Seller> getInitialSellers() {
        List<Seller> sellers = new ArrayList<>();
        sellers.add(new Seller(1000, "Magazin tovarov", "Kanzelyarskie tovari optom", false));
        sellers.add(new Seller(1001, "Magazin tovarov", "Odejda optom", false));
        sellers.add(new Seller(1002, "Magazin tovarov 2", "Kanzelyarskie tovari optom", false));
        return sellers;
    }
}
