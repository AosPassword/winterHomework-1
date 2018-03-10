package org.redrock.gayligayli.manage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ManageSelectServlet",urlPatterns = "/manageSelect")
public class ManageSelectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("manage")!=null){
            Map<String,String[]> map = request.getParameterMap();
            for (Map.Entry entry:map.entrySet()
                 ) {
                System.out.println(entry.getKey());
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request,response);
    }
}
