
package com.lib.service;

import com.lib.bean.Fine;
import com.lib.process.ProcessRequest;

public interface FineService extends ProcessRequest {
     boolean AddFine(Fine fine);
    boolean updateFine(int fineId);
    String getFine(int fineId);
}
