package com.baidu.gcrm.amp.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.amp.dao.IPositionDao;
import com.baidu.gcrm.amp.service.IPositionService;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.resource.position.model.Position;

@Service
public class PositionServiceImpl1 implements IPositionService {
	
	@Autowired
	IPositionDao positionDao;
	
	@Autowired
	I18nKVService i18nService;
	
	@Override
	public void save(Position position) {
		positionDao.save(position);
	}

	@Override
	public List<Position> findPositionByPid(Long pid,LocaleConstants locale) {
		List<Position> positionList = positionDao.findByParentId(pid);
		processI18n(positionList,locale);
		
		return positionList;
	}
	
	

	@Override
	public void save(Position position, List<LocaleVO> nameList) {
		positionDao.save(position);
		i18nService.save(position.getClass(), position.getId(), nameList);
		
	}

	@Override
	public Map<Long,List<Position>> findAllPositionMap(LocaleConstants locale) {
//		List<Position> allPositions = positionDao.findByStatus(PositionStatus.ENABLE);
		return getPositionMap(null,null);
	}
	
	private void processI18n(List<Position> positionList,LocaleConstants locale){
		if(positionList != null){
			for(Position temPosition : positionList){
				i18nService.fillI18nInfo(temPosition, locale);
			}
		}
	}
	
	
	private Map<Long,List<Position>> getPositionMap(List<Position> positionList,LocaleConstants locale){
		if(positionList == null){
			return Collections.<Long,List<Position>>emptyMap();
		}
		
		Map<Long,List<Position>> dataMap = new HashMap<Long,List<Position>>();
		for(Position temPosition : positionList){
			i18nService.fillI18nInfo(temPosition, locale);
			Long pid = temPosition.getParentId();
			List<Position> existsList = dataMap.get(pid);
			if(existsList == null){
				existsList = new ArrayList<Position> ();
				dataMap.put(pid, existsList);
			}
			
			existsList.add(temPosition);
		}
			
		return dataMap;
		
	}
	
}
