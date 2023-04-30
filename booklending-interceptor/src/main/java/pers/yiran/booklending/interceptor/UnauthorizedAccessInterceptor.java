package pers.yiran.booklending.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.yiran.booklending.common.Access;
import pers.yiran.booklending.common.AccessLevel;
import pers.yiran.booklending.entity.User;

import java.lang.reflect.Method;

/**
 * @author Yiran
 */
@SuppressWarnings("all")
@Component
public class UnauthorizedAccessInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Access access = method.getAnnotation(Access.class);
        if (user != null) {
            if (access == null) {
                return true;
            }
            if (access.level() == AccessLevel.ALL) {
                return true;
            }
            if (access.level().getCode() <= user.getRole()) {
                return true;
            } else {
                request.getRequestDispatcher("/permission/no_permissions").forward(request, response);
            }
        } else {
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
