package com.icia.member.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration : 설정 정보를 스프링 실행시 등혹해줌
@Configuration // 인터셉터를 안쓰고 싶으면 이거만 주석처리
public class WebConfig implements WebMvcConfigurer {
    // 로그인 여부에 따른 접속가능 페이지 구분
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        // 만든 LoginCheckIntercepter클래스 내용을 넘김
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1) // 해당 인터셉터가 적용되는 순서
                .addPathPatterns("/**") // 해당 프로젝트의 모든 주소에 대한 인터셉터를 적용
                // 그중에 이 주소는 제외
                .excludePathPatterns("/","/member/save","/member/login","/member/logout","/css/**");
    }
}
