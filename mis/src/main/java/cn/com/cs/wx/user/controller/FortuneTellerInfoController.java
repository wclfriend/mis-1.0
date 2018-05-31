package cn.com.cs.wx.user.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.com.cs.common.json.AjaxJson;
import cn.com.cs.common.service.BaseService;
import cn.com.cs.common.util.MyBeanUtils;
import cn.com.cs.core.hibernate.Page;
import cn.com.cs.wx.order.service.WechatOrderFormService;
import cn.com.cs.wx.user.pojo.FortuneTellerInfo;
import cn.com.cs.wx.user.service.FortuneTellerInfoService;

@Scope("prototype")
@RestController
@RequestMapping("/f")
public class FortuneTellerInfoController {
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	WechatOrderFormService wechatOrderFormService;
	
	@Autowired
	FortuneTellerInfoService fortuneService;
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Page list(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true, value = "start", defaultValue = "0") int start,
			@RequestParam(required = true, value = "length", defaultValue = "10") int length) {
		Page page = new Page();
		page.setStart(start);
		page.setLength(length);
		List<FortuneTellerInfo> list = fortuneService.fortuneList(request, page);
		
		page.setData(list);
		page.setDraw(StringUtils.isBlank(request.getParameter("draw")) ? 1 : Integer.valueOf(request.getParameter("draw")) + 1);
		
		return page;
	}
	

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value="/del")
	@ResponseBody
	public AjaxJson del(FortuneTellerInfo fortune, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		boolean flag = fortuneService.hasOrderForms(id);
		if (!flag) {
			FortuneTellerInfo t = fortuneService.getEntity(FortuneTellerInfo.class, id);
			wechatOrderFormService.delete(t);
			
			j.setMsg("删除成功");
		} else {
			j.setSuccess(false);
			j.setMsg("测算师名下存在订单，不可删除");
		}
		
		return j;
	}


	/**
	 * 添加订单信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public AjaxJson save(FortuneTellerInfo fortune, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtils.isNotEmpty(fortune.getId())) {
			FortuneTellerInfo t = fortuneService.get(FortuneTellerInfo.class, fortune.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(fortune, t);
				
				t.setModifyTime(new Date());
				fortune.setActive("1");
				fortuneService.saveOrUpdate(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			fortune.setCreateTime(new Date());
			fortune.setModifyTime(new Date());
			fortune.setActive("1");
			fortuneService.save(fortune);
		}
		return j;
	}

	@RequestMapping(value = "detail")
	public ModelAndView detail(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/web/system/fortune/fortune_detail");
		
		FortuneTellerInfo f = fortuneService.findUniqueByProperty(FortuneTellerInfo.class, "id", request.getParameter("id"));
		mav.addObject("f", f);
		
		return mav;
	}
}
