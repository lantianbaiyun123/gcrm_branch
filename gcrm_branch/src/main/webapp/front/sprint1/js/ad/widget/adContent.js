//newAdContent.js
define(function(require, exports, module) {
  	// 模块代码
  	exports.save = function(options){
        var ops = this.ops, 
            $panel = ops.panel
            $panelBody = $panel.find(".panel-body");

        var $panelBodyForm = $panelBody.find("form");
        var formState = $.gcrm.validate_form( $panelBodyForm );
        if(formState == false){
            return;
        }
        var postData = $panelBodyForm.serialize();
        $.ajax({
            type: "POST",
            url: "/",
            data: postData,
            success:function(msg){
                /*!interface 
                    msg = {id:66}
                */
                var msg = {id:66};  //模拟数据
                $panelBody.load("widget/updateAdContent.jsp",function(){
                });
                options.success();
            }
        });
  	}

    exports.tempSave = function(options){
        var ops = this.ops, 
            $panel = ops.panel,
            $panelBody = $panel.find(".panel-body");
        var postData = $panelBody.find("form").serialize();
        $.ajax({
            type: "POST",
            url: "/",
            data: postData,
            success:function(msg){
                options.success();

            }
        });
        
    }

  	exports.edit = function(options){
         var ops = this.ops, 
            $panel = ops.panel,
            $panelBody = $panel.find(".panel-body");

        $.get("widget/newAdContent.jsp", function(data){
            var $el = $(data);
            //三级联动
            adContentFn.adAdressLinkage($el);
            //美化select
            $el.find('select').selectpicker({});
            //checkbox切换
            adContentFn.checkboxStatusChange();
            $("#codeStatues2").click(function(){
                $(".codeContent").addClass("hide");
            })
            $("#codeStatues1").click(function(){
                $(".codeContent").removeClass("hide");
            }) 
            $el.find(".select-tabs").selectTab();
            
            //绑定投放时间datetimepicker
            adContentFn.dateTimeToggle($el);
            $el.find(".date-time").each(function(){
                var locale = $(this).attr('locale') || {};
                $(this).datetimepicker({
                    language : locale,
                    weekStart: 1,
                    todayBtn:  0,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 2,
                    minView: 2,
                    forceParse: 0,
                    format: 'dd-mm-yyyy',
                    pickerPosition:"bottom-right"
                }).on('hide',function(){
                    //选择时间后txt-impt未消失
                    var valLength = $(this).val().length;
                    if(valLength > 0){
                        $(this).keyup();
                    }
                })

            });

            $panelBody.empty().append($el);

            //adContentFn.timeBucket($el, 10);

            options.success();

        });
  	}

  	exports.cancel = function(options){
        var ops = this.ops, 
            $panel = ops.panel,
            $panelBody = $panel.find(".panel-body");
        $panelBody.load("widget/updateAdContent.jsp",function(){
            options.success();
        });
  	}

    exports.del = function(options){
        /*!interface 
            id是保存时被绑定在当前对象上的。
        */
        var id = this.id;
        $.post("/", {id: id}, options.success);
    };

  	exports.init = function(options){
  		var ops = this.ops;    
        $.validator.setDefaults({
            errorPlacement: function(error,element) {
                var elName=$(element).attr("name");
                if(elName=="owner"||elName=="party"||elName=="condition"){
                    element.parents('.showError').find(".help-block").html(error);
                }else{
                    element.parents('.form-group').find(".help-block").html(error);
                }
            }
        });
        
  	}

    var adContentFn={
        checkboxStatusChange:function(){
            //全选或全不选
            $("#checkAll").click(function () {
                var flag = $("#checkAll").prop("checked");
                $("input[name='adDays']").each(function () {
                    this.checked=flag;
                });
                var errorLength=$('.txt-impt').length;
            });
            $("input[name='adDays']").each(function () {
                $(this).click(function () {
                    if ($("input[name='adDays']:checked").length == $("input[name='adDays']").length) {
                        $("#checkAll")[0].checked=true;
                    }else{
                        $("#checkAll").removeAttr("checked");
                    }
                });
            });
        },
        materialsFileupload:function($el){
            var fileNum=0;
            $el.fileupload({
                dataType: 'json',
                add: function (e, data) {
                    data.submit();
                },
                send:function(e,data){
                    data.fileNum=fileNum;
                    data.context = [
                        '<tr>',
                        '    <td class="col-md-2"></td>',
                        '    <td><a href="###">删除</a></td>',
                        '</tr>'
                    ].join("");
                    var $tpl = $(data.context);
                    $tpl.find("td:eq(0)").text(data.files[0].name);
                    data.context = $tpl.insertBefore('#materialsTable>tbody>tr:last-child');
                    fileNum++;
                }
            });
        },
        dateTimeToggle:function($el){
            var adTimeDom = ['<div class="col-md-12 resetPadd">',
                                '<div class="col-md-9 resetPadd mb10">',
                                    '<div class="col-md-5 resetPadd">',
                                        '<input type="text" required locale="zh-CN" id="" name="startName" class="form-control date-time" placeholder="">',
                                    '</div>',
                                    '<label class="col-md-1 form-control-static pull-left">-</label>',
                                '<div class="col-md-5 resetPadd">',
                                    '<input type="text" required locale="zh-CN" id="" name="endName" class="form-control date-time" placeholder="">',
                                '</div>',
                            '</div>',
                            '<a class="adDateTime col-md-3" href="#"><p class="text-success  form-control-static"><strong>+ </strong>新增时段</p></a>',
            '</div>'].join("");

            

            var deTimeDom = ['<a class="deDateTime col-md-3" href="#">',
                            '<p class="text-success  form-control-static">',
                            '<strong>- </strong>删除时段</p></a>'].join("");
            var $deTimeDom = $(deTimeDom);

            $el.delegate('.adDateTime','click',function(){
                
                $(this).replaceWith(deTimeDom);
                var $adTimeDom = $(adTimeDom);
                $adTimeDom.find(".date-time").each(function(){
                    var locale = $(this).attr('locale') || {};

                    $(this).datetimepicker({
                        language : locale,
                        weekStart: 1,
                        todayBtn:  0,
                        autoclose: 1,
                        todayHighlight: 1,
                        startView: 2,
                        minView: 2,
                        forceParse: 0,
                        format: 'dd-mm-yyyy',
                        pickerPosition:"bottom-right"
                    }).on('hide',function(){
                        //选择时间后txt-impt未消失
                        var valLength = $(this).val().length;
                        if(valLength > 0){
                            $(this).keyup();
                        }
                    });
                });
                $el.find('.dateTimePicker').append( $adTimeDom );
                return false;
            });

            $el.delegate('.deDateTime','click',function(){
                $(this).parent('.col-md-12').remove();
                return false;
            });
        },
        timeBucket:function($el, num){
            $el.delegate('.date-time','keyup',function(){
                var focusElVal=$(this).val().length;
 
                if(focusElVal = num){ 
                    var thisName = $(this).attr('name');
                    var dateTimeVal = $(this).closest('.col-md-9').find('.date-time:not(:input[name="'+thisName+'"])');
                    if(dateTimeVal = num){

                    }
                }
            })
        },
        adAdressLinkage:function($el){
            var $adChanel = $el.find("#adChanel");
            var $adDistrict = $el.find("#adDistrict");
            var $adAdress = $el.find("#adAdress");

            var adChanel = {"3":"频道","0":"新闻","1":"教育"};
            var adDistrict = {"0":"区域","1":"首页","2":"专题"};
            var adAdress = {"-1":"位置","0":"头部","1":"底部"};

            $.each(adChanel,function(index,chanel){
                appendOptionTo($adChanel,index,chanel);
            });
            //频道
            $adChanel.change(function(){
                $adDistrict.html("");
                $adAdress.html("");
                var chanelVal = $("option:selected",this).val();
                //$.ajax({
                    //type: "POST",
                    //url: "/",
                    //data: {"chanel.val" : chanelVal},
                    //dataType: "json",
                    //success: function(msg){
                       //if(msg.success){
                            $.each(adDistrict,function(index,district){
                                appendOptionTo($adDistrict,index,district);
                            });
                            $adDistrict.selectpicker('refresh');
                       //} else { 
                          
                        //}
                    //}
                //})

            });
            //区域
            $adDistrict.change(function(){
                var districtVal = $("option:selected",this).val();
                $adAdress.html("");
                //$.ajax({
                    //type: "POST",
                    //url: "/",
                    //data: {"district.val" : districtVal},
                    //dataType: "json",
                    //success: function(msg){
                       //if(msg.success){
                            $.each(adAdress,function(index,adress){
                                appendOptionTo($adAdress,index,adress);
                            });
                            $adAdress.selectpicker('refresh');
                       // } else { 
                          
                        //}
                    //}
                //}) 
            });
            //位置
            $adAdress.change(function(){
                var districtVal = $el.find("#adChanel option:selected").val(),
                    districtVal = $el.find("#adDistrict option:selected").val(),
                    adressVal = $el.find("option:selected",this).val();
                //$.ajax({
                    //type: "POST",
                    //url: "/",
                    //data: {"adChanel.val" : districtVal,"district.val":districtVal,"adress.val":adressVal},
                    //dataType: "json",
                    //success: function(msg){
                       //if(msg.success){
                           $("#material").load("widget/newAdContentMaterial.jsp",function(){
                                var codeContent = $el.find(".codeContent"),
                                    uploadSize = $el.find(".uploadSize"),
                                    fileuploadBtn = $el.find("#fileupload"),
                                    codeStatues2 = $el.find("#codeStatues2"),
                                    codeStatues1 = $el.find("#codeStatues1");
                                //上传文件
                                adContentFn.materialsFileupload( fileuploadBtn );
                                //
                                codeStatues1.click(function(){
                                    codeContent.removeClass("hide");
                                })

                                codeStatues2.click(function(){
                                    codeContent.addClass("hide");
                                })
                                console.log(uploadSize);
                                console.log(adContentFn);
                                //物料详细信息
                                uploadSize.text("物料说明：");
                           })
                       // } else { 
                          
                        //}
                    //}
                //})
            });
            function appendOptionTo($select,index,v){
                var $option = $("<option>").val(index).text(v);
                if(index =="-1"){
                    $option.attr("selected","selected");
                }
                $option.appendTo($select);
            }
        }
    }
});

