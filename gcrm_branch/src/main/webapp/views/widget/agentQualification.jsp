<!--代理商资历-->
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="alert alert-info"><s:message code="qualification.tip"/></div>
<div class="form-group">
	<input type="hidden" name="customer.id" value="${customerId}">
	<input type="hidden" name="qualification.id" value="${qualification.id}">
	<input type="hidden" name="qualification.customerResources[0].id" value="${customerBean.qualification.customerResources[0].id}">
	<input type="hidden" name="qualification.customerResources[1].id" value="${customerBean.qualification.customerResources[1].id}">
	<input type="hidden" name="qualification.customerResources[2].id" value="${customerBean.qualification.customerResources[2].id}">
    <label class="control-label col-md-2"><s:message code="costomerResource.major.partner.names"/>：</label>
    <div class="col-md-3">
        <div class="input-group">
            <span class="input-group-addon">top1</span>
            <input type="text" class="form-control" placeholder="" id="qualification.parterTop1" name="qualification.parterTop1" value="${customerBean.qualification.parterTop1}">
        </div> 
        <div class="help-block"><s:message code="${errors['qualification.parterTop1']}"/></div>
    </div>
    <div class="col-md-3">
        <div class="input-group">
            <span class="input-group-addon">top2</span>
            <input type="text" class="form-control" placeholder="" name="qualification.parterTop2" value="${customerBean.qualification.parterTop2}"> 
        </div>
        <div class="help-block"><s:message code="${errors['qualification.parterTop2']}"/></div>
    </div>
    <div class="col-md-3">
        <div class="input-group">
            <span class="input-group-addon">top3</span>
            <input type="text" class="form-control" placeholder="" name="qualification.parterTop3" value="${customerBean.qualification.parterTop3}">
        </div>
        <div class="help-block"><s:message code="${errors['qualification.parterTop3']}"/></div>
    </div>
</div>
<div class="form-group">
    <label class="control-label col-md-2"><s:message code="costomerResource.major.customer.resources"/>：</label>
    <div class="col-md-9">
        <table class="table">
            <thead>
                <tr>
                    <th><s:message code="costomerResource.major.customer.resources.index"/></th>
                    <th><s:message code="costomerResource.major.customer.resources.industry"/></th>
                    <th class="col-xs-1"><s:message code="costomerResource.major.customer.resources.companyName"/></th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="col-md-1">top1</td>
                    <td class="col-md-2">
                    	<input type="text" class="form-control" placeholder="" name="qualification.customerResources[0].industry" value="${customerBean.qualification.customerResources[0].industry}">
                    	<div class="help-block"><s:message code="${errors['qualification.customerResources[0].industry']}"/></div>
                    </td>
                    <td class="col-md-3">
                    	<input type="text" class="form-control" placeholder="" name="qualification.customerResources[0].advertisersCompany1" value="${customerBean.qualification.customerResources[0].advertisersCompany1}">
                   		<div class="help-block"><s:message code="${errors['qualification.customerResources[0].advertisersCompany1']}"/></div>
                    </td>
                    <td class="col-md-2">
                    	<input type="text" class="form-control" placeholder="" name="qualification.customerResources[0].advertisersCompany2" value="${customerBean.qualification.customerResources[0].advertisersCompany2}">
                   		<div class="help-block"><s:message code="${errors['qualification.customerResources[0].advertisersCompany2']}"/></div>
                    </td>
                    <td class="col-md-2">
                    	<input type="text" class="form-control" placeholder="" name="qualification.customerResources[0].advertisersCompany3" value="${customerBean.qualification.customerResources[0].advertisersCompany3}">
                    	<div class="help-block"><s:message code="${errors['qualification.customerResources[0].advertisersCompany3']}"/></div>
                    </td>
                </tr>
                <tr>
                    <td class="col-md-1">top2</td>
                    <td class="col-md-2">
                    	<input type="text" class="form-control" placeholder="" name="qualification.customerResources[1].industry" value="${customerBean.qualification.customerResources[1].industry}">
                    	<div class="help-block"><s:message code="${errors['qualification.customerResources[1].industry']}"/></div>
                    </td>
                    <td class="col-md-3">
                    	<input type="text" class="form-control" placeholder="" name="qualification.customerResources[1].advertisersCompany1" value="${customerBean.qualification.customerResources[1].advertisersCompany1}">
                   		<div class="help-block"><s:message code="${errors['qualification.customerResources[1].advertisersCompany1']}"/></div>
                    </td>
                    <td class="col-md-2">
                    	<input type="text" class="form-control" placeholder="" name="qualification.customerResources[1].advertisersCompany2" value="${customerBean.qualification.customerResources[1].advertisersCompany2}">
                   		<div class="help-block"><s:message code="${errors['qualification.customerResources[1].advertisersCompany2']}"/></div>
                    </td>
                    <td class="col-md-2">
                    	<input type="text" class="form-control" placeholder="" name="qualification.customerResources[1].advertisersCompany3" value="${customerBean.qualification.customerResources[1].advertisersCompany3}">
                    	<div class="help-block"><s:message code="${errors['qualification.customerResources[1].advertisersCompany3']}"/></div>
                    </td>
                </tr>
                <tr>
                    <td class="col-md-1">top3</td>
                    <td class="col-md-2">
                    	<input type="text" class="form-control" placeholder="" name="qualification.customerResources[2].industry" value="${customerBean.qualification.customerResources[2].industry}">
                    	<div class="help-block"><s:message code="${errors['qualification.customerResources[2].industry']}"/></div>
                    </td>
                    <td class="col-md-3">
                    	<input type="text" class="form-control" placeholder="" name="qualification.customerResources[2].advertisersCompany1" value="${customerBean.qualification.customerResources[2].advertisersCompany1}">
                    	<div class="help-block"><s:message code="${errors['qualification.customerResources[2].advertisersCompany1']}"/></div>
                    </td>
                    <td class="col-md-2">
                    	<input type="text" class="form-control" placeholder="" name="qualification.customerResources[2].advertisersCompany2" value="${customerBean.qualification.customerResources[2].advertisersCompany2}">
                    	<div class="help-block"><s:message code="${errors['qualification.customerResources[2].advertisersCompany2']}"/></div>
                    </td>
                    <td class="col-md-2">
                    	<input type="text" class="form-control" placeholder="" name="qualification.customerResources[2].advertisersCompany3" value="${customerBean.qualification.customerResources[2].advertisersCompany3}">
                    	<div class="help-block"><s:message code="${errors['qualification.customerResources[2].advertisersCompany3']}"/></div>
                    </td>
                </tr>
            </tbody>
         </table>
    </div>
</div>
<div class="form-group">
    <label class="control-label col-md-2"><s:message code="qualification.holder.title"/>：</label>
    <div class="col-md-9">
        <textarea class="form-control" rows="3" cols="30" placeholder="" name="qualification.performanceHighlights"  >${customerBean.qualification.performanceHighlights}</textarea>
        <div class="help-block"><s:message code="${errors['qualification.performanceHighlights']}"/></div>
    </div>
</div><!--form-group-->
<script type="text/javascript">
	moduleConfig.detailAgentQualification.success = function(){
		$('#editAgentQualification .panel-body select').addClass("col-md-12").selectpicker({});
		$.gcrm.editAgentQualification();
	};
</script>