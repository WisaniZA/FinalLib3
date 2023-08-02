package com.lib.factory;

import com.lib.process.ProcessRequest;
import com.lib.process.impl.AuthorProcess;
import com.lib.process.impl.BookProcess;
import com.lib.process.impl.CategoryProcess;
import com.lib.process.impl.LoanProcess;
import com.lib.process.impl.MemberProcess;
import com.lib.process.impl.PriceProcess;
import com.lib.process.impl.RoleProcess;

public class RequestActionFactory{
  public static ProcessRequest createProcess(String process){
    switch(process.toLowerCase()){
      case "book": return new BookProcess();
      case "author": return new AuthorProcess();
      case "member": return new MemberProcess();
      case "category": return new CategoryProcess();
      case "role": return new RoleProcess();
      case "loan": return new LoanProcess();
      case "price": return new PriceProcess();
          
      default:return null;
    }
  }
}