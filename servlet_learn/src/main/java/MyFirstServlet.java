import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyFirstServlet extends HttpServlet {

    /**
     * 这是针对前端的get请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决服务器响应的中文乱码
        resp.setContentType("text/html;charset=utf-8");
        //处理前端的相关操作
        System.out.println("hello,Servlet");
        // 尝试获取配置在web.xml中的init-param参数
        System.out.println(getServletConfig().getInitParameter("url"));
        //响应给浏览器信息
        resp.getWriter().write("hello,这是我第一个Servlet" + "这是读取到的url内容：" + getServletConfig().getInitParameter("url"));

    }

    /**
     * 针对的是前端的post请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}