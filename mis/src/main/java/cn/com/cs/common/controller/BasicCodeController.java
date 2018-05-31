package cn.com.cs.common.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.com.cs.common.json.AjaxJson;
import cn.com.cs.common.pojo.BasicTypeGroup;
import cn.com.cs.common.service.BaseService;
import cn.com.cs.system.service.BasicCodeService;
import cn.com.cs.wx.order.service.WechatOrderFormService;

@Scope("prototype")
@RestController
@RequestMapping("/code")
public class BasicCodeController {
	@Autowired
	BaseService baseService;
	
	@Autowired
	WechatOrderFormService wechatOrderFormService;
	
	@Autowired
	BasicCodeService basicCodeService;
	
	@RequestMapping(value="/init")
	@ResponseBody
	public Map<String, String> initEmums(HttpServletRequest request) {
		return baseService.getBasicCodeByTypeName(request.getParameter("typeName"));
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public List<BasicTypeGroup> list() {
		return basicCodeService.list();		
	}
	
	@RequestMapping(value="/update")
	public AjaxJson update(HttpServletRequest request) {
		try {
			return basicCodeService.update(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="del")
	public AjaxJson del(HttpServletRequest request) {
		AjaxJson json = basicCodeService.del(request);
		return json;
	}
	
	@RequestMapping(value="addG")
	public AjaxJson addG(@RequestParam String code,@RequestParam String codeName) {
		AjaxJson json = basicCodeService.addGroup(code, codeName);
		return json;
	}
	
	@RequestMapping(value="addT")
	public AjaxJson addT(@RequestParam String code,@RequestParam String codeName) {
		AjaxJson json = basicCodeService.addType(code, codeName);
		
		return json;
	}
}
