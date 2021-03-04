package kr.or.connect.reservation.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { ApplicationConfig.class, SecurityConfig.class };
	}

	// Spring MVC 설정 파일 클래스를 지정합니다.
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { MvcConfig.class };
	}

	// DispatcherServlet이 동작할 맵핑정보를 설정합니다. "/"를 설정한다는 것은 모든 요청을 DispatcherServlet이
	// 처리한다는 것을 의미합니다.
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");

		return new Filter[] { encodingFilter };
	}
}
