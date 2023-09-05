package com.itss.demo;

import java.util.ArrayList;
import java.util.List;
import com.ibm.icu.math.BigDecimal;
import com.temenos.t24.api.complex.eb.enquiryhook.EnquiryContext;
import com.temenos.t24.api.complex.eb.enquiryhook.FilterCriteria;
import com.temenos.t24.api.hook.system.Enquiry;
import com.temenos.t24.api.party.Customer;
import com.temenos.t24.api.records.account.AccountRecord;
import com.temenos.t24.api.system.DataAccess;

/**
 * TODO: Document me!
 *
 * @author klaus
 *
 */
public class demo4 extends Enquiry {
    
    List<String> ReturnData = new ArrayList<>();
    int selCount = 0;
    String cusId = "";
    DataAccess dataAccess = new DataAccess(this);
    List<String> accList = new ArrayList<>();
    int postiveBalanceCount = 0,negativeBalanceCount = 0;
    BigDecimal conCatpostiveBalnce = BigDecimal.ZERO, conCatnegativeBalnce = BigDecimal.ZERO;
    int balance = 0;  
    FilterCriteria filerCrt = new FilterCriteria();

    @Override
    public List<String> setIds(List<FilterCriteria> filterCriteria, EnquiryContext enquiryContext) {
        // TODO Auto-generated method stub
        selCount = filterCriteria.size();
        for(int i=0;i<selCount;i++){
            if((filterCriteria.get(i).getFieldname()).equals("CUSTOMER.NO")){
                cusId = filterCriteria.get(i).getValue();
                break;
            }
        }
        Customer cusRec = new Customer(this);
        cusRec.setCustomerId(cusId);
        accList = cusRec.getAccountNumbers();
        for(String accId : accList){
            String balan = "";
            BigDecimal workingBalnce = BigDecimal.ZERO;
            AccountRecord accRec = new AccountRecord(dataAccess.getRecord("ACCOUNT", accId));
            balan = accRec.getWorkingBalance().getValue();
            if(!balan.equals("")){
                workingBalnce = new BigDecimal(balan);
                balance = workingBalnce.compareTo(BigDecimal.ZERO);
                if(balance>0){
                    conCatpostiveBalnce = conCatpostiveBalnce.add(workingBalnce);
                    postiveBalanceCount = postiveBalanceCount+1;
                }else{
                    conCatnegativeBalnce = conCatnegativeBalnce.add(workingBalnce);
                    negativeBalanceCount = negativeBalanceCount +1;
                }
                
            }
            
        }
        conCatpostiveBalnce = conCatpostiveBalnce.setScale(2);
        conCatnegativeBalnce = conCatnegativeBalnce.setScale(2);
        ReturnData.add(cusId+"*"+postiveBalanceCount+"*"+conCatpostiveBalnce+"*"+negativeBalanceCount+"*"+conCatnegativeBalnce);
        return ReturnData;
    }

    @Override
    public List<FilterCriteria> setFilterCriteria(List<FilterCriteria> filterCriteria, EnquiryContext enquiryContext) {
        // TODO Auto-generated method stub
        
        filerCrt.setFieldname("CURRENCY");
        filerCrt.setOperand("EQ");
        filerCrt.setValue("USD");
        
        filterCriteria.add(filerCrt);
        
        return filterCriteria;
    }
}
