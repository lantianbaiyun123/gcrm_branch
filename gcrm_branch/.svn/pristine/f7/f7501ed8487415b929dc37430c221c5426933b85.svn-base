<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html>
<fis:html>
    <head>
    <title>GCRM</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <fis:styles/>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>

    <![endif]-->
    <%@ include file="../include/common.jsp"%>
    <script type="text/javascript" src="/gcrm/resources/lib/js/jquery.js" ></script>
    <script type="text/javascript" src="/gcrm/resources/js/detail.js" ></script>
    </head>
    <body> 
        <fis:require name="resources/lib/bootstrap/css/bootstrap.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-select.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-datetimepicker.min.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-extend.css"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload.css"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload-ui.css"></fis:require>
        <fis:require name="resources/css/common.css"></fis:require>

        
        <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.iframe-transport.js"></fis:require>
        
        <fis:require name="resources/lib/jqueryValidation/jquery.validate.js"></fis:require>
        <fis:require name="resources/lib/jqueryValidation/additional-methods.js"></fis:require>
        
        <fis:require name="resources/lib/js/jquery.form.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.suggestion.js"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/js/jquery.fileupload.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-datetimepicker.min.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></fis:require>
        <fis:require name="resources/lib/jqueryValidation/jquery.validate.js"></fis:require>
        <fis:require name="resources/js/common.js"></fis:require>
        

        
        <div class="container"> 
            <%@ include file="../widget/nav.jsp"%>
        </div>
        <div class="container">
            <ol class="breadcrumb marginBottom10">
                <li><a href="#"><s:message code="gcrm.title.home"/></a></li>
                <li><a href="<c:url value='/customer'/>"><s:message code="gcrm.title.customer"/></a></li>
                <li class="active"><s:message code="gcrm.title.customer.detail"/></li>
            </ol>
        </div>
        
        <div class="container">
                <div class="page-header marginBottom10">
	                <h3><font style="vertical-align: middle;">${customerView.customer.companyName}</font>
	                <small style="margin-left: 1em;color: #666666;">
						(<s:message code="gcrm.title.company.status.${customerView.customer.companyStatus}"/>)
					</small>
	                    <div class="pull-right">
							<div style="display:inline-block;"><a class="btn btn-primary submit" customerId="${customerView.customer.id}"><s:message code="gcrm.title.customer.submit"/></a></div>
                            <c:choose>
	                             <c:when test="${customerView.customer.companyStatus != '2'}">
	                                 <a class="btn btn-success" id="invalidCustomer" toStatus="2" customerId="${customerView.customer.id}"><s:message code="customer.list.invalid"/></a>
	                                 <a class="btn btn-success hidden" id="restoreCustomer" toStatus="0" customerId="${customerView.customer.id}"><s:message code="customer.list.restore"/></a>
	                             </c:when>
	                             <c:otherwise>
	                                 <c:if test="${customerView.customer.approvalStatus == '0'}">
	                                     <a class="btn btn-success hidden" id="invalidCustomer" toStatus="2" customerId="${customerView.customer.id}"><s:message code="customer.list.invalid"/></a>
	                                     <a class="btn btn-success" id="restoreCustomer" toStatus="0" customerId="${customerView.customer.id}"><s:message code="customer.list.restore"/></a>
	                                 </c:if>
	                             </c:otherwise>
	                         </c:choose>
	                     </div>
	                </h3>
	            </div>

                <div id="J_detailBaseInfo" class="panel panel-gcrm marginBottom10"> 
                    <div class="panel-heading clearfix"><s:message code="customer.title.base"/>
                        <a id="editBaseInfo" href="###"  class="pull-right" customerId="${customerView.customer.id}">[ <s:message code="gcrm.title.customer.modify"/> ]</a>
                    </div>
                   
                    <div class="panel-body" >
                        <form id='baseInfoForm' class='form-horizontal' method='POST' action='afterEdit'>
                    	   <%@ include file="../widget/detailBaseInfo.jsp"%>
                        </form>
                	</div>
                    <div id="BaseInfoSubmitBox" class="panel-footer text-left hide">
                        <a id="saveBaseInfo" href="###" class="btn btn-primary"  customerId="${customerView.customer.id}"><s:message code="gcrm.title.customer.save"/></a>
                        <a id="tempSaveBaseInfo"  href="#" class="btn btn-default" customerId="${customerView.customer.id}"><s:message code="gcrm.title.customer.tempSave"/></a>
                        <a id="cancelBaseInfo"  href="#" class="btn btn-default" customerId="${customerView.customer.id}" disabled ><s:message code="gcrm.title.customer.cancel"/></a>
                    </div>
                </div><!-- end panel -->
                <div class="panel panel-gcrm marginBottom10" id="detailConcatInfo">
                	<div class="panel-heading clearfix"><s:message code="contact.info"/>  
                        <a  id="editContactInfo" data-id="detailConcatInfo" customerId="${customerView.customer.id}"  data-url="<%=basePath%>contact/view/"  class="pull-right" href="###">[ <s:message code="gcrm.title.customer.modify"/> ]</a>
                    </div>
                    <div class="panel-body <c:if test='${empty contactList}'>hide</c:if>">
                        <%@ include file="../widget/detailConcatInfo.jsp"%>
                    </div>
                </div>

                 <!--代理商资历-->
                 <div class="panel marginBottom10 panel-gcrm <c:if test="${customerView.customerType != 'offline'}">hide</c:if>" id="detailAgentQualification">
                    <div class="panel-heading clearfix">
                        <s:message code="qualification.title"/>  
                        <a id="editAgentQualification" data-id="detailAgentQualification" data-url="<%=basePath%>qualification/gotoEditQualification/" class="pull-right <c:if test="${customerView.customerType != 'offline'}">J_init</c:if>" href="###">[ <s:message code="gcrm.title.customer.modify"/> ]</a>
                     </div>
                     <div class="panel-body <c:if test='${empty qualification}'>hide</c:if>" id="J_agentQualificationContainer">
                     	<form id='agentQualificationForm' class='form-horizontal' method='POST' >
                         	<%@ include file="../widget/detailAgentQualification.jsp"%>
                         </form>
                     </div><!--panel-body-->
				    <div class="panel-footer hide" id="AgentQualificationSubmitBox">
				        <a id="saveAgentQualification" customerNumber="${customerNumber}" href="###" class="btn btn-info"><s:message code="gcrm.title.customer.save"/></a>
				        <a id="cancelAgentQualification" customerNumber="${customerNumber}" href="#" class="btn btn-default"><s:message code="gcrm.title.customer.cancel"/></a>
				    </div>
                 </div><!-- end panel -->
             	<!--业务机会-->
                 <div class="panel marginBottom10 panel-gcrm <c:if test="${customerView.customerType == 'offline'}">hide</c:if>" id="detailBusinessChance">
                     <div class="panel-heading clearfix"><s:message code="opportunity.title"/>
                         <a  id="editBusinessChance" data-id="detailBusinessChance" data-url="<%=basePath%>opportunity/preUpdateOpportunity/" class="pull-right <c:if test="${customerView.customerType == 'offline'}">J_init</c:if>" href="###" >[ <s:message code="gcrm.title.customer.modify"/> ]</a>
                     </div>
                     <div id="J_businessChanceContainer" class="panel-body <c:if test='${empty opportunityView}'>hide</c:if>">
                     	   <%@ include file="../widget/detailBusinessChance.jsp"%>
                     </div><!--panel-body-->
                     <div class="panel-footer hide" id="BusinessChanceSubmitBox">
                         <a id="saveBusinessChance" href="###" class="btn btn-info"><s:message code="gcrm.title.customer.save"/></a>
                         <a id="cancelBusinessChance" data-url="<%=basePath%>opportunity/reloadOpportunity/" data-id="detailBusinessChance" href="###" class="btn btn-default"><s:message code="gcrm.title.customer.cancel"/></a>
                     </div>
                 </div><!-- end panel -->

                    
               
	        	<!--资质认证材料-->
	            <div class="panel panel-gcrm gcrm-relative marginBottom10" id="detailMaterials">
	                <div class="panel-heading clearfix"><s:message code="materials.title" />
	                    <a id="editMaterials" class="pull-right " data-id="detailMaterials" data-url="<%=basePath%>materials/gotoEditMaterials/${customerView.customer.customerNumber}" href="###">[ <s:message code="gcrm.title.customer.modify"/> ]</a>
	                </div> 
	                <div class="panel-body <c:if test='${empty attachments}'>hide</c:if>" id="J_materialsContainer">
                            <%@ include file="../widget/detailMaterials.jsp"%>		
	                </div><!--panel-body-->
	                <div class="panel-footer hide" id="MaterialsSubmitBox">
	                    <a id="saveMaterials" href="###" class="btn btn-info"><s:message code="gcrm.title.customer.save"/></a>
	                    <a id="cancelMaterials" href="#" class="btn btn-default"><s:message code="gcrm.title.customer.cancel"/></a>
	                </div>
	            </div><!-- end panel -->
                <div class="text-center">
                	<div style="display:inline-block;">
                    	<a class="btn btn-primary submit" customerId="${customerView.customer.id}"><s:message code="gcrm.title.customer.submit"/></a>
                	</div>
                </div>
       	 </div>
    </body>
    
    <fis:scripts/>
    <fis:require name="resources/js/validateCustomerDetail.js"></fis:require>
    <fis:require name="resources/lib/js/additional-methods.js"></fis:require>
    <fis:require name="resources/js/detailBaseInfo.js"></fis:require>
    <fis:require name="resources/js/newConcatInfo.js"></fis:require>

    <fis:require name="resources/js/newBusinessChance.js"></fis:require>
    <fis:require name="resources/js/newAgentQualification.js"></fis:require>
    
    <fis:require name="resources/js/newMaterials.js"></fis:require>
    <fis:require name="resources/js/detailMaterialsEmpty.js"></fis:require>
    <fis:require name="resources/js/newCustomer.js"></fis:require>
    <fis:require name="resources/js/customer-undone.js"></fis:require>
    
    <fis:script>
    	function reviseBtnShow(){
           $("#editMaterials,#editAgentQualification,#editBusinessChance,#editContactInfo,#editBaseInfo").removeClass('hide');
        }
		$(document).ready(function(){
            $("#detailConcatInfo,#detailConcatInfo,#detailBusinessChance,#detailMaterials").unbind();
            $("body").delegate(".addAgentBtn","click",function(){
                $('.addAgentCompany').removeClass('hide');
            });
            
            $("body").delegate(".agentCompanyBtn","click",function(){

                var addAgentName=$('#addAgentName').val();
                console.log(addAgentName);
                $('#agentCompany').val(addAgentName);
            })
		});
    </fis:script>
</fis:html>