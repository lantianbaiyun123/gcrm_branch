define(['app'], function (app) {
    app.registerDirective('ytBusySpin', [
        '$compile',
        '$document',
    function ($compile, $document) {
        return {
            restrict: 'A',
            scope: {
                ytBusySpinShow: '='
            },
            link: function (scope, element, attrs) {
                var spinTypes = {
                    doubleBounce: 'yt-busy-spin-double-bounce',
                    pulse: 'yt-busy-spin-pulse',
                    wave: 'yt-busy-spin-wave',
                    wanderingCubes: 'yt-busy-spin-wandering-cubes',
                    chasingDots: 'yt-busy-spin-chasing-dots',
                    threeBounce: 'yt-busy-spin-three-bounce',
                    circle: 'yt-busy-spin-circle'
                };
                var spinType = attrs.ytBusySpinType || 'threeBounce';
                var body = $document.find('body').eq(0);
                var scopeBusySpin = scope.$new();
                var busySpinEl = $compile('<div class="busy-spin" ' + spinTypes[spinType] + '></div>')(scopeBusySpin);

                scope.$watch('ytBusySpinShow', function (isShow) {
                    if (isShow) {
                        body.append(busySpinEl);
                    } else {
                        busySpinEl.remove();
                    }
                });
            }
        };
    }]);

    app.registerDirective('ytBusySpinDoubleBounce', function () {
        return {
            restrict: 'A',
            template: '<div class="double-bounce">' +
                        '<div class="double-bounce1"></div>' +
                        '<div class="double-bounce2"></div>' +
                      '</div>',
            link: function (scope, element, attrs) {

            }
        };
    });

    app.registerDirective('ytBusySpinPulse', function () {
        return {
            restrict: 'A',
            template: '<div class="pulse"></div>',
            link: function (scope, element, attrs) {

            }
        };
    });

    app.registerDirective('ytBusySpinWave', function () {
        return {
            restrict: 'A',
            template: '<div class="wave">' +
                        '<div class="rect1"></div>' +
                        '<div class="rect2"></div>' +
                        '<div class="rect3"></div>' +
                        '<div class="rect4"></div>' +
                        '<div class="rect5"></div>' +
                      '</div>',
            link: function (scope, element, attrs) {

            }
        };
    });

    app.registerDirective('ytBusySpinWanderingCubes', function () {
        return {
            restrict: 'A',
            template: '<div class="wandering-cubes">' +
                        '<div class="cube1"></div>' +
                        '<div class="cube2"></div>' +
                      '</div>',
            link: function (scope, element, attrs) {

            }
        };
    });

    app.registerDirective('ytBusySpinChasingDots', function () {
        return {
            restrict: 'A',
            template: '<div class="chasing-dots">' +
                        '<div class="dot1"></div>' +
                        '<div class="dot2"></div>' +
                      '</div>'
        };
    });

    app.registerDirective('ytBusySpinThreeBounce', function () {
        return {
            restrict: 'A',
            template: '<div class="three-bounce">' +
                        '<div class="bounce1"></div>' +
                        '<div class="bounce2"></div>' +
                        '<div class="bounce3"></div>' +
                      '</div>'
        };
    });

    app.registerDirective('ytBusySpinCircle', function () {
        return {
            restrict: 'A',
            template: '<div class="circle">' +
                            '<div class="spinner-container container1">' +
                              '<div class="circle1"></div>' +
                              '<div class="circle2"></div>' +
                              '<div class="circle3"></div>' +
                              '<div class="circle4"></div>' +
                            '</div>' +
                            '<div class="spinner-container container2">' +
                              '<div class="circle1"></div>' +
                              '<div class="circle2"></div>' +
                              '<div class="circle3"></div>' +
                              '<div class="circle4"></div>' +
                            '</div>' +
                            '<div class="spinner-container container3">' +
                              '<div class="circle1"></div>' +
                              '<div class="circle2"></div>' +
                              '<div class="circle3"></div>' +
                              '<div class="circle4"></div>' +
                            '</div>' +
                      '</div>'
        };
    });
});