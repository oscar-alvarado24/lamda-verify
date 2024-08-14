package com.medicine.lamdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;

public class Handler implements RequestHandler<SQSEvent, Void> {

    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    public static final String SERVICE_SID = System.getenv("TWILIO_SERVICE_SID");
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
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Verification verification =
                    Verification.creator(SERVICE_SID,  Constants.COLOMBIAN_INDICATIVE.concat(msg.getBody()), Constants.SMS)
                            .create();
            context.getLogger().log("codigo generado con el id: " + verification.getSid());
        } catch (Exception e) {
            context.getLogger().log("An error occurred");
            throw e;
        }

    }
}