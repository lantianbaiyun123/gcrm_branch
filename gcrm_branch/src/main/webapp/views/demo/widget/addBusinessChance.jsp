<!--业务机会-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="form-group">
    <label class="control-label col-md-2">在线广告投放预算：</label>
    <div class="col-md-3">
        <div class="row">
            <div class="col-md-3">
                <select class="form-control" class="col-mk">
                    <option value="0">￥</option>
                    <option value="1">$</option>
                </select>
            </div>
            <div class="col-md-9">
                <input type="text" class="form-control">
            </div>
        </div><!--row-->
    </div>
</div>
<div class="form-group">
    <label class="control-label col-md-2">预算对应投放周期：</label>
    <div class="col-md-3">
        <input type="text" placeholder="请输入注册时间(dd-mm-yyyy)" name="registerTime" id="registerTime" class="form-control date-time">
    </div>
</div>
<div class="form-group">
    <label class="control-label col-md-2">可接受的付款方式：</label>
    <div class="col-md-2">
        <label class="radio radio-inline">
            <input type="radio" name="agentType" value="0">预付款
        </label>
        <label class="radio radio-inline">
            <input type="radio" name="agentType" value="1">后付款
        </label>
    </div>

    <div class="col-md-3">
        <div class="input-group">
            <span class="input-group-addon">需要的付款账期</span>
            <input type="text" class="form-control">
            <span class="input-group-addon">天</span>
        </div>
    </div>
</div>
<div class="form-group">
    <label class="control-label col-md-2">期望计费模式：</label>
    <div class="col-md-5">
        <label class="checkbox checkbox-inline">
            <input type="checkbox" name="agentType" value="0">分成
        </label>
        <label class="checkbox checkbox-inline">
            <input type="checkbox" name="agentType" value="1">CPC
        </label>
        <label class="checkbox checkbox-inline">
            <input type="checkbox" name="agentType" value="1">CPT
        </label>
        <label class="checkbox checkbox-inline">
            <input type="checkbox" name="agentType" value="1">CPM
        </label>
        <label class="checkbox checkbox-inline">
            <input type="checkbox" name="agentType" value="1">CPS
        </label>
        <label class="checkbox checkbox-inline">
            <input type="checkbox" name="agentType" value="1">CPA
        </label>
    </div>
</div>
<div class="form-group">
    <label class="control-label col-md-2">合作业务类型：</label>
    <div class="col-md-2">
        <label class="checkbox checkbox-inline">
            <input type="checkbox" name="agentType" value="0">变现
        </label>
        <label class="checkbox checkbox-inline">
            <input type="checkbox" name="agentType" value="1">销售
        </label>
    </div>
</div>
<div class="form-group">
    <label class="control-label col-md-2">投放平台：</label>
    <div class="col-md-10">
        <label class="checkbox checkbox-inline">
            <input type="checkbox" name="agentType" value="0">国际化hao123
        </label>
        <label class="checkbox checkbox-inline">
            <input type="checkbox" name="agentType" value="1">国际化hao123
        </label>
    </div>
</div>
<div class="form-group">
    <label class="control-label col-md-2">&nbsp;</label>
    <div class="col-md-10">
        <label class="checkbox checkbox-inline">
            <input type="checkbox" name="agentType" value="0">国际化hao123
        </label>
        <label class="checkbox checkbox-inline">
            <input type="checkbox" name="agentType" value="1">国际化hao123
        </label>
    </div>
</div>
<div class="form-group">
    <label class="control-label col-md-2">其他说明：</label>
    <div class="col-md-5">
        <textarea placeholder="其他说明" cols="30" rows="3" class="form-control"></textarea>
    </div>
</div>