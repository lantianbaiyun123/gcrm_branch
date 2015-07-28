package com.baidu.gcrm.amp.web;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.amp.model.Country;
import com.baidu.gcrm.amp.model.Country.CountryStatus;
import com.baidu.gcrm.amp.service.ICountryService;
import com.baidu.gcrm.amp.service.IPositionService;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.i18n.Message;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.resource.position.model.Position;


@Controller
@RequestMapping("/setting")
public class SettingAction extends ControllerHelper{
	
	@Autowired
	IPositionService positionService;
	
	@Autowired
	ICountryService countryService;
	
	
	
	@RequestMapping("/position/add")
	public String addPosition(HttpServletRequest request,Model model){
		model.addAttribute("lan", LocaleConstants.values());
		model.addAttribute("topPosition", positionService.findPositionByPid(Long.valueOf(0),currentLocale));
		return "amp/setting/addPosition";
		
	}
	
	@RequestMapping("/position/getSub")
	@ResponseBody
	public List<Position> getSubPosition(HttpServletRequest request,Position position,Model model){
		return positionService.findPositionByPid(position.getParentId(),currentLocale);
		
	}
	
	@RequestMapping("/position/edit")
	public String editPosition(HttpServletRequest request,Model model){
		model.addAttribute("allPositionMap", positionService.findAllPositionMap(currentLocale));
		return "amp/setting/editPosition";
		
	}
	
	@RequestMapping("/position/save")
	@ResponseBody
	public Message savePosition(HttpServletRequest request){
		
		List<LocaleVO> nameList = new ArrayList<LocaleVO> ();
		String pidStr = request.getParameter("pid");
		String paramIndexStr = request.getParameter("paramIndex");
		if(paramIndexStr != null){
			int paramIndex = Integer.parseInt(paramIndexStr);
			for(int i = 0; i < paramIndex; i++){
				String localeParamName = new StringBuilder("locale").append(i).toString();
				String nameParamName = new StringBuilder("name").append(i).toString();
				String temLocleValue = request.getParameter(localeParamName);
				String temName = request.getParameter(nameParamName);
				LocaleVO temLocaleVO = new LocaleVO(LocaleConstants.valueOf(temLocleValue),temName);
				nameList.add(temLocaleVO);
			}
		}
		Position position = new Position();
		if(pidStr != null){
			position.setParentId(Long.valueOf(pidStr));
		}
//		position.setCreateOperator(SpringUtil.getCurrUserId());
		positionService.save(position,nameList);
		
		return new Message(null,Message.Type.SUCCESS);
		
	}
	
	
	@RequestMapping(value="/country/list/{status}",method=RequestMethod.GET)
	public String listCountry(HttpServletRequest request,@PathVariable String status,Model model){
		model.addAttribute("countryList",countryService.findAllCountry(CountryStatus.valueOf(status), currentLocale));
		model.addAttribute("status", status);
		return "amp/setting/listCountry";
		
	}
	
	@RequestMapping(value="/country/update")
	public Message updateCountry(HttpServletRequest request,Country country){
		countryService.updateCountry(country);
		
		return new Message(null,Message.Type.SUCCESS);
		
	}
	
	
	
}
