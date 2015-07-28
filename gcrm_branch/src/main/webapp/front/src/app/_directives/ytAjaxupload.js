define([
  'app',
  'ajaxupload',
  '../_common/ytCommon'
], function ( app ) {

  app.registerDirective('ytAjaxupload', [
    '$parse',
    'Modal',
    'APP_CONTEXT',
    '$filter',
    'Utils',
  function ($parse, Modal, APP_CONTEXT, $filter, Utils) {
    return {
      scope: {
        ngModel: '=',
        uploadedCbfn: '&',
        sendBeginCbfn: '&'
      },
      link : function (scope, element, attrs) {
        var parsed = $parse(attrs.ngModel);
        var isPushIn = Utils.toBoolean(attrs.isPushIn) || false;//上传后返回结果不直接赋值给ngModal，而是从尾部加入到ngModal列表中
        var isSpliceTo = Utils.toBoolean(attrs.isSpliceTo) || false;//上传后返回结果不直接赋值给ngModal，而是从首部到加入到ngModal列表中
        var uploadUrl = APP_CONTEXT + attrs.uploadUrl;
        var uploadType = attrs.uploadType || '.*';
        var invalidateUploadType = attrs.uploadInvalidatetype || '';
        var uploadTypeRegExp = new RegExp('^(' + uploadType + ')$');
        var invalidateTypeRegExp = new RegExp('^(' + invalidateUploadType + ')$');
        // var invalidateTypeRegExp = new RegExp('\\.(' + invalidateUploadType + ')$');
        var _initParameter = JSON.parse(attrs.ytAjaxupload);
        var beginUploadFun = _initParameter.beginUpload;
        var uploadDoneFun = _initParameter.uploadDone;
        /**/
        if (!uploadUrl) {
            return false;
        }

        var textUploading = $filter('translate')('AJAX_UPLOAD_UPLOADING'),
            textNotAllow = $filter('translate')('AJAX_UPLOAD_TYPE_NOT_ALLOW'),
            textUpload = $filter('translate')('AJAX_UPLOAD_UPLOAD'),
            textUploadAgain = $filter('translate')('AJAX_UPLOAD_TRY_AGAIN');
        var fileUploadFunc = function (triggerData) {
          if (triggerData) {
            var opts = {
              data: {
                id: triggerData
              },
              action: uploadUrl,
              name:"materialFile",
              onSubmit:function(file,ext){
                // if(ext && /^(JPG|JPEG|BMP|jpg|jpeg|png|gif|bmp)$/.test(ext)){
                if(ext && !invalidateTypeRegExp.test(ext) && uploadTypeRegExp.test(ext)){
                  //ext是后缀名
                  element.find('.upload-text').html(_initParameter.uploadingText || textUploading);
                  scope.$apply(function (argument){
                    if(scope[beginUploadFun]){
                      scope[beginUploadFun]();
                    }
                    if(scope.sendBeginCbfn) {
                      scope.sendBeginCbfn();
                    }
                  });
                }else{
                  Modal.alert({content: $filter('translate')(_initParameter.formatErrorText) || textNotAllow});
                  return false;
                }
              },
              onComplete:function(file,response){
                var resultStr = response;
                if (response.indexOf("<PRE>") > -1){
                  resultStr = response.replace("<PRE>","").replace("</PRE>","");
                } else if (response.indexOf("<pre>") > -1) {
                  resultStr = response.replace("<pre>","").replace("</pre>","");
                } else {
                  if (response.indexOf("<pre") > -1) {
                    var temp = response.substring(59);
                    resultStr = temp.substr(0, temp.length -6 );
                  }
                }


                var msg = JSON.parse(resultStr);
                if( msg !== null ) {
                  if ( msg.code == 200 ){
                    element.find('.upload-text').html(_initParameter.uploadedText || textUpload);
                    scope.$apply(function (argument) {
                      // parsed.assign(scope, msg.data);
                      if (isPushIn) {
                        scope.ngModel.push(msg.data);
                      }  else if (isSpliceTo) {
                        scope.ngModel.splice(0, 0, msg.data);
                      } else {
                        scope.ngModel = msg.data;
                      }
                      if(scope.uploadedCbfn) {
                        scope.uploadedCbfn();
                      }
                    });
                  } else {
                    if ( msg.code === 204 ) {
                      scope.ngModel = msg;
                      ( scope.uploadedCbfn || angular.noop )();
                      element.find('.upload-text').html( textUploadAgain );
                    } else {
                      /*NULL异常处理*/
                      if(msg === null){
                        msg = {};
                        msg.message = $filter('translate')(_initParameter.formatErrorText) || textNotAllow;
                      }

                      element.find('.upload-text').html(_initParameter.tryAgainText || textUploadAgain);
                      Modal.alert({content: msg.message || _initParameter.uploadFalseText || "上传失败，请重新上传"});
                    }
                  }
                }
                scope.$apply(function (argument) {
                  if(scope[uploadDoneFun]){
                    scope[uploadDoneFun]();
                  }

                  // if(scope.uploadedCbfn) {
                  //   scope.uploadedCbfn();
                  // }
                });
              }
            };
            if ( attrs.uploadFileName ) {
              opts.name = attrs.uploadFileName;
            }
            if ( triggerData && JSON.parse(triggerData).adPlatformId ) {
              var tempData = JSON.parse(triggerData);
              opts.data = {
                adPlatformId: tempData.adPlatformId,
                siteId: tempData.siteId
              };
            }
            new AjaxUpload( element, opts );
          }
        }; /*fileUploadFunc*/

        if ( attrs.uploadTriggersource ) {
          attrs.$observe('uploadTriggersource', function (uploadTriggersource) {
            fileUploadFunc(uploadTriggersource);
          });
        } else {
          fileUploadFunc(-1);
        }
      }
    };
}]);
});