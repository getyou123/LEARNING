import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "NullToHtml",
        urlPatterns = "/nullTest"
)
public class NullToHtml extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决服务器响应的中文乱码
        resp.setContentType("text/html;charset=utf-8");
        //处理前端的相关操作
        String date = null;

        String s = date.toString();
        //响应给浏览器信息
        resp.getWriter().write(s);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决服务器响应的中文乱码
        resp.setContentType("text/html;charset=utf-8");
        //处理前端的相关操作
        String date = null;

        String s = date.toString();
        //响应给浏览器信息
        resp.getWriter().write(s);
    }
}
