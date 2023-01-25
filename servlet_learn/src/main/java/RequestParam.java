import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * 从表单额请求中获取相应的数据
 */
public class RequestParam extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //修改request缓冲区的字符集为UTF-8
        request.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        // 获取内容，做其他操作
        // 获取姓名
        String username = request.getParameter("username");
        // 获取密码
        String password = request.getParameter("password");
        // 获取性别
        String sex = request.getParameter("sex");
        // 获取城市
        String city = request.getParameter("city");
        // 获取语言
        String[] languages = request.getParameterValues("language");
        writer.write("用户名：" + username + "<br/>" + "密码：" + password + "<br/>" + "性别：" + sex + "<br/>" + "城市：" + city
                + "<br/>" + "使用过的语言：" + Arrays.toString(languages) + "<br/>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}