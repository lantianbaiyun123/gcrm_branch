package com.baidu.gcrm.resource.position.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.mail.PositionDisableContent;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.PositionQueryBean;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.resource.position.web.vo.PositionCondition;

public interface PositionRepositoryCustom {
    
    long findExistsName(Long id, String i18nValue, LocaleConstants locale);
    
    PositionCondition<PositionQueryBean> query(PositionCondition<PositionQueryBean> page,
            List<AdPlatformSiteRelation> relations, LocaleConstants locale);
    
    int updatePositionProperty(Position positionProperty);
    
    int updateSubPositionPropertyWithSalesAmount(Position positionProperty);
    
    int updateSubPositionProperty(Position positionProperty);
    
    List<PositionDisableContent> findDisableContent(Collection<Long> adContentIds, LocaleConstants locale);
    
    List<Position> findByIndexStrLikeAndStatusAndType(Position dbPosition,
            PositionStatus queryStatus,PositionType queryType);
    /**
     * 功能描述：   查询指定日期，指定的位置的投放情况
     * 创建人：yudajun    
     * 创建时间：2014-5-17 下午5:50:14   
     * 修改人：yudajun
     * 修改时间：2014-5-17 下午5:50:14   
     * 修改备注：   
     * 参数： @param positionIdList
     * 参数： @param operateDate
     * 参数： @return
     * @version
      */
     public Long findPositionOperation(List<Long> positionIdList,Date operateDate);
     
     /**
     * 功能描述：   查询一段时间类，指定操作类型的位置
     * 创建人：yudajun    
     * 创建时间：2014-5-20 上午11:08:23   
     * 修改人：yudajun
     * 修改时间：2014-5-20 上午11:08:23   
     * 修改备注：   
     * 参数： @param startDate
     * 参数： @param endDate
     * 参数： @param operateType
     * 参数： @return
     * @version
      */
     public List<Position> findListBetweenDate(Date startDate,Date endDate,String operateType,PositionType positionType);
     
     List<com.baidu.gcrm.data.bean.PositionBean> getAllPositions();
}
