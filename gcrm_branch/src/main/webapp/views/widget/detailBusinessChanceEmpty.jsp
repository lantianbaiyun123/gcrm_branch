<!--业务机会-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<div class="text-center">
    <div class="jumbotron" style="background-color:#fff;">
        <P><s:message code="empty.text"/></p>
        <button type="button" class="btn btn-success" id="addBusinessChance"><span class="glyphicon glyphicon-plus"></span> <s:message code="empty.add"/></button>
    </div>
</div>
<script type="text/javascript">
	moduleConfig.detailBusinessChance.success = function(){
		$("#editBusinessChance").hide();
		$("#BusinessChanceSubmitBox").addClass("hide");
		
		$("#addBusinessChance").click(function(e){
		    try{
		        var customerNumber = $("#saveBusinessChance").attr("customerNumber");
		        var $el = $("<form id='businessChance' class='form-horizontal' method='POST' action='opportunity/preUpdateOpportunity?'></form>");
		       	$("#J_businessChanceContainer").empty().append($el);
				var id="detailBusinessChance";
				$.gcrm.loadModule({
		        	moduleId :id,
		            container:$("#"+moduleConfig[id].containerId).find("form"),
		            url: GCRM.constants.CONTEXT+"opportunity/preUpdateOpportunity/"+customerNumber || moduleConfig[id].url,
		            success:function(){
		             	$("#BusinessChanceSubmitBox").removeClass("hide");
		             	$("a[href='#detailBusinessChance']:eq(0)").attr("data-url","");
		             	lockTab("detailBusinessChance");
						moduleConfig.detailBusinessChance.success();
						
						/*禁用提交广告方案按钮*/
						disabledSubmitAd();
		            }
			    });
		        
		        e.stopPropagation();
		    }catch(e){
		        console.log(e);
		    }
		});
		$.gcrm.dealCancellation("#addBusinessChance,#detailBusinessChance .panel-heading");
	};
</script>