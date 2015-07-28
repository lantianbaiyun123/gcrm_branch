package com.baidu.gcrm.amp.web;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.amp.model.Country;
import com.baidu.gcrm.amp.model.Subscribe;
import com.baidu.gcrm.amp.service.ICountryService;
import com.baidu.gcrm.amp.service.ISubscribeService;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.i18n.Message;


@Controller
@RequestMapping("/subscribe")
public class SubscribeAction  extends ControllerHelper{
	
	@Autowired
	ICountryService countryService;
	
	@Autowired
	ISubscribeService subscribeService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,
			@RequestParam(defaultValue="1",value="pageNum" ) int pageNum,
			@RequestParam(defaultValue="10",value="pageSize") int pageSize,
			Subscribe subscribe,Model model){
		Pageable pageable = new PageRequest(pageNum,pageSize);
		model.addAttribute("countryList",countryService.findAllCountry(Country.CountryStatus.ENABLE, currentLocale));
		model.addAttribute("page", subscribeService.findAll(subscribe, pageable));
		model.addAttribute("subscribe", subscribe);
		
		return "amp/subscribe/list";
	}
	
	@RequestMapping("/add")
	public String add(HttpServletRequest request,Model model){
		model.addAttribute("countryList",countryService.findAllCountry(Country.CountryStatus.ENABLE, currentLocale));
		return "/amp/subscribe/add";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Message save(HttpServletRequest request,Subscribe subscribe,Model model){
		
		if(subscribe.getEmail() != null && subscribe.getEmail().indexOf("@") == -1){
			subscribe.setEmail(subscribe.getEmail() + "@baidu.com");
		}
//		subscribe.setCreateUserId(SpringUtil.getCurrUserId());
//		subscribe.setLastUpdateUserId(SpringUtil.getCurrUserId());
		subscribeService.saveSubscribe(subscribe);
		return new Message(null,Message.Type.SUCCESS);
	} 
	
}
