package com.lib.dao;

import com.lib.bean.Price;
import java.util.List;

public interface PriceDao {

    boolean updatePrice(Price price);

    double getPrice(int priceId);

    List<Price> getPrices();
}
