package ${package}.support.cros;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FixedCorsConfiguration extends CorsConfiguration {

    @Override
    public List<String> checkHeaders(List<String> requestHeaders) {
        if (requestHeaders == null) {
            return null;
        }
        if (requestHeaders.isEmpty()) {
            return Collections.emptyList();
        }
        // allow Access-Control-Allow-Headers : fix in ie9
        if (requestHeaders.size() == 1 && org.apache.commons.lang3.StringUtils.isBlank(requestHeaders.get(0))) {
            return Collections.emptyList();
        }

        if (ObjectUtils.isEmpty(this.getAllowedHeaders())) {
            return null;
        }

        boolean allowAnyHeader = this.getAllowedHeaders().contains(ALL);
        List<String> result = new ArrayList<String>();
        for (String requestHeader : requestHeaders) {
            if (StringUtils.hasText(requestHeader)) {
                requestHeader = requestHeader.trim();
                for (String allowedHeader : this.getAllowedHeaders()) {
                    if (allowAnyHeader || requestHeader.equalsIgnoreCase(allowedHeader)) {
                        result.add(requestHeader);
                        break;
                    }
                }
            }
        }
        return (result.isEmpty() ? null : result);
    }
}
