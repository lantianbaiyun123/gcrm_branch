<!--基本信息-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form action="" class="form-horizontal" class="baseInfoForm">
  <h5 class="bdt ptb10 pl10 ">客户基本信息</h5> 
  <div class="form-group">
      <label class="control-label col-md-2">公司名称：</label>
      <div class="col-md-4">
          <input placeholder="请输入一个公司实体名称" type="text" class="form-control" id="companyName" name="companyName" data-describedby="companyNameTitle" data-description="companyName" data-required="true">
          <input type="hidden" id="customerNumber" name="customerView.customer.customerNumber">
      </div>
      <div class="col-md-5 help-block">“广告主-非直客”类型的客户，此处需填写代理的公司名称</div>
  </div>
  <div class="hide" id="companyInfo">
      <div class="form-group">
          <label class="control-label col-md-2">客户类型：</label>
          <p class="form-control-static col-md-3" id="customerType"></p>
          <label class="control-label col-md-2">所属销售：</label>
          <p class="form-control-static col-md-4" id="sale"></p>
      </div>
      <div class="form-group">
          <label class="control-label col-md-2">代理类型：</label>
          <p class="form-control-static col-md-3" id="agentType"></p>
          <label class="control-label col-md-2">所属国家：</label>
          <p class="form-control-static col-md-4" id="country"></p>
      </div>
      <div class="form-group">
          <label class="control-label col-md-2">代理国家：</label>
          <p class="form-control-static col-md-3" id="agentCountry"></p>
          <label class="control-label col-md-2">代理地区：</label>
          <p class="form-control-static col-md-4" id="agentArea"></p>
      </div>
  </div>


    <h5 class="bdt ptb10 pl10 ">所属合同</h5> 
    <div class="form-group">
    <label class="control-label col-md-2">是否有所属合同：</label>
    <div class="col-md-3">
      <label class="radio radio-inline">
        <input type="radio" value="1" name="hasContract" checked="checked" />  
        是
      </label>
      <label class="radio radio-inline">
        <input type="radio" value="0" name="hasContract" />  
        否
      </label>
    </div>
    </div>
    <div id="agentTypeBox">
        <div class="form-group">
          <label class="control-label col-md-2" for="registerTime">合同编号：</label>
          <div class="col-md-2">
              <input type="text" class="form-control" id="contractNumber" name="contract.number" placeholder="合同编号" />  
          </div>
          <div class="col-md-3 help-block"></div>
        </div>
        <div id="contractInfo" class="hide">
              <div class="form-group">
                  <label class="control-label col-md-2" for="registerTime" >合同概要：</label>
                  <p class="form-control-static col-md-4" id="contractSummary"></p>
              </div>
              <div class="form-group">
                  <label class="control-label col-md-2" for="registerTime" >合同类型：</label>
                  <p class="form-control-static col-md-4" id="contractType"></p>
              </div>
              <div class="form-group">
                  <label class="control-label col-md-2" for="belongSales" >合同有效期：</label>
                  <p class="form-control-static col-md-4" id="contractexpDate"></p>
              </div>
        </div>
    </div>
    <h5 class="bdt ptb10 pl10 ">所属合同</h5>
    <div class="form-group">
        <label class="control-label col-md-2" for="registerTime">广告预算：</label>
        <div class="col-md-2">
            <div class="input-group">
                <span class="input-group-addon">$</span>
                <input type="text" class="form-control" id="" name="advertiseSolutionView.advertiseSolution.budget" />
            </div>  
        </div>
        <div class="col-md-3 help-block"></div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2" for="registerTime">投放周期：</label>
        <div class="col-md-2">
            <input type="text" class="form-control date-time" id="" name="advertiseSolutionView.advertiseSolution.periodStrat" placeholder="" />
        </div>
        <label class="pull-left control-label">-</label>
        <div class="col-md-2">
            <input type="text" class="form-control date-time" id="" name="advertiseSolutionView.advertiseSolution.periodEnd" placeholder="" />
        </div>
        <div class="col-md-3 help-block"></div>
    </div>
</form>

