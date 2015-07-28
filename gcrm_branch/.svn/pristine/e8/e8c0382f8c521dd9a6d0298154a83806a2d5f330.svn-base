package com.baidu.gcrm.amp.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baidu.gcrm.amp.model.Log;
import com.baidu.gcrm.amp.service.ILogService;


@Controller
@RequestMapping("/log")
public class LogAction {
	
	@Autowired
	ILogService logService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model){
		Pageable page = new PageRequest(0,10);
		Page<Log> logPage = logService.findAll(null, page);
		model.addAttribute("page", logPage);
		return "amp/log/list";
		
	}
	
	
}
