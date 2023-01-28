import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/SessionAdd1")
public class SessionAdd1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String id = session.getId();
        long creationTime = session.getCreationTime();
        long lastAccessedTime = session.getLastAccessedTime();
        int count = 0;
        try {
            count = (int) session.getAttribute("count");
        } catch (Exception e) {

        }
        resp.setContentType("text/html;charset=UTF-8");
        if (count == 0) {
            resp.getWriter().write(id + "首次session访问,session创建时间为" + creationTime + "，上次访问时间是" + lastAccessedTime);

        } else {
            resp.getWriter().write(id + "第" + count + "次访问，session创建时间为" + creationTime + "，上次访问时间是" + lastAccessedTime);
        }
        count += 1;
        if (count > 20) session.invalidate();
        session.setAttribute("count", count);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
