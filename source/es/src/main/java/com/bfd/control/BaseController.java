package com.bfd.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.google.common.base.Strings;
import com.netease.train.constant.CommonConstant;
import com.netease.train.util.TrainUtil;

/**
 * 所有Controller共有的操作
 * @author senlin
 *
 */

@Controller
public class BaseController {

	public boolean verifyPara(String... params) {

		for (String param : params) {
			if (Strings.isNullOrEmpty(param)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 向request中设置属性
	 * 
	 * @return
	 */
	protected void setAttribute(String attrName, Object attrValue) {

		HttpServletRequest request = CommonConstant.requestTL.get();
		request.setAttribute(attrName, attrValue);
	}

	/**
	 * 向request中设置属性
	 * 
	 * @return
	 */
	protected String getParameter(String parameterName) {

		HttpServletRequest request = CommonConstant.requestTL.get();
		return Strings.nullToEmpty(request.getParameter(parameterName));
	}
	
	/**
	 * 向request中设置属性
	 * 
	 * @return
	 */
	protected String getClientIP() {

		HttpServletRequest request = CommonConstant.requestTL.get();
		return TrainUtil.getLastIp(request);
	}
}
