import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtil {
    /**
     * 设置cookie
     *
     * @param request
     * @param response
     * @param cookieName   cookie名称
     * @param cookieValue  cookie值
     * @param cookieMaxAge cookie有效期 -1(浏览器关闭时)
     * @param encoding     编码
     */
    private static void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxAge, String encoding) {
        if (cookieValue == null) {
            cookieValue = "";
        }
        try {
            cookieValue = URLEncoder.encode(cookieValue, encoding);//对cookie的value进行url编码
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxAge > 0) {
                cookie.setMaxAge(cookieMaxAge);//设置有效期
            }
            cookie.setPath("/");
            response.addCookie(cookie);//设置cookie
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param response
     * @param cookieName   名称
     * @param cookieValue  值
     * @param cookieMaxAge 有效期
     * @param isEncoder    是否编码，如果编码，则使用utf-8
     */
    private static void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxAge, boolean isEncoder) {
        if (cookieValue == null) {
            cookieValue = "";
        }
        try {
            if (isEncoder) {
                cookieValue = URLEncoder.encode(cookieValue, "utf-8");//对cookie的value进行url编码
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxAge > 0) {
                cookie.setMaxAge(cookieMaxAge);//设置有效期
            }
            cookie.setPath("/");
            response.addCookie(cookie);//设置cookie
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定编码格式
     *
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxAge
     * @param encoding
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxAge, String encoding) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxAge, encoding);
    }

    /**
     * 默认使用utf-8进行url编码
     *
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxAge
     * @param isEncoder
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxAge, boolean isEncoder) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxAge, isEncoder);
    }

    /**
     * @param request
     * @param cookieName 名称
     * @param encoding   编码
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encoding) {
        Cookie[] cookies = request.getCookies();
        Cookie findCookie = null;
        for (Cookie c : cookies) {
            if (cookieName.equals(c.getName())) {
                findCookie = c;
                break;
            }
        }
        if (findCookie != null) {
            try {
                return URLDecoder.decode(findCookie.getValue(), encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 默认使用utf-8进行解码
     *
     * @param request
     * @param cookieName
     * @param isEncoder  是否解码
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isEncoder) {
        Cookie[] cookies = request.getCookies();
        Cookie findCookie = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName())) {
                    findCookie = c;
                    break;
                }
            }
        }

        if (findCookie != null) {
            try {
                if (isEncoder) {
                    return URLDecoder.decode(findCookie.getValue(), "utf-8");
                } else {
                    return findCookie.getValue();
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}