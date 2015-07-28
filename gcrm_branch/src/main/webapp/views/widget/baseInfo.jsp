<!--基本信息-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<input type="hidden" id="customerId" name="customer.id" value="" >
<input type="hidden" id="approvalStatus" name="customer.approvalStatus" value="" >
<div class="form-group">
    <label class="control-label col-md-2" for="companyName"><span class="txt-impt">*</span><s:message code="customer.title.companyName"/>：</label>
    <div class="col-md-3">
        <input data-required placeholder='<s:message code="customer.holder.companyName"/>' type="text" class="form-control" id="companyName" name="customer.companyName" value="${customerBean.customer.companyName}" >
    </div>
    <div class="col-md-3 help-block"><s:message code="${errors['customer.companyName']}"/></div>
</div>  
  
<div class="form-group">
    <label class="control-label col-md-2" for="businessType"><span class="txt-impt">*</span><s:message code="customer.title.businessType"/>：</label>
    <div class="col-md-3">
        <select data-required class="form-control col-md-12 validate" name="customer.businessType" >
            <option selected="" value="-1"><s:message code="customer.holder.choose"/></option>
            <c:forEach	items="${customerTypes}" var="customerType" varStatus="customerTypeStatus">
            	<option value="${customerType.value}" <c:if test="${customerBean.customer.businessType == customerType.value}">selected</c:if>><s:message code="customer.type.${customerType}"/></option> 
            </c:forEach>
        </select>
    </div> 
    <div class="col-md-3 help-block"><s:message code="${errors['customer.businessType']}"/></div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="customer.country"><span class="txt-impt">*</span><s:message code="customer.title.country"/>：</label>
    <div class="col-md-3">
      <input type="text"  class="form-control tags" class="span2" value="${customerView.country.i18nName}" placeholder='<s:message code="customer.holder.country"/>'/>
      <input name="customer.country" class="form-control span2 validate" value="${customerView.country.id}" style="display:none;" />
    </div>
    <div class="col-md-3 help-block"><s:message code="${errors['customer.country']}"/></div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="industry"><span class="txt-impt">*</span><s:message code="customer.title.industry"/>：</label>
    <div class="col-md-3">
        <select class="form-control col-md-12 validate" id="industry" name="customer.industry" >
            <option selected value="-1"><s:message code="customer.holder.industry"/></option>  
            <c:forEach	items="${industrys}" var="industry" varStatus="industryStatus">
            	<option value="${industry.id}" 
            		<c:if test="${customerBean.customer.industry == industry.id}">
            			selected
            		</c:if>
            	>${industry.i18nName}</option>
            </c:forEach>  
        </select>
    </div>
    <div class="col-md-3 help-block"><s:message code="${errors['customer.industry']}"/></div>    
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="businessLicense"><span class="txt-impt">*</span><s:message code="customer.title.businessLicense"/>：</label>
    <div class="col-md-3">
        <input data-required type="text" class="form-control" id="businessLicense" value="${customerBean.customer.businessLicense}" name="customer.businessLicense" placeholder='<s:message code="customer.holder.businessLicense"/>' >
    </div>
    <div class="col-md-3 help-block"><s:message code="${errors['customer.businessLicense']}"/></div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="registerTime"><span class="txt-impt">*</span><s:message code="customer.title.registerTime"/>：</label>
    <div class="col-md-3">
    	<input data-required type="text" class="form-control date-time" id="registerTime" name="customer.registerTime"  placeholder='<s:message code="customer.holder.registerTime"/>' value='<fmt:formatDate value="${customerBean.customer.registerTime}" pattern="dd-MM-yyyy" />'  >
    </div>
    <div class="col-md-3 help-block"><s:message code="${errors['customer.registerTime']}"/></div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="url"><span class="txt-impt">*</span><s:message code="customer.title.url"/>：</label>
    <div class="col-md-3">
        <input data-required type="text" class="form-control" id="url" value="${customerBean.customer.url}" name="customer.url" placeholder='<s:message code="customer.holder.url"/>' >
    </div>
    <div class="col-md-3 help-block"><s:message code="${errors['customer.url']}"/></div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" ><span class="txt-impt">*</span><s:message code="customer.title.type"/>：</label>
    <div class="col-md-3">
        <label class="checkbox checkbox-inline">
            <input data-required type="checkbox" value="0" name="customer.type" 
            	<c:if test="${fn:contains(customerBean.customer.type,'0')}">
            		checked="checked"
            	</c:if>
	       	/><s:message code="customer.title.type.sale"/>
        </label> 
        <label class="checkbox checkbox-inline">
            <input data-required type="checkbox" value="1" name="customer.type" 
            	<c:if test="${fn:contains(customerBean.customer.type,'1')}">
            		checked="checked"
            	</c:if>
            /><s:message code="customer.title.type.cash"/>
        </label>
    </div>
    <div class="col-md-3 help-block"><s:message code="${errors['customer.type']}"/></div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="belongSales"><span class="txt-impt">*</span><s:message code="customer.title.belongSales"/>：</label>
    <div class="col-md-3">
    	<input id="belongSalesName" type="text"  class="form-control" class="span2" value="${belongSales.name}" placeholder='<s:message code="customer.holder.belongSales"/>'/>
      	<input id="belongSales" name="customer.belongSales" class="form-control span2 validate" value="${belongSales.ucid}" style="display:none;" />
        <!-- <input data-required type="text" class="form-control" id="belongSales" value="${customerBean.customer.belongSales}" name="customer.belongSales" placeholder='<s:message code="customer.holder.belongSales"/>' >
         -->
    </div>
    <div class="col-md-3 help-block"><s:message code="${errors['customer.belongSales']}"/></div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="belongManager"><s:message code="customer.title.belongManager"/>：</label>
    <div class="col-md-3">
        <input type="text" readonly="readonly"  class="form-control" id="belongManager" value="${customerBean.customer.belongManager}" name="customer.belongManager">
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="companySize"><s:message code="customer.title.companySize"/>：</label>
    <div class="col-md-3">
        <select data-required class="form-control col-md-12" name="customer.companySize">
            <c:forEach	items="${companySizes}" var="companySize" varStatus="companySizeStatus">
            	<option value="${companySize}"
            		<c:if test="${customerBean.customer.companySize == companySize}">
            			selected
            		</c:if>
            	>
            	<s:message code="customer.companySize.${companySize}"/>
            	</option> 
            </c:forEach>
        </select>
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="currencyType"><s:message code="customer.title.currencyType"/>：</label>
    <div class="col-md-3">
        <div class="row">
            <div class="col-md-4">
                 <select data-required class="form-control col-mk col-md-12" id="currencyType" name="customer.currencyType" >
		            <c:forEach	items="${currencyTypes}" var="currencyType" varStatus="currencyTypeStauts">
		            	<option value="${currencyType.id}" 
			            		<c:if test="${currencyType.id == '1'}">
			            			selected
			            		</c:if>
		            	> ${currencyType.i18nName}</option> 
		            </c:forEach>
		        </select>
            </div> 
            <div class="col-md-8">
                <input id="registerCapital" name="customer.registerCapital" value="${customerBean.customer.registerCapital}" type="text" class="form-control" class="form-control">
            </div>
        </div>
    </div> 
    <div class="col-md-3 help-block"><s:message code="${errors['customer.registerCapital']}"/></div>  
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="businessScope"><s:message code="customer.title.businessScope"/>：</label>
    <div class="col-md-3">
        <input type="text" class="form-control" id="businessScope" value="${customerBean.customer.businessScope}" name="customer.businessScope" placeholder='<s:message code="customer.holder.businessScope"/>'>
     </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="address"><span class="txt-impt">*</span><s:message code="customer.title.address"/>：</label>
    <div class="col-md-3">
        <input data-required type="text" class="form-control" id="address" value="${customerBean.customer.address}" name="customer.address" placeholder='<s:message code="customer.holder.address"/>' >
    </div>
    <div class="col-md-3 help-block"><s:message code="${errors['customer.address']}"/></div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="description"><s:message code="customer.title.description"/>：</label>
    <div class="col-md-5">
        <textarea class="form-control" id="description" name="customer.description" rows="3" cols="30" placeholder='<s:message code="customer.holder.description"/>'>${customerBean.customer.description}</textarea>
    </div>
