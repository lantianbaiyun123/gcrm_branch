<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<div class="container">
		<div class="container">
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.budget"/>：</label>
				    <div class="col-md-3">
				        <div class="row">
				            <div class="col-md-4">
				                <select id="opportunity.currencyType" name="opportunity.currencyType" class="col-mk form-control col-md-12">
				                	<option value=""><s:message code="opportunity.pleaseSelect"/></option>
									<c:forEach items="${currencyTypes}" var="currencyType" varStatus="status">
									<option value="${currencyType.id}" 
									<c:if test="${customerBean.opportunity.currencyType==currencyType.id}">
				            			selected
				            		</c:if>
									>${currencyType.i18nName}</option>
									</c:forEach>
								</select>
				            </div>
				            <div class="col-md-8">
				                <input type="text" id="opportunity.budget" name="opportunity.budget" value="${customerBean.opportunity.budget}" class="form-control" data-pattern="^[1-9]\d*$">
							</div>
				        </div>
				    </div>
				    <div class="col-md-3 help-block"><s:message code="${errors['opportunity.budget']}"/></div>  
				</div>

				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.spendingTime"/>：</label>
				    <div class="col-md-3">
				        <input type="text" placeholder="<s:message code='opportunity.registerTime'/>"
											name="opportunity.spendingTime" id="opportunity.spendingTime" class="form-control"
											value="${customerBean.opportunity.spendingTime}">
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.payment"/>：</label>
				    <div class="col-md-2">
				        <label class="radio radio-inline">
				            <input type="radio" name="opportunity.payment" value="0" <c:out value="${customerBean.opportunity.payment==0?'checked':'' }"/>><s:message code="opportunity.payment.advance"/>
				        </label>
				        <label class="radio radio-inline">
				            <input type="radio" name="opportunity.payment" value="1" <c:out value="${customerBean.opportunity.payment==1?'checked':'' }"/>><s:message code="opportunity.payment.after"/>
				        </label>
				    </div>
				
				    <div class="col-md-2">
				        <div class="input-group">
				            <input type="text" class="form-control" placeholder="<s:message code="opportunity.paymentPeriod"/>" name="opportunity.paymentPeriod" id="opportunity.paymentPeriod" value="${customerBean.opportunity.paymentPeriod}" data-pattern="^[1-9]\d*$">
				            <span class="input-group-addon"><s:message code="opportunity.day"/></span>
				        </div>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.billingModel"/>：</label>
					    <div class="col-md-5">
			        	<c:forEach items="${billingModelList}" var="billingModel" varStatus="status">
		        			<label class="checkbox checkbox-inline">
				            	<input type="checkbox" name="opportunity.billingModel" value="${billingModel.id}" <c:if test="${fn:contains(customerBean.opportunity.billingModel,billingModel.id)}">checked="checked"</c:if>>${billingModel.name}
				        	</label>
						</c:forEach>
				    </div>
				</div> 
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.businessType"/>：</label>
				    <div class="col-md-2">
						<label class="checkbox checkbox-inline"> <input
							type="checkbox" name="opportunity.businessType" value="0" <c:if test="${fn:contains(customerBean.opportunity.businessType,'0')}">checked="checked"</c:if>><s:message code="opportunity.businessType.realization"/>
						</label> <label class="checkbox checkbox-inline"> <input
							type="checkbox" name="opportunity.businessType" value="1" <c:if test="${fn:contains(customerBean.opportunity.businessType,'1')}">checked="checked"</c:if>><s:message code="opportunity.businessType.sales"/>
						</label>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.advertisingPlatform"/>：</label>
				    <div class="col-md-10" id="checkboxs0">
			        	<c:forEach items="${realizedAdvertisingPlatformList}" var="advertisingPlatform" varStatus="status">
		        			<label class="checkbox checkbox-inline">
				            	<input type="checkbox" name="platformIds" value="${advertisingPlatform.id}" <c:if test="${fn:contains(customerBean.platformIds,advertisingPlatform.id)}">checked="checked"</c:if>>${advertisingPlatform.name}
				        	</label>
						</c:forEach>				    
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2">&nbsp;</label>
				    <div class="col-md-10" id="checkboxs1">
			        	<c:forEach items="${salesAdvertisingPlatformList}" var="advertisingPlatform" varStatus="status">
		        			<label class="checkbox checkbox-inline">
				            	<input type="checkbox" name="platformIds" value="${advertisingPlatform.id}" <c:if test="${fn:contains(customerBean.platformIds,advertisingPlatform.id)}">checked="checked"</c:if>>${advertisingPlatform.name}
				        	</label>
						</c:forEach>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.description"/>：</label>
				    <div class="col-md-5">
				        <textarea placeholder="<s:message code="opportunity.description"/>" name="opportunity.description" id="opportunity.description" cols="30" rows="3" class="form-control" value="${customerBean.opportunity.description}">${customerBean.opportunity.description}</textarea>
				    </div>
				</div>
				<!--<input type="text" id="aa" name="aa" value="${opportunityVO.realizedAdvertisingPlatformList[0].name}"/>  -->
				
		</div>
	</div>
