#1371 资源位的排期情况
findContentByPosition.json
参数：positionId 资源位ID
url：occupation/{positionId}
返回结果中的schedules是前端显示广告内容的顺序，不能乱序
资源位的“查看投放情况”也要改成这个url，去掉“修改排期情况”功能

日历控件返回json
queryDateOccupation.json
参数：positionId, from, to, billingModelId, oldContentId(如果是方案变更或内容修改需要传这个参数)
url: occupation/queryDateOccupation

合并选择的时间段(原来的occupation/queryInsertDates替换成这个)
combinePeriods.json
参数：选择的时间段list，每个时间段有from和to, positionId, billingModelId, oldContentId
url：occupation/combinePeriods
-----------------------------------------------------------------------------------------------------------------------
#1481 上下线变更的时间控制
adSolutionContentView.json
描述：在返回的json串中增加

        "datePeriodState": [
        
            {
                "endChangeable": true,  //可以修改
                "startChangeable": false  //不可以修改
            }
            
        ]
1. datePeriodState 可以为空，此时表示每个投放时间段的开始，结束均可修改   
2. datePeriodState 不为空时，其包含对象数量一定和投放时间段个数相等，且对应表示每个投放时间段的开始，结束时间是否可以修改. 
-----------------------------------------------------------------------------------------------------------------------
#1427 物料模块调整
materialApplyDetail.json
参数：applyId 物料单ID
url:/queryMaterialApplyDetail
说明：
相对之前的json格式有如下变化：
1.将data结点下的imageList改为materialList，并放在结点下materialApply；
2.在materialApply结点下增加materialMenuList结点；
3.在materialApply结点下增加periodDescription结点；
4。在materialApply结点下增加materialMenuEnabled结点。

materialDetailInfo.json
说明：
1.在materialContentVo结点下增加materialMenuList结点；
2.在materialContentVo结点下增加materialPeriodDescription结点；
3.将imageList结点改名为materialList结点。

materialApproveInfo.json
url:material/approveview
说明：
相对之前的json格式有如下变化：
1.将data结点下的imageList改为materialList；
2.在materialApply结点下增加materialMenuList结点；
3.在materialApply结点下增加materialPeriodDescription结点。

publishDateList.json
url:/publish/findPublishDateByPublishNumber
说明：
相对之前的json格式有如下变化：
1.将data结点下的imageList改为materialList；
2.在data结点下增加materialMenuList结点；
-----------------------------------------------------------------------------------------------------------------------
#1427 物料模块调整(内容入口)
一、新增内容保存物料
  saveAdContent.json
  uri:/adsolution/content/saveAdsolutionContent
二、删除物料
  uri:/material/delete/{applyId}
----------------------------------------------------------------------------------------------------------------------
#1476 位置属性需要新增“轮播顺序”字段
1.资源位位置属性需要新增“轮播顺序”字段,添加了rotationOrder字段, random:随机轮播  sequence：顺序轮播
	updatePositionProperty.json
	uri:/position/updatePositionProperty
	
	



