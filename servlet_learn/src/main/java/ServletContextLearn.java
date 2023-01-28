import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet("/resource_use")
public class ServletContextLearn extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取ServletContext对象
        ServletContext servletContext = getServletContext();

        // 进行数据的读取
        InputStream resourceAsStream = servletContext.getResourceAsStream("/WEB-INF/classes/jdbc_for_servlet_use.properties");
        Properties prop = new Properties();
        prop.load(resourceAsStream);

        // 通过key来获取配置文件
        String url = prop.getProperty("url");

        resp.getWriter().write("url:" + url);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
