define([
  'app',
  './ServiceNoticeHttp'
], function (app) {
  app.registerService('Notice', [
    'NoticeHttp',
    '$state',
    'GCRMUtil',
  function (NoticeHttp, $state, GCRMUtil) {
    var privateFun = {
      resolvePostData: function (noticeData, send) {
        var postData = angular.copy(noticeData);
        postData.send = (send && 1) || 0;
        // 过滤掉img，由于浏览器肯那个从剪贴板粘贴base64图片
        postData.content = postData.content.replace(/<img [^>]*>/g, '');

        return postData;
      }
    };
    return {
      getList: function(paramObj) {
        return NoticeHttp.getList(paramObj);
      },
      initNoticeView: function (noticeScope) {
        var detailPromise = NoticeHttp.getDetail({
          noticeId: noticeScope.notice.id
        }).success(function (response) {
          if (response.code === 200 && response.data) {
            noticeScope.notice = response.data;
          }
        });
        this.setStateView(noticeScope);
        return detailPromise;
      },
      setStateView: function (noticeScope) {
        noticeScope.state.isEdit = false;
      },
      setStateEdit: function (noticeScope) {
        noticeScope.e.formData = angular.copy(noticeScope.notice);
        noticeScope.state.isEdit = true;
      },
      validate: function (noticeScope) {
        noticeScope.state.onSave = true;
        if (noticeScope.formNotice.$valid && noticeScope.e.formData.content) {
          return true;
        }
        return false;
      },
      send: function (noticeId) {
        return NoticeHttp.send({
          noticeId: noticeId
        });
      },
      save: function (noticeData) {
        var postData = privateFun.resolvePostData(noticeData);
        return NoticeHttp.save(postData);
      },
      saveSend: function (noticeData) {
        var postData = privateFun.resolvePostData(noticeData, 1);
        return NoticeHttp.save(postData);
      },
      updateView: function (noticeScope, noticeData) {
        if (noticeScope.notice.id) {
          noticeScope.notice = angular.extend(noticeScope.notice, noticeData);
          this.setStateView(noticeScope);
        } else {
          $state.go('notice.facade', {id:noticeData.id});
        }
      },
      updateBtnPermit: function (noticeScope) {

        var btnPermit = noticeScope.btnPermit;

        btnPermit.save = (function () {
          var ownerPermit = GCRMUtil.checkOwnerPermit('btn_tool_notice_save', noticeScope.notice.isOwner);
          var authorized = noticeScope.BtnIndex.btn_tool_notice_save;

          return (ownerPermit && authorized);
        }());

        btnPermit.send = (function () {
          var ownerPermit = GCRMUtil.checkOwnerPermit('btn_tool_notice_send', noticeScope.notice.isOwner);
          var authorized = noticeScope.BtnIndex.btn_tool_notice_send;

          return (ownerPermit && authorized);
        }());

        btnPermit.saveSend = (btnPermit.save && btnPermit.send);
        btnPermit.edit = (btnPermit.save || btnPermit.send);
      }
    };
  }]);

  app.registerService('NoticeSuggest', [
    'NoticeHttp',
    'Account',
    '$filter',
  function (NoticeHttp, Account, $filter) {
    return {
      getUser: function ( options ) {
        var
          defaults = {},
          opts = angular.extend( defaults, options );
        return {
          id: function(priv) {
            return priv.ucid;
          },
          multiple: true,
          placeholder: $filter('translate')('NOTICE_SUGGEST_INPUT_RECEIVERS'),
          minimumInputLength: 1,
          formatInputTooShort: function(term, minLength) {
            return $filter('translate')('SELECTTOSUGGEST_PLACEINPUT') + ' ' + minLength + ' ' + $filter('translate')('SELECTTOSUGGEST_CHARACTERS');
          },
          query: function(query) {
            var data = {
              results: []
            };
            var dataHead = {};
            NoticeHttp.getSuggestInnerUser({
              query: $.trim(query.term)
            }).success( function ( response ) {
              if ( response.data ) {
                data.results = response.data;
                query.callback( data );
              }
            });
          },
          formatNoMatches: function() {
            return $filter('translate')('SELECTTOSUGGEST_NO_USER');
          },
          formatResult: function(item) {
            return item.realName + '(' + item.username + ')';
          },
          formatSelection: function(item) {
            ( opts.onSelect || angular.noop )( item );
            return item.realName + '(' + item.username + ')';
          },
          width: 'copy'
        };
      },
      getAdOwner: function ( options ) {
        var
          defaults = {},
          opts = angular.extend( defaults, options );
        return {
          id: function(priv) {
            return priv.data;
          },
          multiple: true,
          placeholder: $filter('translate')('NOTICE_SUGGEST_INPUT_RECEIVERS'),
          minimumInputLength: 1,
          formatInputTooShort: function(term, minLength) {
            return $filter('translate')('SELECTTOSUGGEST_PLACEINPUT') + ' ' + minLength + ' ' + $filter('translate')('SELECTTOSUGGEST_CHARACTERS');
          },
          query: function(query) {
            var data = {
              results: []
            };
            var dataHead = {};
            NoticeHttp.getSuggestAdOwner({
              query: $.trim(query.term)
            }).success( function ( response ) {
              if ( response.data ) {
                data.results = response.data;
                query.callback( data );
              }
            });
          },
          formatNoMatches: function() {
            return $filter('translate')('SELECTTOSUGGEST_NO_ADVERTISER_NAME');
          },
          formatResult: function(item) {
            return item.value;
          },
          formatSelection: function(item) {
            ( opts.onSelect || angular.noop )( item );
            return item.value;
          },
          width: 'copy'
        };
      }
    };
  }]);
});
