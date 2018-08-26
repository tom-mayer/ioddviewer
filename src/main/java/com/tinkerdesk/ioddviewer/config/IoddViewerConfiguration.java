package com.tinkerdesk.ioddviewer.config;

import com.tinkerdesk.ioddviewer.service.DirectoryWatcherService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class IoddViewerConfiguration {

    @Value("${iodd.path}")
    private String ioddPath;

    @Value("${iodd.interval}")
    private long ioddInterval;

    @Bean
    @Qualifier("iodd")
    public DirectoryWatcherService createDefaultDirectoryWatcher(){
        DirectoryWatcherService service = new DirectoryWatcherService();
        service
            .setPath(this.ioddPath)
            .setInterval(this.ioddInterval);
        return service;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }


}
