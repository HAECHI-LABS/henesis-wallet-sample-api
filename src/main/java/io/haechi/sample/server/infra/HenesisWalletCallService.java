package io.haechi.sample.server.infra;

import io.haechi.sample.server.infra.exceptions.HenesisWalletException;

public abstract class HenesisWalletCallService {
    protected <T> T serviceTemplate(ServiceCallback<T> callback) {
        try {
            return callback.sendRequest();
        } catch (Exception e) {
            throw new HenesisWalletException("internal wallet service failed", e);
        }
    }

    protected interface ServiceCallback<T> {
        T sendRequest() throws Exception;
    }
}
