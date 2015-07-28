define(['app'], function (app) {
  app.registerFactory('ModalC', [
    '$modal',
    '$q',
    '$timeout',
    '$filter',
    'GCRMUtil',
    'STATIC_DIR',
    function ( $modal, $q, $timeout, $filter, GCRMUtil, STATIC_DIR ) {
      return {
        alert: function (opts, cbfn) {
          var
            defered = $q.defer();
            modalInstance,
            optsDefault = {
              title: $filter('translate')('ALERT'),
              content: ''
            };
          $timeout(function () {
            modalInstance = $modal.open({
              templateUrl: STATIC_DIR + 'app/_common/modal/modal.tpl.html',
              controller: 'CtrlModal',
              // backdrop : 'static',
              windowClass: 'ngAlert ' + opts.ngClass,
              resolve: {
                opts: function () {
                  return angular.extend(optsDefault, opts);
                }
              }
            });

            modalInstance.result.then(function (result) {
              defered.resolve(result);
            });
          }, 0);

          return defered.promise;
        },
        confirm: function (opts) {
          var
            defered = $q.defer(),
            optsDefault = {
              title: $filter('translate')('CONFIRM'),
              content: '',
              showCancel: true
            };

          var modalInstance = $modal.open({
            templateUrl: STATIC_DIR + 'app/_common/modal/modal.tpl.html',
            controller: 'CtrlModal',
            // backdrop : 'static',
            windowClass: 'ngConfirm',
            resolve: {
              opts: function () {
                return angular.extend(optsDefault, opts);
              }
            }
          });
          modalInstance.result.then(function (result) {
            defered.resolve(result);
          });

          return defered.promise;
        },
        success: function (opts) {
          var
            defered = $q.defer(),
            optsDefault = {
              title: '',
              content: ''
            };

          var modalInstance = $modal.open({
            templateUrl: STATIC_DIR + 'app/_common/modal/successModal.tpl.html',
            controller: 'CtrlModal',
            // backdrop : 'static',
            windowClass: 'successModal',
            resolve: {
              opts: function () {
                return angular.extend(optsDefault, opts);
              }
            }
          });

          $timeout(function () {
            modalInstance.dismiss('cancel');
            defered.resolve('cancel');
          }, opts.timeOut || 2000);

          return defered.promise;
        },
        error: function (opts) {
          var optsDefault = {
            title: $filter( 'translate' )( 'ALERT' )
          };
          var modalInstance = $modal.open({
            templateUrl: STATIC_DIR + 'app/_common/modal/errorModal.tpl.html',
            controller: 'CtrlModal',
            resolve: {
              opts: function () {
                return angular.extend( optsDefault, opts );
              }
            }
          });
        },
        showError: function ( errorList ) {
          var errorTextList, errors = [];
          angular.forEach( errorList, function ( value, key ) {
            errorTextList = [];
            angular.forEach( value, function ( value ) {
              errorTextList.push( value );
            });
            errors.push({
              typeName: GCRMUtil.decodeGCRMMessageSingle( key ),
              list: errorTextList
            });
          });
          this.error( {
            errors: errors
          });
        }
      };
    }
  ]);

});