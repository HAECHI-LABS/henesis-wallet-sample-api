package io.haechi.sample.server.helper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class HttpRequest {
    private static final String accessToken = "-Yy_0RFEqOgf4V83DpKuzwRdc0dLKkjPstBtYFcnVc1PSwSOyNMoLOmrLyi_0aCSOFtBJQZGa6DCtLzDcxr28YkRvWWCE_govjqYavU66jgk8mmn5vm6W9sE9pwSyghNs7E4visa98sCjdxN1YR3LPNAux_zEKchvGf6sS2okCeXhcHhEa_9iEB41mM3PTimC_Mh5wj6HmMKdk-e7BGbrJRvAH5-qJByf8RB-YusEjTE_gwK0rZWWBqjpL2RIrLKZTcVcjlBHj3gutdjEsV1esiBnlobF3n6WB1uWjB6YAXZxvarW9xARghwZVmNzHQBDgb-gvjoGsJWXSXViM4OOzaJIq5Du8-0Ri3JiVcLg-aLUJAUocigKOXEbbJ6wuEhVe3DGpEvK3Q9l7udgfVEkHIUmkI6EfgzU_9U8sv_xN5rSyqdaeJ6eQuLEHfbou35NeDAL-kQGDsm4s_mKwHq0jNgjqxVVcKG6efBeWdKJcEtdGN-3cAv4nG5G3dt98YwPbwctzG0vr8oVxJnGtrZAkhG7-krqtQ7HgbBwzrwArzPCqYTPS7-H3Il9DDnFzMA3ZHtpnbzqL7ga87ViKK3-9i8C32tanoqGBzw6lUgpKs";

    public static  <T> HttpEntity<T> getAuthorizedHttpEntity () {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        return new HttpEntity<>(null, headers);
    }

    public static <T> HttpEntity<T> getAuthorizedHttpEntity (T body) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        return new HttpEntity<>(body, headers);
    }
}
