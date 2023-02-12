package kr.co.gallery.backend.config;

import java.io.IOException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@ComponentScan(basePackages="kr.co.gallery.backend")
@MapperScan(basePackages="kr.co.gallery.backend.dao")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Bean
	public CommonsMultipartResolver multipartResolver() throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		// 10MB
	    multipartResolver.setMaxUploadSize(1024*1024*10);
	    // 2MB
	    multipartResolver.setMaxUploadSizePerFile(1024*1024*2);
	    // 1MB
	    multipartResolver.setMaxInMemorySize(1024*1024);
	    // temp upload
	    multipartResolver.setUploadTempDir(new FileSystemResource("C:\\upload\\temp"));
	    multipartResolver.setDefaultEncoding("UTF-8");
	    return multipartResolver;
	}
	
	
}
