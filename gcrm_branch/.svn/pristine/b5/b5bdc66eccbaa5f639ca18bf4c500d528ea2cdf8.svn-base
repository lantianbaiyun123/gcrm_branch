define( [ 'app', 'jquery-fileupload', '../_common/ytCommon' ], function ( app ) {
  app.registerDirective('ytJqueryFileUpload', ['$parse', '$log', 'Modal', 'APP_CONTEXT', '$filter','$compile', 'Utils', function ($parse, $log, Modal, APP_CONTEXT, $filter, $compile, Utils) {
    return {
      scope: {
        ngModel: '=',
        uploadData: '=',
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
        // var uploadTypeRegExp = new RegExp('^(' + uploadType + ')$');
        var uploadTypeRegExp = new RegExp('\\.(' + uploadType + ')$');
        //var invalidateTypeRegExp = new RegExp('^.*\\.((?!' + invalidateUploadType + ').)*$');
        var invalidateTypeRegExp = new RegExp('\\.(' + invalidateUploadType + ')$');
        var _initParameter = JSON.parse(attrs.ytJqueryFileUpload);
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


        var _element = element;

        /*生成input*/
        if( element.nodeName != "INPUT" ){
          var uploadFileName = attrs.uploadFileName || 'materialFile';
            element.css("position","relative");
            _element = angular.element('<input type="file" name="' + uploadFileName + '" style=" position:absolute; opacity: 0; -ms-filter: \'alpha(opacity=0)\'; height:100%; cursor:pointer; top:0; left:0; width:100%;" multiple="true"/>');

            if(element.html() !== ""){
                var tempNode = $compile('<span class="upLoadText">'+ element.text() +'</span>')(scope);
                    iconNode = element.children(".glyphicon");
                element.html("");
                if(iconNode){
                  element.append(iconNode).append(' ');
                }
                element.append(tempNode);
            }
            element.append(angular.element('<form action="'+ uploadUrl +'" method="post"></form>').append(_element).append(angular.element('<input name="id" style="display:none;" type="text" value="'+ attrs.uploadTriggersource +'">')));
           // element.append(_element);
        }


        if(element.nodeName != "INPUT"){
          _element.click(function(){
              isFileInvalidate = false;
            }
          );
        } else {
          element.click(function(){
              isFileInvalidate = false;
            }
          );
        }
        var newElement = element;
        var fileMount = 0;
        /*确保单次上传的非法弹窗提示只出现一次*/
        var isFileInvalidate = false;

        var fileUploadFunc = function (uploadTriggersource) {
            if (uploadTriggersource || uploadTriggersource === -1) {
              _element.fileupload({
                url : uploadUrl,
                formData : {
                  id : uploadTriggersource
                },
                dataType : 'json',
                add: function (e, data) {
                  if(data.files[0].name && !invalidateTypeRegExp.test(data.files[0].name) && uploadTypeRegExp.test(data.files[0].name)){
                    //ext是后缀名
                    newElement.find('.upLoadText').html(_initParameter.uploadingText || textUploading);
                    data.submit();
                  }else{
                    if(!isFileInvalidate){
                      Modal.alert({content: $filter('translate')(_initParameter.formatErrorText) || textNotAllow});
                      isFileInvalidate = true;
                    }
                    //return false;
                  }
                },
                send:function (e, data) {
                  if( !fileMount ){
                    fileMount = 1;
                  } else {
                    fileMount++;
                  }
                  scope.$apply(function (argument) {
                    if(scope[beginUploadFun]){
                      scope[beginUploadFun]();
                    }

                    if(scope.sendBeginCbfn) {
                      scope.sendBeginCbfn();
                    }
                  });
                },
                done: function (e, data) {
                  // var msg = JSON.parse(data.result);
                  var msg = data.result;
                  fileMount--;
                  if(msg !== null && msg.code == 200){
                    if( fileMount === 0){
                      newElement.find('.upLoadText').html(_initParameter.uploadedText || textUpload);
                      // isFileInvalidate = false;
                      scope.$apply(function (argument) {
                        if(scope[uploadDoneFun]){
                          scope[uploadDoneFun]();
                        }

                        if(scope.uploadedCbfn) {
                          scope.uploadedCbfn();
                        }
                      });
                    }
                    scope.$apply(function (argument) {
                      if (isPushIn) {
                        scope.ngModel.push(msg.data);
                      } else if (isSpliceTo) {
                        scope.ngModel.splice(0, 0, msg.data);
                      } else {
                        scope.ngModel = msg.data;
                      }

                    });
                  } else {
                    if( fileMount === 0){
                      newElement.find('.upLoadText').html(_initParameter.uploadedText || textUploadAgain);
                      // isFileInvalidate = false;
                      scope.$apply(function (argument) {
                        if(scope[uploadDoneFun]){
                          scope[uploadDoneFun]();
                        }
                        if(scope.uploadedCbfn) {
                          scope.uploadedCbfn();
                        }
                      });
                    }

                    if(msg === null && !isFileInvalidate){
                      msg = {};
                      msg.message = $filter('translate')(_initParameter.formatErrorText) || textNotAllow;
                    }
                    Modal.alert({content: msg.message || _initParameter.uploadFalseText || "上传失败，请重新上传"});
                  }
                }
              });
            }
        }; /*fileUploadFunc*/

        if (attrs.uploadTriggersource) {
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