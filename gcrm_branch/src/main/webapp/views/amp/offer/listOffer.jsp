<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="gcrm" uri="http://gcrm.baidu.com/tags"%>
<!DOCTYPE html>
<html lang="en">
	<head>
	    <title><s:message code="view.settings"/></title>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	    <%@include file="../../include/common.jsp"%>
	    <link rel="stylesheet" href="<%=basePath%>resources/css/ui-lightness/jquery-ui-1.10.3.custom.min.css" />
	    <script type="text/javascript" src="<%=basePath%>resources/js/pinyinEngine.full.js"></script>
	    <script type="text/javascript" src="<%=basePath%>resources/js/bootstrap.min.js"></script>
		<script src="<%=basePath%>resources/js/jquery-ui-1.10.3.custom.min.js"></script>
	    
	</head>
	<body>
		<%@include file="../../include/header.jsp"%>
		
		<div class="container-fluid">
			<div class="row-fluid">
			
				<div class="span2">
					<div class="according" id="according1">
						<div class="accordion-group">
							<div class="accordion-heading">
								<li class="accordion-toggle" data-toggle="collapse" data-parent="#according1">
									<s:message code="view.offer"/>
								</li>
							</div>
							<div id="collapse1" class="accordion-body collapse in">
								<div class="accordion-inner">
									<ul class="nav nav-list">
			                            <li>
			                            	<a class="bgcolor" href="<s:url value="/offer/list/" />">
			                            		<i class=" icon-th-list"></i><small><s:message code="view.offer.ad"/></small>
		                            		</a>
			                            </li>
			                            <li>
			                            	<a href="<s:url value="/offer/customer/list" />">
			                            		<i class=" icon-th-list"></i><small><s:message code="view.offer.customer"/></small>
		                            		</a>
			                            </li>
			                         </ul>
								</div>
							</div>
						</div>
					</div>
					
				</div>
				
				<div class="span10">
					<div class="row-fluid">
						
						<div class="span11">
						    <div class="span3">
						    <blockquote>
						            <p><s:message code="view.offer"/></p>
						    </blockquote>
						    </div>
						    <div class="span2" style="margin-left:-30px;">
						        <a id="btn_open" class="btn btn-small" href="javascript:void(0);"><i class="icon-folder-open"></i><s:message code="view.add"/></a>
						        <a id="btn_close" class="btn btn-small" href="javascript:void(0);" style="display:none"><i class="icon-folder-close"></i><s:message code="view.close"/></a>
						    </div>
						</div>
						<style>
							.row-fluid .span3{
							    margin-left:0%;
							}
							.s1 {
							    width:162px;
							}
							.url-len {
							    width:481px;
							}
							.user-len {
							    width:179px;
							}
						</style>
						
						<div class="span12 hide" id="addOffer">
							<form class="form-horizontal" id="add_form" action="<s:url value="/offer/save" />" method="post">
						        <table class="">
						            <tr>
						                <td width="100"><s:message code="view.offer.customer.name"/>：</td>
						                <td width="251"><div class="input-append"><input type="text" readonly class="user-len" name="cname" id="inputName">
						                    <span class="add-on"><a href="javascript:void(0);" id="showuser_btn" title="<s:message code="view.search"/>"><i class="icon-user"></i></a></span></div>
						                    <input type="hidden" id="inputUid" name="cid">
						                </td>
						                <td align="right" width="100"><s:message code="view.startTime"/>：</td>
						                <td><div class="input-prepend">
						                    <span class="add-on"><a href="#"><i class="icon-time"></i></a></span>
						                    <input type="text" class="input-small" id="obtimeInput" name="startDate"></div> <s:message code="view.to"/>：
						                    <div class="input-prepend"><span class="add-on"><a><i class="icon-time"></i></a></span>
						                    <input type="text" class="input-small" id="oetimeInput" name="endDate"></div>
						                </td><td>
						                    <s:message code="view.settings.country.nation"/>：<select name="country" class="input-medium">
						                    <c:forEach items="${countryList}" var="country">
						                    	<option value="${country.id}">${country.id}- ${country.i18nName}</option>
								        	</c:forEach>
						                    </select>
						                </td>
						            </tr>
						            <tr>
						            
						                <td><s:message code="view.offer.type"/>：</td>
						                <td><select name="type"></select></td>
						                
						                <td align="right"><s:message code="view.settings.position"/>：</td>
						                <td colspan="2">
						                    <select class="s1" name="position1" id="position1">
						                        <option value="-1"><s:message code="view.select"/></option> 
									        	<c:forEach items="${topPositionList}" var="position" varStatus="status">
								        			<option value="${position.id}">${position.i18nName}</option>
												</c:forEach>
						                    </select>
						                    <select class="s1" name="position2" id="position2"></select>
						                    <select class="s1" name="position3" id="position3"></select>
						                </td>
						            </tr>
						            <tr>
						                <td><s:message code="view.offer.content"/>：</td>
						                <td><input type="text" name="content">
						                <td align="right"><s:message code="view.offer.url"/>：</td>
						                <td colspan="2"><input type="text" class="url-len" name="url">
						            </tr>
						            <tr>
						                <td><s:message code="view.remark"/>：</td>
						                <td colspan="2"><textarea  class="span12" name="remark" rows="1"></textarea></td>
						                <td colspan="2"><br><button class="btn offset7 btn-primary" id="submit_btn" type="button"><s:message code="view.save"/></button></td>
						            </tr>
						            </tbody>
						        </table>
							
						    </form>
						</div>
						
						<style>
							.nav li {
							    float:left;
							    margin-left:15px;
							}
							#showuser_modal .input-append {
							    margin-left:10px;
							}
						</style>
						<div id="showuser_modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						    <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						        <h3 id="myModalLabel"><s:message code="view.select"/></h3>
						    </div>
						    <br>
						    <div class="input-append">
							    <input class="input-xlarge" id="user_search" type="text" placeholder="<s:message code="view.offer.customer.name"/>"> 
							    <span class="add-on">..</span>
						    </div>
						
						    <div class="modal-body nav" id="user_body"><s:message code="view.loading"/>...</div>
						    <div class="modal-body" id="log_div"></div>
						</div>
						
						
						
						
						<div class="span11">
						    <hr>
						    <form class="form-search" id="search_form" action="" method="get">
						    	<s:message code="view.offer.customer.name"/>：<input type="text" name="customer.name" value="">  
						        <s:message code="view.startTime"/>：<div class="input-prepend"><span class="add-on"><i class="icon-time"></i></span><input type="text" class="input-small" id="obtimeSearch" name="startDate"></div> 
						        <s:message code="view.to"/>：<div class="input-prepend"><span class="add-on"><i class="icon-time"></i></span><input type="text" class="input-small" id="oetimeSearch" name="endDate"></div> 
						        <s:message code="view.settings.country.nation"/>：
						        <select name="country" class="input-medium">
						            <option value="-1"><s:message code="view.select"/></option>
						            <c:forEach items="${countryList}" var="country">
				                    	<option value="${country.id}">${country.id}- ${country.i18nName}</option>
						        	</c:forEach>
						        </select> 
						        <br><br>
						        <select class="input-medium" name="queryIdType">
						            <option value="OID"><s:message code="view.offer"/> ID</option>
						            <option value="CID"><s:message code="view.offer.customer"/> ID</option>
						        </select>
						        <input type="text" name="svalue" class="span2" value="">
						        <button type="button" id="search_btn" class="btn btn-info"><s:message code="view.search"/></button>
						        <%-- export list csv
						        <a class="btn" href="/export/?<?php echo http_build_query($args);?>&call=offer"><i class="icon-download-alt"></i><?php echo _('导出数据');?></a>
						        
						        --%>
						    </form>
						</div>
						<hr>
						
						<style>
							.row-fluid .span11{
							    width:97%;
							    word-break:break-all
							}
							.row-fluid .span11 table{
							    table-layout:fixed;
							}
							.row-fluid .span11 td{
							    overflow:hidden;
							}
						</style>
						
						<div class="span11">
						    <table class="table table-hover table-bordered table-condensed">
						    <thead>
						        <tr>
						            <th width="90px">
						            	<a href="javascript:void(0);" class="order_btn" data-field="oid" data-order=""> 
							            	<i class="icon-arrow-up"></i>
							            	<s:message code="view.offer"/> ID
						            	</a>
						            </th>
						            <th width="6%"><s:message code="view.settings.country.nation"/></th>
						            <th><s:message code="view.offer.customer.name"/></th>
						            <th width="10%"><s:message code="view.offer.customer.name"/></th>
						            <th width="12%"><s:message code="view.offer.content"/></th>
						            <th width="85px">
						            	<a href="javascript:void(0);" class="order_btn" data-field="obtime" data-order="<?php echo $orderBy['order'];?>"><i class="icon-arrow-up"></i>
						            		<s:message code="view.startTime"/>
						            	</a>
					            	</th>
						            <th width="85px">
						            	<a href="javascript:void(0);"  class="order_btn" data-field="oetime" data-order="<?php echo $orderBy['order'];?>"> 
								            <i class="icon-arrow-up"></i>
								            <s:message code="view.endTime"/>
							            </a>
						            </th>
						            <th><s:message code="view.offer.url"/></th>
						            <th><s:message code="view.remark"/></th>
						            <th width="80px"><s:message code="view.operate"/></th>
						        </tr>
						    </thead>
						    <tbody>
						    	<c:forEach items="${page.content}" var="offer">
					        		<tr>
					                    <td>${offer.id}</td>
					                    <td>${offer.country}</td>
					                    <td>${offer.customer.name}</td>
					                    <td>${offer.content}</td>
					                    <td>${offer.startDate}</td>
					                    <td>${offer.endDate}</td>
					                    <td>${offer.offerUrl}</td>
					                    <td>${offer.remark}</td>
					                    <td>
					                    	<td><a href="javascript:void(0);" rel="${offer.id}" class="edit_btn"><s:message code="view.edit"/></a> | <a href="javascript:void(0);" rel="${offer.id}" class="del_btn"><s:message code="view.delete"/></a></td>
										</td>
					                </tr>
					        	</c:forEach>
								        	
						    </tbody>
						    </table>
						    <gcrm:page pageSize="${page.size}" totalPage="${page.totalPages}" totalCount="${page.totalElements}" currPage="${page.number}" formId="search_form"/>
						</div>
				
						
						
					</div>
				</div>
				
				
				
				
								
				
				  
				
			</div>
		</div>
		
		<script type="text/javascript">
			$(document).ready(function(){
				
				$(".del_btn").click(function(){
			        var oid = $(this).attr('rel');
			        
			        var confirmInfo = GCRM.util.message('delete.confirm');
			        if (confirm(confirmInfo)) {
				        $.post("/offer/del", {oid: oid}, function(data){
				            if (data.type == 'SUCCESS') {
				                $('#row_' + oid).remove();
				            } else {
				                $("#alert_msg").html('删除失败，请重试！');
				            }
				            $("#alert_title").html('OFFER管理');
				            $("#alert_del_div").modal('show');
				       });
			    	}
			    });
    
    
			    
		        $("#oetimeInput, #obtimeInput").datepicker();
		        if ($("#oetimeInput").val() == '') {
		            $("#oetimeInput").datepicker("option", "dateFormat", "yy-mm-dd");
		        }
		        if ($("#obtimeInput").val() == '') {
		            $("#obtimeInput").datepicker("option", "dateFormat", "yy-mm-dd");
		        }

			    var userBody = $("#user_body");
			    var userData = null;
			    var userHtml = null;
			    
			    var initUserList = function(data) {
			        var html = '';
			        $.each(data, function(key, val){
			            html += '<li><a href="javascript:void(0);" data-uid="' + val.id + '" data-name="' + val.name + '">' + val.name + '</a></li>'; 
			            Engine.setCache([val.name], val);
			        });
			        userHtml = html;
			        userBody.html(html);

			    };
			 
			    $("#showuser_btn, #inputName").click(function(){
			        $("#showuser_modal").modal({backdrop:false, show:true});
			        if (userData !== null) {
			            initUserList(userData);        
			        } else {
			            $.get("<%=basePath%>/offer/customer/getAll", function(data){
			                userData = data['content'];
			                initUserList(data['content']);        
			            });
			        }
			    });

			    
			    var Engine = pinyinEngine(); // 初始化搜索引擎
			    var Timer = function (){
			        this.startTime = (new Date()).getTime();
			    };
			    Timer.prototype.end = function(){
			        return (new Date()).getTime() - this.startTime;
			    };
			    //拼音中文搜索
			    var pinyinSearch = function (keyword, callback) {
			        var time = new Timer();
			        var txt = [];
			        var len = 0;

			        if (keyword === '') {
			            txt = userHtml;
			        } else {
			            Engine.search(keyword, function (data) {
			                txt.push('<li><a href="javascript:void(0);" data-uid="');
			                txt.push(data.id);
			                txt.push('" data-name="');
			                txt.push(data.name);
			                txt.push('">');
			                txt.push(data.name);
			                txt.push('</a></li>');
			                len ++;
			            });
			            txt = txt.join('');
			            txt = txt == '' ? '<li><div class="tmpl-schoolBox-noContent"><s:message code="view.noResult"/>..</div></li>' : txt;
			        };
			        callback(txt);
			        //$("#log_div").html('查询到' + len + '条结果，匹配耗时：' + time.end() + '毫秒');
			    };

			    //英文搜索
			    var englishSearch = function (keyword, callback) {
			        var time = new Timer();
			        var txt = [];
			        var len = 0;
			        var RegObj = new RegExp("^" + keyword, 'i');
			        if (keyword === '') {
			            txt = userHtml;
			        } else {
			            $.each(userData, function(key, data){
			                if (RegObj.test(data.name)) {
			                    txt.push('<li><a href="javascript:void(0);" data-uid="');
			                    txt.push(data.id);
			                    txt.push('" data-name="');
			                    txt.push(data.name);
			                    txt.push('">');
			                    txt.push(data.name);
			                    txt.push('</a></li>');
			                    len ++;
			                }
			            });
			            txt = txt.join('');
			            txt = txt == '' ? '<li><div class="tmpl-schoolBox-noContent"><s:message code="view.noResult"/>..</div></li>' : txt;
			        }
			        callback(txt);
			        //$("#log_div").html('查询到' + len + '条结果，匹配耗时：' + time.end() + '毫秒');
			    };

			    var isEnglist = function (str) { 
			        return /^[a-zA-Z0-9]/.test(str);
			    };
			    var timer;
			    var userVal = $("#user_search").val();
			    $("#user_search").keyup(function(){
			        var val = $(this).val();
			        if (val === userVal) return;
			        userVal = $(this).val();


			        if (isEnglist(userVal.substr(0,1))) {
			            englishSearch(val, function (html) {
			                    userBody.html(html);
			            });
			        } else {
			            clearTimeout(timer);
			            timer = setTimeout(function () {
			                pinyinSearch(val, function (html) {
			                    userBody.html(html);
			                });
			            }, 40); 
			        }
			    });

			    
			    $("#user_body").delegate('a', 'click', function(){
			        var linkObj = $(this);
			        var id = linkObj.attr("data-uid");
			        var name = linkObj.attr("data-name");
			        $("#inputName").val(name);
			        $("#inputUid").val(id);
			    
			        $("#showuser_modal").modal('hide');
			    });

			    
			    $("#submit_btn").click(function(){
			        if ($("#inputUid").val() == '') {
			            $("#showuser_btn").click();
			            return false;
			        }
			        if ($("#obtimeInput").val() == '') {
			            $("#obtimeInput").focus();
			            return false;
			        }
			        if ($("#oetimeInput").val() == '') {
			        }

			        var p3 = $("#position3").val();
			        if (p3 == null || p3 < 0) {
			            $("#position3").popover({content:'<s:message code="view.required"/>'});
			            $("#position3").popover('show');
			            return false;
			        }

			        $("#position3").popover('hide');
			        var contentObj = $("#add_form [name='content']");
			        if (contentObj.val() == '') {
			            contentObj.focus();
			            contentObj.attr('placeholder', "<s:message code="view.required"/>");
			            return false;
			        }
			        var urlObj = $("#add_form [name='url']");
			        if (urlObj.val() == '') {
			            urlObj.focus();
			            urlObj.attr('placeholder', "<s:message code="view.required"/>");
			            return false;
			        }
			        $("#add_form").submit();
			    });

			    $("#btn_close, #btn_open").click(function(){
			        if ($(this).attr("id") == "btn_open") {
			            $(this).hide();
			            $("#btn_close").show();
			            $("#addOffer").show("fast");
			        } else {
			            $(this).hide();
			            $("#btn_open").show();
			            $("#addOffer").hide();
			        }
			    });
			    
			    
			    $("#position1").change(function(){
				    var selectVal = $(this).val();
				    var secObj = $("#position2");
				    var thirdObj = $("#position3");
				    secObj.empty();
				    thirdObj.empty();
				    $.ajax({
					   type: "POST",
					   dataType: "json",
					   url: "<%=basePath%>/setting/position/getSub",
					   data: {"pid":selectVal},
					   success: function(msg){
						   if (msg != null) {
					            $.each(msg, function(key, val) {
					            	secObj.append('<option value="' + val.id + '">' + val.i18nName + '</option>');
					            	if(key == 0){
					            		var currSecondId = val.id;
					            		$.ajax({
					 					   type: "POST",
					 					   dataType: "json",
					 					   url: "<%=basePath%>/setting/position/getSub",
					 					   data: {"pid":currSecondId},
					 					   success: function(msg){
					 						   if (msg != null) {
					 					            $.each(msg, function(key, val) {
					 					            	thirdObj.append('<option value="' + val.id + '">' + val.i18nName + '</option>');
					 					            });
					 					        }
					 					   }
					 					});
					            	}
					            });
					        }
					   }
					});
				});
				
				
				$("#position2").change(function(){
				    var secVal = $(this).val();
				    var thirdObj = $("#position3");
				    thirdObj.empty();
				    $.ajax({
					   type: "POST",
					   dataType: "json",
					   url: "<%=basePath%>/setting/position/getSub",
					   data: {"pid":secVal},
					   success: function(msg){
						   if (msg != null) {
					            $.each(msg, function(key, val) {
					            	thirdObj.append('<option value="' + val.id + '">' + val.i18nName + '</option>');
					            });
					        }
					   }
					});
				});
				
				
				
				$("#search_btn").click(function(){
			        var hadVal = 0;
			        var inputObj = $("#search_form input");
			        $.each(inputObj, function(val){
			            if ($.trim($(this).val()) != '') {
			                hadVal += 1;
			            }
			        });
			        if (hadVal > 0) {
			            $("#search_form").submit();
			        } else {
			            window.location.href="<%=basePath%>/offer/list/?rand=" + Math.random();
			        }
			    });
				
				
			});
			
			
		
		</script>
		
		
	</body>
</html>