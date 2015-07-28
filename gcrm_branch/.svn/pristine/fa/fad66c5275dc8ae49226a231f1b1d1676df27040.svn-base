#104-广告方案_基础信息添加
contractSuggest.json  所属合同suggest返回
customersSuggest.json 客户基本信息suggest返回
queryContract.json    加载合同的信息
queryCustomer.json    加载客户的基本信息
preSave.json 保存、暂存、编辑正常情况下都可用这个格式
#154 广告方案_报价情况与投放量 
billModels.json  获取计费方式
quote262.json    获取刊例价
quote261.json    获取分成比例
quotationUpdate.json 提交报价情况

#230 审批信息显示 adApprovalInfo.json  

广告投放时间
1. DateStatusByFixedPositionIdAndDateFromTo.json 获取非轮播位置指定日期的日历中每天的状态
参数： long positionId, DatePeriod period
DatePeriod {Date from, Date to}
返回的部分说明：
1)非轮播位每天的状态可能是空闲idle，竞价bidding（显示竞价客户数biddingcount）、预定reserved、确认confirmed、锁定locked
2)位置是否为轮播rotation，true轮播，false非轮播

2. DateStatusByFixedRotationIdAndDateFromTo.json 获取轮播位置指定日期的日历中每天的状态
参数： long positionId, DatePeriod period
DatePeriod {Date from, Date to}
返回的部分说明：
1)轮播位每天的状态可能是空闲idle或busy，busy需要显示（busycount/totalcount）
2)totalcount

3. insertDateByDatePeriod.json 根据多个投放时间段返回插单日期，并合并重叠、相邻时间段
参数：long positionId, List<DatePeriod> periods
DatePeriod {Date from, Date to}
返回的部分说明：
1)datePeriod 合并后的投放时间段列表
2)insertDate 插单日期
3)totalDays 投放时间段内总天数，默认插单日期全选状态

#271竞价排期-提交人等页面基本信息
viewScedule.json 提交人等页面基本信息

#273排期单-列表
scedule.json 列表 

#318 方案详情页
adDetailInfo.json