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
public class Task3Rtn extends RecordLifecycle {
    @Override
    public String checkId(String currentRecordId, TransactionContext transactionContext) {
        // TODO Auto-generated method stub
        currentRecordId = "66"+currentRecordId;
        return currentRecordId;
    }

    @Override
    public TValidationResponse validateRecord(String application, String currentRecordId, TStructure currentRecord,
            TStructure unauthorisedRecord, TStructure liveRecord, TransactionContext transactionContext) {
        // TODO Auto-generated method stub
        EbDemo2Record localTableRec = new EbDemo2Record(currentRecord);
        TField creditAcc = localTableRec.getCreditCur();
        if(creditAcc.getValue().isEmpty()){
            creditAcc.setError("Credit Currency is Empty");
        }
       return localTableRec.getValidationResponse();
    }
}