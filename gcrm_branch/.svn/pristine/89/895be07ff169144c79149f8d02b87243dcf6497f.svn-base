//new.js
seajs.config({
  base: "../../resources/js/ad/"
})
seajs.use(["/gcrm/resources/js/ad/widget/baseInfo.js", "/gcrm/resources/js/ad/widget/adContent.js"], function(baseInfoBtnEvent, adContentBtnEvent){
	
	/*!
		extend dom object 
		add attribute g_disabled for disabled element;
		add function g_disable and g_able, so change element's attrbute.
	*/
	var __extendDom = function($el){

		/*if($el.length != 1){
			return;
		}*/

		$el.g_disabled = false;

        //扩展对象的属性，g_是为了避免和jquery有冲突。
        $el.g_disable = function(ishidden){
	        if(!!ishidden){
	                this.addClass("hide");
	            }
	            //this.attr("disabled", "disabled");
	            this.addClass("disabled");
	            this.g_disabled = true;
	        };

        $el.g_able = function(){
            if(this.is(":hidden")){
                this.removeClass("hide");
            }
            //this.removeAttr("disabled");
            this.removeClass("disabled");
            this.g_disabled = false;
        };
        return $el;
	};

	//对象集合，将baseinfo he 
	var baseInfo, __adCollect = [];

	var $J_addAdContentBtn = __extendDom($(".J_addAdContentBtn"));
	var $J_submit = __extendDom($(".J_submit"));

	/*!
		以下这部分代码没想好。
	*/
	var __save = function(){
		$("#editBaseInfo").add(".J_editAdContent").removeClass("hide");
		$J_submit.g_able();
		$J_addAdContentBtn.g_able();
	}
	var __edit = function(){
		$("#editBaseInfo").add(".J_editAdContent").addClass("hide");
		$J_submit.g_disable();
		$J_addAdContentBtn.g_disable();
	}
	var __cancel = function(){
		$("#editBaseInfo").add(".J_editAdContent").removeClass("hide");
		$J_submit.g_able();
		$J_addAdContentBtn.g_able();
	};

	var __getEditingModule = function(){
		////console.log(baseInfo.editing, "baseInfo.editing")
		var result = "";
		if(baseInfo.editing){
			result = baseInfo.ops.panel.find(".panel-heading").text();
		}else{
			$.each(__adCollect, function(i, item){
				//console.log(this, i, item, "__getEditingModule");
				if( this.editing ){
					result = this.ops.panel.find(".panel-heading").text();
				}
			})
		}
		return result;
	}

	//【禁用按钮】hover时提示未保存模块
	$.each([$J_submit, $J_addAdContentBtn], function(){
		
		var $self = this, $parent = this.parent();
		$parent.popover({
			content:function(){
				var result = __getEditingModule();
				return '请先保存或取消' + result ;
			},
			trigger:'hover',
			html:true,
			placement:"auto top",
			delay: { show: 200, hide: 100 }
		});

		$parent.on("show.bs.popover", function(){
			//console.log( $self, $self.g_disabled, "------" );
			if( !$self.g_disabled ){
				return false;
			}
		})

	})

	var BaseInfo = function(_kernel, options){
		var ops = $.extend({
            "save":null,
			"tempSave":null,
			"edit":null,
			"cancel":null
        }, options);

		var self = this;
		$.each(ops, function(i, item){
			ops[i] = !!this.jquery ? this : $(this);
			__extendDom(ops[i]);
		});

		self.ops = ops;
		self.id = 0;
		self.editing = false;

		ops.save.on("click", function(){
			////console.log(this, ops.save.g_disabled, "------- g_disabled");
			if(!ops.save.g_disabled){
				_kernel.save.call(self, {
					success:function(id){
						ops.save.parent().addClass("hide");
						ops.save.g_disable(true);
						ops.tempSave.g_disable(true);
						ops.cancel.g_disable(true);
						ops.edit.text("修改").g_able();
						//显示所有修改按钮
						__save();
						
						if(self.id == 0){
							__adCollect[0].ops.edit.click();
						}
						self.id = id;
						self.editing = false;

					}
				});
			}
		});

		ops.tempSave.popover({
    		content:'暂存成功',
    		trigger:'manual',
    		html:true,
    		placement:"top",
    		delay: { show: 200, hide: 100 }
    	});

		ops.tempSave.on("click", function(){
			////console.log(this, ops.tempSave.g_disabled, "------- g_disabled");
			if(!ops.tempSave.g_disabled){
				_kernel.tempSave.call(self, {
					success:function(){
				    	ops.tempSave.popover('show')
				    	setTimeout(function(){
				    		ops.tempSave.popover('hide')
				    	}, 1000)
					}
				});
			}
		});

		ops.cancel.on("click", function(){
			if(!ops.cancel.g_disabled){
				_kernel.cancel.call(self, {
					success:function(){
						ops.save.g_disable(true);
						ops.tempSave.g_disable(true);
						ops.cancel.g_disable(true);
						ops.edit.g_able();
						//显示所有修改按钮
						__cancel();
						ops.save.parent().addClass("hide");
					}
				});
			}
		});

		ops.edit.on("click", function(){
			////console.log(this, ops.edit.g_disabled, "------- g_disabled=======================");
			if(!ops.edit.g_disabled){
				var id = self.id;
				//console.log(id, "id ------------------")
				_kernel.edit.call(self, {
					success:function(){
						ops.save.parent().removeClass("hide").prev().removeClass("hide");
						ops.edit.g_disable(true);
						ops.tempSave.g_able();
						ops.save.g_able();
						if(!!id){
							ops.cancel.g_able();
						}else{
							ops.cancel.g_disable();
						}
						//隐藏所有修改按钮
						__edit();
						
						self.editing = true;
					}
				});
			}
		});

		//初始化按钮和事件。
		ops.save.g_disable(true);
        ops.tempSave.g_disable(true);
        ops.cancel.g_disable(true);
        ops.edit.g_able();
        ops.edit.click();

		_kernel.init.call(self);
	};

	
	/*!
		广告内容类
	*/
	var AdContent = function(_kernel, options){
  		var ops = $.extend({
            "save":null,
            "edit":null,
            "cancel":null
        }, options);

  		var self = this;
  			
        $.each(ops, function(i, item){
        	ops[i] = !!this.jquery ? this : $(this);
            __extendDom(ops[i]);
        });

        self.ops = ops;
		self.id = 0;
		self.editing = false;

        ops.save.on("click", function(){
            //console.log(this, ops.save.g_disabled, "------- g_disabled");
            if(!ops.save.g_disabled){
                _kernel.save.call(self, {
                    success:function(id){
                        ops.save.g_disable(true);
                        ops.cancel.g_disable(true);
                        ops.edit.text("修改").g_able();
                        
                        __save();
                        //if( __adCollect.length < 2 ){
                        ops.save.parent().addClass("hide")
                        //}
                        if(self.id == 0){
							//__adCollect[0].ops.edit.click();
						}
						self.id = id;
						self.editing = false;
                        
                    }
                });
            }
        });

        ops.edit.on("click", function(){
            if(!ops.edit.g_disabled){
                _kernel.edit.call(self, {
                    success:function(){
                    	ops.save.parent().removeClass("hide").prev().removeClass("hide");
                        ops.edit.g_disable(true);
                        ops.save.g_able();
                        
                        //如果广告方案大于1个，就显示删除按钮，取消按钮可用。
                        if(__adCollect.length > 1){
                        	ops.del.g_able();
                        	ops.cancel.g_able();
                        }else{
                        	//只有一个广告方案，取消按钮禁用不隐藏。
                        	ops.cancel.g_disable();
                        }
                        __edit();
                        self.editing = true;
                        //test
                        ////console.log("test, ==============", $(".select-tabs"))
                    }
                });
            }
        });

        ops.cancel.on("click", function(){
            if(!ops.cancel.g_disabled){
                _kernel.cancel.call(self, {
                    success:function(){
                        ops.save.g_disable(true);
                        ops.cancel.g_disable(true);
                        ops.edit.g_able();
                        __cancel();
                        ops.save.parent().removeClass("hide");

                        if(self.id == 0){
							ops.panel.remove();
							//console.log(__adCollect.length, "---- start --- ")
							__adCollect.remove(self);
							//console.log(__adCollect.length,  "---- end --- ")
						}

						self.editing = false;
                    }
                });
            }
        });
        //确实删除按钮操作,
        ops.panel.delegate(".J_delAdContentOk", "click", function(){
        	//debugger;
        	_kernel.del.call(self, {
                success:function(){
                    ops.panel.remove();
                    __adCollect.remove(self);
                    $J_addAdContentBtn.g_able();
                }
            });
        });
        //确实取消按钮操作,
        ops.panel.delegate(".J_delAdContentCancel", "click", function(){
        	ops.del.popover("hide").g_able();
        });

        
    	if(!ops.del.g_disabled){
    		ops.del.popover({
	    		content:'<p>确定要删除吗</p><p><a href="###" class="J_delAdContentOk btn btn-primary">确定</a>&nbsp;&nbsp;'+
	                        '<a href="###" class="J_delAdContentCancel btn btn-default">取消</a></p>',
	            trigger:'manual',
	    		html:true,
	    		placement:"top",
	    		delay: { show: 500, hide: 100 }
	    	});
    	}

    	ops.del.on("click", function(){
    		ops.del.popover("show");
    		ops.del.g_disable();
    	});

    	//初始化默认的模块按钮状态。
    	ops.save.g_disable(true);
        ops.cancel.g_disable(true);
        ops.edit.g_able();

        _kernel.init.call(self);
  	};

  	
  	//基本信息
  	$(".J_newBaseInfoPanel").each(function(){
		var $self = $(this);
		baseInfo = new BaseInfo(baseInfoBtnEvent, {
			"panel":$self,
			"save":$self.find("#saveBaseInfo"),
			"tempSave":$self.find("#tempSaveBaseInfo"),
			"edit":$self.find("#editBaseInfo"),
			"cancel":$self.find("#cancelBaseInfo")
		});
	});
	//广告内容
	$("#newAdContentPanelBox > .panel").each(function(){
		////console.log($(this), "------")
		var $self = $(this);
		var ops = {
			"panel":$self,
			"save":$self.find(".J_saveAdContent"),
			"edit":$self.find(".J_editAdContent"),
			"cancel":$self.find(".J_cancelAdContent"),
			"del":$self.find(".J_delAdContent")
		}
		__adCollect.push( new AdContent(adContentBtnEvent, ops) );
	});

	$J_addAdContentBtn.on("click", function(){
		//console.log(this == $addAdContentBtn)
		var appendStr = ['<div class="J_newAdContent panel panel-gcrm">',
        '    <a href="###" class="J_editAdContent btn btn-link pull-right">添加</a>',
        '    <div class="panel-heading"><span class="black-block">&nbsp;</span>广告内容' + ( __adCollect.length + 1 ) + '</div>',
        '    <div class="panel-body">',
        '    </div><!--panel-body-->',
        '    <div class="panel-footer text-left">',
        '        <a href="###" class="J_saveAdContent btn btn-primary">保存</a>',
        '        <a href="###" class="J_cancelAdContent btn btn-default">取消</a>',
        '        <a href="###" class="J_delAdContent btn btn-danger hide">删除</a>',
        '    </div>',
        '</div><!-- end panel -->'].join("");
        var $self = $(appendStr);
        var adContent = new AdContent(adContentBtnEvent, {
        	"panel":$self,
        	"save":$self.find(".J_saveAdContent"),
			"edit":$self.find(".J_editAdContent"),
			"cancel":$self.find(".J_cancelAdContent"),
			"del":$self.find(".J_delAdContent")
        });
        $("#newAdContentPanelBox").append($self);
        
        adContent.ops.edit.click();

        __adCollect.push(adContent);
        
	});
});
