<!--代理商资历-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<div class="text-center">
    <div class="jumbotron" style="background-color:#fff;">
        <P><s:message code="empty.text"/></p>
        <button type="button" class="btn btn-success" id="editAgentQualificationEmpty"><span class="glyphicon glyphicon-plus"></span> <s:message code="empty.add"/></button>
    </div>
</div>
<script type="text/javascript">
	moduleConfig.detailAgentQualification.success = function(){
		$("#detailAgentQualification div.panel-heading").hide();
		$("#detailAgentQualification").undelegate("#editAgentQualificationEmpty","click").delegate("#editAgentQualificationEmpty","click",function(e){
			try{
				$.gcrm.loadModule({
				    container:$("#detailAgentQualification").find(".panel-body"),
				    url: "../qualification/gotoEditQualification/"+$("#editAgentQualification").attr("customerNumber"),
				    js:["/gcrm/resources/js/detailAgentQualification.js"],
				    success:function(){
				       	$("#detailAgentQualification select").addClass("col-md-12").selectpicker({});
				       	$.gcrm.editAgentQualification();
				       	$("#detailAgentQualification div.panel-heading #editAgentQualification:eq(0)").click();
						$("#detailAgentQualification div.panel-heading").show();
						lockTab("detailAgentQualification");
						
						/*禁用提交广告方案按钮*/
						disabledSubmitAd();
				    }
				});
				
				e.stopPropagation();
			}catch(e){
				console.log(e);
			}
		});
		$.gcrm.dealCancellation("#editAgentQualificationEmpty");
		$("#editAgentQualification").hide();
	};
</script>