package com.itss.demo;

import com.temenos.api.TField;
import com.temenos.api.TStructure;
import com.temenos.api.TValidationResponse;
import com.temenos.t24.api.complex.eb.templatehook.TransactionContext;
import com.temenos.t24.api.hook.system.RecordLifecycle;
import com.temenos.t24.api.tables.ebdemo2.EbDemo2Record;

/**
 * TODO: Document me!
 *
 * @author klaus
 *
 */
public class demoLocalAppRtn extends RecordLifecycle {

    @Override
    public String checkId(String currentRecordId, TransactionContext transactionContext) {
        // TODO Auto-generated method stub
        currentRecordId = "99"+currentRecordId;
        return currentRecordId;
    }

    @Override
    public TValidationResponse validateRecord(String application, String currentRecordId, TStructure currentRecord,
            TStructure unauthorisedRecord, TStructure liveRecord, TransactionContext transactionContext) {
        // TODO Auto-generated method stub
        EbDemo2Record localTableRec = new EbDemo2Record(currentRecord);
        TField debitAcc = localTableRec.getDebitCur();
        TField creditAcc = localTableRec.getCreditCur();
        if(!debitAcc.getValue().equals(creditAcc.getValue())){
            creditAcc.setError("Debit Currency and Credit Currency Diff");
        }
       return localTableRec.getValidationResponse();
    }

}
