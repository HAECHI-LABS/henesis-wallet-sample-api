package io.haechi.sample.server.config.util;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

public class EthSigner {
    public static Credentials getDummyCredentials() {
        ECKeyPair dummyKeyPair;
        try {
            dummyKeyPair = Keys.createEcKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e); // FIXME
        }
        return Credentials.create(dummyKeyPair);
    }
}
