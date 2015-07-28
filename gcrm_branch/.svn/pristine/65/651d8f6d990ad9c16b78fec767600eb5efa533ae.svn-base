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
		<div class="container panel panel-default"> 
			<div class="page-header">
            	<h3>报价信息列表</h3>
            </div>  
			
			<div class="panel panel-default">
           		<div class="panel-body">
					<form class="form-horizontal" role="form" id="quoteSearchFrom" action="<c:url value='/quote/query'></c:url>" method="post">
						
						<div class="pull-left searchBar4">
					        <div class="col-md-4 setPad3"><p class="form-control-static">产品:</p></div>
					        <div class="col-md-8 setPad0">
					            <select class="form-control" name="advertisingPlatformId" id="advertisingPlatformId" onchange="loadSites(this)">
					                <option value=-1>==请选择==</option>
					                <c:forEach items="${advertisingPlatforms}" var="advertisingPlatform">
										<option value="${advertisingPlatform.id}" 
											<c:if test="${advertisingPlatform.id==quoteCondition.advertisingPlatformId}"
										>selected</c:if>>
										${advertisingPlatform.name}</option>
									</c:forEach>
					            </select>
					        </div>
					    </div>
					    <div class="pull-left searchBar4">
					        <div class="col-md-4 setPad3"><p class="form-control-static">站点:</p></div>
					        <div class="col-md-8 setPad0">
					            <select class="form-control" name="siteId" id="siteId">
					            </select>
					        </div>
					    </div>
					    <div class="pull-left searchBar4">
					        <div class="col-md-4 setPad3"><p class="form-control-static">计费模式:</p></div>
					        <div class="col-md-8 setPad0">
					            <select class="form-control" name="billingModelId" id="billingModelId">
					                <option value=-1>==请选择==</option>
					                <c:forEach items="${billingModels}" var="billingModel">
										<option value="${billingModel.id}" type="${billingModel.type}"
											<c:if test="${billingModel.id==quoteCondition.billingModelId}">selected</c:if>
										>
											${billingModel.name}
										</option>
									</c:forEach>
					            </select>
					        </div>
					    </div>
					    
					    <div class="pull-left searchBar7">
					        <div class="setPad3">
					        <button type="button" id="listSearchBtn" class="btn btn-info" onclick="search()">查询</button>
					        </div>
					    </div>
					    <div class="pull-left searchBar7">
					        <div class="setPad3">
					           <a target="_blank" href="<c:url value='/quote/preSave'/>" class="btn btn-info">新增</a>
					        </div>
					    </div>
					</form>
				</div>
			</div>
			
	        <div class="panel-body" id="quoteListContainer">               
	       		<%@ include file="listTable.jsp"%>
        	</div> 
        	
        	<div class="panel-body" id="quoteEditContainer"></div> 
        	
       	</div><!-- end container --> 
        
        
	    
	    
	    <fis:require name="resources/lib/js/jquery.js"></fis:require>
	    <fis:require name="resources/lib/js/jquery.suggestion.js"></fis:require>
	    <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
	    <fis:require name="resources/lib/js/jquery.iframe-transport.js"></fis:require>
	    <fis:require name="resources/lib/jqueryValidation/jquery.validate.js"></fis:require>
	    <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
	    <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
	    <fis:require name="resources/js/common.js"></fis:require>
        <fis:require name="resources/js/list.js"></fis:require>
        <fis:require name="resources/js/quote/utils.js"></fis:require>
        <fis:script>
	        $.gcrmList = $.gcrmList || {};
			var listCall = function(){
				//$('select').addClass("col-md-12").selectpicker();
				var listObj = $("#quoteListContainer");
				$(".pagination li").click(function(){
			        var self = $(this);
			        var licss = self.attr("class");
			        var thisPageNum = self.attr("pagenum");
			        if("disabled" == licss || "active" == licss){
			            return;
			        }
			        $.gcrmList.query(listObj,thisPageNum);
			        
			    });
			    
			    $("#gcrmPageSize").change(function(){
			        $.gcrmList.query(listObj,1);
			    });
			    
			};//end listCall
			
			$.gcrmList.query = function(listObj,pageNum,successCall){
			    var pageSizeVal = $("#gcrmPageSize").val();
			    var formId = $("#gcrmPageSize").attr("refFormId");
			    var formAction = $(formId).attr("action");
			    var addParam = "";
			    if(formAction.indexOf("\?") == -1){
			        addParam += "?";
			    }else{
			        addParam += "&";
			    }
			    addParam += "number="+pageNum + "&size=" + pageSizeVal;
			    var url = formAction + addParam;
			    var postData = $(formId).serialize();
			    $(listObj).load(url, postData, function(){
								    	if(successCall){
								    		successCall();
								    	}else{
								    		listCall();
								    	}
								    }
								);
			};
			
			function search(){
				var listObj = $("#quoteListContainer");
				$.gcrmList.query(listObj,1);
			}
			
			function del(quoteId){
			    if(confirm("确认删除？")==false){
			         return;
			    }
                $.ajax({
                     url:"/gcrm/quote/deleteById/"+quoteId,
                     success:function(){
                         var listObj = $("#quoteListContainer");
                         
                         var pageNum = $(".pagination").find(".active").attr("pagenum");
                         if(pageNum==false){
                            pageNum = 1;
                         }
                         $.gcrmList.query(listObj,pageNum);
                     }
                 });
            }

			
			
        </fis:script>
        <fis:script>
        	$(document).ready(function(){
        			listCall();
        			$("select[name='advertisingPlatformId']").trigger("change");
        		}
        	);
        </fis:script>
	    <fis:scripts/>
    </body>
</fis:html>