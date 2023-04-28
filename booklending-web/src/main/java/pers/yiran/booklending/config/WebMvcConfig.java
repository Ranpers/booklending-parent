package pers.yiran.booklending.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.yiran.booklending.interceptor.UnauthorizedAccessInterceptor;

/**
 * @author Yiran
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private UnauthorizedAccessInterceptor unauthorizedAccessInterceptor;
    @Autowired
    public void setUnauthorizedAccessInterceptor(UnauthorizedAccessInterceptor unauthorizedAccessInterceptor) {
        this.unauthorizedAccessInterceptor = unauthorizedAccessInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(unauthorizedAccessInterceptor).
                addPathPatterns("/**").
                excludePathPatterns(
                        "/**/*.html", "/**/*.js", "/**/*.css",
                        "/**/*.png", "/**/*.jpg");
    }
}