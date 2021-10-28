package local.tin.tests.tomee.hello.world.filters;

import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import local.tin.tests.log.aggregates.LogException;
import local.tin.tests.log.aggregates.LogStep;
import local.tin.tests.tomee.hello.world.logging.Sl4jEntriesExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 *
 * @author benitodarder
 */
public class SimpleFilter implements javax.servlet.Filter {

    public static final String HEADER_X_REQUEST_ID = "X-Request-Id";
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("SimpleFilterToo doFilter starts!");
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            LOGGER.warn("LoggingFilter just supports HTTP requests");
        } else {

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            SimpleHttpServletRequestWrapper requestWrapper = new SimpleHttpServletRequestWrapper(httpRequest);
            SimpleHttServletResponseWrapper responseWrapper = new SimpleHttServletResponseWrapper(httpResponse);
            String requestBody = requestWrapper.getContent();
            long t0 = System.currentTimeMillis();

            try {
                LogStep logStep = new LogStep();
                logStep.setMessage(getInitalMessage(httpRequest, requestBody));
                String xRequestId = httpRequest.getHeader(HEADER_X_REQUEST_ID);
                if (xRequestId != null) {
                    logStep.setId(xRequestId);
                }
                Sl4jEntriesExecutor.getInstance().initialize(logStep);
                String logStepId = logStep.getId();
                MDC.put(HEADER_X_REQUEST_ID, logStepId);
                try {
                    logStep = new LogStep();
                    logStep.setId(logStepId);
                    logStep.setMessage("Before doFilter!");
                    Sl4jEntriesExecutor.getInstance().append(logStep);
                    filterChain.doFilter(requestWrapper, responseWrapper);
                    logStep = new LogStep();
                    logStep.setId(logStepId);
                    logStep.setMessage(getFinalMessage(responseWrapper.getContent()));
                    Sl4jEntriesExecutor.getInstance().finalize(logStep);
                    httpResponse.getOutputStream().write(responseWrapper.getContentAsBytes());

                } catch (Exception e) {
                    logStep = new LogStep();
                    logStep.setId(logStepId);
                    logStep.setMessage(getFinalMessage(e.getMessage()));

                    Sl4jEntriesExecutor.getInstance().finalize(logStep);

                    httpResponse.getOutputStream().write(e.getMessage().getBytes());
                }
            } catch (LogException ex) {
                LOGGER.error("Wow!", ex);
            } finally {
                MDC.remove(HEADER_X_REQUEST_ID);
            }

        }
        LOGGER.info("SimpleFilterToo doFilter ends!");
    }

    public String getFinalMessage(String responseBody) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-----------------------------------------------------------------------")
                .append(System.lineSeparator())
                .append("Response body:")
                .append(System.lineSeparator())
                .append(responseBody);
        return stringBuilder.toString();
    }

    private String getInitalMessage(HttpServletRequest httpRequest, String requestBody) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(httpRequest.getMethod())
                .append(" ")
                .append(httpRequest.getRequestURI())
                .append(" ")
                .append(httpRequest.getQueryString())
                .append(System.lineSeparator())
                .append("Request body:")
                .append(System.lineSeparator())
                .append(requestBody)
                .append(System.lineSeparator())
                .append("-----------------------------------------------------------------------");
        return stringBuilder.toString();
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
