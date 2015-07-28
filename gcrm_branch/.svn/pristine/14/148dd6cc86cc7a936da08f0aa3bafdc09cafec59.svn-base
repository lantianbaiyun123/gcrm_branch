/**
 * [description] this module require `app` to be loaded
 */
define(['app'], function (app) {
/**
  * register `routeDefs`
  *
 */
app.registerProvider( 'routeDefs', [
  '$stateProvider',
  '$urlRouterProvider',
  '$couchPotatoProvider',
  'STATIC_DIR',
  function (
    $stateProvider,
    $urlRouterProvider,
    $couchPotatoProvider,
    STATIC_DIR
  ) {

    this.$get = function () {
      // this is a config-time-only provider
      // in a future sample it will expose runtime information to the app
      return {};
    };
    // $locationProvider.html5Mode(true);

    $urlRouterProvider.otherwise('home');

    var baseUrl = STATIC_DIR + "app/";
    var headerConfig = {
      templateUrl: baseUrl + '_common/header/header.tpl.html',
      controller: 'CtrlHeader',
      resolve: {
        ctrl: $couchPotatoProvider.resolveDependencies(['_common/header/CtrlHeader'])
      }
    };
    var footerConfig = {
      templateUrl: baseUrl + '_common/footer/footer.tpl.html'
    };
    /**
     * [url description] home temp
     * @type {String}
     */
    $stateProvider.state('temp', {
      url: '/temp',
      views: {
        'body': {
          templateUrl: baseUrl + 'home/temp.tpl.html'
        }
      }
    });
    /**
     * [url description] home
     * @type {String}
     */
    $stateProvider.state('home', {
      url: '/home',
      views: {
        'body': {
          templateUrl: baseUrl + 'home/home.tpl.html',
          controller: 'CtrlHome',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['home/CtrlHome'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    });
    /**
     * [url description] error pages
     * @type {String}
     */
    $stateProvider.state('error', {
      url: '/error',
      'abstract': true,
      views: {
        'body': {
          templateUrl: baseUrl + '_common/error/error.tpl.html'
        },
        header: headerConfig,
        footer: footerConfig
      }
    })
      .state('error.four', {
        url: '/404',
        views: {
          'errorType': {
            templateUrl: baseUrl + '_common/error/error_404.tpl.html'
          }
        }
      })
      .state('error.five', {
        url: '/500',
        views: {
          'errorType': {
            templateUrl: baseUrl + '_common/error/error_500.tpl.html'
          }
        }
      });
    /**
     * [url description] advertise add
     * @type {String}
     */
    // $stateProvider.state('ad', {
    //   url: '/ad?programId&customerNumber',
    //   'abstract': true,
    //   views: {
    //     'body': {
    //       templateUrl: baseUrl + 'ad/ad.tpl.html',
    //       controller: 'CtrlAd',
    //       resolve: {
    //         ctrl: $couchPotatoProvider.resolveDependencies(['ad/CtrlAd'])
    //       }
    //     },
    //     header: headerConfig,
    //     footer: footerConfig
    //   }
    // })
    //   .state('ad.facade', {
    //     url: '',
    //     views: {
    //       'navBar': {
    //         templateUrl: baseUrl + '_common/header/header.tpl.html',
    //         controller: 'CtrlHeader',
    //         resolve: {
    //           ctrl: $couchPotatoProvider.resolveDependencies(['_common/header/CtrlHeader'])
    //         }
    //       },
    //       'basicEdit': {
    //         templateUrl: baseUrl + 'ad/adBasicEdit.tpl.html'
    //       },
    //       'basicReview': {
    //         templateUrl: baseUrl + 'ad/adBasicReview.tpl.html'
    //       },
    //       'adContent': {
    //         templateUrl: baseUrl + 'ad/adContent.tpl.html',
    //         controller: 'CtrlAdContent',
    //         resolve: {
    //           ctrl: $couchPotatoProvider.resolveDependencies(['ad/CtrlAdContent'])
    //         }
    //       }
    //     }
    //   });
    /**
     * [url description] advertise add
     * @type {String}
     */
    $stateProvider.state('ad2', {
      url: '/ad2?id&customerNumber',
      'abstract': true,
      views: {
        'body': {
          templateUrl: baseUrl + 'ad2/ad.tpl.html',
          controller: 'CtrlAd',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['ad2/CtrlAd'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    })
      .state('ad2.facade', {
        url: '',
        'abstract': true,
        views: {
          'adBasicView': {
            templateUrl: baseUrl + 'ad2/fragments/adBasicView.tpl.html'
          },
          'adBasicEdit': {
            templateUrl: baseUrl + 'ad2/fragments/adBasicEdit.tpl.html',
            controller: 'CtrlAdBasic',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['ad2/CtrlAdBasic'])
            }
          },
          'adContent': {
            templateUrl: baseUrl + 'ad2/adContent.tpl.html',
            controller: 'CtrlAdContent',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['ad2/CtrlAdContent'])
            }
          }
        }
      })
      .state('ad2.facade.facade', {
        url: '',
        views: {
          'contentFragmentGeneralEdit': {
            templateUrl: baseUrl + 'ad2/fragments/generalEdit.tpl.html',
            controller: 'CtrlAdContentGeneral'
          },
          'contentFragmentPositionEdit': {
            templateUrl: baseUrl + 'ad2/fragments/positionEdit.tpl.html',
            controller: 'CtrlAdContentPosition'
          },
          'contentFragmentAdvertisingEdit': {
            templateUrl: baseUrl + 'ad2/fragments/advertisingEdit.tpl.html',
            controller: 'CtrlAdContentAdvertising'
          },
          'contentFragmentPricingEdit': {
            templateUrl: baseUrl + 'ad2/fragments/pricingEdit.tpl.html',
            controller: 'CtrlAdContentPricing'
          },
          'contentFragmentMaterialEdit': {
            templateUrl: baseUrl + 'ad2/fragments/materialEdit.tpl.html',
            controller: 'CtrlAdContentMaterial'
          },

          'contentFragmentGeneralView': {templateUrl: baseUrl + 'ad2/fragments/generalView.tpl.html'},
          'contentFragmentPositionView': {templateUrl: baseUrl + 'ad2/fragments/positionView.tpl.html'},
          'contentFragmentAdvertisingView': {templateUrl: baseUrl + 'ad2/fragments/advertisingView.tpl.html'},
          'contentFragmentPricingView': {templateUrl: baseUrl + 'ad2/fragments/pricingView.tpl.html'},
          'contentFragmentMaterialView': {
            templateUrl: baseUrl + 'ad2/fragments/materialView.tpl.html',
            controller: 'CtrlAdContentMaterial'
          }
        }
      });
    /**
     * [url description] task and approval
     * @type {String}
     */
    $stateProvider.state('task', {
      url: '/task',
      'abstract': true,
      views: {
        'body': {
          templateUrl: baseUrl + 'task/taskMain.tpl.html',
          controller: 'CtrlTask',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['task/CtrlTask'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    })
      .state('task.facade', {
        url: '',
        views: {
          'userTask': {
            templateUrl: baseUrl + 'task/task.tpl.html'
            // controller: 'CtrlTask',
            // resolve: {
            //     ctrl: $couchPotatoProvider.resolveDependencies(['task/CtrlTask'])
            // }
          },
          'taskMainWrap': {
            templateUrl: baseUrl + 'task/taskMainWrap.tpl.html'
          }
        }
      })
      .state('task.facade.noTask', {
        url: '/noTask',
        views: {
          'taskMain': {
            templateUrl: baseUrl + 'task/noTask.tpl.html',
            controller: 'CtrlNoTask',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['task/CtrlNoTask'])
            }
          }
        }
      })
      .state('task.facade.approval', {
        url: '/approval?adSolutionId&activityId',
        views: {
          'taskMain': {
            templateUrl: baseUrl + 'task/approval/approval.tpl.html',
            controller: 'CtrlApproval',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['task/approval/CtrlApproval'])
            }
          }
        }
      })
      .state('task.facade.schedule', {
        url: '/schedule?adSolutionId&activityId&processId',
        views: {
          'taskMain': {
            templateUrl: baseUrl + 'task/schedule/schedule.tpl.html',
            controller: 'CtrlSchedule',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['task/schedule/CtrlSchedule'])
            }
          }
        }
      })
      .state('task.facade.benchmarkPriceApproval', {
        url: '/benchmarkPriceApproval?quoteId&activityId',
        views: {
          'taskMain': {
            templateUrl: baseUrl + 'task/benchmarkPriceApproval/benchmarkPriceApproval.tpl.html',
            controller: 'CtrlBenchmarkPriceApproval',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['task/benchmarkPriceApproval/CtrlBenchmarkPriceApproval'])
            }
          }
        }
      })
      .state('task.facade.materialApproval', {
        url: '/materialApproval?materialId&activityId',
        views: {
          'taskMain': {
            templateUrl: baseUrl + 'task/materialApproval/materialApproval.tpl.html',
            controller: 'CtrlMaterialApproval',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['task/materialApproval/CtrlMaterialApproval'])
            }
          }
        }
      })
      .state('task.facade.customerApproval', {
        url: '/customerApproval?customerId&activityId&processId',
        views: {
          'taskMain': {
            templateUrl: baseUrl + 'task/customerApproval/customerApproval.tpl.html',
            controller: 'CtrlCustomerApproval',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['task/customerApproval/CtrlCustomerApproval'])
            }
          }
        }
      })
      .state('task.facade.pubApplyApproval', {
        url: '/pubApplyApproval?onlineApplyId&activityId&processId',
        views: {
          'taskMain': {
            templateUrl: baseUrl + 'task/pubApplyApproval/pubApplyApproval.tpl.html',
            controller: 'CtrlPubApplyApproval',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['task/pubApplyApproval/CtrlPubApplyApproval'])
            }
          }
        }
      })
      .state('task.history', {
          url: '/historyApproval',
          views: {
            'body': {
              templateUrl: baseUrl + 'task/historyApproval/approvalHistoryList.tpl.html',
              controller: 'CtrlApprovalHistoryList',
              resolve: {
                ctrl: $couchPotatoProvider.resolveDependencies(['task/historyApproval/CtrlApprovalHistoryList'])
              }
            },
            header: headerConfig,
            footer: footerConfig
          }
      });
    /**
     * [url description] schedule list and detail
     * @type {String}
     */
    $stateProvider.state('scheduleList', {
      url: '/scheduleList',
      //'abstract': true,
      views: {
        'body': {
          templateUrl: baseUrl + 'schedule/list/scheduleList.tpl.html',
          controller: 'CtrlScheduleList',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['schedule/list/CtrlScheduleList'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    })
      .state('scheduleDetail', {
        url: '/scheduleDetail?scheduleId',
        //'abstract': true,
        views: {
          'body': {
            templateUrl: baseUrl + 'schedule/detail/scheduleDetail.tpl.html',
            controller: 'CtrlScheduleDetail',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['schedule/detail/CtrlScheduleDetail'])
            }
          },
          header: headerConfig,
          footer: footerConfig
        }
      });
    /**
     * [url description] adSolution list and detail
     * @type {String}
     */
    $stateProvider.state('adSolutionList', {
      url: '/adSolutionList?adNumber',
      //'abstract': true,
      views: {
        'body': {
          templateUrl: baseUrl + 'adSolution/list/adSolutionList.tpl.html',
          controller: 'CtrlAdSolutionList',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['adSolution/list/CtrlAdSolutionList'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      },
      reloadOnSearch: false
    })
      .state('adSolutionDetail', {
        url: '/adSolutionDetail?id',
        //'abstract': true,
        views: {
          'body': {
            templateUrl: baseUrl + 'adSolution/detail/adSolutionDetail.tpl.html',
            controller: 'CtrlAdSolutionDetail',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['adSolution/detail/CtrlAdSolutionDetail'])
            }
          },
          header: headerConfig,
          footer: footerConfig
        }
      });
    /**
     * [url description] ad content detail
     * @type {String}
     */
    $stateProvider.state('adContentDetail', {
      url: '/adContentDetail?id',
      views: {
        'body': {
          templateUrl: baseUrl + 'adContent/detail/adContentDetail.tpl.html',
          controller: 'CtrlAdContentDetail',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['adContent/detail/CtrlAdContentDetail'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    });
    /**
     * [url description] 标杆价
     * @type {String}
     */
    $stateProvider.state('benchmarkPriceManagement', {
      url: '/benchmarkPriceManagement',
      'abstract': true,
      views: {
        'body': {
          templateUrl: baseUrl + 'benchmarkPrice/benchmarkPriceManagement.tpl.html',
          controller: 'CtrlBenchmarkPriceManagement',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['benchmarkPrice/CtrlBenchmarkPriceManagement'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    })
      .state('benchmarkPriceManagement.list', {
        url: '/list',
        views: {
          'benchmarkPriceManagement': {
            templateUrl: baseUrl + 'benchmarkPrice/benchmarkPriceList.tpl.html',
            controller: 'CtrlBenchmarkPriceList',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['benchmarkPrice/CtrlBenchmarkPriceList'])
            }
          }
        }
      })
      .state('benchmarkPriceManagement.add', {
        url: '/add',
        'abstract': true,
        views: {
          'benchmarkPriceManagement': {
            templateUrl: baseUrl + 'benchmarkPrice/benchmarkPriceAdd.tpl.html',
            controller: 'CtrlBenchmarkPriceAdd',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['benchmarkPrice/CtrlBenchmarkPriceAdd'])
            }
          }
        }
      })
      .state('benchmarkPriceManagement.add.facade', {
        url: '',
        views: {
          'benchmarkPriceEdit': {
            templateUrl: baseUrl + 'benchmarkPrice/benchmarkPriceEdit.tpl.html',
            controller: 'CtrlBenchmarkPriceEdit',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['benchmarkPrice/CtrlBenchmarkPriceEdit'])
            }
          }
        }
      })
      .state('benchmarkPriceManagement.detail', {
        url: '/detail?id',
        'abstract': true,
        views: {
          'benchmarkPriceManagement': {
            templateUrl: baseUrl + 'benchmarkPrice/benchmarkPriceDetail.tpl.html',
            controller: 'CtrlBenchmarkPriceDetail',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['benchmarkPrice/CtrlBenchmarkPriceDetail', 'benchmarkPrice/CtrlBenchmarkPriceEdit'])
            }
          }
        }
      })
      .state('benchmarkPriceManagement.detail.facade', {
        url: '',
        views: {
          'benchmarkPriceEdit': {
            templateUrl: baseUrl + 'benchmarkPrice/benchmarkPriceEdit.tpl.html',
            controller: 'CtrlBenchmarkPriceEdit',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['benchmarkPrice/CtrlBenchmarkPriceEdit'])
            }
          }
        }
      })
      .state('benchmarkPriceManagement.current', {
        url: '/current',
        views: {
          'benchmarkPriceManagement': {
            templateUrl: baseUrl + 'benchmarkPrice/benchmarkPriceCurrent.tpl.html',
            controller: 'CtrlBenchmarkPriceCurrent',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['benchmarkPrice/CtrlBenchmarkPriceCurrent'])
            }
          }
        }
      });
    /**
     * [url description] 资源位
     * @type {String}
     */
    $stateProvider.state('resourcePositionAdd', {
      url: '/resourcePositionAdd?platformId&siteId',
      views: {
        'body': {
          templateUrl: baseUrl + 'resourcePosition/resourcePositionAdd.tpl.html',
          controller: 'CtrlResourcePositionAdd',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['resourcePosition/CtrlResourcePositionAdd'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    })
      .state('resourcePositionList', {
        url: '/resourcePositionList',
        views: {
          'body': {
            templateUrl: baseUrl + 'resourcePosition/resourcePositionList.tpl.html',
            controller: 'CtrlResourcePositionList',
            resolve: {
              ctrl: $couchPotatoProvider.resolveDependencies(['resourcePosition/CtrlResourcePositionList'])
            }
          },
          header: headerConfig,
          footer: footerConfig
        }
      });
    /**
     * [url description] 投放平台列表
     * @type {String}
     */
    $stateProvider.state('platformList', {
      url: '/platformList',
      views: {
        'body': {
          templateUrl: baseUrl + 'platform/platformList.tpl.html',
          controller: 'CtrlPlatformList',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['platform/CtrlPlatformList'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    });
    /**
     * [url description] 物料管理-列表
     * @type {String}
     */
    $stateProvider.state('materialList', {
      url: '/materialList',
      views: {
        'body': {
          templateUrl: baseUrl + 'material/materialList.tpl.html',
          controller: 'CtrlMaterialList',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['material/CtrlMaterialList'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    });
    /**
     * [url description] 物料管理-详情
     * @type {String}
     */
    $stateProvider.state('materialDetail', {
      url: '/materialDetail?contentId',
      views: {
        'body': {
          templateUrl: baseUrl + 'material/materialDetail.tpl.html',
          controller: 'CtrlMaterialDetail',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['material/CtrlMaterialDetail'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    });

    /**
     * [url description]上下线管理
     * @type {String}
     */
    $stateProvider.state('publicationMgmt', {
      url: '/publicationMgmt?applyNumber',
      views: {
        'body': {
          templateUrl: baseUrl + 'publication/publicationMgmt.tpl.html',
          controller: 'CtrlPublicationMgmt',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['publication/CtrlPublicationMgmt'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    });
    /**
     * [url description]上下线管理-人员配置
     * @type {String}
     */
    $stateProvider.state('publicationMember', {
      url: '/publicationMember',
      views: {
        'body': {
          templateUrl: baseUrl + 'publication/member/publicationMember.tpl.html',
          controller: 'CtrlPublicationMember',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['publication/member/CtrlPublicationMember'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    });
    /**
     * 客户管理-客户列表
     */
    $stateProvider.state('customer', {
      url: '/customer',
      views: {
        'body': {
          templateUrl: baseUrl + 'customer/customer.tpl.html',
          controller: 'CtrlCustomer',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/CtrlCustomer'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    });
    $stateProvider.state('customer.list', {
      url: '/list',
      views: {
        'customer': {
          templateUrl: baseUrl + 'customer/list/customerList.tpl.html',
          controller: 'CtrlCustomerList',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/list/CtrlCustomerList'])
          }
        }
      }
    })
    .state('customer.add', {
      url: '/add?customerId',
      'abstract': true,
      views: {
        'customer': {
          templateUrl: baseUrl + 'customer/add/add.tpl.html',
          controller: 'CtrlCustomerAdd',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/add/CtrlCustomerAdd'])
          }
        }
      }
    })
    .state('customer.add.facade', {
      url: '',
      views: {
        'customerBasic': {
          templateUrl: baseUrl + 'customer/customerBasic.tpl.html',
          controller: 'CtrlCustomerBasic',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/CtrlCustomerBasic'])
          }
        },
        'customerContacts': {
          templateUrl: baseUrl + 'customer/customerContacts.tpl.html',
          controller: 'CtrlCustomerContacts',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/CtrlCustomerContacts'])
          }
        },
        'customerOpportunity': {
          templateUrl: baseUrl + 'customer/customerOpportunity.tpl.html',
          controller: 'CtrlCustomerOpportunity',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/CtrlCustomerOpportunity'])
          }
        },
        'customerQualification': {
          templateUrl: baseUrl + 'customer/customerQualification.tpl.html',
          controller: 'CtrlCustomerQualification',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/CtrlCustomerQualification'])
          }
        },
        'customerAttachment': {
          templateUrl: baseUrl + 'customer/customerAttachment.tpl.html',
          controller: 'CtrlCustomerAttachment',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/CtrlCustomerAttachment'])
          }
        }
      }
    })
    .state('customer.detail', {
      url: '/detail?customerId',
      'abstract': true,
      views: {
        'customer': {
          templateUrl: baseUrl + 'customer/detail/detail.tpl.html',
          controller: 'CtrlCustomerDetail',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/detail/CtrlCustomerDetail'])
          }
        }
      }
    })
    .state('customer.detail.facade', {
      url: '',
      views: {
        'customerBasic': {
          templateUrl: baseUrl + 'customer/customerBasic.tpl.html',
          controller: 'CtrlCustomerBasic',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/CtrlCustomerBasic'])
          }
        },
        'customerContacts': {
          templateUrl: baseUrl + 'customer/customerContacts.tpl.html',
          controller: 'CtrlCustomerContacts',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/CtrlCustomerContacts'])
          }
        },
        'customerOpportunity': {
          templateUrl: baseUrl + 'customer/customerOpportunity.tpl.html',
          controller: 'CtrlCustomerOpportunity',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/CtrlCustomerOpportunity'])
          }
        },
        'customerQualification': {
          templateUrl: baseUrl + 'customer/customerQualification.tpl.html',
          controller: 'CtrlCustomerQualification',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/CtrlCustomerQualification'])
          }
        },
        'customerAttachment': {
          templateUrl: baseUrl + 'customer/customerAttachment.tpl.html',
          controller: 'CtrlCustomerAttachment',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/CtrlCustomerAttachment'])
          }
        },
        // 'customerAdSolutionList': {
        //   templateUrl: baseUrl + 'customer/customerAdSolutionList.tpl.html',
        //   controller: 'CtrlCustomerAdSolutionList',
        //   resolve: {
        //     ctrl: $couchPotatoProvider.resolveDependencies(['customer/CtrlCustomerAdSolutionList'])
        //   }
        // },
        'customerAdSolutionList': {
          templateUrl: baseUrl + 'adSolution/list/adSolutionList.tpl.html',
          controller: 'CtrlAdSolutionList',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['adSolution/list/CtrlAdSolutionList'])
          }
        },
        'customerContractList': {
          templateUrl: baseUrl + 'customer/customerContractList.tpl.html',
          controller: 'CtrlCustomerContractList',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/CtrlCustomerContractList'])
          }
        },
        'customerAccountList': {
          templateUrl: baseUrl + 'customer/customerAccountList.tpl.html',
          controller: 'CtrlCustomerAccountList',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['customer/CtrlCustomerAccountList'])
          }
        }
      }
    });
    /**
     * 内部用户管理
     */
    $stateProvider.state('adminUserList', {
      url: '/adminUserList',
      views: {
        'body': {
          templateUrl: baseUrl + 'admin/adminUserList.tpl.html',
          controller: 'CtrlAdminUserList',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['admin/CtrlAdminUserList'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    });
    /**
     * 用户详情
     */
    $stateProvider.state('userDetail', {
      url: '/userDetail?ucId',
      views: {
        'body': {
          templateUrl: baseUrl + 'admin/userDetail.tpl.html',
          controller: 'CtrlUserDetail',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['admin/CtrlUserDetail'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    });
    /**
     * 公告管理
     */
    $stateProvider.state('noticeList', {
      url: '/noticeList',
      views: {
        'body': {
          templateUrl: baseUrl + 'notice/noticeList.tpl.html',
          controller: 'CtrlNoticeList',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['notice/CtrlNoticeList'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    });
    /**
     * 公告管理详情
     */
    $stateProvider.state('notice', {
      url: '/notice?id&isEdit',
      'abstract': true,
      views: {
        'body': {
          templateUrl: baseUrl + 'notice/notice.tpl.html',
          controller: 'CtrlNotice',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['notice/CtrlNotice'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    })
    .state('notice.facade', {
      url: '',
      views: {
        edit: {
          templateUrl: baseUrl + 'notice/noticeEdit.tpl.html',
          controller: 'CtrlNoticeEdit',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['notice/CtrlNotice'])
          }
        },
        detail: {
          templateUrl: baseUrl + 'notice/noticeDetail.tpl.html',
          controller: 'CtrlNoticeDetail',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['notice/CtrlNotice'])
          }
        }
      }
    });
    $stateProvider.state('report', {
      url: '/report',
      'abstract': true,
      views: {
        'body': {
          templateUrl: baseUrl + 'report/report.tpl.html',
          controller: 'CtrlReport',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['report/CtrlReport'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    })
    .state('report.hao123Trace', {
      url: '/hao123Trace',
      views: {
        report: {
          templateUrl: baseUrl + 'report/hao123Trace.tpl.html',
          controller: 'CtrlHao123Trace',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['report/CtrlHao123Trace'])
          }
        }
      }
    });

    $stateProvider.state('defaultAd', {
      url: '/defaultAd',
      views: {
        'body': {
          templateUrl: baseUrl + 'defaultAd/defaultAd.tpl.html',
          controller: 'CtrlDefaultAd',
          resolve: {
            ctrl: $couchPotatoProvider.resolveDependencies(['defaultAd/CtrlDefaultAd'])
          }
        },
        header: headerConfig,
        footer: footerConfig
      }
    });

    angular.noop(); //do not remove this line,grunt tool use this to do reg match.
  }
]);
//end for define
});