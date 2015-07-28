define( [ 'app' ], function ( app ) {
/**
 * Page 页面 (Angular Service)
 * set title head breadcrumb ···
 * --USAGES--
 *   PageSet.set({
        siteName: 'adSolutionDetail',
        activeIndex: 1
      });
 *
 */
app.registerFactory('PageSet', [
  '$rootScope',
  '$filter',
  'CMS_HOME_URL',
  function ($rootScope, $filter, CMS_HOME_URL) {
    var translFilter = $filter('translate');



    var pages = {
      home: {
        text: translFilter('PAGE_HOME'),
        url:'#/home'
      },
      task: {
        text: translFilter('HEAD_TASK'),
        parent: 'menu_todo'
      },
      historyList: {
        text: translFilter('HISTORY_OPERATE_TITILE'),
        parent: 'menu_todo'
      },
      //客户
      customer: {
        text: translFilter('HEAD_CUSTOMER'),
        parent: 'menu_cust'
      },
      //客户-客户管理
      customerList: {
        btnIndex: 'menu_cust_list',
        text: translFilter('PAGE_CUSTOMER_LIST'),
        url: '#/customer/list',
        parent: 'menu_cust'
      },
      customerAdd: {
        btnIndex: 'menu_cust_add',
        text: translFilter('PAGE_CUSTOMER_ADD'),
        parent: 'menu_cust'
      },
      customerDetail: {
        btnIndex: 'menu_cust_detail',
        text: translFilter('PAGE_CUSTOMER_DETAIL'),
        parent: 'menu_cust'
      },
      //销售
      sale: {
        text: translFilter('HEAD_SELL'),
        parent: 'menu_sales'
      },
      //销售-广告方案
      adSolutionAdd: {
        text: translFilter('PAGE_AD_SOLUTION_ADD'),
        url: '#/ad',
        parent: 'menu_sales'
      },
      adSolutionList: {
        btnIndex: 'menu_adsol_list',
        text: translFilter('PAGE_AD_SOLUTION_LIST'),
        url: '#/adSolutionList',
        parent: 'menu_sales'
      },
      adSolutionDetail: {
        text: translFilter('PAGE_AD_SOLUTION_DETAIL'),
        parent: 'menu_sales'
      },
      adContentDetail: {
        text: translFilter('PAGE_AD_CONTENT_DETAIL'),
        parent: 'menu_sales'
      },
      //销售-标杆价管理
      benchmarkPriceList: {
        btnIndex: 'menu_quotprice_list',
        text: translFilter('PAGE_BENCHMARK_PRICE_LIST'),
        url: '#/benchmarkPriceManagement/list',
        parent: 'menu_sales'
      },
      benchmarkPriceAdd: {
        text: translFilter('PAGE_BENCHMARK_PRICE_ADD'),
        url: '#/benchmarkPriceManagement/add',
        parent: 'menu_sales'
      },
      benchmarkPriceDetail: {
          text: translFilter('PAGE_BENCHMARK_PRICE_DETAIL'),
          parent: 'menu_sales'
      },
      benchmarkPriceCurrent: {
          text: translFilter('PAGE_BENCHMARK_PRICE_CURRENT'),
          url: '#/benchmarkPriceManagement/current',
          parent: 'menu_sales'
      },
      //销售-物料管理
      materialList: {
        btnIndex: 'menu_mater_list',
        text: translFilter('PAGE_MATERIAL_LIST'),
        url: '#/materialList',
        parent: 'menu_sales'
      },
      materialDetail: {
        text: translFilter('PAGE_MATERIAL_DETAIL'),
        url: '#/materialDetail',
        parent: 'menu_sales'
      },
      //销售-平台管理
      platformList: {
        btnIndex: 'menu_platform_list',
        text: translFilter('PAGE_PLATFORM_LIST'),
        url: '#/platformList',
        parent: 'menu_mater'
      },
      //资源
      resource: {
        text: translFilter('HEAD_RESOURCE')
      },
      //资源-资源位管理
      resourcePositionMgmt: {
        btnIndex: 'menu_pos_list',
        text: translFilter('PAGE_RESOURCE_POSITION_MGMT'),
        url: '#/resourcePositionList',
        parent: 'menu_mater'
      },
      resourcePositionList: {
        btnIndex: 'menu_pos_list',
        text: translFilter('PAGE_RESOURCE_POSITION_MGMT'),
        url: '#/resourcePositionList',
        parent: 'menu_mater'
      },
      resourcePositionAdd: {
        text: translFilter('PAGE_RESOURCE_POSITION_ADD'),
        url: '#/platformList',
        parent: 'menu_mater'
      },
      //资源-排期单
      scheduleList: {
        btnIndex: 'menu_schedule_list',
        text: translFilter('PAGE_AD_SCHEDULELIST'),
        url: '#/scheduleList',
        parent: 'menu_mater'
      },
      scheduleDetail: {
        text: translFilter('PAGE_AD_SCHEDULEDETAIL'),
        parent: 'menu_mater'
      },
      //资源-默认广告管理
      defaultAd: {
        text: translFilter('PAGE_DEFAULT_AD'),
        url: '#/defaultAd',
        parent: 'menu_mater',
        btnIndex: 'menu_default_ad'
      },
      //投放
      publication: {
        text: translFilter('HEAD_PUBLICATION'),
        parent: 'menu_pub'
      },
      //投放-上下线管理
      publicationMgmt: {
        btnIndex: 'menu_pub_manage',
        text: translFilter('PAGE_PUBLICATION_MGMT'),
        url: '#/publicationMgmt',
        parent: 'menu_pub'
      },
      publicationMember: {
        text: translFilter('PAGE_PUBLICATION_MEMBER'),
        url: '',
        parent: 'menu_pub'
      },
      //系统管理
      admin: {
        text: translFilter('PAGE_ADMIN'),
        url: '',
        parent: 'menu_sys'
      },
      //内部用户
      adminUserList: {
        text: translFilter('PAGE_ADMIN_USER_LIST'),
        btnIndex: 'menu_sys',
        url: '#/adminUserList',
        parent: 'menu_sys'
      },

      //工具
      tool: {
        text: translFilter('HEAD_TOOL'),
        parent: 'menu_tool'
      },
      //工具-公告管理
      noticeList: {
        text: translFilter('PAGE_NOTICE_MGMT'),
        url: '#/noticeList',
        parent: 'menu_tool',
        btnIndex: 'menu_notice'
      },
      noticeAdd: {
        text: translFilter('PAGE_NOTICE_ADD'),
        parent: 'menu_tool',
        btnIndex: 'menu_notice'
      },
      noticeDetail: {
        text: translFilter('PAGE_NOTICE_DETAIL'),
        parent: 'menu_tool',
        btnIndex: 'menu_notice'
      },
      //工具-报表
      report: {
        text: translFilter('PAGE_REPORT'),
        url: '#/report/hao123Trace',
        parent: 'menu_tool',
        btnIndex: 'menu_report'
      },
      hao123Trace: {
        text: translFilter('PAGE_HAO123TRACE'),
        url: '#/report/hao123Trace',
        parent: 'menu_tool',
        btnIndex: 'menu_report'
      }
    };

    var breadcrumbs = {
      home: [pages.home],
      task: [pages.home, pages.task],
      historyList: [pages.home, pages.task, pages.historyList],
      //客户管理
      customerList: [pages.home, pages.customer, pages.customerList],
      customerAdd: [pages.home, pages.customer, pages.customerList, pages.customerAdd],
      customerDetail: [pages.home, pages.customer, pages.customerList, pages.customerDetail],


      //广告方案
      adSolutionList: [pages.home, pages.sale, pages.adSolutionList],
      adSolutionAdd: [pages.home, pages.sale, pages.adSolutionList, pages.adSolutionAdd],
      adSolutionDetail: [pages.home, pages.sale, pages.adSolutionList, pages.adSolutionDetail],
      adContentDetail: [pages.home, pages.sale, pages.adSolutionList, pages.adSolutionDetail, pages.adContentDetail],

      //标杆价管理
      benchmarkPriceList: [pages.home, pages.sale, pages.benchmarkPriceList],
      benchmarkPriceAdd: [pages.home, pages.sale, pages.benchmarkPriceList, pages.benchmarkPriceAdd],
      benchmarkPriceDetail: [pages.home, pages.sale, pages.benchmarkPriceList, pages.benchmarkPriceDetail],
      benchmarkPriceCurrent: [pages.home, pages.sale, pages.benchmarkPriceList, pages.benchmarkPriceCurrent],

      //物料管理
      materialList: [pages.home, pages.sale, pages.materialList],
      materialDetail: [pages.home, pages.sale, pages.materialList, pages.materialDetail],

      //投放平台管理
      platformList: [pages.home, pages.resource, pages.platformList],

      //资源位管理
      resourcePositionList: [pages.home, pages.resource, pages.resourcePositionMgmt],
      resourcePositionAdd: [pages.home, pages.resource, pages.resourcePositionMgmt, pages.resourcePositionAdd],

      //排期单列表
      scheduleList: [pages.home, pages.resource, pages.scheduleList],
      scheduleDetail: [pages.home, pages.resource, pages.scheduleList, pages.scheduleDetail],

      //上下线管理
      publicationMgmt: [pages.home, pages.publication, pages.publicationMgmt],
      publicationMember: [pages.home, pages.publication, pages.publicationMgmt, pages.publicationMember],

      //内部用户
      adminUserList: [pages.home, pages.admin, pages.adminUserList],

      //公告管理
      noticeList: [pages.home, pages.tool, pages.noticeList],
      noticeAdd: [pages.home, pages.tool, pages.noticeList, pages.noticeAdd],
      noticeDetail: [pages.home, pages.tool, pages.noticeList, pages.noticeDetail],

      //报表
      hao123Trace: [pages.home, pages.tool, pages.report, pages.hao123Trace],

      //默认广告管理
      defaultAd: [pages.home, pages.resource, pages.defaultAd],

      fakeEnd: []
    };

    var headNavs = [
      {
        text:'HEAD_CUSTOMER',
        sub: [pages.customerList],
        btnIndex: 'menu_cust'
      },
      {
        text:'HEAD_SELL',
        sub: [pages.adSolutionList, pages.benchmarkPriceList, pages.materialList],
        btnIndex: 'menu_sales'
      },
      {
        text:'HEAD_RESOURCE',
        sub: [pages.platformList, pages.resourcePositionMgmt, pages.scheduleList, pages.defaultAd],
        btnIndex: 'menu_mater'
      },
      {
        text: 'HEAD_CONTRACT',
        url: CMS_HOME_URL,
        btnIndex: 'menu_contract'
      },
      {
        text: 'HEAD_PUBLICATION',
        sub: [pages.publicationMgmt],
        btnIndex: 'menu_pub'
      },
      {
        text: 'HEAD_TOOL',
        sub: [pages.noticeList, pages.report],
        btnIndex: 'menu_tool'
      },
      {
        text: 'HEAD_TASK',
        btnIndex: 'menu_todo'
      },
      {
        text: 'HEAD_SYSTEM',
        sub: [pages.adminUserList],
        btnIndex: 'menu_sys'
      }
    ];

    return {
      set: function(paramObj){
        $rootScope.breadcrumb = breadcrumbs[paramObj.siteName];
        var btnIndex = pages[paramObj.siteName].parent;
        $rootScope.activeIndex = getActiveIndex( btnIndex );
        // $rootScope.activeIndex = paramObj.activeIndex;
      },

      setHeadNavs: function () {
        var menuCodeList, buttonCodeList, ownerOperFuncs;
        if ( window.GCRM && window.GCRM.rights && window.GCRM.rights.menuCodeList && window.GCRM.rights.buttonCodeList && window.GCRM.rights.menuCodeList.length && window.GCRM.rights.buttonCodeList.length && window.GCRM.ownerOperFuncs ) {
          menuCodeList = window.GCRM.rights.menuCodeList;
          buttonCodeList = window.GCRM.rights.buttonCodeList;
          ownerOperFuncs = window.GCRM.ownerOperFuncs;
        } else {
          menuCodeList = [
            "menu_cust",
            "menu_sales",
            "menu_mater",
            "menu_contract",
            "menu_pub",
            "menu_todo",
            "menu_sys",
            "menu_schedule_list",
            "menu_cust_add",
            "menu_cust_list",
            "menu_cust_detail",
            "menu_adsol_add",
            "menu_adsol_list",
            "menu_adsol_detail",
            "menu_platform_list",
            "menu_pos_add",
            "menu_pos_list",
            "menu_mater_list",
            "menu_mater_detail",
            "menu_quotprice_list",
            "menu_quotprice_add",
            "menu_quotprice_detail",
            "menu_pub_manage",
            "menu_idx_todo",
            "menu_idx_recent_submit",
            "menu_idx_opera",
            "menu_idx_module_data",
            "menu_report",
            "menu_notice",
            "menu_tool",
            "menu_default_ad"
          ];
          buttonCodeList = [
            "btn_adsol_list_add",
            "btn_cust_list_view",
            "btn_cust_list_create",
            "btn_cust_list_query",
            "btn_cust_list_cancel",
            "btn_cust_list_withdraw",
            "btn_cust_list_recover",
            "btn_cust_list_account_add",
            "btn_cust_list_sale_transfer",
            "btn_cust_list_remind",
            "btn_cust_detail_submit",
            "btn_cust_detail_cancel",
            "btn_cust_detail_remind",
            "btn_cust_detail_adsol_create",
            "btn_cust_detail_withdraw",
            "btn_cust_detail_appr_record_view",
            "btn_cust_detail_mod_record_view",
            "btn_cust_detail_baseinfo_mod",
            "btn_cust_detail_contact_mod",
            "btn_cust_detail_busiopp_mod",
            "btn_cust_detail_qualify",
            "btn_cust_detail_account_mod",
            "btn_cust_detail_recover",
            "btn_adsol_list_detail_view",
            "btn_adsol_list_exe_transfer",
            "btn_adsol_list_sol_change",
            "btn_adsol_list_remind",
            "btn_adsol_list_cont_conf",
            "btn_adsol_detail_cont_conf",
            "btn_adsol_detail_cont_mod",
            "btn_adsol_detail_coop_term",
            "btn_adsol_detail_reschedule",
            "btn_adsol_detail_po_create",
            "btn_adsol_detail_submit_to_busi",
            "btn_adsol_detail_cont_change",
            "btn_adsol_detail_adsol_change",
            "btn_adsol_detail_adsol_mod",
            "btn_adsol_detail_contract_change",
            "btn_adsol_detail_contract_withdraw",
            "btn_platform_list_add",
            "btn_platform_list_mod",
            "btn_platform_list_close",
            "btn_pos_list_import",
            "btn_pos_list_add",
            "btn_pos_list_name_mod",
            "btn_pos_list_property_add",
            "btn_pos_list_launch_mod",
            "btn_pos_list_image_mod_add",
            "btn_pos_list_enable_disable",
            "btn_pos_list_property_view",
            "btn_pos_list_launch_view",
            "btn_pos_list_image_view",
            "btn_mater_detail_change_add",
            "btn_mater_detail_mod",
            "btn_mater_detail_cancel_recover",
            "btn_quotprice_list_add",
            "btn_quotprice_list_view",
            "btn_quotprice_list_withdraw",
            "btn_quotprice_list_cancel_recover",
            "btn_quotprice_list_current",
            "btn_quotprice_detail_withdraw",
            "btn_quotprice_detail_cancel_recover",
            "btn_pub_manage_user_config",
            "btn_pub_manage_launch_term",
            "btn_pub_manage_mater_publish",
            "btn_pub_manage_publish",
            "btn_pub_manage_offline",
            "btn_pub_manage_force_publish",
            "btn_idx_todo_handle",
            "btn_idx_recent_submit_cust",
            "btn_idx_recent_submit_cust_adsol",
            "btn_idx_recent_submit_cust_quotprice",
            "btn_idx_recent_submit_cust_mater",
            "btn_idx_opera_cust",
            "btn_idx_opera_adsol_contract",
            "btn_idx_opera_pos_occupy",
            "btn_quotprice_list_remind",
            "btn_schedule_list_query",
            "btn_schedule_detail_view",
            "btn_adsol_detail_cont_save",
            "btn_adsol_detail_submit",
            "btn_mater_detail_withdraw",
            "btn_tool_notice_save", //保存公告
            "btn_tool_notice_send", //发送公告
            "btn_tool_notice_list", //管理公告
            "btn_tool_report_list",  //报表管理
            "btn_adsol_detail_adsol_del"
          ];
          ownerOperFuncs = [
            "btn_cust_list_create",
            "btn_cust_list_cancel",
            "btn_cust_list_remind",
            "btn_cust_list_withdraw",
            "btn_cust_list_recover",
            "btn_cust_detail_submit",
            "btn_cust_detail_cancel",
            "btn_cust_detail_remind",
            "btn_cust_detail_withdraw",
            "btn_cust_detail_baseinfo_mod",
            "btn_cust_detail_contact_mod",
            "btn_cust_detail_busiopp_mod",
            "btn_cust_detail_qualify",
            "btn_cust_detail_account_mod",
            "btn_adsol_list_sol_change",
            "btn_adsol_list_remind",
            "btn_adsol_list_cont_conf",
            "btn_adsol_detail_cont_conf",
            "btn_adsol_detail_cont_mod",
            "btn_adsol_detail_coop_term",
            "btn_adsol_detail_reschedule",
            "btn_adsol_detail_po_create",
            "btn_adsol_detail_submit_to_busi",
            "btn_adsol_detail_cont_change",
            "btn_adsol_detail_adsol_change",
            "btn_adsol_detail_contract_withdraw",
            "btn_adsol_detail_contract_change",
            "btn_adsol_detail_cont_save",
            "btn_adsol_detail_submit",
            "btn_adsol_detail_adsol_mod",
            "btn_quotprice_list_withdraw",
            "btn_quotprice_list_remind",
            "btn_quotprice_list_cancel_recover",
            "btn_quotprice_detail_withdraw",
            "btn_quotprice_detail_cancel_recover",
            "btn_tool_notice_save", //保存公告
            "btn_tool_notice_send", //发送公告
            "btn_adsol_detail_adsol_del"
          ];
        }

        var i, j, resultHeadNavs = [], tempObj, tempHead, tempSub;
        for ( i = 0; i < headNavs.length; i++) {
          if ( menuCodeList.indexOf( headNavs[ i ].btnIndex ) !== -1 ) {
            tempObj = {
              text: headNavs[ i ].text
            };
            tempSub = [];
            tempHead = headNavs[ i ];
            for ( j = 0; tempHead.sub && j < tempHead.sub.length; j++) {
              if ( menuCodeList.indexOf( tempHead.sub[ j ].btnIndex ) !== -1 ) {
                tempSub.push( tempHead.sub[ j ] );
              }
            }
            tempObj.sub = tempSub;
            tempObj.btnIndex = headNavs[ i ].btnIndex;
            //如果有url定义，加上（当前只有合同的外部url）
            if ( headNavs[ i ].url ) {
              tempObj.url = headNavs[ i ].url;
            }
            resultHeadNavs.push( tempObj );
          }
        }
        $rootScope.headNavs = resultHeadNavs;

        setGlobalBtnIndex( buttonCodeList, menuCodeList );
        setOwnerOpers(ownerOperFuncs);
      },

      pageSet: function (page, opts) {
        Angular.extend(this.pages[page], opts);
      }
    };
    function setGlobalBtnIndex ( buttonCodeList, menuCodeList ) {
      var obj = {};
      for ( var i = 0; i < buttonCodeList.length; i++ ) {
        obj[ buttonCodeList[ i ] ] = true;
      }
      for (var j = 0; j < menuCodeList.length; j++) {
        obj[ menuCodeList[j] ] = true;
      }
      $rootScope.BtnIndex = obj;
    }
    function getActiveIndex( btnIndex ) {
      if ( !btnIndex ) {
        return -1;
      }
      for (var i = 0; i < $rootScope.headNavs.length; i++) {
        if ( $rootScope.headNavs[i].btnIndex === btnIndex ) {
          return i;
        }
      }
      return -1;
    }
    function setOwnerOpers(ownerOperFuncs) {
      var obj = {};
      for (var i = ownerOperFuncs.length - 1; i >= 0; i--) {
        obj[ownerOperFuncs[i]] = true;
      }

      $rootScope.OwnerOpers = obj;
    }
  }
]);


});