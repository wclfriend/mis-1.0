package cn.com.cs.wx.order.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.com.cs.common.controller.BaseController;
import cn.com.cs.common.enums.OrderStatus;
import cn.com.cs.common.enums.OrderType;
import cn.com.cs.common.json.AjaxJson;
import cn.com.cs.common.service.BaseService;
import cn.com.cs.common.util.DateUtils;
import cn.com.cs.common.util.IpUtil;
import cn.com.cs.core.hibernate.Page;
import cn.com.cs.pay.dto.wechat.WechatPaymentDTO;
import cn.com.cs.pay.service.WechatPaymentService;
import cn.com.cs.wx.order.pojo.WechatOrderForm;
import cn.com.cs.wx.order.service.WechatOrderFormService;
import cn.com.cs.wx.user.service.FortuneTellerInfoService;

@Scope("prototype")
@RestController
@RequestMapping("/order")
public class WechatOrderFormController extends BaseController {
	@Autowired
	BaseService baseService;

	@Autowired
	WechatOrderFormService orderService;
	
	@Autowired
	WechatPaymentService wechatPaymentService;
	
	@RequestMapping(value="/scanCode", method={RequestMethod.GET})
	public void scanCode(HttpServletRequest request,HttpServletResponse response) {
		String orderSerialNumber = request.getParameter("orderSerialNumber");
		WechatOrderForm orderForm = orderService.findUniqueByProperty(WechatOrderForm.class, "orderSerialNumber", orderSerialNumber);
		if (orderForm == null)
			return;
		
		WechatPaymentDTO dto = new WechatPaymentDTO();
		//产品名称
		dto.setBody("body");
		//IP
		dto.setIpAddress(IpUtil.getIpAddr(request));
		//订单金额
		dto.setTotalAmount(orderForm.getOrderAmount());
		//商户订单号
		dto.setOutTradeNo(orderForm.getOrderSerialNumber());
		//回调地址
		dto.setNotifyUrl(wechatPaymentService.getWxPayParam().get("notifyurl") + "/wechatPay/notifyCallBack.do");
		
		boolean flag = wechatPaymentService.wechatScanCode(response, dto);
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Page list(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true, value = "start", defaultValue = "0") int start,
			@RequestParam(required = true, value = "length", defaultValue = "10") int length) {
		Page page = new Page();
		page.setStart(start);
		page.setLength(length);
		List<WechatOrderForm> list = orderService.list(WechatOrderForm.class, page);
		
		page.setData(list);
		page.setDraw(StringUtils.isBlank(request.getParameter("draw")) ? 1 : Integer.valueOf(request.getParameter("draw")) + 1);
		
		return page;
	}
	
	@RequestMapping(value="/detail")
	public ModelAndView detail(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/web/order/order_show");
		
		WechatOrderForm order = orderService.findUniqueByProperty(WechatOrderForm.class, "orderSerialNumber", request.getParameter("orderNo"));
		mav.addObject("order", order);
		
		return mav;
	}
	
	@RequestMapping("close")
	@ResponseBody
	public AjaxJson close(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson result = new AjaxJson();
		
		String id = request.getParameter("id");
		WechatOrderForm order = orderService.getEntity(WechatOrderForm.class, id);
		if (order == null) {
			result.setSuccess(false);
			result.setMsg("ID错误");
			return result;
		}
		if (order.getOrderType().equals(String.valueOf(OrderType.free.getValue()))) {
			result.setSuccess(false);
			result.setMsg("免费订单不可关闭");
			return result;
		}
		if (order.getOrderStatus().equals(String.valueOf(OrderStatus.close.getValue()))) {
			result.setSuccess(false);
			result.setMsg("订单已关闭，请勿重复操作");
			return result;
		}
		if (!order.getOrderStatus().equals(String.valueOf(OrderStatus.paid.getValue()))) {
			result.setSuccess(false);
			result.setMsg("订单未支付，不可关闭");
			return result;
		}
		
		order.setOrderStatus(String.valueOf(OrderStatus.close.getValue()));
		order.setModifyTime(new Date());
		orderService.update(order);
		
		//触发退款流程
//		wechatRefundService.wechatRefunxn(order);
		
		result.setMsg("订单关闭成功");
		return result;
	}
	
	@Autowired
	FortuneTellerInfoService fortuneService;
	
	@RequestMapping(value = "getTime")
	@ResponseBody
	public AjaxJson getTime(HttpServletRequest request) {
		String id = request.getParameter("id");
		String date = request.getParameter("date");
		AjaxJson j = new AjaxJson();
		WechatOrderForm order = orderService.getOrderByAnyId("id", id);
		
		return j;
	}
	
