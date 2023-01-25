import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 这是第一个servlet
 */
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置向页面输出内容格式
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        // 尝试在请求转发前向response缓冲区写入内容，最后在页面查看是否展示
        writer.write("<h1>这是转发前在响应信息内的内容！</h1>");
        // 向request域对象中添加属性，传递给下一个web资源
        request.setAttribute("webName", "测试");
        request.setAttribute("url", "这里是设置的url内容的value");
        request.setAttribute("welcome", "欢迎用语");
        // 转发 这里转发的时候增加了三个属性，转发到另外的servlet了
        request.getRequestDispatcher("/DoServletTest").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}