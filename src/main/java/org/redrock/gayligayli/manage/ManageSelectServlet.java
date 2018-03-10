package org.redrock.gayligayli.manage;

import org.redrock.gayligayli.Dao.Dao;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "ManageSelectServlet", urlPatterns = "/manageSelect")
public class ManageSelectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("manage") != null) {
            String action = request.getParameter("action");
            String type = request.getParameter("type");
            String username = request.getParameter("username");
            String where = request.getParameter("where");
            String text = request.getParameter("text");

            if("user".equals(type)&&"name".equals(username)){
                username="nickname";
            }
            String sql = null;
            StringBuilder sb = new StringBuilder();
            JdbcTemplate jdbcTemplate = Dao.getJdbcTemplate();
            System.out.println(action);
            if ("select".equals(action)) {
                if("like".equals(where)){
                    text="%"+text+"%";
                }
                sql = "SELECT * FROM " + type + " WHERE " + username + " "+where+" '" + text+"'";
                List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
                System.out.println(list.size());
                if (list.size() != 0) {
                    sb.append("<table border=\"1\">\n<tr>\n<td>");
                    Set<String> set = list.get(0).keySet();
                    for (String str:set) {
                        sb.append(str).append("</td>\n<td>");
                    }
                    sb.delete(sb.length()-4,sb.length());
                    for(Map<String,Object> map:list){
                        sb.append("<tr>\n<td>");
                        for (String str:set) {
                            sb.append(map.get(str)).append("</td>\n<td>");
                        }
                        sb.delete(sb.length()-4,sb.length());
                    }
                }else{
                    sb.append("NOT FIND");
                }
            } else {
                sql = "DELETE FROM " + type + " WHERE " + username + " = '" + text+"'";
                System.out.println(sql);
                jdbcTemplate.update(sql);
                sb.append("成功！");
            }

            request.setAttribute("text", sb.toString());
            RequestDispatcher view = request.getRequestDispatcher("darling.jsp");
            view.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
