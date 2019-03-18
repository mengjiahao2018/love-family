package com.love.family.system.mvc;

import java.io.File;

import net.bytebuddy.asm.Advice.Local;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.view.JstlView;

public class MyJstlView extends JstlView {

	public MyJstlView() {
		super();
	}

	public MyJstlView(String url, MessageSource messageSource) {
		super(url, messageSource);
	}

	public MyJstlView(String url) {
		super(url);
	}

	public boolean checkResource(Local local) throws Exception {
		File file = new File(removeFirstSlaps(this.getServletContext()
				.getResource("/") + removeFirstSlaps(getUrl(), "/"), "file:"));
		return file.exists();
	}

	public static String removeFirstSlaps(String url, String prefix) {
		if (StringUtils.isNotBlank(url) && url.startsWith(prefix)) {
			return url.substring(prefix.length());
		} else {
			return url;
		}
	}
}
