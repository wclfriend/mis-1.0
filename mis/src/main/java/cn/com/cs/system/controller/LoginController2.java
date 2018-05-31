package cn.com.cs.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.com.cs.common.controller.BaseController;
import cn.com.cs.system.service.SystemMenuService;

@Scope("prototype")
@RestController
@RequestMapping("/tt")
public class LoginController2 extends BaseController {
	
	@Autowired
	SystemMenuService systemMenuService;
	
	String id;

	@RequestMapping(method=RequestMethod.GET,value="{id}")
	public ModelAndView viewUser(@PathVariable("id")String id){
		
		System.out.println("hello,world!");
		System.out.println(id);
		
		return new ModelAndView("user/view");
	}

	@RequestMapping(method=RequestMethod.GET,value="/ss/{id}")
	public ModelAndView ss(@PathVariable("id")String id){
		
		System.out.println("hello,world!");
		System.out.println(id);
		
		return new ModelAndView("user/view");
	}
	
}
