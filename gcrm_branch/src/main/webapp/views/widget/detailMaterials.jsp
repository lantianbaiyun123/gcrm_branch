<!--资质认证材料-->
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<input type=hidden class="m_approvalStatus" name="approvalStatus" value="${approvalStatus}">
    <table id="materialsTable" class="table table-gcrm materialsTable-check">
    <thead>
        <tr>
            <th class="col-md-2"><s:message code="materials.type"/></th>
            <th class="col-md-3"><s:message code="materials.filecontent"/></th>
            <%-- <th class="col-md-1"><s:message code="materials.operation"/></th> --%>
        </tr>
    </thead>
    <tbody>
    	<c:forEach items="${attachments }" var="attachment" varStatus="status">
    	<tr>
            <td class="col-md-2"><s:message code="${attachmentTypes[attachment.type]}"/></td>
            <td class="col-md-3"><a target="_blank" href="<c:url value='/materials/download/${attachment.id}/${attachment.name}'/>">${attachment.name}</a></td>
            <%-- <td class="col-md-1"><span class="glyphicon glyphicon-remove"></span></td> --%>
        </tr>
    	</c:forEach>
    </tbody>
 </table>
 <script type="text/javascript">
	moduleConfig.detailMaterials.success = function(){
		$.gcrm.dealCancellation("#editMaterials");
		$('#detailMaterials .panel-body select').addClass("col-md-12").selectpicker({});
		if($("input[name=approvalStatus]").val()==2){
			$("body").undelegate("#editMaterials", "click").delegate("#editMaterials", "click", function(){
				//alert(GCRM.util.message('materials.can.not.edit'));
				alertModal("canNotEditModal",GCRM.util.message('materials.can.not.edit'));
			});
		}else{
        	$.gcrm.editMaterials();
		}
	};
</script>
 