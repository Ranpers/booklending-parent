package pers.yiran.booklending.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.yiran.booklending.entity.User;

/**
 * @author Yiran
 */
@SuppressWarnings("all")
@Component
public class UnauthorizedAccessInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        //获取请求的路径
        String uri = request.getRequestURI();
        //如果用户是已登录状态，判断访问的资源是否有权限
        int need;
        if (uri.contains("admin")) {
            need = 0;
        } else if (uri.contains("employee")) {
            need = 1;
        } else {
            need = 2;
        }
        if (user != null) {
            if (user.getRole() <= need) {
                return true;
            } else {
                request.getRequestDispatcher("/permission/no_permissions").forward(request, response);
            }
        }
        //其他情况都直接跳转到登录页面
        else {
            request.getSession().setAttribute("isRedirect", true);
            response.sendRedirect("/booklending/user/login");
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
