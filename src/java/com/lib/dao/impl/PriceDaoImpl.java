package com.lib.dao.impl;

import com.lib.bean.Category;
import com.lib.bean.Price;
import com.lib.dao.PriceDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

/**
 *
 * @author Mosa
 */
public class PriceDaoImpl implements PriceDao {

    private BasicDataSource bds = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    boolean bool;

    public PriceDaoImpl(BasicDataSource bds) {
        this.bds = bds;
    }

    @Override
    public boolean updatePrice(Price price) {

        bool = false;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("UPDATE price SET amount=? WHERE price_id=?");
                ps.setDouble(1, price.getAmount());
                ps.setInt(2, price.getPriceId());

                if ((ps.executeUpdate() > 0)) {
                    bool = true;
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
            }
        }
        return bool;
    }

    @Override
    public List<Price> getPrices() {

        List<Price> prices = new ArrayList<>();
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("SELECT price_id, amount, price_desc FROM price");
                rs = ps.executeQuery();
                while (rs.next()) {
                    prices.add(new Price(rs.getInt("price_id"), rs.getString("price_desc"), rs.getDouble("amount")));
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
            }
        }
        return prices;
    }

    @Override
    public double getPrice(int priceId) {

        double amount = 0;
        if (bds != null) {
            try {
                con = bds.getConnection();
                ps = con.prepareStatement("SELECT price_id, amount, price_desc FROM price WHERE price_id=?");
                ps.setInt(1, priceId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    amount = rs.getDouble("amount");
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            } finally {
                closePsCon(ps, con);
            }
        }
        return amount;
    }

    public void closePsCon(PreparedStatement preStm, Connection con) {
        if (preStm != null) {
            try {
                preStm.close();
            } catch (SQLException ex) {
                System.out.println("Could not close: " + ex.getMessage());
            }
            try {
                con.close();
            } catch (SQLException ex) {

            }
        }
    }
}
