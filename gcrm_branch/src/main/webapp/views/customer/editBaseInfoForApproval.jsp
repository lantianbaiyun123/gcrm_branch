<!--基本信息-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<fis:div>
<input type="hidden" id="customerId" name="customer.id" value="${customerView.customer.id}" >
<input type="hidden" name="customer.approvalStatus" value="${customerView.customer.approvalStatus}" >
<div class="form-group">
    <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="customer.title.companyName"/>：</label>
    <span class="control-label col-md-8" style="text-align:left;">${customerView.customer.companyName}</span>
</div>


<div class="form-group">
    <label class="control-label col-md-2" for="customer.companySize"><s:message code="customer.title.companySize"/>：</label>
    <span class="control-label col-md-10" style="text-align:left;"><s:message code="customer.companySize.${customerView.companySize}"/></span>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="customer.businessType"><span class="txt-impt">*</span><s:message code="customer.title.businessType"/>：</label>
    <span class="control-label col-md-10" style="text-align:left;"><s:message code="customer.type.${customerView.customerType}"/></span>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="registerTime"><span class="txt-impt">*</span><s:message code="customer.title.registerTime"/>：</label>
     <span class="control-label col-md-10" style="text-align:left;">
     	<fmt:formatDate value="${customerView.customer.registerTime}" pattern="dd-MM-yyyy" />
     	<input type="hidden" name="customer.registerTime"  value="${customerView.customer.registerTime}"/>
     </span>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="customer.businessLicense"><span class="txt-impt">*</span><s:message code="customer.title.businessLicense"/>：</label>
     <span class="control-label col-md-8" style="text-align:left;">${customerView.customer.businessLicense}</span>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="customer.belongSales"><span class="txt-impt">*</span><s:message code="customer.title.belongSales"/>：</label>
    <div class="col-md-3">
        <input id="belongSalesName" type="text"  class="form-control" class="span2" value="${customerView.belongSales.name}" placeholder='<s:message code="customer.holder.belongSales"/>'/>
      	<input id="belongSales" name="customer.belongSales" class="form-control span2 validate" value="${customerView.belongSales.ucid}" style="display:none;" />
    </div>
    <div class="col-md-3 help-block"><s:message code="${errors['customer.belongSales']}"/></div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="currencyType"><s:message code="customer.title.currencyType"/>：</label>
    <div class="col-md-3">
        <div class="row">
            <div class="col-md-4">
                 <select class="form-control col-mk" name="customer.currencyType" >
		            <c:forEach	items="${currencyTypes}" var="currencyType" varStatus="currencyTypeStauts">
		            	<option value="${currencyType.id}" 
		            		<c:if test="${ customerView.customer.currencyType == currencyType.id && !empty customerView.customer.registerCapital }">
		            			selected
		            		</c:if>
		            		<c:if test="${ currencyType.id == '1'&& empty customerView.customer.registerCapital }">
		            			selected
		            		</c:if>
		            		> ${currencyType.i18nName}</option> 
		            </c:forEach>
		        </select>
            </div> 
            <div class="col-md-8">
                <input id="registerCapital" name="customer.registerCapital" value="${customerView.customer.registerCapital}" type="text" class="form-control" class="form-control">
            </div>
        </div>
    </div> 
    <div class="col-md-3 help-block"><s:message code="${errors['customer.registerCapital']}"/></div>  
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="customer.belongManager"><s:message code="customer.title.belongManager"/>：</label>
    <div class="col-md-3">
        <input type="text" readonly="readonly"  class="form-control" id="belongManager" value="${customerView.customer.belongManager}" name="customer.belongManager">
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="customer.country"><span class="txt-impt">*</span><s:message code="customer.title.country"/>：</label>
    <span class="control-label col-md-10" style="text-align:left;">${customerView.country.i18nName}</span>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="customer.businessScope"><s:message code="customer.title.businessScope"/>：</label>
    <span class="control-label col-md-10" style="text-align:left;">${customerView.customer.businessScope}</span>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="customer.address"><span class="txt-impt">*</span><s:message code="customer.title.address"/>：</label>
    <div class="col-md-3">
        <input data-required type="text" class="form-control" id="address" value="${customerView.customer.address}" name="customer.address" placeholder='<s:message code="customer.holder.address"/>'>
    </div>
    <div class="col-md-3 help-block"><s:message code="${errors['customer.address']}"/></div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="customer.url"><span class="txt-impt">*</span><s:message code="customer.title.url"/>：</label>
    <span class="control-label col-md-10" style="text-align:left;">
    	<a href="${customerView.customer.url}" target="_blank">${customerView.customer.url}</a>
    </span> 
</div>

