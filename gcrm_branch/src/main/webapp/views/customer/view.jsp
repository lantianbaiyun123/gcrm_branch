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
        <fis:require name="resources/lib/bootstrap/css/bootstrap-datetimepicker.min.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-extend.css"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload.css"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload-ui.css"></fis:require>
        <fis:require name="resources/css/common.css"></fis:require>

        <fis:require name="resources/lib/js/jquery.js"></fis:require>
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
                <div class="page-header marginBottom10 paddingBottom12">
	                <h3 class="marginTop0 marginBottom10"><s:message code="gcrm.title.customer.detail"/> 
	                    <div class="pull-right">
	                    	<c:choose>
								<c:when test="${customerView.approvalStatus == 'approving' && customerView.customer.companyStatus != '2'}">
									<input class="btn btn-primary" type="submit" value='<s:message code="gcrm.title.customer.withdraw"/>'>
								</c:when>
								<c:when test="${customerView.approvalStatus == 'refused' && customerView.customer.companyStatus != '2' }">
									<input class="btn btn-primary submitToApprove" type="submit" customerId="${customerView.customer.id}" value='<s:message code="gcrm.title.customer.submit"/>'>
								</c:when>
								<c:when test="${customerView.approvalStatus == 'approved' && customerView.customer.companyStatus != '2' }">
									<a class="btn btn-primary submitAd" type="submit" href="../views/main.jsp#/ad?customerNumber=${customerView.customer.customerNumber}" ><s:message code="gcrm.title.customer.createADProject"/></a>
								</c:when>
							</c:choose>
							<c:choose>
								<c:when test="${customerView.customer.companyStatus != '2'}">
	                                <input class="btn btn-success" id="invalidCustomer" type="button"  toStatus="2" customerId="${customerView.customer.id}" value="<s:message code="customer.list.invalid"/>"></a>
	                                <input class="btn btn-success hidden" id="restoreCustomer" type="button" toStatus="0" customerId="${customerView.customer.id}" value="<s:message code="customer.list.restore"/>">
	                            </c:when>
	                            <c:when test="${customerView.customer.companyStatus == '2'}">
	                            	<input class="btn btn-success" id="restoreCustomer" type="button" toStatus="0" customerId="${customerView.customer.id}" data-companyStatus="${customerView.customer.companyStatus}"value="<s:message code="customer.list.restore"/>">
	                            </c:when>
	                            <c:otherwise>
	                               <c:if test="${customerView.approvalStatus == 'approving'}">
	                                   <input class="btn btn-success hidden" id="invalidCustomer" type="button"  toStatus="2" customerId="${customerView.customer.id}" value="<s:message code="customer.list.invalid"/>"></a>
                                       <input class="btn btn-success" id="restoreCustomer" type="button" toStatus="0" customerId="${customerView.customer.id}" value="<s:message code="customer.list.restore"/>">
	                               </c:if>
	                            </c:otherwise>
                            </c:choose>
	                     </div>
	                </h3>
	            </div>
				<div id="J_companyHeader" class="panel panel-gcrm marginBottom10">
               		 <div class="panel-body">
                		<%@ include file="../widget/companyHeader.jsp"%>
                	</div>
                </div>

                <div id="J_detailBaseInfo" class="panel panel-gcrm marginBottom10"> 
                    <div class="panel-heading clearfix"><s:message code="gcrm.title.customer.detail"/>
                        <a id="editBaseInfo" href="###" customerId="${customerView.customer.id}" class="btn btn-default pull-right"><s:message code="gcrm.title.customer.modify"/></a>
                    </div>
                   
                    <div class="panel-body">
                    	 <%@ include file="../widget/detailBaseInfo.jsp"%>
                	</div>
                    <div id="BaseInfoSubmitBox" class="panel-footer text-center hide">
                        <a id="saveBaseInfo" customerId="${customerView.customer.id}" href="###" class="btn btn-info"><s:message code="gcrm.title.customer.save"/></a>
                        <a id="cancelBaseInfo" customerId="${customerView.customer.id}" href="#" class="btn btn-default"><s:message code="gcrm.title.customer.cancel"/></a>
                    </div>
                </div><!-- end panel -->
        
				<ul class="nav nav-tabs" id="myTab">
					<li ><a data-id="detailConcatInfo" data-url="<%=basePath%>contact/view/${customerView.customer.customerNumber}" 
					href="#detailConcatInfo" data-toggle="tab"><s:message code="contact.info"/></a></li>
					<c:choose>
  						<c:when test="${customerView.customerType == 'offline'}">
  							<li id="businessChanceLi" style="display:none;">
  							   <a href="#detailBusinessChance" data-url="<%=basePath%>opportunity/reloadOpportunity/${customerView.customer.customerNumber}" data-id="detailBusinessChance" data-toggle="tab"><s:message code="opportunity.title"/></a> 
							</li>
  							<li id="qualificationLi"><a href="#detailAgentQualification" data-url="<%=basePath%>qualification/gotoQualificationDetail/${customerView.customer.customerNumber}" data-id="detailAgentQualification" customerNumber="${customerView.customer.customerNumber}" data-toggle="tab"><s:message code="qualification.title"/></a></li>
  						</c:when>
  						<c:otherwise>
  							<li id="qualificationLi" style="display:none;"><a href="#detailAgentQualification" data-url="<%=basePath%>qualification/gotoQualificationDetail/${customerView.customer.customerNumber}" data-id="detailAgentQualification" data-toggle="tab"><s:message code="qualification.title"/></a></li>
  							<li id="businessChanceLi">
  							   <a href="#detailBusinessChance" data-url="<%=basePath%>opportunity/reloadOpportunity/${customerView.customer.customerNumber}" data-id="detailBusinessChance" data-toggle="tab"><s:message code="opportunity.title" /></a>
						   </li>
  						</c:otherwise> 
  					</c:choose>
					<li ><a href="#detailMaterials" data-url="<%=basePath%>materials/gotoMaterialsDetail/${customerView.customer.customerNumber}" data-id="detailMaterials" data-toggle="tab" customerNumber="${customerView.customer.customerNumber}"><s:message code="materials.title" /> </a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="detailConcatInfo">
                        <div class="panel panel-gcrm marginBottom10">
                            <div class="panel-body">

                            </div>
                        </div>
					</div>
                    <!--业务机会-->
					<div class="tab-pane" id="detailBusinessChance">
                        <div class="panel panel-gcrm marginBottom10">
                            <div class="panel-heading clearfix">  
                                <a  id="editBusinessChance" customerNumber="${customerView.customer.customerNumber}" class="btn btn-default pull-right" href="###"><s:message code="gcrm.title.customer.modify"/></a>
                            </div>
                            <div id="J_businessChanceContainer" class="panel-body"></div><!--panel-body-->
                            <div class="panel-footer hide" id="BusinessChanceSubmitBox">
                                <a id="saveBusinessChance" customerNumber="${customerView.customer.customerNumber}" href="###" class="btn btn-info"><s:message code="gcrm.title.customer.save"/></a>
                                <a id="cancelBusinessChance" customerNumber="${customerView.customer.customerNumber}" href="###" class="btn btn-default"><s:message code="gcrm.title.customer.cancel"/></a>
                            </div>
                        </div><!-- end panel -->
					</div>

					<!--代理商资历-->
	               <div class="tab-pane" id="detailAgentQualification">
	                   <div class="panel panel-gcrm marginBottom10">
	                       <div class="panel-heading clearfix">  
	                           <a id="editAgentQualification" class="btn btn-default pull-right" customerNumber="${customerView.customer.customerNumber}" href="###"><s:message code="gcrm.title.customer.modify"/></a>
	                        </div>
	                        <div class="panel-body" id="J_agentQualificationContainer">

                            </div><!--panel-body-->
                             <div class="panel-footer hide" id="AgentQualificationSubmitBox">
							        <a id="saveAgentQualification" customerNumber="${customerView.customer.customerNumber}" href="###" class="btn btn-info"><s:message code="gcrm.title.customer.save"/></a>
							        <a id="cancelAgentQualification" customerNumber="${customerView.customer.customerNumber}" href="#" class="btn btn-default"><s:message code="gcrm.title.customer.cancel"/></a>
							 </div>
	                   </div><!-- end panel -->
	               </div>
                    <!--资质认证材料-->
	                <div class="tab-pane" id="detailMaterials">
	                    <div class="panel panel-gcrm gcrm-relative marginBottom10">
	                        <div class="panel-heading clearfix gcrm-absolute">
	                            <a id="editMaterials" class="btn btn-default pull-right" customerNumber="${customerView.customer.customerNumber}" href="###"><s:message code="gcrm.title.customer.modify"/></a>
	                        </div> 
	                        <div class="panel-body" id="J_materialsContainer">
									
	                        </div><!--panel-body-->
                            <div class="panel-footer hide" id="MaterialsSubmitBox">
                                <a id="saveMaterials" customerNumber="${customerView.customer.customerNumber}" href="###" class="btn btn-info"><s:message code="gcrm.title.customer.save"/></a>
                                <a id="cancelMaterials" customerNumber="${customerView.customer.customerNumber}" href="#" class="btn btn-default"><s:message code="gcrm.title.customer.cancel"/></a>
                            </div>
	                    </div><!-- end panel -->
	                </div>
				</div>
       	 </div>
    </body>
    
    
    <fis:require name="resources/lib/js/additional-methods.js"></fis:require>
    <fis:require name="resources/js/validateCustomerDetail.js"></fis:require>
    <fis:require name="resources/js/detail.js"></fis:require>
    <fis:require name="resources/js/detailBaseInfo.js"></fis:require>
    <fis:require name="resources/js/detailConcatInfo.js"></fis:require>
    <fis:require name="resources/js/detailBusinessChance.js"></fis:require>
    
    <fis:require name="resources/js/detailAgentQualification.js"></fis:require>
    
    <fis:require name="resources/js/detailMaterials.js"></fis:require>
    <fis:require name="resources/js/detailMaterialsEmpty.js"></fis:require>
    <fis:scripts/>
</fis:html>