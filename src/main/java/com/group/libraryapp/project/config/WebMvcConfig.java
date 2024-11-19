package com.group.libraryapp.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 정적 리소스를 제공하기 위한 사용자 정의 리소스 핸들러를 추가합니다.
     * `/img/**` 경로로 들어오는 요청을 classpath 의 `/static/img/` 디렉토리로 매핑하며,
     * 요청된 리소스가 없거나 읽을 수 없는 경우 기본 이미지(`no-book-img.png`)를 반환합니다.
     *
     * @param registry 정적 리소스 핸들러를 등록할 {@link ResourceHandlerRegistry} 객체
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {

                        Resource requestedResource = location.createRelative(resourcePath);

                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }

                        return new ClassPathResource("static/img/no-book-img.png") ;
                    }
                });
    }
}
// TODO [공부] 클라이언트 측 경로 (/img/no-result.png) VS 서버 측 경로 (/static/img/no-result.png)
// TODO [공부] src 로 파일 요청시 처리 프로세스