package hr.algebra.advanced_interoperability_project.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class TransactionFilterTest {
    private TransactionFilter filter;
    private HttpServletRequest request;
    private ServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        filter = new TransactionFilter();
        request = mock(HttpServletRequest.class);
        response = mock(ServletResponse.class);
        filterChain = mock(FilterChain.class);

        when(request.getRequestURI()).thenReturn("/test-uri");
    }

    @Test
    void testDoFilter_logsTransactionAndContinuesChain() throws ServletException, IOException {
        filter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(request, atLeastOnce()).getRequestURI();
    }
}