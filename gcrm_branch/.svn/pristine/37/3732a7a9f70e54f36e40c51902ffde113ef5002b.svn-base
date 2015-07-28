package com.baidu.gcrm.resource.position.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation.AdPlatformSiteRelationStatus;
import com.baidu.gcrm.resource.position.model.IPositionModel;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionPropertyStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.resource.position.model.PositionQueryBean;
import com.baidu.gcrm.resource.position.web.vo.PositionCondition;
import com.baidu.gcrm.resource.position.web.vo.PositionVO;

public interface IPositionService {

    List<PositionVO> findI18nByPositionIds(List<Long> ids, LocaleConstants locale);

    List<PositionVO> findChannelBySiteIdAndStatus(Long adPlatformId, Long siteId, LocaleConstants locale,
            PositionStatus status);

    List<PositionVO> findByParentIdAndStatus(Long parentId, LocaleConstants locale, PositionStatus status);

    List<Position> findByIndexStrAndType(Long id, String indexStr, PositionType type);

    List<Position> findByIndexStrLikeAndTypeAndPropertyStatus(Long id, String indexStr, PositionType type,
            PositionPropertyStatus propertyStatus);

    List<Position> findByIndexStrLikeAndTypeAndStatus(Long id, String indexStr, PositionType type, PositionStatus status);

    List<Position> findByParentId(Long parentId);

    List<Position> findByPositionNumber(String positionNumber, PositionType type);

    List<Position> findAll();

    PositionVO findPositionVOById(Long id);

    Position findById(Long id);

    List<Position> findByIds(Collection<Long> ids);

    PositionCondition<PositionQueryBean> query(PositionCondition<PositionQueryBean> page,
            List<AdPlatformSiteRelation> relations, LocaleConstants locale);

    PositionCondition<PositionQueryBean> queryListTree(PositionCondition<PositionQueryBean> page, LocaleConstants locale);

    Position savePositionAndI18nInfo(Position position, boolean isImport) throws CRMBaseException;

    Position savePosition(Position position);

    int updatePositionProperty(Position currPosition, Position dbPosition);

    void updatePositionName(PositionVO positionVO);

    void updateStatusByAdPlatformId(Long adPlatformId, Date updateTime, Long updateOpreator, PositionStatus status);

    Position updatePositionStatus(Position updatePosition, Position dbPosition, List<Position> occupationPositionList);

    Integer queryMaxSalesAmount(Long id);

    Position queryPreSaveTree(Long adPlatformId, Long siteId);

    long findExistsI18nName(Long id, String value, LocaleConstants locale);

    List<Position> findByAdPlatformAndStatusAndType(Long adPlatform, AdPlatformSiteRelationStatus relationStatus,
            PositionStatus status, PositionType type);

    void releaseOccupationByPositions(List<Position> positionList);

    List<Position> findOperateOccupationPosition(Position updatePosition, Position dbPosition);

    boolean hasProperty(IPositionModel positionModel);

    /**
     * 功能描述： 查询指定日期，指定的位置的投放情况 创建人：yudajun 创建时间：2014-5-17 下午5:50:14 修改人：yudajun 修改时间：2014-5-17 下午5:50:14 修改备注： 参数： @param
     * positionIdList 参数： @param operateDate 参数： @return
     * 
     * @version
     */
    public Long findPositionOperation(List<Long> positionIdList, Date operateDate);

    /**
     * 功能描述： 查询一段时间类，指定操作类型的位置 创建人：yudajun 创建时间：2014-5-20 上午11:08:23 修改人：yudajun 修改时间：2014-5-20 上午11:08:23 修改备注： 参数： @param
     * startDate 参数： @param endDate 参数： @param operateType 参数： @return
     * 
     * @version
     */
    public List<Position> findListBetweenDate(Date startDate, Date endDate, String operateType,
            PositionType positionType);

    List<Position> findByType(PositionType type);

    List<com.baidu.gcrm.data.bean.PositionBean> getAllPositions();

    String generatePositionCode(String areaRequired, String positionNumber);

    List<Position> findByStatusAndType(PositionStatus status, PositionType type);
    
    /**
     * 功能：初始化资源位代码
     */
    void initPositionCode();
    
    /**
     * 获取禁用状态的位置的id列表
     * @return 禁用状态的位置，key是positionId，value是position
     */
    Map<Long, Position> findDisabledPositions();
}