</div>

<div class="form-group" style="display:none;">
    <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="customer.title.agentType"/>：</label>
    <div class="col-md-4">
        <c:forEach var="agentType" items="${agentTypes}" varStatus="status">
    		<label class="radio radio-inline"> 
    			<input type="radio" value='${agentType.value}' name="customer.agentType" 
    				<c:if test="${customerBean.customer.agentType == agentType.value}">
            			checked="checked"
            		</c:if>
    			/><s:message code="customer.agentType.${agentType}"/>
	        </label> 
    	</c:forEach>
    </div>
    <div class="col-md-3 help-block"><s:message code="${errors['customer.agentType']}"/></div>
</div>

<div class="form-group" style="display:none;"> 
    <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="customer.title.agentRegional"/>：</label>
    <div class="col-md-5">
    	<c:forEach var="agentRegional" items="${agentRegionals}" varStatus="status">
    		<label class="radio radio-inline"> 
    			<input type="radio" value='${agentRegional.id}' name="customer.agentRegional" 
    				<c:if test="${customerBean.customer.agentRegional == agentRegional.id}">
            			checked="checked"
            		</c:if>
    			/>${agentRegional.i18nName}
	        </label> 
    	</c:forEach>
    </div>
    <div class="col-md-3 help-block"><s:message code="${errors['customer.agentRegional']}"/></div>