<div class="form-group">
    <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="customer.title.type"/>：</label>
    <div class="col-md-3">
      <c:if test="${fn:contains(customerView.customer.type,'0')}">
		<s:message code="customer.title.type.sale"/>
	  </c:if>
	  <c:if test="${fn:contains(customerView.customer.type,'1')}">
		<s:message code="customer.title.type.cash"/>
	  </c:if>
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2"><s:message code="customer.title.description"/>：</label>
    <div class="col-md-5">
        <textarea class="form-control" name="customer.description" rows="3" cols="30" placeholder='<s:message code="customer.holder.description"/>'>${customerView.customer.description}</textarea>
    </div>
</div>

<c:choose>
  	<c:when test="${customerView.customerType == 'offline'}">
  	<div class="form-group">
	    <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="customer.title.agentType"/>：</label>
	    <div class="col-md-3">
	      <span class="control-label col-md-10" style="text-align:left;"><s:message code="customer.agentType.${customerView.agentType}" /></span>
	    </div>
	</div>
	
	<div class="form-group">
	    <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="customer.title.agentRegional"/>：</label>
	    <div class="col-md-3">
	      <span class="control-label col-md-10" style="text-align:left;"><s:message code="${customerView.agentRegional.i18nName}" /></span>
	    </div>
	</div>
	
	<div class="form-group">
	    <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="customer.title.agentCountry"/>：</label>
	    <div class="col-md-3">
	    	<span class="control-label col-md-10" style="text-align:left;">
		    	<c:forEach items="${customerView.agentCountry}" var="agentCountry">
		   			 <span>${agentCountry.i18nName}</span>
		   		</c:forEach>
	   		</span>
	    </div>
	</div>
  	</c:when>
  	<c:when test="${customerView.customerType == 'nondirect'}">
  	 <div class="form-group">
	    <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="customer.title.agentCompany"/>：</label>
	    <div class="col-md-3">
	    	<span class="control-label col-md-10" style="text-align:left;">${customerView.agentCompany.companyName}</span>
	    </div>
	</div>
    <div class="form-group">
	    <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="customer.title.industry"/>：</label>
	    <div class="col-md-3">
	    	<span class="control-label col-md-10" style="text-align:left;">${customerView.industry.i18nName}</span>
	    </div>
	</div>
	</c:when>
  	<c:otherwise>
  	 <div class="form-group">
	    <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="customer.title.industry"/>：</label>
	    <div class="col-md-3">
	    	<span class="control-label col-md-10" style="text-align:left;">${customerView.industry.i18nName}</span>
	    </div>
	 </div>
  	</c:otherwise> 
  </c:choose>
  <fis:script>
  	$(function(){
		var errorJson = {"customer.address":"customer.address.required","customer.belongSales":"customer.belongSales.required","customer.businessType":"customer.businessType.required","customer.companyName":"customer.companyName.required","customer.country":"customer.country.required","customer.registerTime":"customer.registerTime.required","customer.type":"customer.type.required","customer.url":"customer.url.required","customer.industry":"customer.industry.required","customer.agentCountry":"customer.agentCountry.required","customer.agentRegional":"customer.agentRegional.required","customer.agentType":"customer.agentType.required"};
		 $.each(errorJson, function(i, item){ 
			$('[name="'+ i +'"]').closest(".form-group").find(".help-block").text(GCRM.util.message(item));
		 });
		 $(".help-block").each(function(){
		 	if($(this).closest('.form-group').hasClass("has-error")){
		 		$(this).show(); 
		 	} else {
		 		$(this).hide(); 
		 	}
		 });
		 $('#belongSalesName').closest("div").css("position","relative");
		 $('#belongSalesName').tagSuggest({
            matchClass: 'autocomplete-suggestions',
            tagContainer:'div',
            tagWrap: 'div',
            separator: " ",
            url: "belongSalesSuggest",
            inputName:"input[name='customer.belongSales']",
            deleteCallback:function(){
            	$("input[name='customer.belongSales']").val("").blur();
            },
            onSelected:function(){
				$("input[name='customer.belongSales']").blur();
            }
        }); 
         
       $(".autocomplete-suggestions").css({
       	position:"absolute",
       	zIndex:9999,
       	backgroundColor:"#fff",
       	maxHeight:"400px",
       	overflow:"auto",
       	boxShadow:"0 2px 2px #cfcfcf",
       	border:"1px solid #cfcfcf"
       });

      	$("body").append([
      		'<style type="text/css">',
				'div.autocomplete-suggestions>div {padding: 6px 20px;cursor: pointer;margin-top: 2px;text-align: left;}',
				'div.autocomplete-suggestions>div:nth-child(2n) {background: #f5f6f8;}',
				'div.autocomplete-suggestions>div:hover {background: #fff6f7;}',
			'</style>'
 	].join(""));
	}); 
  </fis:script>
  <fis:scripts/>
</fis:div>