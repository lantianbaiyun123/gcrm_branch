<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>

<fis:div>
    <div class="container">
        <div class="container">
				<input type="text" id="opportunity.customerNumber" name="opportunity.customerNumber"
				value="${opportunityVO.opportunity.customerNumber}" hidden="true">	
				<input type="text" id="opportunity.id" name="opportunity.id"
				value="${opportunityVO.opportunity.id}" hidden="true">

				
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.budget"/>：</label>
				    <div class="col-md-3">
				        <div class="row">
				            <div class="col-md-4">
				                <select id="opportunity.currencyType" name="opportunity.currencyType" class="col-mk form-control">
									<c:forEach items="${currencyTypeList}" var="currencyType" varStatus="status">
										<option value="${currencyType.id}" 
										<c:if test="${ opportunityVO.opportunity.currencyType == currencyType.id && !empty opportunityVO.opportunity.currencyType }">
											selected
										</c:if>
										<c:if test="${ currencyType.id == '1'&& empty opportunityVO.opportunity.currencyType }">
											selected
										</c:if>
										>${currencyType.i18nName}</option>
									</c:forEach>
								</select>
				            </div>
				            <div class="col-md-8">
				                <input type="text" id="opportunity.budget" name="opportunity.budget" value="${opportunityVO.opportunity.budget}" class="form-control" data-pattern="^[1-9]\d*$">
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
											value="${opportunityVO.opportunity.spendingTime}">
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.payment"/>：</label>
				    <div class="col-md-3">
				        <label class="radio radio-inline">
				            <input type="radio" name="opportunity.payment" value="0" <c:out value="${opportunityVO.opportunity.payment==0?'checked':'' }"/>><s:message code="opportunity.payment.advance"/>
				        </label>
				        <label class="radio radio-inline">
				            <input type="radio" name="opportunity.payment" value="1" <c:out value="${opportunityVO.opportunity.payment==1?'checked':'' }"/>><s:message code="opportunity.payment.after"/>
				        </label>
				    </div>
				
				    <div class="col-md-2">
				        <div class="input-group">
				            <input type="text" class="form-control" placeholder="<s:message code="opportunity.paymentPeriod"/>" name="opportunity.paymentPeriod" id="opportunity.paymentPeriod" value="${opportunityVO.opportunity.paymentPeriod}" data-pattern="^[1-9]\d*$">
				            <span class="input-group-addon"><s:message code="opportunity.day"/></span>
				        </div>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.billingModel"/>：</label>
					    <div class="col-md-5">
			        	<c:forEach items="${billingModelList}" var="billingModel" varStatus="status">
		        			<label class="checkbox checkbox-inline">
				            	<input type="checkbox" name="opportunity.billingModel" value="${billingModel.id}" <c:if test="${fn:contains(opportunityVO.opportunity.billingModel,billingModel.id)}">checked="checked"</c:if>>${billingModel.name}
				        	</label>
						</c:forEach>
				    </div>
				</div> 
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.businessType"/>：</label>
				    <div class="col-md-2">
						<label class="checkbox checkbox-inline"> <input
							type="checkbox" name="opportunity.businessType" value="0" <c:if test="${fn:contains(opportunityVO.opportunity.businessType,'0')}">checked="checked"</c:if>><s:message code="opportunity.businessType.realization"/>
						</label> <label class="checkbox checkbox-inline"> <input
							type="checkbox" name="opportunity.businessType" value="1" <c:if test="${fn:contains(opportunityVO.opportunity.businessType,'1')}">checked="checked"</c:if>><s:message code="opportunity.businessType.sales"/>
						</label>
				    </div>
				</div>
				<div class="form-group" style="display:none;" id="advertisiongPlatform">
				    <label class="control-label col-md-2"><s:message code="opportunity.advertisingPlatform"/>：</label>
				    <div class="col-md-9" style="min-height:54px;">
					    <div  id="checkboxs0" style="display:none;">
				        	<c:forEach items="${realizedAdvertisingPlatformList}" var="advertisingPlatform" varStatus="status">
			        			<label class="checkbox checkbox-inline">
					            	<input type="checkbox" name="platformIds" value="${advertisingPlatform.id}" <c:if test="${fn:contains(opportunityVO.platformIds,advertisingPlatform.id)}">checked="checked"</c:if>>${advertisingPlatform.i18nName}
					        	</label>
							</c:forEach>				    
					    </div>
					    <div  id="checkboxs1" style="display:none;">
				        	<c:forEach items="${salesAdvertisingPlatformList}" var="advertisingPlatform" varStatus="status">
			        			<label class="checkbox checkbox-inline">
					            	<input type="checkbox" name="platformIds" value="${advertisingPlatform.id}" <c:if test="${fn:contains(opportunityVO.platformIds,advertisingPlatform.id)}">checked="checked"</c:if>>${advertisingPlatform.i18nName}
					        	</label>
							</c:forEach>
					    </div>
				    </div>
				</div>
				<!--<div class="form-group">
				    <label class="control-label col-md-2">&nbsp;</label>
				    <div class="col-md-9" id="checkboxs1">
			        	<c:forEach items="${salesAdvertisingPlatformList}" var="advertisingPlatform" varStatus="status">
		        			<label class="checkbox checkbox-inline">
				            	<input type="checkbox" name="platformIds" value="${advertisingPlatform.id}" <c:if test="${fn:contains(opportunityVO.platformIds,advertisingPlatform.id)}">checked="checked"</c:if>>${advertisingPlatform.i18nName}
				        	</label>
						</c:forEach>
				    </div>
				</div>-->
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.description"/>：</label>
				    <div class="col-md-5">
				        <textarea placeholder="<s:message code="opportunity.description"/>" name="opportunity.description" id="opportunity.description" cols="30" rows="3" class="form-control" value="${opportunityVO.opportunity.description}">${opportunityVO.opportunity.description}</textarea>
				    </div>
				</div>
				<!--<input type="text" id="aa" name="aa" value="${opportunityVO.realizedAdvertisingPlatformList[0].name}"/>  -->
			</div>
		</div>	

