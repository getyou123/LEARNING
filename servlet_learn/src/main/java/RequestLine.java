import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestLine extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println("请求方式:" + request.getMethod() + "<br/>" +
                "客户端的 IP 地址:" + request.getRemoteAddr() + "<br/>" +
                "应用名字（上下文）:" + request.getContextPath() + "<br/>" +
                "URI:" + request.getRequestURI() + "<br/>" +
                "请求字符串:" + request.getQueryString() + "<br/>" +
                "Servlet所映射的路径:" + request.getServletPath() + "<br/>" +
                "客户端的完整主机名:" + request.getRemoteHost() + "<br/>"
        );
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}