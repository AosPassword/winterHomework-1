package org.redrock.gayligayli.controller.filter;

import net.sf.json.JSONObject;
import org.redrock.gayligayli.service.Receiver;
import org.redrock.gayligayli.util.JsonUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.redrock.gayligayli.util.FinalStringUtil.RECEIVE;

public class SignatureFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String requestJson = JsonUtil.getJsonStr(request.getInputStream());
        Receiver receiver = new Receiver(requestJson);
        if(receiver.isSignatureTrue()){
            request.setAttribute(RECEIVE,receiver);
            chain.doFilter(req, resp);
        }else{
            receiver.errorString();
            JSONObject jsonObject = new JSONObject();
            JsonUtil.writeResponse(response,receiver.getResponse());
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
