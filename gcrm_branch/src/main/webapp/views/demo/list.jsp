<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
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

    </head>
    <body>
        <fis:require name="resources/lib/bootstrap/css/bootstrap.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-select.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-extend.css"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload.css"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload-ui.css"></fis:require>
        <fis:require name="resources/css/common.css"></fis:require>

        <fis:require name="resources/lib/js/jquery.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.iframe-transport.js"></fis:require>
        <fis:require name="resources/lib/js/jquery-validate.js"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/js/jquery.fileupload.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>

        <div class="container">
            <%@ include file="./widget/nav.jsp"%>
        </div>
        <div class="container">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">客户管理</a></li>
                <li class="active">客户信息列表</li>
            </ol>
        </div>
        <div class="container">                   
            <div class="panel panel-gcrm">
                <div class="panel-heading">客户信息列表
                    <div class="pull-right">
                        <a href="#"><button type="button" class="btn btn-primary">+ 创建新用户</button></a>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="search-block">
                        <%@ include file="./widget/listSearch.jsp"%>
                    </div>
                    <%@ include file="./widget/listInformation.jsp"%>
                </div>
            </div>
            
            
                   
        </div>
        <!--销售转移-->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <form role="form" class="form-horizontal">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">销售转移</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group row">
                                <p  class="form-control-static col-md-2">客户：</p>
                                <p  class="form-control-static col-md-9 clientName"></p>
                            </div>
                            <div class="form-group row">
                                <p  class="form-control-static col-md-2">原销售：</p>
                                <p  class="form-control-static col-md-5 marketUser"></p>
                            </div>
                            <div class="form-group row clearfix">
                                <p class="form-control-static col-md-2">转移至：</p>
                                <div class="col-md-3"><input type="text" class="form-control seachName"></div>
                                <div class="col-md-1 btn"><span class="glyphicon"></span></div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" data-dismiss="modal" class="btn btn-primary">确认</button>
                        </div>
                </div><!-- /.modal-content -->
                </form>
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <!--添加账号-->
        <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <form role="form" class="form-horizontal">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">添加账号</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group row">
                            <p  class="form-control-static col-md-2">客户：</p>
                            <p  class="form-control-static col-md-5 clientName"></p>
                        </div>
                        <div class="form-group row clearfix">
                            <p class="col-md-2 form-control-static">账户用户名：</p>
                            <div class="col-md-3"><input type="text" class="form-control"></div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary">确认</button>
                    </div>
                </form>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <!--撤回审批-->
        <div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content modal-footer">     
                    <div class="col-md-12"> 
                        <h3 class="clearfix">
                            <div class="col-md-4 moalTitle">确认撤回审批</div>
                            <div class="col-md-8">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary">确认</button>
                            </div>
                        </h3>
                    </div> 
                </div>
            </div>
        </div>
        <!--发送催办-->
        <div class="modal fade" id="myModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content modal-footer">     
                    <div class="col-md-12"> 
                        <h3 class="clearfix">
                            <div class="col-md-4 moalTitle">催办已发送</div>
                        </h3>
                    </div> 
                </div>
            </div>
        </div>
        <!--作废-->
        <div class="modal fade" id="myModal5" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content modal-footer">     
                    <div class="col-md-12"> 
                        <h3 class="clearfix">
                            <div class="col-md-4 moalTitle">确认作废此客户</div>
                            <div class="col-md-8">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary invalidBtn">确认</button>
                            </div>
                        </h3>
                    </div> 
                </div>
            </div>
        </div>
    </body>
    
    <fis:require name="resources/js/common.js"></fis:require>
    
    <fis:require name="resources/js/list.js"></fis:require>
    <fis:scripts/>
</fis:html>





