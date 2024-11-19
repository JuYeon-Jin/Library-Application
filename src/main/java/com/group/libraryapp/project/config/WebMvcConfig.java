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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        // 요청된 리소스를 확인
                        Resource requestedResource = location.createRelative(resourcePath);

                        // 리소스가 존재하고 읽을 수 있으면 반환
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }

                        // 요청된 리소스가 없을 경우 기본 리소스 반환
                        return new ClassPathResource("static/img/no-src.png");
                    }
                });
    }
}
// TODO [공부] 클라이언트 측 경로 (/img/no-result.png) VS 서버 측 경로 (/static/img/no-result.png)
// TODO [공부] src 로 파일 요청시 처리 프로세스