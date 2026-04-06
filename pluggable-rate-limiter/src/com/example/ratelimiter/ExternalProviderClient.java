package com.example.ratelimiter;

public class ExternalProviderClient {
    public String callPaidResource(ClientRequest request) {
        return "external-result-for-" + request.getPayload();
    }
}
