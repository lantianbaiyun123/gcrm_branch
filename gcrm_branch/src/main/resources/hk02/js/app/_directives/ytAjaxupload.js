define(["app","ajaxupload","../_common/ytCommon"],function(e){e.registerDirective("ytAjaxupload",["$parse","Modal","APP_CONTEXT","$filter","Utils",function(e,a,n,t,l){return{scope:{ngModel:"=",uploadedCbfn:"&",sendBeginCbfn:"&"},link:function(o,d,r){var p=(e(r.ngModel),l.toBoolean(r.isPushIn)||!1),i=l.toBoolean(r.isSpliceTo)||!1,u=n+r.uploadUrl,s=r.uploadType||".*",f=r.uploadInvalidatetype||"",c=new RegExp("^("+s+")$"),g=new RegExp("^("+f+")$"),m=JSON.parse(r.ytAjaxupload),A=m.beginUpload,x=m.uploadDone;if(!u)return!1;var O=t("translate")("AJAX_UPLOAD_UPLOADING"),P=t("translate")("AJAX_UPLOAD_TYPE_NOT_ALLOW"),T=t("translate")("AJAX_UPLOAD_UPLOAD"),_=t("translate")("AJAX_UPLOAD_TRY_AGAIN"),b=function(e){if(e){var n={data:{id:e},action:u,name:"materialFile",onSubmit:function(e,n){return n&&!g.test(n)&&c.test(n)?(d.find(".upload-text").html(m.uploadingText||O),void o.$apply(function(){o[A]&&o[A](),o.sendBeginCbfn&&o.sendBeginCbfn()})):(a.alert({content:t("translate")(m.formatErrorText)||P}),!1)},onComplete:function(e,n){var l=n;if(n.indexOf("<PRE>")>-1)l=n.replace("<PRE>","").replace("</PRE>","");else if(n.indexOf("<pre>")>-1)l=n.replace("<pre>","").replace("</pre>","");else if(n.indexOf("<pre")>-1){var r=n.substring(59);l=r.substr(0,r.length-6)}var u=JSON.parse(l);null!==u&&(200==u.code?(d.find(".upload-text").html(m.uploadedText||T),o.$apply(function(){p?o.ngModel.push(u.data):i?o.ngModel.splice(0,0,u.data):o.ngModel=u.data,o.uploadedCbfn&&o.uploadedCbfn()})):204===u.code?(o.ngModel=u,(o.uploadedCbfn||angular.noop)(),d.find(".upload-text").html(_)):(null===u&&(u={},u.message=t("translate")(m.formatErrorText)||P),d.find(".upload-text").html(m.uploadedText||_),a.alert({content:u.message||m.uploadFalseText||"上传失败，请重新上传"}))),o.$apply(function(){o[x]&&o[x]()})}};if(r.uploadFileName&&(n.name=r.uploadFileName),e&&JSON.parse(e).adPlatformId){var l=JSON.parse(e);n.data={adPlatformId:l.adPlatformId,siteId:l.siteId}}new AjaxUpload(d,n)}};r.uploadTriggersource?r.$observe("uploadTriggersource",function(e){b(e)}):b(-1)}}}])});