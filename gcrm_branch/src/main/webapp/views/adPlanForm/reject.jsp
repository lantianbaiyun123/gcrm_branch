<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="form-group">
   <label class="control-label col-md-2">审批意见：</label>
   <div class="col-md-10">
   	<input class="bpm_update " type="radio" value="1" id="agreeRadio" name="approved" checked="checked" style="width:auto;">
		<span class="bpm_locale" lang="agree">同意</span>
		<input class="bpm_update " type="radio" value="0" id="rejectRadio" name="approved" style="width:auto;" >
		<span class="bpm_locale" lang="reject">打回</span> 
		<div id="dynreject_1_agree" style="margin-top: 5px;">
			<span>备注</span>:
			<textarea name="reason" id="BPM_AGREE_REASON" cols="60" rows="3"></textarea>
		</div>
		<div id="dynreject_1" style="display: none;margin-top: 5px;">
			<span class="bpm_locale" lang="reject_to">打回到</span>&nbsp;&nbsp;:
			<select name="BPM_REJECT_TARGET" id="BPM_REJECT_TARGET" disabled="disabled" style="margin-bottom: 5px;">
				<c:forEach items="${actDefines}" var="actDefine">
					<option value="${actDefine.activityDefineId}">${actDefine.activityName}</option>
					</c:forEach>
				</select>
				<br>
				<!-- <span class="bpm_locale" lang="reject_reason">打回原因</span>:
				<textarea name="BPM_REJECT_REASON" cols="60" rows="3" id="BPM_REJECT_REASON" disabled="disabled"></textarea> -->
			</div>
		</div>
</div>
<script type="text/javascript">
$("#agreeRadio").click(function(){
	$("#BPM_REJECT_TARGET").attr("disabled","disabled");
	//$("#BPM_REJECT_REASON").attr("disabled","disabled");
	//$("#BPM_AGREE_REASON").removeAttr("disabled");
	//$("#dynreject_1_agree").css("display","");
	//$("#dynreject_1").css("display","none");
});
$("#rejectRadio").click(function(){
	$("#BPM_REJECT_TARGET").removeAttr("disabled");
	//$("#BPM_REJECT_REASON").removeAttr("disabled");
	//$("#BPM_AGREE_REASON").attr("disabled","disabled");
	//$("#dynreject_1").css("display","");
	//$("#dynreject_1_agree").css("display","none");
});
</script>