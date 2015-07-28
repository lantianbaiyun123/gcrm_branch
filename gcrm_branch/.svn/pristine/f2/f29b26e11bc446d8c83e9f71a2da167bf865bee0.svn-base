<!--代理商资历-->
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<dl class="dl-horizontal baseInfo-check " >
    <dt><s:message code="costomerResource.major.partner.names"/>：</dt>
    <dd>
        <div class="row">
            <div class="col-md-3"><span class="gray">top1 </span>${qualification.parterTop1}</div>
            <div class="col-md-3"><span class="gray">top2 </span>${qualification.parterTop2}</div>
            <div class="col-md-3"><span class="gray">top3 </span>${qualification.parterTop3}</div>
        </div>
    </dd>
    <dt><dt><s:message code="costomerResource.major.customer.resources"/>：</dt>
    <dd>
        <table class="table tableBorder">
            <thead>
                <tr>
                    <th></th>
                    <th><s:message code="costomerResource.major.customer.resources.industry"/></th>
                    <th><s:message code="costomerResource.major.customer.resources.companyName"/></th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>top1</td>
                    <td>${qualification.customerResources[0].industry}</td>
                    <td>${qualification.customerResources[0].advertisersCompany1}</td>
                    <td>${qualification.customerResources[0].advertisersCompany2}</td>
                    <td>${qualification.customerResources[0].advertisersCompany3}</td>
                </tr>
                <tr>
                    <td>top2</td>
                    <td>${qualification.customerResources[1].industry}</td>
                    <td>${qualification.customerResources[1].advertisersCompany1}</td>
                    <td>${qualification.customerResources[1].advertisersCompany2}</td>
                    <td>${qualification.customerResources[1].advertisersCompany3}</td>
                </tr>
                <tr>
                    <td>top3</td>
                    <td>${qualification.customerResources[2].industry}</td>
                    <td>${qualification.customerResources[2].advertisersCompany1}</td>
                    <td>${qualification.customerResources[2].advertisersCompany2}</td>
                    <td>${qualification.customerResources[2].advertisersCompany3}</td>
                </tr>
            </tbody>
        </table>
    </dd>
    <dt><s:message code="qualification.holder.title"/>：</dt>
    <dd>${qualification.performanceHighlights}</dd>
</dl>
<script type="text/javascript">
	moduleConfig.detailAgentQualification.success = function(){
		$.gcrm.editAgentQualification();
		$.gcrm.dealCancellation("#editAgentQualification");
	};
</script>