import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class DoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置向页面输出内容格式
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        String webName = (String) request.getAttribute("webName");
        String url = (String) request.getAttribute("url");
        String welcome = (String) request.getAttribute("welcome");
        if (webName != null) {
            writer.write("<h3>" + webName + "</h3>");
        }
        if (url != null) {
            writer.write("<h3>" + url + "</h3>");
        }
        if (welcome != null) {
            writer.write("<h3>" + welcome + "</h3>");
        }

        String username = request.getParameter("username");
        // 获取密码
        String password = request.getParameter("password");
        // 获取性别
        String sex = request.getParameter("sex");
        // 获取城市
        String city = request.getParameter("city");
        // 获取使用语言返回是String数组
        String[] languages = request.getParameterValues("language");
        writer.write("用户名：" + username + "<br/>" + "密码：" + password + "<br/>" + "性别：" + sex + "<br/>" + "城市：" + city
                + "<br/>" + "使用过的语言：" + Arrays.toString(languages) + "<br/>"
        );
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}