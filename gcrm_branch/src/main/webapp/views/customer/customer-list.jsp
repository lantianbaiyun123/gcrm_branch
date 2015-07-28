<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
        <fis:require name="resources/lib/bootstrap/css/bootstrap-select.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-extend.css"></fis:require>
        <fis:require name="resources/css/common.css"></fis:require>
        
        <div class="container">
            <%@ include file="../widget/nav.jsp"%>
        </div>
        <div class="container">
            <ol class="breadcrumb marginBottom10">
                <li><a href="#"><s:message code="gcrm.title.home"/></a></li>
                <li><a href="<c:url value='/customer'/>"><s:message code="gcrm.title.customer"/></a></li>
                <li class="active"><s:message code="gcrm.title.customerlist"/></li>
            </ol>
        </div>
        
		<div class="container">   
			<c:if test='${message != null && message.message != "" }'>
				<div class="alert alert-info alert-dismissable">
			   		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			    	${message.message}
				</div>
			</c:if>
	                        
       		<h3 class="row marginTop0 marginBottom10">
            	<div class="col-md-10"><s:message code="customer.list"/></div>
               	<div class="col-md-2 text-right">
                	<button type="button" id="createCustomer" class="btn btn-primary">+ <s:message code="customer.list.add"/></button>
               	</div>
            </h3>
            
           	<div class="panel panel-default marginBottom10">
               	<div class="panel-body">                              
                   	<%@ include file="../widget/searchCustomer.jsp"%>
               	</div>
           	</div>

			<div class="panel panel-default marginBottom10">
           		<div class="panel-body" id="customerListContainer">
                    	<%@ include file="../widget/customerList.jsp"%>
              		</div>
           	</div> 
            
        </div>
        
        <!--销售转移-->
	    <div class="modal fade" id="salesModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	        <div class="modal-dialog">
	            <form role="form" id="moveSalesModalFrom" class="form-horizontal">
	                <div class="modal-content">
	                    <div class="modal-header">
	                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                        <h4 class="modal-title" id="myModalLabel"><s:message code="customer.list.changeSales"/></h4>
	                    </div>
	                    <div class="modal-body">
	                        <div class="form-group row">
	                            <input type="hidden" id="changeSalesId">
	                            <p  class="form-control-static col-md-2 textRight"><s:message code="customer.list.changeSales.customer"/>：</p>
	                            <p  id="changeSalesCustomerName" class="form-control-static col-md-9 clientName"></p>
	                        </div>
	                        <div class="form-group row">
	                            <p  class="form-control-static col-md-2 textRight"><s:message code="customer.list.changeSales.from"/>：</p>
	                            <p  id="changeSalesName" class="form-control-static col-md-5 marketUser"></p>
	                        </div>
	                        <div class="form-group row">
	                            <p class="form-control-static col-md-2 textRight"><s:message code="customer.list.changeSales.to"/>：</p>
	                            <div class="col-md-3" id="changeSalesToBox">
	                            <input id="belongSalesName" type="text"  class="form-control" class="span2"/>
      							<input id="changeSalesTo" name="salesMoveto" class="form-control"  style="display:none;" />
	                            </div>
	                            <div class="col-md-3 help-block"></div>
	                        </div>
	                    </div>
	                    <div class="modal-footer">
	                    	<button type="button" id="changeSalesSaveBtn" data-dismiss="modal" class="btn btn-primary"><s:message code="view.ok"/></button>
	                        <button type="button" id="changeSalesCancleBtn" class="btn btn-default" data-dismiss="modal"><s:message code="view.cancel"/></button>
	                    </div>
	            </div><!-- /.modal-content -->
	            </form>
	        </div><!-- /.modal-dialog -->
	    </div><!-- /.modal -->
        
        <!--添加账号-->
        <div class="modal fade" id="accountModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <form role="form" class="form-horizontal">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel"><s:message code="accounts.add.title"/></h4>
                    </div>
                    <div class="modal-body">
                     	<input type="hidden" id="accountCustomerNumber">
                        <div class="form-group row">
                            <p  class="form-control-static col-md-2"><s:message code="accounts.customer.name"/>：</p>
                            <p  class="form-control-static col-md-5 clientName"></p>
                            <p  class="form-control-static col-md-5 customerNumber"></p>
                        </div>
                        <div class="form-group row clearfix">
                            <p class="col-md-2 form-control-static"><s:message code="accounts.name"/>：</p>
                            <div class="col-md-3"><input type="text" class="form-control" id="accountName" name="name"></div>
                            <div class="col-md-3 help-block"></div>
                        </div>
                    </div>
                    <div class="modal-footer">
                    	<button type="button" id="accountModelConfirmBtn" class="btn btn-primary"><s:message code="view.ok"/></button>
                        <button type="button" id="accountModelCancelBtn" class="btn btn-default" data-dismiss="modal"><s:message code="view.cancel"/></button>
                    </div>
                </form>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <!--撤回审批-->
        <div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="text-align:center;">
            <div class="modal-dialog" style="min-width:270px; display:inline-block; width:auto;">
                <div class="modal-content">
                	<div class="modal-header" style="text-align:left;">
                		<h4 class="modal-title">确认撤回审批</h4>  
                	</div>
					<div class="modal-body" style="text-align:right;padding:10px 20px 10px;">
							<button type="button" class="btn btn-primary">确认</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    </div>
                        
                </div> 
            </div>
         </div>
     
        <!--发送催办-->
        <div class="modal fade" id="myModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="text-align:center;">
            <div class="modal-dialog">
                <div class="modal-content modal-footer">     
                    <div class="col-md-12"> 
                        <h3 class="clearfix">
                            <div class="col-md-4 moalTitle">催办已发送</div>
                        </h3>
                    </div> 
                </div>
            </div>
        </div>
        <!--作废-->
        <div class="modal fade" id="invalidCustomerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="text-align:center;">
            <div class="modal-dialog" style="min-width:270px; display:inline-block; width:auto;">
                     <div class="modal-content">
                		<div class="modal-header" style="text-align:left;">
                            <h4 class="modal-title"><s:message code="customer.list.invalid.confirm"/></h4>
                        </div>
						<div class="modal-body" style="text-align:right;padding:10px 20px 10px;">
                                <input type="hidden" id="currCustomerId">
                                <input type="hidden" id="currCustomerStatus">
                                <button type="button" class="btn btn-primary invalidBtn" id="invalidCustomerSaveBtn"><s:message code="view.ok"/></button>
	                            <button type="button" class="btn btn-default" id="invalidCustomerCancleBtn" data-dismiss="modal"><s:message code="view.cancel"/></button>                         
                        </div>
                        
                	</div> 
            </div>
         </div>
        
        <div class="modal fade" id="restoreCustomerModal" aria-hidden="true" style="text-align:center;">
             <div class="modal-dialog" style="min-width:270px; display:inline-block; width:auto;">
                <div class="modal-content">
                	<div class="modal-header" style="text-align:left;">
                           <h4 class="modal-title"><s:message code="customer.list.restore.confirm"/></h4>
                    </div>
                    
                   <div class="modal-body" style="text-align:right;padding:10px 20px 10px;">
                        <input type="hidden" id="restoreCustomerId">
                        <input type="hidden" id="restoreCustomerStatus">
                        <button type="button" class="btn btn-primary invalidBtn" id="restoreCustomerSaveBtn"><s:message code="view.ok"/></button>
                        <button type="button" class="btn btn-default" id="restoreCustomerCancleBtn" data-dismiss="modal"><s:message code="view.cancel"/></button>
                    </div> 
                </div>
            </div>
        </div>
        <div class="alert" style="min-width:270px; width:auto; position: absolute; top: 1px; left: 40%">
		</div>
	    
	    <fis:require name="resources/lib/js/jquery.js"></fis:require>
	    <fis:require name="resources/lib/js/jquery.suggestion.js"></fis:require>
	    <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
	    <fis:require name="resources/lib/js/jquery.iframe-transport.js"></fis:require>
	    <fis:require name="resources/lib/jqueryValidation/jquery.validate.js"></fis:require>
	    <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
	    <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
	    <fis:require name="resources/js/common.js"></fis:require>
        <fis:require name="resources/js/list.js"></fis:require>
        <fis:script>
            $(document).ready(function(){
                var currListObj = $("#customerListContainer");
                
                var currListCallback = function(){
				    $('select').addClass("col-md-12").selectpicker();
				    
				    $(".moveSales").click(function(){
				        $("#changeSalesId").val($(this).attr("refid"));
				        $("#changeSalesCustomerName").text($(this).attr("refname"));
				        $("#changeSalesName").text($(this).attr("refsales"));
				        $("#belongSales").val("");
				        $("#changeSalesTo").val("");
				        $("#changeSalesTo").closest(".form-group").find('.help-block').text("");
				        $("#salesModal").modal("show");
				    });
				    
				    $(".invalidCustomer").click(function(){
				        $("#currCustomerId").val($(this).attr("referid"));
				        $("#currCustomerStatus").val("2");
				        $("#invalidCustomerModal").modal("show");
				    });
				    
				    
				    $(".restoreCustomer").click(function(){
                        $("#restoreCustomerId").val($(this).attr("refid"));
                        $("#restoreCustomerStatus").val("0");
                        $("#restoreCustomerModal").modal("show");
                    });
				    
				    
				    
				    $(".viewCustomer").click(function(){
				        var customerId = $(this).attr("referid");
				        window.location.href = GCRM.constants.CONTEXT + "customer/" + customerId;
				    });
				    
				    $(".addAccount").click(function(){
				    	var customerNumber = $(this).attr("refid");
				        $("#accountCustomerNumber").val(customerNumber);
				        $(".clientName").text($(this).attr("refName"));
				        $(".customerNumber").text(customerNumber);
				        $("#accountModel").modal("show");
				    });
				};
				currListCallback();

                $.gcrmList.querySuccess(currListObj,currListCallback);
                
		        var validator = $("#moveSalesModalFrom").validate({
		            rules:{
		                "salesMoveto":{
		                    required: true,
		                    digits: true
		                }
		            },
		            messages: {
		                "salesMoveto" : GCRM.util.message('customer.list.changeSales.to.salesRequired')
		            },
		            errorPlacement: function(error,element) {
	                    element.closest(".form-group").find('.help-block').html(error);
	                }
		        });
		        
		        var updateCustomerStatus = function(customerId,customerStatus,modalId){
                    var url = GCRM.constants.CONTEXT + "customer/updateStatus";
                    if($.trim(customerStatus) == '' || $.trim(customerId) == ''){
                        return false; 
                    }else{
                        url += "/" + customerId + "/" + customerStatus;
                    }
                    $.post(url,function(data){
                        if(data != null && data.success){
                            $(modalId).modal("hide");
                            $.gcrmList.query(currListObj,1,currListCallback);
                        }
                    });
		          
		        }
                
                $("#changeSalesSaveBtn").click(function(){
                    var isValid = validator.form(); 
	                if(!isValid){
	                    return false;
	                }
                
			        var changeSalesUrl = GCRM.constants.CONTEXT + "customer/updateSales";
			        var toId = $("#changeSalesTo").val();
			        var customerId = $("#changeSalesId").val();
			        if($.trim(toId) == '' || $.trim(customerId) == ''){
			            return; 
			        }else{
			            changeSalesUrl += "/" + customerId + "/" + toId;
			        }
			        
			        $.post(changeSalesUrl,function(data){
			            if(data != null && data.success){
			                $("#salesModal").modal("hide");
			                $.gcrmList.query(currListObj,1,currListCallback);
			            }
			        }); 
			    });
			    
			    $("#invalidCustomerCancleBtn").click(function(){
			        $("#invalidCustomerModal").modal("hide");
			    });
			    
			    $("#restoreCustomerSaveBtn").click(function(){
                    $("#restoreCustomerModal").modal("hide");
                });
                
                $("#restoreCustomerSaveBtn").click(function(){
                    var customerId = $("#restoreCustomerId").val();
                    var status = $("#restoreCustomerStatus").val();
                    updateCustomerStatus(customerId,status,"#restoreCustomerModal");
                });
			    
			    $("#invalidCustomerSaveBtn").click(function(){
			        var customerId = $("#currCustomerId").val();
			        var status = $("#currCustomerStatus").val();
			        updateCustomerStatus(customerId,status,"#invalidCustomerModal");
			    });
			    
			    $("#changeSalesCancleBtn").click(function(){
			        $("#salesModal").modal("hide");
			    });
			    
			    $("#accountModelCancelBtn").click(function(){
			        $("#accountModel").modal("hide");
			    });
			    
			    $("body").undelegate().delegate('#accountModelConfirmBtn','click', function(){
			        var customerNumber = $("#accountCustomerNumber").val();
			        var accountName = $("#accountName").val();
			        if($.trim(accountName) == '' || $.trim(customerNumber) == ''){
			            return false; 
			        }
			        
			        $.ajax({
			             type: "POST",
			             url: GCRM.constants.CONTEXT + "account/register",
			             data: {"customerNumber": customerNumber, "name": accountName},
			             dataType: "json",
			             success: function(msg){
			                if(msg.success){ 
			                    $("#accountModel").modal("hide");
			                    $("#accountName").val("");
			                    var whitespace = "&nbsp;&nbsp;";
			                    var content = GCRM.util.message("accounts.register.success", msg.retBean);
			                    content += whitespace + GCRM.util.message("accounts.init.password") + GCRM.constants.DEFAULT_PWD + whitespace;
			                    $(".alert").alert(content, "success");
			                } else {
			                    $.each(msg.errors, function(key, value){ 
			                        $('[name="'+ key +'"]').closest(".form-group")
			                            .children(".help-block").show().addClass('txt-impt').text(GCRM.util.message(value));
			                    });
			                    $("#accountName").change(function() {
			                        $('#accountName').closest(".form-group").children(".help-block").hide().removeClass('txt-impt').text("");
			                    });
			                }
			            }
			        });
			       
			    });
    
                
                $("#createCustomer").click(function(){
                    window.location.href = GCRM.constants.CONTEXT + "customer/preAddNewCustomer";
                });
                
                
	            $("#listSearchBtn").click(function(){
	                $.gcrmList.query(currListObj,1,currListCallback);
	            });
	            
	            $('#searchCountry').closest("div").css("position","relative");
	            
	            $('#searchCountry').tagSuggest({
		            matchClass: 'autocomplete-suggestions',
		            tagContainer:'div',
		            tagWrap: 'div',
		            separator: " ",
		            url: "<%=basePath%>customer/countrySuggest",
		            delay: "500",
		            inputName:"input[name='country']",
		            querySuccessCallback: function(){
                        if($.trim($("#searchCountry").val()) == ''){
                          $("#searchCountryId").val("");
                        }else{
                          $("#searchCountryId").val("-3");
                        }
                    },
                    setSelectionCallback: function(){
                        if($.trim($("#searchCountry").val()) == ''){
                          $("#searchCountryId").val("");
                        }
                    }
		        });
		        
		        $('#belongSalesName').tagSuggest({
		            matchClass: 'autocomplete-suggestions',
		            tagContainer:'div',
		            tagWrap: 'div',
		            separator: " ",
		            url: "<%=basePath%>customer/belongSalesSuggest",
		            inputName:"input[name='salesMoveto']",
		            deleteCallback:function(){
		            	$("input[name='salesMoveto']").val("").blur();
		            },
		            onSelected:function(){
						$("input[name='salesMoveto']").blur();
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
	            
	            $('#moreSelect').on('click', function () {
	            	var _self=$(this);
	            	if(_self.hasClass("collapseShow")){
						$(this).text(GCRM.util.message("customer.list.moreQueries")).removeClass("collapseShow");
						$("#clientType").val("").change();
						$("#region").val("").change();
					} else {
						$(this).text(GCRM.util.message("customer.list.simplify")).addClass("collapseShow");
					}
				});
				
            });
           
        </fis:script>
	    <fis:scripts/>
    </body>
</fis:html>