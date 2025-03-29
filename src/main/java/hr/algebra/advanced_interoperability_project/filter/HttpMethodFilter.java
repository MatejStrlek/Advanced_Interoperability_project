package hr.algebra.advanced_interoperability_project.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(2)
public class HttpMethodFilter implements Filter {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HttpMethodFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String httpMethod = ((HttpServletRequest) servletRequest).getMethod();
        log.info("HTTP method: {}", httpMethod);
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("HttpMethod filter done!");
    }
}