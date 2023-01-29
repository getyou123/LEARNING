import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(
        description = "my first WebFilter annotation",
        displayName = "my first WebFilter annotation",
        filterName = "myFirstFilterName", // 可选的 不给的话使用类的全限定名称
        urlPatterns = "/MyFirstFilterTest"  // value和这个等价，但是不同时出现
)
public class MyFirstFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 这里只是设置中文的不乱码
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("text/html;charset=UTF-8");
        // 放行，从这开始就到servlet中
        filterChain.doFilter(servletRequest, servletResponse);
        // 这里就是执行完servlet之后的操作

    }

    @Override
    public void destroy() {
    }
}
