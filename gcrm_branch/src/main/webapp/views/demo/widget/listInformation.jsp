<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>客户/代理编号</th>
                                                    <th >公司实体名称</th>
                                                    <th>客户类型</th>
                                                    <th>所属国家</th>
                                                    <th>代理地区</th>
                                                    <th>所属代理商</th>
                                                    <th>所属销售</th>
                                                    <th>审核状态</th>
                                                    <th>操作</th>
                                                    
                                                </tr>
                                            </thead>
                                            <tbody id="dataTable">
                                                <!-- 后台传入dom -->                                             
                                            </tbody>
                                        </table>

                                        <div class="col-md-9">
                                            <div class="col-md-1">
                                                <select class="form-control" name="companySize" id="pageNumLeft">
                                                    <option selected="" value="0">1</option>
                                                    <option value="1">2</option>
                                                    <option value="2">3</option>
                                                    <option value="3">4</option>
                                                </select>
                                            </div>
                                            <div class="col-md-2">
                                                <form class="form-horizontal"><p class="form-control-static">共15页300条记录</p></form>
                                            </div>
                                            
                                        </div>
                                        <div class="pull-right">
                                            <ul class="pagination pagination-sm">
                                              <li><a href="#">&laquo;</a></li>
                                              <li><a href="#">1</a></li>
                                              <li><a href="#">2</a></li>
                                              <li><a href="#">3</a></li>
                                              <li><a href="#">4</a></li>
                                              <li><a href="#">5</a></li>
                                              <li><a href="#">&raquo;</a></li>
                                            </ul>
                                        </div>
                                    
                                    </div><!--<div class="panel-body">-->
                                </div>
                                