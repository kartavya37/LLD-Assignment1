package com.example.ratelimiter;

public class ClientRequest {
    private final String tenantId;
    private final String payload;
    private final boolean externalCallNeeded;

    public ClientRequest(String tenantId, String payload, boolean externalCallNeeded) {
        this.tenantId = tenantId;
        this.payload = payload;
        this.externalCallNeeded = externalCallNeeded;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getPayload() {
        return payload;
    }

    public boolean isExternalCallNeeded() {
        return externalCallNeeded;
    }
}
