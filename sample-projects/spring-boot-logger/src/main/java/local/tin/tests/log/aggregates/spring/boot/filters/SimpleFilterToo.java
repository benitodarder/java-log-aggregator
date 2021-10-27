package local.tin.tests.log.aggregates.spring.boot.filters;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import org.springframework.stereotype.Component;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import local.tin.tests.log.aggregates.LogException;
import local.tin.tests.log.aggregates.LogStep;
import local.tin.tests.log.aggregates.spring.boot.logging.Sl4jEntriesExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author benitodarder
 */
@Component
public class SimpleFilterToo implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleFilterToo.class);

    @Autowired
    private Sl4jEntriesExecutor sl4jEntriesExecutor;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("SimpleFilterToo initialized!");
    }

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
                logStep.setMessage(requestBody);
                sl4jEntriesExecutor.initialize(logStep);
                UUID uuid = logStep.getId();
                try {
                    logStep = new LogStep();
                    logStep.setId(uuid);
                    logStep.setMessage("Before doFilter");
                    sl4jEntriesExecutor.append(logStep);
                    filterChain.doFilter(requestWrapper, responseWrapper);
                    logStep = new LogStep();
                    logStep.setId(uuid);
                    logStep.setMessage(responseWrapper.getContent());
                    sl4jEntriesExecutor.finalize(logStep);
                    httpResponse.getOutputStream().write(responseWrapper.getContentAsBytes());

                } catch (Exception e) {
                    logStep = new LogStep();
                    logStep.setId(uuid);
                    logStep.setMessage(e.getMessage());

                    sl4jEntriesExecutor.finalize(logStep);

                    httpResponse.getOutputStream().write(e.getMessage().getBytes());
                }
            } catch (LogException ex) {
                LOGGER.error("Wow!", ex);
            }

        }
        LOGGER.info("SimpleFilterToo doFilter ends!");
    }

    @Override
    public void destroy() {
        LOGGER.info("SimpleFilterToo destroyed!");
    }

}
