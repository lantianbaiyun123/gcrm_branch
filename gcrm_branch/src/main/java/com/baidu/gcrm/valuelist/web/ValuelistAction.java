package com.baidu.gcrm.valuelist.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.baidu.gcrm.valuelist.service.IValuelistWithCacheService;
import com.baidu.gcrm.valuelist.utils.ColumnMetaData;
import com.baidu.gcrm.valuelist.utils.ITableMetaDataManger;
import com.baidu.gcrm.valuelist.utils.TableMetaData;

/**
 * 
 * @author weichengke
 *
 */
@Controller
@RequestMapping("/valuelist")
public class ValuelistAction {
	@Autowired
	private IValuelistWithCacheService valuelistService;
	
	@Autowired
	private ITableMetaDataManger tableManager;
	
	
	@RequestMapping("/getAllFromCache")
	public ModelAndView gotoGetAllFromCacheIndex(){
		
		ModelAndView mv = new ModelAndView("/valuelist/getAll");
		
		//默认加载第一个表的数据
		List<String> tableNames = tableManager.getTableNames();
		String tableName;
		if(tableNames!=null){
			tableName = tableNames.get(0);
		}else{
			return mv;
		}
		
		TableMetaData table = tableManager.getTableMetaData(tableName);
		if(null != table){
			List<ColumnMetaData> tableInfo = table.getColumns();
			mv.addObject("tableInfo", tableInfo);
		}
		
		List<Map<String,String>> datas = valuelistService.getAllFromCache(tableName);
		
		mv.addObject("tableNames", tableNames);
		
		mv.addObject("datas", datas);
		
		return mv;
	}
	
	@RequestMapping("/getAllFromCache/{tableName}")
	public ModelAndView gotoGetAllFromCache(@PathVariable("tableName")String tableName){
		
		ModelAndView mv = new ModelAndView("/valuelist/getAll");
		
		TableMetaData table = tableManager.getTableMetaData(tableName);
		if(null != table){
			List<ColumnMetaData> tableInfo = table.getColumns();
			mv.addObject("tableInfo", tableInfo);
		}else{
			mv.addObject("message", tableName+"表不存在！");
		}
		
		List<Map<String,String>> datas = valuelistService.getAllFromCache(tableName);
		List<String> tableNames = tableManager.getTableNames();
		
		mv.addObject("tableNames", tableNames);
		mv.addObject("datas", datas);
		
		return mv;
	}
	
	@RequestMapping("/doRefresh/{tableName}")
	public ModelAndView doRefresh(@PathVariable("tableName")String tableName){
		ModelAndView mv = new ModelAndView("/valuelist/getAll");
		
		TableMetaData table = tableManager.getTableMetaData(tableName);
		if(null!=table){
			List<ColumnMetaData> tableInfo = table.getColumns();
			mv.addObject("tableInfo", tableInfo);
		}
		
		valuelistService.refreshCache(table);
		List<Map<String,String>> datas = valuelistService.getAllFromCache(tableName);
		List<String> tableNames = tableManager.getTableNames();
		
		mv.addObject("tableNames", tableNames);	
		mv.addObject("datas", datas);
		
		return mv;
	}
	
	@RequestMapping("/gotoUpdate/{tableName}/{dataId}")
	public ModelAndView gotoUpdate(@PathVariable("tableName")String tableName,
				@PathVariable("dataId")String dataId,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/valuelist/update");
		
		TableMetaData table = tableManager.getTableMetaData(tableName);
		if(null!=table){
			List<ColumnMetaData> tableInfo = table.getColumns();
			mv.addObject("tableInfo", tableInfo);
		}
		
		Map<String,String> datas = valuelistService.get(tableName,"id",dataId);
		String cacheKey = tableManager.getRowKey(tableName, datas);
			
		mv.addObject("data", datas);
		mv.addObject("tableName", tableName);
		mv.addObject("dataId",dataId);
		mv.addObject("message", request.getParameter("message"));
		mv.addObject("cacheKey",cacheKey);
		
		return mv;
	}
	
	@RequestMapping("/doUpdate")
	public ModelAndView doUpdate(Model model ,HttpServletRequest request){
		String tableName = request.getParameter("tableName");
		String dataId = request.getParameter("dataId");
		String cacheKey = request.getParameter("cacheKey");
		
		ModelAndView mv = new ModelAndView("redirect:/valuelist/getAllFromCache/"+tableName);
		Map<String,String> params = new HashMap<String, String>();
		Enumeration<String> enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()){
			String paramName = enumeration.nextElement();
			if(paramName.endsWith("_tlv")){//以_tlv结束的为要保存的值
				params.put(paramName.substring(0,paramName.length()-4), request.getParameter(paramName));
				System.out.println(paramName.substring(0,paramName.length()-4));
			}
		}
		
		try{
			valuelistService.update(tableName, "id",dataId,params,cacheKey);
		}catch(Exception e){
			mv.setViewName("redirect:/valuelist/gotoUpdate/"+tableName+"/"+dataId);
			mv.addObject("message", "can not change the id attribute!");
		}

		return mv;
	}
	
	@RequestMapping("/gotoAdd/{tableName}")
	public ModelAndView gotoAdd(@PathVariable("tableName")String tableName,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/valuelist/add");
		
		TableMetaData table = tableManager.getTableMetaData(tableName);
		if(null!=table){
			List<ColumnMetaData> tableInfo = table.getColumns();
			mv.addObject("tableInfo", tableInfo);
		}
				
		mv.addObject("tableName", tableName);
		mv.addObject("message", request.getParameter("message"));
		
		return mv;
	}
	
	@RequestMapping("/doAdd")
	public ModelAndView doAdd(Model model ,HttpServletRequest request){
		String tableName = request.getParameter("tableName");
		ModelAndView mv = new ModelAndView("redirect:/valuelist/getAllFromCache/"+tableName);
		
		Map<String,String> params = new HashMap<String, String>();
		Enumeration<String> enumeration = request.getParameterNames();
		
		if(enumeration==null) return mv;
		
		while(enumeration.hasMoreElements()){
			String paramName = enumeration.nextElement();
			if(paramName.endsWith("_tlv")){//以_tlv结束的为要保存的值
				params.put(paramName.substring(0,paramName.length()-4), request.getParameter(paramName));
				System.out.println(paramName.substring(0,paramName.length()-4));
			}
		}
		
		valuelistService.save(tableName,params);
		
		return mv;
	}
	
	@RequestMapping("/delete/{tableName}/{dataId}")
	public ModelAndView doDelete(@PathVariable("tableName")String tableName,
			@PathVariable("dataId")String dataId){
		ModelAndView mv = new ModelAndView("redirect:/valuelist/getAllFromCache/"+tableName);
		
		valuelistService.delete(tableName, "id", dataId);
		
		return mv;
	}
}
