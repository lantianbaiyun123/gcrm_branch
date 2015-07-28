package com.baidu.gcrm.amp.service;

import java.util.List;
import java.util.Map;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.resource.position.model.Position;

public interface IPositionService {

	void save(Position position);
	
	void save(Position position,List<LocaleVO> nameList);
	
	List<Position> findPositionByPid(Long pid,LocaleConstants locale);
	
	Map<Long,List<Position>> findAllPositionMap(LocaleConstants locale);
}