	@RequestMapping(value = "delay")
	public ModelAndView delay(HttpServletRequest request) {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		WechatOrderForm order = orderService.getOrderByAnyId("id", id);
		if (order == null)
			return new ModelAndView("login/login");
		
		List<String> dates = new ArrayList<String>();
		for (int i = 1; i <= 20; i++) {
			dates.add(DateUtils.formatDate(DateUtils.addDays(new Date(), i)));
		}
		
		request.setAttribute("dates", dates);
		return new ModelAndView("weixin/analyse/order/delay");
	}
	
	@RequestMapping(value = "confirmDelay")
	@ResponseBody
	public AjaxJson confirmDelay(HttpServletRequest request) {
		AjaxJson result = new AjaxJson();
		String id = request.getParameter("id");
		String serviceDate = request.getParameter("serviceDate");
		String serviceTime = request.getParameter("serviceTime");
		WechatOrderForm order = orderService.getOrderByAnyId("id", id);
		
		return result;
	}


	/**
	 * 订单信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value="orders")
	public ModelAndView wechatOrderForm(HttpServletRequest request) {
		return new ModelAndView("web/order/list");
	}
	@RequestMapping(value = "addFile")
    public ModelAndView goImplXls(HttpServletRequest request) {
		request.setAttribute("id", request.getParameter("id"));
	    return  new ModelAndView("weixin/analyse/order/recorder");
    }
	@RequestMapping(value = "uploadFile")
	public void uploadFile(WechatOrderForm wechatOrderForm, HttpServletRequest request) {
		wechatOrderForm = (WechatOrderForm) orderService.findUniqueByProperty(WechatOrderForm.class, "orderSerialNumber", request.getParameter("id"));
		
		orderService.uploadFile(wechatOrderForm, request);
	}


	@RequestMapping(value = "succ")
	@ResponseBody
	public AjaxJson succ(WechatOrderForm wechatOrderForm, HttpServletRequest request) {
		wechatOrderForm = baseService.getEntity(WechatOrderForm.class, request.getParameter("id"));
		AjaxJson j = new AjaxJson();
		if (wechatOrderForm.getOrderStatus().equals(String.valueOf(OrderStatus.freeFinished.getValue()))) {
			j.setMsg("在线录音成功");
		} else {
			j.setMsg("尚未录音");
		}
		return j;
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(value = "datagrid")
	public void datagrid(WechatOrderForm wechatOrderForm,HttpServletRequest request, HttpServletResponse response) {
//		CriteriaQuery cq = new CriteriaQuery(WechatOrderForm.class, dataGrid);
//		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wechatOrderForm, request.getParameterMap());
//		this.wechatOrderFormService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除订单信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "del")
	@ResponseBody
	public AjaxJson del(WechatOrderForm wechatOrderForm, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
//		wechatOrderForm = baseService.getEntity(WechatOrderForm.class, wechatOrderForm.getId());
//		message = "订单信息删除成功";
//		wechatOrderFormService.delete(wechatOrderForm);
//		baseService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
//		
//		j.setMsg(message);
		return j;
	}


	/**
	 * 添加订单信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public AjaxJson save(WechatOrderForm wechatOrderForm, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
//		if (StringUtil.isNotEmpty(wechatOrderForm.getId())) {
//			message = "订单信息更新成功";
//			WechatOrderForm t = wechatOrderFormService.get(WechatOrderForm.class, wechatOrderForm.getId());
//			try {
//				MyBeanUtils.copyBeanNotNull2Bean(wechatOrderForm, t);
//				
////				t.setModifyTime(new Date());
//				wechatOrderFormService.saveOrUpdate(t);
//				baseService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
//			} catch (Exception e) {
//				e.printStackTrace();
//				message = "订单信息更新失败";
//			}
//		} else {
//			message = "订单信息添加成功";
//			wechatOrderFormService.save(wechatOrderForm);
//			baseService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
//		}
//		j.setMsg(message);
		return j;
	}

	/**
	 * 订单信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "addorupdate")
	public ModelAndView addorupdate(WechatOrderForm wechatOrderForm, HttpServletRequest req) {
		if (StringUtils.isNotEmpty(wechatOrderForm.getId())) {
			wechatOrderForm = orderService.getEntity(WechatOrderForm.class, wechatOrderForm.getId());
			req.setAttribute("wechatOrderFormPage", wechatOrderForm);
		}
		return new ModelAndView("weixin/analyse/order/wechatOrderForm");
	}
	
//	@RequestMapping(value = "detail")
//	public ModelAndView detail(WechatOrderForm wechatOrderForm, HttpServletRequest req) {
//		if (StringUtils.isNotEmpty(wechatOrderForm.getId())) {
//			wechatOrderForm = wechatOrderFormService.getEntity(WechatOrderForm.class, wechatOrderForm.getId());
//			req.setAttribute("wechatOrderFormPage", wechatOrderForm);
//		}
//		return new ModelAndView("weixin/analyse/order/wechatOrderFormDetail");
//	}
}
