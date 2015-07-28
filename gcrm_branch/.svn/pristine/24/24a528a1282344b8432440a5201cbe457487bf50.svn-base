<!--基本信息-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="newAdContentForm" method="post" class="form-horizontal clearfix">
    <h5 class="ptb10 pl10 bdt">基础信息</h5>
    <div class="form-group">
        <label class="control-label col-md-2"><span class="txt-impt">*</span>广告主：</label>
        <div class="col-md-3">
            <input placeholder="" type="text" class="form-control" id="" name="adName" required />
        </div>
        <div class="col-md-3 help-block"></div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2"><span class="txt-impt">*</span>内容描述：</label>
        <div class="col-md-3">
            <input placeholder="" type="text" class="form-control" id="companyDescribe" name="companyDescribe"required />
        </div>
        <div class="col-md-3 help-block"></div>
    </div>

    <h5 class="ptb10 pl10 bdt">位置信息</h5>
    <div class="form-group">
        <label class="control-label col-md-2">投放平台：</label>
        <div class="col-md-3 adPlatform">

        </div>
        <div class="col-md-3 help-block"></div>
    </div>
    <div class="form-group platformaGentTypeEl hide">
        <label class="control-label col-md-2">投放地点：</label>
        <div class="col-md-6 platformaGentType">

        </div>
        <div class="col-md-3 help-block"></div>
    </div>

    <div class="form-group adChanelEl hide">
        <label class="control-label col-md-2">投放位置：</label>
        <div class="col-md-2">
            <select class="form-control validate" id="adChanel" required name="adChanel">

            </select>
        </div>
        <div class="col-md-2">
            <select class="form-control validate" id="adDistrict" required  name="adDistrict">
                <option value="-1">区域</option>

            </select>
        </div>
        <div class="col-md-2">
            <select class="form-control validate" id="adAdress" required name="adAdress">
                <option value="-1">位置</option>

            </select>
        </div>
        <div class="col-md-1 resetPadd">
           <p class="form-control-static"><a href="#" class="textDeco">位置指引</a></p>
        </div>
        <div class="col-md-2 help-block resetPadd"></div>
    </div>


    <h5 class="ptb10 pl10 bdt">投放时间</h5>
    <div class="form-group">
        <label class="control-label col-md-2">投放时间段：</label>
        <div class="col-md-6 dateTimePicker">
            <div class="col-md-12 resetPadd">
                <div class="col-md-9 resetPadd mb10">
                    <div class="col-md-5 resetPadd">
                        <input placeholder="" type="text" class="form-control date-time" name="startName" id="" name="" locale="zh-CN" required>
                    </div>
                    <label class="col-md-1 form-control-static pull-left">-</label>
                    <div class="col-md-5 resetPadd">
                        <input placeholder="" type="text" class="form-control date-time" name="endName" id="" name="" locale="zh-CN" required>
                    </div>
                </div>
                <a href="#" class="adDateTime col-md-3"><p class="text-success  form-control-static"><strong>+ </strong>新增时段</p></a>
            </div>
        </div>
        <div class="col-md-3 help-block"></div>

    </div>

    <div class="form-group">
        <label class="control-label col-md-2">插单时段：</label>
        <div class="col-md-10">
            <div class="col-md-12 clearfix row">
                <div class="col-md-2 row">
                    <label class="checkbox checkbox-inline">
                        <input type="checkbox" value="0" id="checkAll" />全选
                    </label>
                </div>
                <div class="col-md-2">
                    <p class="form-control-static">共<span class="adDayschecked"> </span>天</p>
                </div>
                <div class="col-md-3 help-block"></div>
            </div>
            <div class="col-md-12 row adDaysEles">

                <div class="col-md-2 row">
                    <label class="checkbox checkbox-inline">
                        <input type="checkbox" name="adDays">12月20日
                    </label>
                </div>

            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-2">总投放天数：</label>
        <div class="col-md-3">
           <p class="form-control-static" name="adAllDays" ></p>
        </div>
        <div class="col-md-3 help-block"></div>
    </div>

    <h5 class="ptb10 pl10 bdt">报价情况</h5>

    <div class="select-tabs">
        <div class="form-group">
            <label class="control-label col-md-2">计费方式：</label>
            <div class="col-md-2">
               <select data-toggle="select-tab" class="form-control" name="accountingType" required >
                    <option value="-1">请选择</option>
                    <option value="adCpt">CPT-天</option>
                    <option value="adCpm">CPM</option>
                    <option value="home">游戏变现</option>
                    <option value="profile">其他</option>
                </select>
            </div>
            <div class="col-md-3 help-block row"></div>
        </div>
    </div>

    <!-- Tab panes -->
    <div class="select-tab-content">
        <div class="select-tab-panel" id="adCpt">
            <div class="form-group">
                <label class="control-label col-md-2">刊例价：</label>
                <div class="col-md-2">
                    <p class="form-control-static" required></p>
                </div>
                <div class="col-md-3 help-block resetPadd"></div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">客户报价：</label>
                <div class="col-md-2">
                     <div class="col-md-12 input-group">
                         <span class="input-group-btn">
                             <button class="btn btn-default" type="button">$</button>
                         </span>
                         <input type="text" class="form-control " name="adQuote" required />
                     </div>
                </div>
                <div class="col-md-3 help-block resetPadd"></div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">折扣：</label>
                <div class="col-md-2">
                    <input type="text" class="form-control" name="discount" required>
                </div>
                <div class="col-md-3 help-block resetPadd"></div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">预算：</label>
                <div class="col-md-2">
                     <div class="col-md-12 input-group">
                         <span class="input-group-btn">
                             <button class="btn btn-default" type="button">$</button>
                         </span>
                         <input type="text" class="form-control " name="adQuote" required />
                     </div>
                </div>
                <div class="col-md-3 help-block resetPadd"></div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">总价：</label>
                <div class="col-md-2">
                    <p class="form-control-static" required></p>
                </div>
                <div class="col-md-3 help-block resetPadd"></div>
            </div>
        </div>
        <div class="select-tab-panel" id="adCpm">
            <div class="form-group">
                <label class="control-label col-md-2">刊例价：</label>
                <div class="col-md-2">
                    <p class="form-control-static" required></p>
                </div>
                <div class="col-md-3 help-block resetPadd"></div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">客户报价：</label>
                <div class="col-md-2">
                     <div class="col-md-12 input-group">
                         <span class="input-group-btn">
                             <button class="btn btn-default" type="button">$</button>
                         </span>
                         <input type="text" class="form-control " name="adQuote" required />
                     </div>
                </div>
                <div class="col-md-3 help-block resetPadd"></div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">折扣：</label>
                <div class="col-md-2">
                    <input type="text" class="form-control" name="discount" required>
                </div>
                <div class="col-md-3 help-block resetPadd"></div>
            </div>
            <h5 class="ptb10 pl10 bdt">投放量</h5>
            <div class="form-group">
                <label class="control-label col-md-2">日投放量预估：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">总投放量：</label>
                <div class="col-md-3">
                    <input type="text" class="form-control">
                </div>
            </div>
        </div>
        <div class="select-tab-panel" id="home">
            <div class="form-group">
                <label class="control-label col-md-2">分成比例：</label>
                <div class="col-md-2">
                   <p class="form-control-static">我方：40%</p>
                </div>
                <div class="col-md-2">
                   <p class="form-control-static">客户：40%</p>
                </div>
                <div class="col-md-3 help-block resetPadd"></div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-2">实际分成比例：</label>
                <div class="col-md-5">
                    <div class="col-md-6 showError row">
                        <div class="col-md-12 input-group">
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button">我方</button>
                            </span>
                            <input type="text" class="form-control proportionChange" name="owner" required />
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button">%</button>
                            </span>
                        </div>
                        <div class="col-md-12 help-block resetPadd"></div>
                    </div>
                    <div class="col-md-6 showError">
                        <div class="col-md-12 input-group">
                            <span class="input-group-btn">
                                <button class="btn btn-default proportionChange" type="button" >客户</button>
                            </span>
                            <input type="text" class="form-control" name="party" required>
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="button">%</button>
                            </span>
                        </div>
                        <div class="col-md-12 help-block resetPadd"></div>
                    </div>
                </div>
                <div class="col-md-5 showError resetMrLeft">
                    <div class="input-group">
                      <span class="input-group-btn">
                        <button class="btn btn-default" type="button">分成条件</button>
                      </span>
                      <input type="text" class="form-control" name="condition" required />
                    </div>
                    <div class="col-md-12 help-block resetPadd"></div>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-2">是否为预估值：</label>
                <div class="col-md-2">
                    <label class="radio radio-inline">
                        <input type="radio" name="adEstimate"  value="0" checked="checked">是
                    </label>
                    <label class="radio radio-inline">
                        <input type="radio" name="adEstimate"  value="1">否
                    </label>
                </div>
                <div class="col-md-5">
                    <p class="form-control-static gray">合同中没有明确约定签约方的分成比例的，则勾选预估值</p>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-2">分成条件说明：</label>
                <div class="col-md-5">
                    <textarea class="form-control" name="adExplain" required rows="3" cols="30" placeholder="其他说明"></textarea>
                </div>
                <div class="col-md-3 help-block"></div>
            </div>
        </div>

        <div class="select-tab-pane" id="profile">
            <div class="form-group">
                <label class="control-label col-md-2">刊例价：</label>
                <div class="col-md-2">
                    <p class="form-control-static" required></p>
                </div>
                <div class="col-md-3 help-block resetPadd"></div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">客户报价：</label>
                <div class="col-md-2">
                     <div class="col-md-12 input-group">
                         <span class="input-group-btn">
                             <button class="btn btn-default" type="button">$</button>
                         </span>
                         <input type="text" class="form-control " name="adQuote" required />
                     </div>
                </div>
                <div class="col-md-3 help-block resetPadd"></div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">折扣：</label>
                <div class="col-md-2">
                    <input type="text" class="form-control" name="discount" required>
                </div>
                <div class="col-md-3 help-block resetPadd"></div>
            </div>
        </div>
    </div>
    <h5 class="ptb10 pl10 bdt">物料信息</h5>
    <div id="material">

    </div>
</form>




