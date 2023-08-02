package com.lib.service.impl;

import com.lib.bean.Price;
import com.lib.dao.PriceDao;
import com.lib.dao.impl.PriceDaoImpl;
import com.lib.service.PriceService;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class PriceServiceImpl implements PriceService {

    private PriceDao priceDao;

     public PriceServiceImpl(BasicDataSource bds){
    priceDao = new PriceDaoImpl(bds);
  }
    
    @Override
    public boolean updatePrice(Price price) {
        return priceDao.updatePrice(price);
    }

    @Override
    public double getPrice(int priceId) {
        return priceDao.getPrice(priceId);
    }

    @Override
    public List<Price> getPrices() {
        return priceDao.getPrices();
    }
}
