package hr.algebra.advanced_interoperability_project.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.IOException;

import static org.mockito.Mockito.*;

class HttpMethodFilterTest {

    private HttpMethodFilter filter;
    private MockHttpServletRequest request;
    private ServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        filter = new HttpMethodFilter();
        request = new MockHttpServletRequest();
        response = mock(ServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

    @Test
    void testDoFilter_logsAndContinuesChain() throws ServletException, IOException {
        request.setMethod("POST");

        filter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }
}