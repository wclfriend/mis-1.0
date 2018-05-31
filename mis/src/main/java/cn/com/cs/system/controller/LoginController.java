package cn.com.cs.system.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.web.servlet.ModelAndView;

@Path("/test")
public class LoginController {
	
	@PathParam("id")
	String id;
	@GET
	@Path("{id}/ss")
	public ModelAndView ss() {
		System.out.println("hello,world!");
		
		ModelAndView mav = new ModelAndView("/web/system/user/user_list");
		
		System.out.println(mav.toString());
		return mav;
	}
}