</fis:div>
<script type="text/javascript">
    moduleConfig.detailBusinessChance.success = function(){
    	$('#detailBusinessChance .panel-body select').addClass("col-md-12").selectpicker({});
        $.gcrm.editBusinessChance();
        
        $("[name='opportunity.paymentPeriod']").closest(".col-md-2").hide();
	    $("[name='opportunity.payment']").each(function(index,element){
	        if($(this).is(":checked") && $(this).val()==1){
                $("[name='opportunity.paymentPeriod']").closest(".col-md-2").show();                
	        }
	     });     
	     
	     $("[name='platformIds']").each(function(index,element){
	        $(element).attr("disabled",true);
	      });   
	     
	     $("[name='opportunity.businessType']").each(function(index,element){
	        var bussinessType=$(this).val();
	        var advertisiongPlatform = $("#advertisiongPlatform");
	        var checkboxs0 = $("#checkboxs0");
	        var checkboxs1 = $("#checkboxs1");
	        if($(this).is(":checked")){
	        	advertisiongPlatform.css("display","block");
	            if(bussinessType==0){	            	
	            	checkboxs0.css("display","block");
	                $("[id='checkboxs0']").find("[name='platformIds']").each(function(index,element){
	                    $(element).attr("disabled", false);
	                });                 
	            }else if(bussinessType==1){
	            	checkboxs1.css("display","block");
	                $("[id='checkboxs1']").find("[name='platformIds']").each(function(index,element){
	                    $(element).attr("disabled", false);
	              });
	            }
	
	        }               
	     });
	     
	     $("[name='opportunity.payment']").click(function(){
	         var payment = $(this).val();
	         if(payment==0){
	             $("[name='opportunity.paymentPeriod']").closest(".col-md-2").hide();
	             $("[name='opportunity.paymentPeriod']").val("");
	             
	         }else if(payment==1){
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
	         var advertisiongPlatform = $("#advertisiongPlatform");
	         if(businessType==0){
	             if($(this).is(":checked")){
		        	 if(advertisiongPlatform.css("display") == "none"){
		        		 advertisiongPlatform.css("display","block")
		        	 }
	                 $("[id='checkboxs0']").find("[name='platformIds']").each(function(index,element){
	                     $(element).attr("disabled", false);
	               		}); 
	                 $("#checkboxs0").css("display","block");
	             }else{
	                 $("[id='checkboxs0']").find("[name='platformIds']").each(function(index,element){                   
	                     $(element).attr("disabled",true);
	                     $(element).attr("checked", false);
	               		}); 
	            	 if( $("#checkboxs1").css("display") == "none"){
	            		 advertisiongPlatform.css("display","none")
	            	 }
	                 $("#checkboxs0").css("display","none");
	             }

	         }else if(businessType==1){          
	             if($(this).is(":checked")){
	            	 if(advertisiongPlatform.css("display") == "none"){
	            		 advertisiongPlatform.css("display","block")
		        	 }
	                 $("[id='checkboxs1']").find("[name='platformIds']").each(function(index,element){
	                     $(element).attr("disabled", false);
	              		 }); 
	                 $("#checkboxs1").css("display","block");
	             }else{
	                 $("[id='checkboxs1']").find("[name='platformIds']").each(function(index,element){
	                     $(element).attr("disabled",true);
	                     $(element).attr("checked", false);
	               		});
	            	 if( $("#checkboxs0").css("display") == "none"){
	            		 advertisiongPlatform.css("display","none")
	            	 }
	                 $("#checkboxs1").css("display","none");
	             }
	         }
	       }); 
	};
</script>
