package top.ibase4j.core.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.JstlView;
import top.ibase4j.core.filter.CsrfFilter;
import top.ibase4j.core.filter.XssFilter;
import top.ibase4j.core.interceptor.BaseInterceptor;
import top.ibase4j.core.interceptor.MaliciousRequestInterceptor;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.PropertiesUtil;

import javax.servlet.MultipartConfigElement;
import java.util.List;

/**
 * @author ShenHuaJie
 * @since 2018年5月10日 下午2:45:52
 */
@EnableWebMvc
@SuppressWarnings("deprecation")
public abstract class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> encodingFilterRegistration() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        FilterRegistrationBean<CharacterEncodingFilter> registration = new FilterRegistrationBean<CharacterEncodingFilter>(
            encodingFilter);
        registration.setName("encodingFilter");
        registration.addUrlPatterns("/*");
        registration.setAsyncSupported(true);
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<CsrfFilter> csrfFilterRegistration() {
        FilterRegistrationBean<CsrfFilter> registration = new FilterRegistrationBean<CsrfFilter>(new CsrfFilter());
        registration.setName("csrfFilter");
        registration.addUrlPatterns("/*");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<XssFilter>(new XssFilter());
        registration.setName("xssFilter");
        registration.addUrlPatterns("/*");
        registration.setOrder(3);
        return registration;
    }

    public abstract BaseInterceptor eventInterceptor();

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/jsp/", ".jsp");
        registry.enableContentNegotiation(new JstlView());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> mediaTypes = InstanceUtil.newArrayList();
        mediaTypes.add(MediaType.valueOf("application/json;charset=UTF-8"));
        mediaTypes.add(MediaType.valueOf("text/html"));
        converter.setSupportedMediaTypes(mediaTypes);
        converter.setFeatures(SerializerFeature.QuoteFieldNames, SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNonStringValueAsString);
        converters.add(converter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        MaliciousRequestInterceptor requestInterceptor = new MaliciousRequestInterceptor();
        if (DataUtil.isNotEmpty(PropertiesUtil.getInt("request.minInterval"))) {
            requestInterceptor.setMinRequestIntervalTime(PropertiesUtil.getInt("request.minInterval"));
        }
        requestInterceptor.setNextInterceptor(eventInterceptor());
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**").excludePathPatterns("/*.ico", "/*/api-docs",
                "/swagger**", "/swagger-resources/**", "/webjars/**", "/configuration/**");
    }

    /** 文件上传配置 */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 文件最大
        factory.setMaxFileSize("1024Mb"); // KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("1024Mb");
        return factory.createMultipartConfig();
    }

    // 资源重定向(仅作为后台使用不提供静态资源)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("upload/**").addResourceLocations("/WEB-INF/upload/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/", "classpath:/META-INF/resources/",
            "classpath:/resources/", "classpath:/static/", "classpath:/public/");
    }

    // 允许跨域的接口
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/*").allowedOrigins("*").allowCredentials(false)
        .allowedMethods("GET", "POST", "DELETE", "PUT")
        .allowedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Headers",
            "Access-Control-Allow-Methods", "Access-Control-Max-Age")
        .exposedHeaders("Access-Control-Allow-Origin").maxAge(3600);
    }
}