<fis:script>
$( document ).ready(function() {

});
$(function() {
    $("[name='opportunity.paymentPeriod']").closest(".col-md-2").hide();
	$("[name='opportunity.payment']").each(function(index,element){      	
	      	if($(this).is(":checked")){
		      	if($(this).val()==1){
					$("[name='opportunity.paymentPeriod']").closest(".col-md-2").show();    			
		      	}
	      	}     			
	 }); 
	 
     $("[name='platformIds']").each(function(index,element){
	    $(element).attr("disabled",true);
	  });
     
     //console.log($("[name='opportunity.businessType']").val());
     $("[name='opportunity.businessType']").each(function(index,element){
      	var bussinessType=$(this).val();
      	if($(this).is(":checked")){
      		if(bussinessType==0){
      			$("[id='checkboxs0']").find("[name='platformIds']").each(function(index,element){
      				$(element).attr("disabled", false);
      		  	});      			
      		}else if(bussinessType==1){
      			$("[id='checkboxs1']").find("[name='platformIds']").each(function(index,element){
      				$(element).attr("disabled", false);
      		  });
      		}

      	}     			
     });
});

$(function(){   
     $("[name='opportunity.payment']").click(function(){
     	var payment = $(this).val();
     	if(payment==0){
     		$("[name='opportunity.paymentPeriod']").closest(".col-md-2").hide();
     		$("[name='opportunity.paymentPeriod']").val("");
     		
     	}
		else if(payment==1){
     		$("[name='opportunity.paymentPeriod']").closest(".col-md-2").show();
     	}
     });


	$("[name='opportunity.currencyType']").change(function(){
	      var currencyType=$(this).val();
		  if(currencyType==""){
		  	$("[name='opportunity.budget']").val("");
		  }
	});
     $("[name='opportunity.businessType']").click(function(){
      	var businessType = $(this).val();
      	if(businessType==0){   		
      		if($(this).is(":checked")){
      			$("[id='checkboxs0']").find("[name='platformIds']").each(function(index,element){
      				$(element).attr("disabled", false);
      		  });	
      		}else{
      			$("[id='checkboxs0']").find("[name='platformIds']").each(function(index,element){    				
      				$(element).attr("disabled",true);
      				$(element).attr("checked", false);
      		  });	
      		}
      	}else if(businessType==1){   		
      		if($(this).is(":checked")){
      			$("[id='checkboxs1']").find("[name='platformIds']").each(function(index,element){
      				$(element).attr("disabled", false);
      				
      		  });	
      		}else{
      			$("[id='checkboxs1']").find("[name='platformIds']").each(function(index,element){
      				$(element).attr("disabled",true);
      				$(element).attr("checked", false);
      		  });	
      		}
      	}
      });     

});
</fis:script>

					
