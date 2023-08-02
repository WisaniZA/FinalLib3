package com.lib.service;

import com.lib.bean.Price;
import java.util.List;

public interface PriceService {

    boolean updatePrice(Price price);

    double getPrice(int priceId);

    List<Price> getPrices();

}
