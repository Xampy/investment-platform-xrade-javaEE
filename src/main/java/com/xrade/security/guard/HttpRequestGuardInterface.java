package com.xrade.security.guard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpRequestGuardInterface {
	void canActivate(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
