package hr.algebra.advanced_interoperability_project.filter;

import hr.algebra.advanced_interoperability_project.service.JwtService;
import hr.algebra.advanced_interoperability_project.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    private JwtAuthFilter jwtAuthFilter;

    @BeforeEach
    void setUp() {
        jwtAuthFilter = new JwtAuthFilter(jwtService, userDetailsServiceImpl);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_validToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer test-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtService.extractUsername("test-token")).thenReturn("testUser");
        when(userDetailsServiceImpl.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(jwtService.validateToken("test-token", userDetails)).thenReturn(true);
        when(userDetails.getAuthorities()).thenReturn(null);
        when(userDetails.getUsername()).thenReturn("testUser");

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("testUser", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_headerWithoutBearer() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic some-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_invalidToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtService.extractUsername("invalid-token")).thenReturn("testUser");
        when(userDetailsServiceImpl.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(jwtService.validateToken("invalid-token", userDetails)).thenReturn(false);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }
}