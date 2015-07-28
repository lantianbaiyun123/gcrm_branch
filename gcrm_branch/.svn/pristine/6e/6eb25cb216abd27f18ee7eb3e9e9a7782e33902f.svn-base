define([
  'app',
  './ServiceReport',
  '../_directives/ytInputDropdown'
], function (app) {
  app.registerController('CtrlHao123Trace', [
    '$scope',
    'PageSet',
    'Report',
    '$filter',
    'APP_CONTEXT',
  function ($scope, PageSet, Report, $filter, APP_CONTEXT) {
    PageSet.set({siteName:'hao123Trace'});

    $scope.qForm = {};
    $scope.todayDate = new Date();
    $scope.preset = [
      { key:'lastWeek' },
      { key:'lastMonth' },
      { key:'lastYear' },
      { key:'all' }
    ];

    //pagenation
    $scope.pager = {
      pageSize: 10,
      pageSizeSlots: [10,20,30,50],
      pageNumber: 1,
      totalCount: 0,
      totalPages: 0
    };

    //更改分页大小
    $scope.setPageSize = function (size) {
      $scope.pager.pageSize = size;
      $scope.query();
    };
    //当前页变化监听，相当于点击了分页
    $scope.$watch( "pager.pageNumber", function (newValue, oldValue) {
      if (newValue && newValue !== oldValue) {
        getList();
      }
    });

    $scope.setDates = function (index) {
      clearPreset();
      $scope.preset[index].isActive = true;
      setDates($scope.preset[index].key);
      $scope.query();
    };

    $scope.query = function() {
      if ($scope.pager.pageNumber === 1) {
        getList();
      } else {
        $scope.pager.pageNumber = 1;
      }
    };

    $scope.exportList = function () {
      var qForm = conditionDates();
      qForm.siteId = $scope.qForm.siteSelected && $scope.qForm.siteSelected.siteId || '';

      Report.hao123TraceExport(qForm);

    };

    $scope.datesChange = function () {
      clearPreset();
    };

    init();

    function init() {
      $scope.setDates(0);
      Report.initHao123($scope);
    }

    function clearPreset() {
      for (var i = $scope.preset.length - 1; i >= 0; i--) {
        $scope.preset[i].isActive = false;
      }
    }

    function getList() {
      var pager = $scope.pager;
      var qForm = conditionDates();

      qForm.siteId = $scope.qForm.siteSelected && $scope.qForm.siteSelected.siteId || '';
      qForm.pageSize = pager.pageSize;
      qForm.pageNumber = pager.pageNumber;

      Report.hao123TraceQuery(qForm).success(function (response) {
        if (response.code === 200 && response.data) {
          $scope.reportList = response.data.content;
          $scope.pager.totalCount = response.data.totalCount;
          $scope.pager.totalPages = response.data.totalPages;
        }
      });
    }

    function conditionDates() {
      var dateFilter = $filter('date');
      var dateFormat = 'yyyy-MM-dd';

      var dates = {
        beginStr: dateFilter($scope.qForm.startDate, dateFormat),
        endStr: dateFilter($scope.qForm.endDate, dateFormat)
      };

      return dates;
    }

    function setDates(key) {
      var qForm = $scope.qForm;

      qForm.startDate = new Date();
      qForm.endDate = new Date();

      if (key==='lastWeek') {
        qForm.startDate = qForm.startDate.setDate(qForm.startDate.getDate() - 7);
      } else if (key === 'lastMonth') {
        qForm.startDate = qForm.startDate.setMonth(qForm.startDate.getMonth() - 1);
      } else if (key === 'lastYear') {
        qForm.startDate = qForm.startDate.setYear(qForm.startDate.getFullYear() - 1);
      } else {
        qForm.startDate = null;
        qForm.endDate = null;
      }
    }
  }]);
});
