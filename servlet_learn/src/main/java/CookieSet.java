import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 实现设置client端的cookie
 */
@WebServlet("/addCookie")
public class CookieSet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 增加key-value 对
        Cookie c1 = new Cookie("name","getYou123");
        Cookie c2 = new Cookie("url","getYou123.net");
        resp.addCookie(c1);
        resp.addCookie(c2);




        // 如果是中文的话增加的编码
        String test_str = URLEncoder.encode("中文测试串", "utf-8");
        Cookie c3 = new Cookie("ch", test_str);
        // 设置cookie的存活时间 单位是秒
        c3.setMaxAge(100);
        resp.addCookie(c3);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
