//package info.mayankag.springscale.security.config;
//
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class CorsFilterConfig implements Filter {
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        final HttpServletResponse response = (HttpServletResponse) res;
//        //TODO: Change the allowed origin to specific urls
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE");
//        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//        } else {
//            chain.doFilter(req, res);
//        }
//    }
//
//    @Override
//    public void destroy() {
//    }
//
//    @Override
//    public void init(FilterConfig config) throws ServletException {
//    }
//}