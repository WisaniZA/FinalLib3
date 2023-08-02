
package com.lib.process;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface ProcessRequest{
 void processTheRequest(HttpServletRequest request, HttpServletResponse reponse);
}
