import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 这里只是演示基础用法，详细的可以从注解的源码中去读
 */
@WebServlet(
        name = "webServletTest",
        value = {"/WebServletTest1", "/WebServletTest2"},  // 指明url 也可以指明是单个的 value = "\WebServletTest2" 不和 urlPatterns共同使用
        loadOnStartup = 1, // 这个servlet会在容器启动时就启动，不会是懒加载的
        description = "这是个测试使用注解的方式",
        // urlPatterns = {"/a1","a2"}
        initParams = {
                @WebInitParam(name = "websiteName", value = "getYou123.net"),
                @WebInitParam(name = "Welcome", value = "get you")}
)
public class WebServletTest extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置向页面输出内容格式
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        // 尝试在请求转发前向response缓冲区写入内容，最后在页面查看是否展示
        writer.write("<h1>hello by annotation</h1>");
        writer.write(getServletConfig().getInitParameter("Welcome") + "@" + getServletConfig().getInitParameter("websiteName"));
        writer.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
