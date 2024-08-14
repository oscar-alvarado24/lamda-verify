package com.medicine.lamdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

public class Handler implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        for (SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            processMessage(msg, context);
        }
        context.getLogger().log("done");
        return null;
    }

    private void processMessage(SQSEvent.SQSMessage msg, Context context) {
        try {
            context.getLogger().log("Processed message " + msg.getBody());
        } catch (Exception e) {
            context.getLogger().log("An error occurred");
            throw e;
        }

    }
}