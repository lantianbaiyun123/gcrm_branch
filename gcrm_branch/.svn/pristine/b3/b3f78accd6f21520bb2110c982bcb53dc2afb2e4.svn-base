package com.baidu.gcrm.resource.position.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.page.IPageQuery;
import com.baidu.gcrm.data.bean.PositionBean;
import com.baidu.gcrm.mail.PositionDisableContent;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionPropertyStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.resource.position.model.PositionQueryBean;
import com.baidu.gcrm.resource.position.web.vo.PositionCondition;

public class PositionRepositoryImpl implements PositionRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private IPageQuery mysqlPageQuery;
    
    
    @Override
    public long findExistsName(Long id, String i18nValue, LocaleConstants locale) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(sp.id) from g_position p,g_position sp,g_i18n i ")
            .append("where p.id=? and sp.index_str=p.index_str and sp.id != p.id")
            .append(" and concat('g_position.',sp.id)=i.key_name and i.key_value=? and i.locale=? limit 1");
        
        List<Object> paramList = new ArrayList<Object> ();
        paramList.add(id);
        paramList.add(i18nValue);
        paramList.add(locale.name());
        
        return mysqlPageQuery.executeCountQuery(entityManager, sql.toString(), paramList);
    }


    @Override
    public PositionCondition<PositionQueryBean> query(PositionCondition<PositionQueryBean> page,
            List<AdPlatformSiteRelation> relations,
            LocaleConstants locale) {
        StringBuilder sql = new StringBuilder();
        sql.append("select p.id,p.position_number,p.ad_platform_id,p.site_id,p.index_str,p.rotation_type,p.rotation_order,p.material_type,")
            .append("p.area_required,p.sales_amount,p.status,p.type,p.parent_id, ")
            .append(" p.size,p.textlink_length,p.click,p.pv,p.cpt_benchmark_price,p.guide_file_name,")
            .append("i.key_value as i18nValue,i.key_extra_value as i18nExtraValue, p.position_code")
            .append(" from g_position p,g_i18n i ")
            .append("where concat('g_position.',p.id)=i.key_name  and i.locale=? ");
        
        List<Object> paramList = new ArrayList<Object> ();
        paramList.add(locale.name());
        processCondition(page, relations, sql, paramList);
        sql.append(" order by id desc ");
        mysqlPageQuery.executePageQuery(entityManager, sql, paramList, page);
        return page;
    }
    
    private void processCondition(PositionCondition<PositionQueryBean> page,
            List<AdPlatformSiteRelation> relations,
            StringBuilder sql, List<Object> paramList) {
        
        Integer type = page.getType();
        if (type != null) {
            sql.append(" and p.type=? ");
            paramList.add(type);
        }
        
        String positionName = page.getPositionName();
        if (!PatternUtil.isBlank(positionName)) {
            sql.append(" and i.key_extra_value like ? ");
            paramList.add(new StringBuilder().append("%").append(positionName).append("%").toString());
        }
        
        String areaRequired = page.getAreaRequired();
        if (!PatternUtil.isBlank(areaRequired)) {
            sql.append(" and p.area_required like ? ");
            paramList.add(new StringBuilder().append("%").append(areaRequired).append("%").toString());
        }
        
        String positionNumber = page.getPositionNumber();
        if (!PatternUtil.isBlank(positionNumber)) {
            sql.append(" and p.position_number = ? ");
            paramList.add(positionNumber);
        }
        
        Long adPlatformId = page.getAdPlatformId();
        if (adPlatformId != null) {
            sql.append(" and p.ad_platform_id = ? ");
            paramList.add(adPlatformId);
        }
        
        Long siteId = page.getSiteId();
        if (siteId != null) {
            sql.append(" and p.site_id = ? ");
            paramList.add(siteId);
        }
        
        if (!CollectionUtils.isEmpty(relations)) {
            sql.append(" and ( ");
            int i = 0;
            for (AdPlatformSiteRelation temAdPlatformSiteRelation : relations) {
                if (i > 0) {
                    sql.append(" or ");
                }
                sql.append(" (p.ad_platform_id = ? and p.site_id = ?) ");
                paramList.add(temAdPlatformSiteRelation.getAdPlatformId());
                paramList.add(temAdPlatformSiteRelation.getSiteId());
                i++;
            }
            sql.append(" ) ");
        }
        
        Long id = null;
        Long channelId = page.getChannelId();
        Long areaId = page.getAreaId();
        Long positionId = page.getPositionId();
        boolean hasPositionCondition = false;
        StringBuilder indexConditionStr = null;
        if (channelId != null) {
            hasPositionCondition = true;
            indexConditionStr = new StringBuilder();
            indexConditionStr.append(adPlatformId)
                .append("-").append(siteId)
                .append("-").append(channelId).append("-");
                
            if (areaId != null) {
                indexConditionStr.append(areaId).append("-");
            }
            if (positionId != null) {
                id = positionId;
            }
            
        }
        if (hasPositionCondition) {
            sql.append(" and p.index_str like ? ");
            paramList.add(indexConditionStr.append("%").toString());
            
            if (id != null) {
                sql.append(" and p.id=? ");
                paramList.add(id);
            }
            
        }
    }


    @Override
    public int updatePositionProperty(Position positionProperty) {
        StringBuilder sql = new StringBuilder();
        sql.append("update Position set rotationType=?1,materialType=?2, ")
                .append("areaRequired=?3,size=?4,textlinkLength=?5,")
                .append("salesAmount=?6,pv=?7,click=?8,cptBenchmarkPrice=?9,updateTime=?10,")
                .append("updateOperator=?11,propertyStatus=?12,positionCode=?13,rotationOrder=?14 ")
                .append("where id=?15");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter(1, positionProperty.getRotationType());
        query.setParameter(2, positionProperty.getMaterialType());
        query.setParameter(3, positionProperty.getAreaRequired());
        query.setParameter(4, positionProperty.getSize());
        query.setParameter(5, positionProperty.getTextlinkLength());
        query.setParameter(6, positionProperty.getSalesAmount());
        query.setParameter(7, positionProperty.getPv());
        query.setParameter(8, positionProperty.getClick());
        query.setParameter(9, positionProperty.getCptBenchmarkPrice());
        query.setParameter(10, positionProperty.getUpdateTime());
        query.setParameter(11, positionProperty.getUpdateOperator());
        query.setParameter(12, PositionPropertyStatus.enable);
        query.setParameter(13, positionProperty.getPositionCode());
        query.setParameter(14, positionProperty.getRotationOrder());
        query.setParameter(15, positionProperty.getId());

        return query.executeUpdate();

    }

    @Override
    public int updateSubPositionPropertyWithSalesAmount(Position positionProperty) {
        StringBuilder sql = new StringBuilder();
        sql.append(" update Position set rotationType=?1,materialType=?2, ")
                .append("areaRequired=?3,size=?4,textlinkLength=?5,")
                .append("pv=?6,click=?7,cptBenchmarkPrice=?8,updateTime=?9,")
                .append("updateOperator=?10,propertyStatus=?11,salesAmount=?12 ")
                .append("where parentId=?13");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter(1, positionProperty.getRotationType());
        query.setParameter(2, positionProperty.getMaterialType());
        query.setParameter(3, positionProperty.getAreaRequired());
        query.setParameter(4, positionProperty.getSize());
        query.setParameter(5, positionProperty.getTextlinkLength());
        query.setParameter(6, positionProperty.getPv());
        query.setParameter(7, positionProperty.getClick());
        query.setParameter(8, positionProperty.getCptBenchmarkPrice());
        query.setParameter(9, positionProperty.getUpdateTime());
        query.setParameter(10, positionProperty.getUpdateOperator());
        query.setParameter(11, PositionPropertyStatus.enable);
        query.setParameter(12, positionProperty.getSalesAmount());
        query.setParameter(13, positionProperty.getId());

        return query.executeUpdate();

    }

    @Override
    public int updateSubPositionProperty(Position positionProperty) {
        StringBuilder sql = new StringBuilder();
        sql.append(" update Position set rotationType=?1,materialType=?2, ")
                .append("areaRequired=?3,size=?4,textlinkLength=?5,")
                .append("pv=?6,click=?7,cptBenchmarkPrice=?8,updateTime=?9,")
                .append("updateOperator=?10,propertyStatus=?11 ")
                .append("where parentId=?12");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter(1, positionProperty.getRotationType());
        query.setParameter(2, positionProperty.getMaterialType());
        query.setParameter(3, positionProperty.getAreaRequired());
        query.setParameter(4, positionProperty.getSize());
        query.setParameter(5, positionProperty.getTextlinkLength());
        query.setParameter(6, positionProperty.getPv());
        query.setParameter(7, positionProperty.getClick());
        query.setParameter(8, positionProperty.getCptBenchmarkPrice());
        query.setParameter(9, positionProperty.getUpdateTime());
        query.setParameter(10, positionProperty.getUpdateOperator());
        query.setParameter(11, PositionPropertyStatus.enable);
        query.setParameter(12, positionProperty.getId());

        return query.executeUpdate();

    }


    @SuppressWarnings("unchecked")
    @Override
    public List<PositionDisableContent> findDisableContent(Collection<Long> adContentIds,LocaleConstants locale) {
        StringBuilder sql = new StringBuilder();
        sql.append("select c.id,c.number,c.submit_time,u.realname,u.email,i.key_extra_value from g_advertise_solution_content c,g_user u,g_i18n i ")
        .append("where c.id in (?1) and c.create_operator=u.ucid ")
        .append("and i.key_name=concat('g_position.',c.position_id) and i.locale=?2");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter(1, adContentIds);
        query.setParameter(2, locale.name());
        List<Object[]> result = query.getResultList();
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        List<PositionDisableContent> resultList = new LinkedList<PositionDisableContent> ();
        for (Object[] temResult : result) {
            if (temResult.length < 6 ) {
                continue;
            }
            PositionDisableContent content = new PositionDisableContent();
            content.setId(Long.valueOf(temResult[0].toString()));
            content.setAdContentNumber(temResult[1].toString());
            if (temResult[2] != null) {
                content.setAdContentSubmitTime(DateUtils.getString2Date(DateUtils.YYYY_MM_DD_HH_MM_SS, temResult[2].toString()));
            }
            if (temResult[3] != null) {
                content.setOperator(temResult[3].toString());
            }
            if (temResult[4] != null) {
                Set<String> to = new HashSet<String>(1);
                to.add(temResult[4].toString());
                content.setSendTo(to);
            }
            if (temResult[5] != null) {
                content.setPositionName(temResult[5].toString());
            }
            resultList.add(content);
        }
        return resultList;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Position> findByIndexStrLikeAndStatusAndType(Position dbPosition,
            PositionStatus queryStatus,PositionType queryType) {
            
        PositionType currType = dbPosition.getType();
        StringBuilder sql = new StringBuilder();
        sql.append("from Position where  status=?2 and type=?3 ");
        if (PositionType.position != currType) {
            sql.append(" and indexStr like ?1 ");
        } else {
            sql.append(" and id = ?1 ");
        }
        Long id = dbPosition.getId();
        Query query = entityManager.createQuery(sql.toString());
        if (PositionType.position != currType) {
            query.setParameter(1, new StringBuilder()
                .append(dbPosition.getIndexStr())
                .append(id).append("-%").toString());
        } else {
            query.setParameter(1, id);
        }
        query.setParameter(2, queryStatus);
        query.setParameter(3, queryType);
        
        return query.getResultList();
        
    }
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
    public Long findPositionOperation(List<Long> positionIdList,Date operateDate){
    	if(CollectionUtils.isEmpty(positionIdList)){
    		return 0l;
    	}
    	StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
        .append(" COUNT(DISTINCT a.position_id) AS sold_amount ")
        .append(" FROM g_publish a left join g_publish_date b on a.number = b.p_number")
        .append(" WHERE DATE_FORMAT(b.actural_start,'%Y-%m-%d') <= :operateDate ")
        .append(" ANd (b.actural_end is null OR DATE_FORMAT(b.actural_end,'%Y-%m-%d') >= :operateDate)")
        .append(" AND a.position_id in (:positionIdList)");
        
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("positionIdList", positionIdList);
        query.setParameter("operateDate", operateDate);
        BigInteger result = (BigInteger)query.getSingleResult();
		if (result != null) {
			return result.longValue();
		}
        return 0l;
    }


	@Override
	@SuppressWarnings("unchecked")
	public List<Position> findListBetweenDate(Date startDate, Date endDate,
			String operateType, PositionType positionType) {
		List<Position> result = new ArrayList<Position>();
		if(StringUtils.isBlank(operateType) || positionType == null){
			return result;
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("select a from Position a WHERE a.type=:positionType");
		
		if("create".equals(operateType)){
			sb.append(" AND createTime > :startDate AND createTime < :endDate");
		}else{
			sb.append(" AND updateTime > :startDate AND updateTime < :endDate");
		}
		
		Query query = entityManager.createQuery(sb.toString());
        
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("positionType", positionType);
		
		result = (List<Position>)query.getResultList();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PositionBean> getAllPositions() {
		String sql = "select id, position_number, status, type, rotation_type,sales_amount, material_type, area_required, size, index_str from g_position";
		Query query = entityManager.createNativeQuery(sql, PositionBean.class);
		return query.getResultList();
	}
}
