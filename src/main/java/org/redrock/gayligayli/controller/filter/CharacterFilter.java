package org.redrock.gayligayli.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.HEADER_ONE;
import static org.redrock.gayligayli.util.FinalStringUtil.HEADER_TWO;
import static org.redrock.gayligayli.util.FinalStringUtil.UTF8;

@WebFilter(filterName = "CharacterFilter",urlPatterns = "/*")
public class CharacterFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding(UTF8);
        resp.setCharacterEncoding(UTF8);
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setHeader(HEADER_ONE,HEADER_TWO);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
