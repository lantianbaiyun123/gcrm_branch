<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<fis:require name="resources/lib/bootstrap/css/bootstrap.css"></fis:require>
<fis:require name="resources/lib/bootstrap/css/bootstrap-select.css"></fis:require>
<fis:require
	name="resources/lib/bootstrap/css/bootstrap-datetimepicker.min.css"></fis:require>
<fis:require name="resources/lib/bootstrap/css/bootstrap-extend.css"></fis:require>
<fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload.css"></fis:require>
<fis:require
	name="resources/lib/jqueryUpload/css/jquery.fileupload-ui.css"></fis:require>
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
<fis:require
	name="resources/lib/bootstrap/js/bootstrap-datetimepicker.min.js"></fis:require>
<fis:require
	name="resources/lib/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></fis:require>
<fis:require name="resources/js/common.js"></fis:require>

<input type="hidden" name="activityId" value="${activityId}">
<input type="hidden" name=processId value="${processId}">
<div class="form-group">
	<label class="control-label col-md-2">报价低于八折：</label>
	<div class="col-md-3">
		<label class="radio radio-inline"> <c:choose>
				<c:when test="${isLowEightDiscount == '1'}">
	    					是
	    				</c:when>
				<c:otherwise>
	    					否
	    				</c:otherwise>
			</c:choose>
		</label>
	</div>
</div>
</br>


<div class="form-group">
	<label class="control-label col-md-2">报价低于五折：</label>
	<div class="col-md-3">
		<label class="radio radio-inline"> <c:choose>
				<c:when test="${isLowFiveDiscount == '1'}">
	    					是
	    				</c:when>
				<c:otherwise>
	    					否
	    				</c:otherwise>
			</c:choose>
		</label>
	</div>
</div>
</br>


<div class="form-group">
	<label class="control-label col-md-2">是否有插单：</label>
	<div class="col-md-3">
		<label class="radio radio-inline"> <c:choose>
				<c:when test="${isBreakOrder == '1'}">
	    					是
	    				</c:when>
				<c:otherwise>
	    					否
	    				</c:otherwise>
			</c:choose>
		</label>
	</div>
</div>
</br>

<div class="form-group">
	<label class="control-label col-md-2">未备案刊例价：</label>
	<div class="col-md-3">
		<label class="radio radio-inline"> <c:choose>
				<c:when test="${isNoPublishPrice == '1'}">
	    					是
	    				</c:when>
				<c:otherwise>
	    					否
	    				</c:otherwise>
			</c:choose>
		</label>
	</div>
</div>
</br>

<div class="form-group">
	<label class="control-label col-md-2">没有分成比例：</label>
	<div class="col-md-3">
		<label class="radio radio-inline"> <c:choose>
				<c:when test="${isNoSplitPrice == '1'}">
	    					是
	    				</c:when>
				<c:otherwise>
	    					否
	    				</c:otherwise>
			</c:choose>
		</label>
	</div>
</div>
</br>


<div class="form-group">
	<label class="control-label col-md-2">实际分成比例低于我方分成比例：</label>
	<div class="col-md-3">
		<label class="radio radio-inline"> <c:choose>
				<c:when test="${isRealLowMySplitPrice == '1'}">
	    					是
	    				</c:when>
				<c:otherwise>
	    					否
	    				</c:otherwise>
			</c:choose>
		</label>
	</div>
</div>
</br>