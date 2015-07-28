<!--资质认证材料-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<input type=hidden name="approvalStatus" value="${approvalStatus}">
<div class="text-center">
    <div class="jumbotron" style="background-color:#fff;">
        <P><s:message code="empty.text"/></p>
        <button type="button" class="btn btn-success" id="detailMaterialsEmpty"><span class="glyphicon glyphicon-plus"></span> <s:message code="empty.add"/></button>
    </div>
</div>

<script  src="/gcrm/resources/js/detailMaterialsEmpty.js" type="text/javascript"></script>
<script type="text/javascript">
	moduleConfig.detailMaterials.success = function(){
		//detailMaterialsEmpty.js代码被拷贝至此。
		$("#detailMaterials div.panel-heading").hide();
		$("#detailMaterials").undelegate("#detailMaterialsEmpty", "click").delegate("#detailMaterialsEmpty","click",function(e){
			
			if($("input[name=approvalStatus]").val()==2){
				//alert(GCRM.util.message('materials.can.not.edit'));
				alertModal("canNotEditModal",GCRM.util.message('materials.can.not.edit'));
				return;
			}
			
			try{
				$.gcrm.loadModule({
				    container:$("#detailMaterials").find(".panel-body"),
				    url: "../materials/gotoEditMaterials/"+$("#saveMaterials").attr("customerNumber"),
				    js:["/gcrm/resources/js/detailMaterials.js"],
				    success:function(){
				       	$("#detailMaterials select").addClass("col-md-12").selectpicker({});
				       	$.gcrm.editMaterials();
				       	$("#detailMaterials div.panel-heading #editMaterials:eq(0)").click();
						$("#detailMaterials div.panel-heading").show();
						lockTab("detailMaterials");
						
						/*禁用提交广告方案按钮*/
						disabledSubmitAd();
				    }
				});
				e.stopPropagation();
			}catch(e){
				console.log(e);
			}
		});
		$.gcrm.dealCancellation("#detailMaterialsEmpty");
	};
</script>