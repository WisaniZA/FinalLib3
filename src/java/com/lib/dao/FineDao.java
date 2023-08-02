
package com.lib.dao;

import com.lib.bean.Fine;

public interface FineDao {
     boolean removeFine(int fineId);
    boolean updateFine(Fine fine);
    boolean addFine(Fine fine);
    String getFine(int fineId);

    public boolean updateFine(int fineId);
}