</div>

<div class="form-group"  style="display:none;">
    <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="customer.title.agentCountry"/>：</label>
    <c:forEach var="agentRegional" items="${agentRegionals}" varStatus="status">
    	<div class="col-md-5" id="agentCountry_${agentRegional.id}" style="display:none;">
    		<c:forEach items="${agentRegional.agentCountries}" var="agentCountry">
    			 <label class="checkbox checkbox-inline">
		            <input  type="checkbox" value="${agentCountry.id}" name="customer.agentCountry" />
		            ${agentCountry.i18nName}
		        </label>
    		</c:forEach>
    	</div>
   	</c:forEach>
   	<div class="col-md-3 help-block"><s:message code="${errors['customer.agentCountry']}"/></div>
</div>

<div class="form-group" style="display:none;" >
    <label class="control-label col-md-2" for="agentCompany"><span class="txt-impt">*</span><s:message code="customer.title.agentCompany"/>：</label>
    <div class="col-md-3">
        <input name="customer.agentCompany" id="agentCompany" class="validate" style="display:none;">
        <input type="text" placeholder='<s:message code="customer.holder.agent"/>' id="agentCompanyName" class="form-control" >
        
    </div>
    <div style="max-width: 39%; float:left;padding-right: 10px;">
        <button class="btn btn-default addAgentBtn" type="button" style="float:left;"><span class="glyphicon glyphicon-plus"></span><s:message code="customer.agent.add"/></button>
    	<div class="col-md-6 addAgentCompany hide" >
	        <div class="input-group">
	            <input type="text" class="form-control" id="addAgentName" name="addAgentName" >
	            <span class="input-group-btn">
	                <button class="btn btn-default agentCompanyBtn" type="button"><s:message code="gcrm.title.customer.add"/></button>
	            </span>
	        </div><!-- /input-group -->
    	</div>
    </div>
    <div class="help-block" style="float:left;"><s:message code="${errors['customer.agentCompany']}"/></div>
</div>

