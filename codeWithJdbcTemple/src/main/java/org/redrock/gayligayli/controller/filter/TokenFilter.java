package org.redrock.gayligayli.controller.filter;

import net.sf.json.JSONObject;
import org.redrock.gayligayli.service.loginAndRegister.been.Token;
import org.redrock.gayligayli.util.JsonUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.*;

public class TokenFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("filter");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String jwt = request.getHeader(JWT);
        JSONObject jsonObject = null;
        if(jwt!=null){
            Token token = new Token(jwt);
            if(token.isToken()){
                if(token.isNotTokenOverTime()){
                    request.setAttribute(JWT,token);
                    chain.doFilter(req,resp);
                }else{
                     jsonObject = new JSONObject();
                    jsonObject.put(RESULT,TOKEN_OVERTIME);
                }
            }else{
                 jsonObject = new JSONObject();
                jsonObject.put(RESULT,TOKEN_ERROR);
            }
        } else {
            jsonObject=new JSONObject();
            jsonObject.put(RESULT,TOKEN_ERROR);
        }
        assert jsonObject!=null;
        JsonUtil.writeResponse(response,jsonObject.toString());
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
