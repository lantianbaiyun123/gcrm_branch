define( [ 'app', 'gcrm-util' ], function ( app ) {
  app.config( [ '$httpProvider',
    function ( $httpProvider ) {
      var errorInterceptor = [ '$q', '$log', 'GCRMUtil', '$location',
        function ( $q, $log, GCRMUtil, $location ) {
          function modalAlert( message ) {
            var $modal = $(
              '<div tabindex="-1" class="modal fade ngAlert in" ng-class="{in: animate}" style="z-index: 1050; display: block;">' +
              '<div class="modal-dialog">' +
              '<div class="modal-content">\n' +
              '  <div class="modal-header">\n' +
              '   <h4 class="modal-title">' + message + '</h4>\n' +
              '  </div>\n' +
              '  <div class="modal-body">\n' +
              '    <button type="button" class="btn btn-primary btn-interceptor-ok">OK</button>\n' +
              '  </div>\n' +
              '</div>' +
              '</div>' +
              '</div>');
            var $backdrop = $('<div class="modal-backdrop in" style="z-index: 1040;"></div>');
            $modal.appendTo($('body'));
            $backdrop.appendTo($("body"));
            $modal.fadeIn(500);
            $(".btn-interceptor-ok").on('click', function () {
              $modal.remove();
              $backdrop.remove();
            });
          }
          function getInternetExplorerVersion() {
            var rv = -1,
              ua,
              re;
            if (navigator.appName == 'Microsoft Internet Explorer')
            {
              ua = navigator.userAgent;
              re  = new RegExp("MSIE ([0-9]{1,}[.0-9]{0,})");
              if (re.exec(ua) != null) {
                rv = parseFloat( RegExp.$1 );
              }
            }
            else if (navigator.appName == 'Netscape')
            {
              ua = navigator.userAgent;
              re  = new RegExp("Trident/.*rv:([0-9]{1,}[.0-9]{0,})");
              if (re.exec(ua) != null) {
                rv = parseFloat( RegExp.$1 );
              }
            }
            return rv;
          }
          var msie = getInternetExplorerVersion();
          // try {
          //   msie = ~~( navigator.userAgent.toLowerCase().match( /msie (\d)/ ) || [] )[ 1 ];
          // } catch ( e ) {
          // }
          // msie = 1;
          return {
            'request': function ( config ) {
              if ( msie > 0 && config.method === 'GET' && config.url && !/\.js$|\.html$/.test(config.url) ) {
                config.params = config.params || {};
                config.params['_'] = new Date().getTime();
              }
              return config;
            },
            'response': function ( response ) {
              /**
                code说明：
                200：正常处理
                202：errors中有错误提示（errors是对于dom元素中的错误提示，比如表单字段）
                201：message有错误消息（message主要用于dom元素中没有的错误提示，比如alert框中的消息）
                203：errors和message中都有消息。
              */
              if ( response.data && response.data.code && response.data.code !== 200 ) {
                if ( response.data.code === 202) {
                  modalAlert( GCRMUtil.decodeGCRMError( response.data.errors ) );
                } else if ( response.data.code === 201 ) {
                  modalAlert( response.data.message );
                } else if ( response.data.code === 203 ) {
                  modalAlert( response.data.message );
                  modalAlert( GCRMUtil.decodeGCRMError( response.data.errors ) );
                } else if ( response.data.code === 208 ) {
                  //208session失效，跳转到登录页
                  window.location.href = '/gcrm/login';
                }
              }
              return response;
            },
            'responseError': function ( rejection ) {
              $log.error( rejection );
              var status = rejection.status.toString();
              if ( status.indexOf( '4' ) === 0 ) {
                // $location.path('/error/404');
              } else if ( status.indexOf( '5' ) === 0 ) {
                // $location.path('/error/500');
              }

              return $q.reject( rejection );
            }
          };
        }
      ];
      $httpProvider.interceptors.push( errorInterceptor );
    }
  ]);
});