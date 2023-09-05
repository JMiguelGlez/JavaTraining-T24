package com.itss.demo;

import com.temenos.api.TField;
import com.temenos.api.TStructure;
import com.temenos.api.TValidationResponse;
import com.temenos.t24.api.complex.eb.templatehook.TransactionContext;
import com.temenos.t24.api.hook.system.RecordLifecycle;
import com.temenos.t24.api.records.fundstransfer.FundsTransferRecord;


/**
 * TODO: Document me!
 *
 * @author klaus
 *
 */
public class demoRtn extends RecordLifecycle {
    @Override
    public TValidationResponse validateRecord(String application, String currentRecordId, TStructure currentRecord,
            TStructure unauthorisedRecord, TStructure liveRecord, TransactionContext transactionContext) {
        // TODO Auto-generated method stub
        FundsTransferRecord ftRec = new FundsTransferRecord(currentRecord);
        
        TField debitCcy = ftRec.getDebitCurrency();
        TField creditCcy = ftRec.getCreditCurrency();
        
        if(!debitCcy.getValue().equals(creditCcy.getValue())){
            creditCcy.setError("Debit Currency and Credit Currency Diff");
        }
        return ftRec.getValidationResponse();
       
    }

}
