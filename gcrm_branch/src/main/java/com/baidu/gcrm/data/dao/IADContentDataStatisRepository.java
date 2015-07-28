package com.baidu.gcrm.data.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.data.model.ADContentStatistics;

public interface IADContentDataStatisRepository extends JpaRepository<ADContentStatistics, Long> {

    ADContentStatistics findByAdContentId(Long adContentId);

    @Modifying
    @Query(value = "insert into g_adcontent_data_statistics(ad_content_id,platform_id,site_id,channel_id,area_id,position_id,rotation_type,position_number,advertisers,number,description,industry,"
            + "customer_quote,ratio_mine,billing_model_id,total_amount,total_days) select gasc.id,gasc.product_id,gasc.site_id,gasc.channel_id,gasc.area_id,gasc.position_id,"
            + "gpos.rotation_type,gpos.position_number,gasc.advertisers,gasc.number,gasc.description,gc.industry,"
            + "gaq.customer_quote,gaq.ratio_mine,gaq.billing_model_id,case gaq.billing_model_id when 4 then gaq.daily_amount*gasc.total_days*1000 when 5 then gasc.total_days when 1 then gaq.budget/gaq.customer_quote else null end total_amount,gasc.total_days from g_advertise_solution_content gasc left join g_customer gc on "
            + "gc.customer_number=gasc.advertiser_id,g_position gpos,g_advertise_quotation "
            + "gaq where gpos.id=gasc.position_id and gaq.advertise_solution_content_id=gasc.id and gasc.id=?1", nativeQuery = true)
    public void saveAdContentStatisData(Long adContentId);

    List<ADContentStatistics> findByAdContentIdIn(Collection<Long> adContentIdCollection);
    
}
