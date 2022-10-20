package com.example.l4supp.model;

import java.util.ArrayList;
import java.util.List;

public class SellersFactory {

    public static List<Seller> getInitialSellers() {
        List<Seller> sellers = new ArrayList<>();
        sellers.add(new Seller(1000, "Чайно-кофейная фабрика", "Собственное производство. Фасуем чай, делаем чайные купажи.", false));
        sellers.add(new Seller(1001, "Греческие продукты питания", "Официальным представителем крупнейших заводов производителей Греции, поставляя греческие продукты питания в Россию", false));
        sellers.add(new Seller(1002, "Овощи и фрукты оптом", "Мы отправляем оптом овощи и фрукты с Грузии в Россию!", false));
        return sellers;
    }
}
