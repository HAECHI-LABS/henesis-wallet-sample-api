package io.haechi.sample.server.config.utils;

import io.haechi.sample.server.web.dto.ErrorBody;
import io.haechi.sample.server.web.dto.ErrorResponse;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@Component
public class ErrorHelper {
    public ErrorResponse createErrorResponse(HttpStatus status, String responseMessage) {
        return ErrorResponse
                .builder()
                .error(
                        ErrorBody
                                .builder()
                                .code(status.value())
                                .message(responseMessage)
                                .build()
                )
                .build();
    }

    public Object[] createErrorParams(
            HttpServletRequest request,
            String ...args
    ) {
        List<Object> params = new ArrayList<Object>(Arrays.asList(
                "params", getParams(request),
                "request_uri", request.getRequestURL(),
                "http_method", request.getMethod()
        ));
        params.addAll(Arrays.asList(args));
        return params.toArray();
    }

    private JSONObject getParams(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replaceAll("\\.", "-");
            jsonObject.put(replaceParam, request.getParameter(param));
        }
        return jsonObject;
    }
}
