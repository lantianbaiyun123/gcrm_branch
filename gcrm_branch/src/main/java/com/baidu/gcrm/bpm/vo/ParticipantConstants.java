package com.baidu.gcrm.bpm.vo;

/**
 * 与bpm参与人和uc角色对应的id
 *
 */
public enum ParticipantConstants {
	/** 流程发起者 */
	startUser,
	/** pm */
	pm,
	/** pm负责人 **/
	pm_leader,
	/** 变现主管 */
	cash_leader,
	/** 发起者上级 */
	starter_superior,
	/** 区域总监 */
	country_leader,
	/** 财务人员 */
	finance_manager,
	/** 法务人员 */
	law_manager,
	/** 销售 */
	sales,
	/** 国代 */
	countryAgent,
	/**销售主管*/
	sales_manager,
	/**部门总监*/
	dept_manager,
	/**国家化CFO*/
	global_cfo,
	/**直接上级*/
	direct_supervisor,
	/**国际化eco */
	global_ceo,
	/**gpm 总监 */
	gcrm_gpm_manager;
}
