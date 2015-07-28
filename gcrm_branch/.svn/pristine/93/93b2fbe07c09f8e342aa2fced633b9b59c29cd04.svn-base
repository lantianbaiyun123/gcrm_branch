<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://gcrm.baidu.com/tags" prefix="fis" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="gcrm" uri="http://gcrm.baidu.com/tags"%>
<!DOCTYPE html>
<fis:html>
    <head>
    <title>GCRM</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@ include file="../include/common.jsp"%>
    <fis:styles/> 
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    </head>
    
    <body>
        <fis:require name="resources/lib/bootstrap/css/bootstrap.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-datetimepicker.min.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-select.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-extend.css"></fis:require>
        <fis:require name="resources/css/common.css"></fis:require> 
        <fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload.css"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload-ui.css"></fis:require>

        <div class="container">
            <%@ include file="../widget/nav.jsp"%>
        </div>
        <div class="container">
            <ol class="breadcrumb">
                <li><a href="#"><s:message code="gcrm.title.home"/></a></li>
                <li><a href="<c:url value='/customer'/>"><s:message code="gcrm.title.customer"/></a></li>
                <li class="active"><s:message code="gcrm.title.addcustomer"/></li>
            </ol>
        </div>
        
        <div class="container">
        	 <div class="modal fade" id="saveModelMsg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	            <div class="modal-dialog">
	                <div class="modal-content modal-footer">     
	                    <div class="col-md-12"> 
	                        <h3 class="clearfix">
	                            <div class="col-md-4 moalTitle"></div>
	                        </h3>
	                    </div> 
	                </div>
	            </div>
	        </div>
        
            <form class="form-horizontal" method="POST" action="saveNewCustomer">
            	<div id="hiddenCustomerId"></div>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-10"><s:message code="gcrm.title.addcustomer"/></div>
                            <div class="col-md-2">
                                <input type="submit"  class="btn btn-primary addCustomer" value='<s:message code="gcrm.title.customer.submit"/>' />
                                <input type="button"  class="btn btn-success saveCustomer" value='<s:message code="gcrm.title.customer.save"/>'/>
                            </div>
                        </div>
                    </div>
                </div><!--panel-default-->
                <div class="panel panel-default">
                    <div class="panel-heading"><s:message code="customer.title.base"/></div>
                    <div class="panel-body">
                    	 <%@ include file="../widget/baseInfo.jsp"%>
                    </div><!--panel-body-->
                </div><!-- end panel -->
                

                 <div class="panel panel-default">
                    <div class="panel-heading"><s:message code="contact.info"/></div>
                    <div class="panel-body">
                        <%@ include file="../widget/concatInfo.jsp"%>
                    </div><!--<div class="panel-body">-->
                </div>

                <div id="businessChanceDiv" class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading"><s:message code="opportunity.title"/></div>
                    <div class="panel-body"> 
                        <%@ include file="../widget/businessChance.jsp"%>
                    </div>
                </div><!--panel-body-->
                
                <div id="qualificationDiv" class="panel panel-default">
                    <div class="panel-heading"><s:message code="qualification.title"/></div>
                    <div class="panel-body">
                        <%@ include file="../widget/agentQualification.jsp"%>
                    </div>
                </div><!--panel-body-->
                <div class="panel panel-default">
                    <div class="panel-heading"><s:message code="materials.add.title"/></div>
                    <div class="panel-body">
                        <%@ include file="../widget/materials.jsp"%>
                    </div>
                </div><!--panel-body-->
                
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                            	<input type="submit"  class="btn btn-primary addCustomer" value='<s:message code="gcrm.title.customer.submit"/>' />
                                <input type="button"  class="btn btn-success saveCustomer" value='<s:message code="gcrm.title.customer.save"/>'/>
                            </div>
                        </div>
                    </div>
                </div><!--panel-body-->
            </form>
        </div>
    </body>
    <fis:script>
    	$(document).ready(function(){
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
				
    	     $(".addCustomer").click(function(){
    	     	var businessLicenseVal = $("input[name='customer.businessLicense']").val();
    	     	if(businessLicenseVal == ''){
    	     		var businessLicenseValEL = $("input[name='customer.businessLicense']").closest(".form-group");
    	     		
    	     		businessLicenseValEL.addClass('has-error');
    	     		businessLicenseValEL.find('.help-block').text(GCRM.util.message("customer.businessLicense.required"))
    	     		return;
    	     	}
    	     	
    	     	$(".form-horizontal").attr("action","addCustomer");
    	     });
    	     
    	     $(".saveCustomer").click(function(){
    	     	$(".form-group").each(function(i,index){
    	     		$(this).removeClass('has-error');
    	     		$(this).find(".help-block").hide();   
    	     	});
				
    	     	var companyNameVal = $("input[name='customer.companyName']").val();
    	     	if(companyNameVal == ''){
    	     		var formGroupEL = $("input[name='customer.companyName']").closest(".form-group");
    	     		
    	     		formGroupEL.addClass('has-error');
    	     		formGroupEL.find('.help-block').show();
    	     		return;
    	     	}
		
    	     	$(".form-horizontal").attr("action","saveCustomer");
    	     	
    	     	$.ajax({
					type: "POST",
					 url: $(".form-horizontal").attr("action"),
					 data: $(".form-horizontal").serialize(),
					 success: function(msg){
						if(msg.success){
							$("#saveModelMsg").find(".moalTitle").html(GCRM.util.message("customer.save.success"));
							$("#saveModelMsg").modal('show');
			                
							$("#hiddenCustomerId").html('<input type="hidden" name="customer.id" value="' + msg.retBean + '" >')
								
							$(".form-group").each(function(){
								$(this).removeClass("has-error").find(".help-block").text('');
							})
						} else { 
							$("#saveModelMsg").find(".moalTitle").html(GCRM.util.message("customer.save.failed"));
							$("#saveModelMsg").modal('show');
			               
							
							$.each(msg.errors, function(i, item){ 
								$('[name="'+ i +'"]').closest(".form-group").addClass("has-error")
									.removeClass("has-success").find(".help-block").text(GCRM.util.message(item));
							});
						}
						setTimeout(function(){
	                            $("#saveModelMsg").modal('hide');
	                    },500)
					}
				});
    	     });
    	     var businessType = $("select[name='customer.businessType']").val();
    	     if(businessType != 0){
    	    	$("#qualificationDiv").hide('fast');
    	     } else {
    	     	$("#businessChanceDiv").hide('fast');
    	     }
    	});
    </fis:script>
    <fis:script>
    	$(document).ready(function(){
    	    $("select[name='customer.businessType']").change(function(){
    	       if($("select[name='customer.businessType']").val()==0){
    	           $("#qualificationDiv").show('fast');
    	           $("#businessChanceDiv").hide('fast');
    	       }else{
    	       		$("#businessChanceDiv").show('fast');
    	       		$("#qualificationDiv").hide('fast');
    	       }
    	       $("#qualificationDiv").find("input").each(function(){
    	                $(this).val("");  
    	           });
    	       $("#qualificationDiv").find("textarea").val("");
    	    });
    	});
	</fis:script>
    <fis:require name="resources/lib/js/jquery.js"></fis:require>
    <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
    <fis:require name="resources/lib/js/jquery.iframe-transport.js"></fis:require>
    <fis:require name="resources/lib/js/jquery-validate.js"></fis:require>
    <fis:require name="resources/lib/jqueryUpload/js/jquery.fileupload.js"></fis:require>
    <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
    <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
    <fis:require name="resources/lib/bootstrap/js/bootstrap-datetimepicker.min.js"></fis:require>
    <fis:require name="resources/lib/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></fis:require>
    <fis:require name="resources/js/addMaterials.js"></fis:require>
    <fis:require name="resources/js/common.js"></fis:require>
    <fis:require name="resources/js/index.js"></fis:require>
    
    <fis:scripts/>
    
</fis:html>