<fis:script>
$('.date-time').datetimepicker({
       language:  '${currentLocale}',
       weekStart: 1,
       todayBtn:  0,
       autoclose: 1,
       todayHighlight: 1,
       startView: 2,
       minView: 2,
       forceParse: 0,
       format: 'dd-mm-yyyy',
       pickerPosition:"bottom-right"
   });
		    
$(function(){
	var currBusinessType = $("[name='customer.businessType']").val();
	if(currBusinessType != -1){
		$.gcrm_filter(currBusinessType,false);
		if(currBusinessType == 0){
			$("[name='customer.industry']").closest(".form-group").hide();
			
			var regional = '${customerBean.customer.agentRegional}';
	     	var $country = $("#agentCountry_" + regional);
	     	$country.closest(".form-group").children(".col-md-5").hide();
	     	
	     	$country.show();
	     	$country.closest(".form-group").show();
		}
	}
	
	var agentCountry = '${customerBean.customer.agentCountry}';
	if(agentCountry.length > 0) {
		$(agentCountry.split(",")).each(function(index,agentVal){
			$("[name='customer.agentCountry']").each(function(){
				var inputVal = $(this).val();
				if(inputVal == agentVal){
					$(this).attr("checked",true);
				}
			});
		});
	}	
	
    $("[name='customer.businessType']").change(function(){
    	var val = $(this).val();
    	if(val == -1){
    		$("[name='customer.agentType']").closest(".form-group").hide();
            $("[name='customer.agentRegional']").closest(".form-group").hide();
            $("[name='customer.agentCompany']").removeClass("validate").closest(".form-group").hide();
            $("[name='customer.industry']").removeClass("validate").closest(".form-group").show();
            $("[name='customer.agentCountry']").closest(".form-group").hide();
    	} else {
    		$("[name='customer.agentCountry']").closest(".form-group").hide();
    		$("[name='customer.industry'],[name='customer.agentCompany']").removeClass("validate");
    		if(val == 1){
    			$("[name='customer.industry']").addClass("validate");
    		}
    		else if(val == 2){
    			$("[name='customer.industry'],[name='customer.agentCompany']").addClass("validate");
    		}
    	    $.gcrm_filter($(this).val(),true);
        }
        
    });
    
     
     
     	/*Suggestion 初始化*/
		$('#agentCompanyName').closest("div").css("position","relative");
       	
        $('#agentCompanyName').tagSuggest({
            matchClass: 'autocomplete-suggestions',
            tagContainer:'div',
            tagWrap: 'div',
            separator: " ",
            url: "agentCompanySuggest",
            inputName:"input[name='customer.agentCompany']",
            deleteCallback:function(){
	            	$("#agentCompany").val("").blur();
            },
            onSelected:function(){
				$("#agentCompany").blur();
            }
        });
        
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
        
        $('.tags').tagSuggest({
            matchClass: 'autocomplete-suggestions',
            tagContainer:'div',
            tagWrap: 'div',
            separator: " ",
            url: "countrySuggest",
            inputName:"input[name='customer.country']",
            deleteCallback:function(){
            	$("input[name='customer.country']").val("").blur();
            },
            onSelected:function(){
				$("input[name='customer.country']").blur();
            }
        }); 
        /*修改Suggestion样式*/
        $(".autocomplete-suggestions").css({
        	position:"absolute",
        	zIndex:9999,
        	backgroundColor:"#fff",
        	maxHeight:"200px",
        	overflow:"auto",
        	boxShadow:"0 2px 2px #cfcfcf",
        	border:"1px solid #cfcfcf"
        });

		if(!$("#suggestCss")[0])
	       	$("body").append([
	       		'<style type="text/css" id="suggestCss">',
					'div.autocomplete-suggestions>div {padding: 6px 20px;cursor: pointer;margin-top: 2px;text-align: left;}',
					'div.autocomplete-suggestions>div:nth-child(2n) {background: #f5f6f8;}',
					'div.autocomplete-suggestions>div:hover {background: #fff6f7;}',
				'</style>'
	       	].join(""));
	   
});
	
</fis:script>