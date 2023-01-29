import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(
        filterName = "myFirstFilterName", // 可选的，但是用于在filter chain中顺序规定
        urlPatterns = "/MyFirstFilterTest"  // value和这个等价，但是不同时出现
)
public class MyFirstFilter2 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤器链第二个");
        servletRequest.setCharacterEncoding("utf-8");
        // 继续下面的过滤器链，如果没有的话就是
        filterChain.doFilter(servletRequest, servletResponse);
        // 顺序上，下面部分就是执行完成调用返回之后执行的代码
    }
}
