angular.module('myApp', ['ngRoute'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/status', {
                templateUrl: 'views/status.html'
            })
            .when('/login', {
                templateUrl: 'views/login.html',
                controller: 'loginCtr'
            })
            .when('/hra', {
                templateUrl: 'views/hra.html',
                controller: 'hraCtr'
            })
            .otherwise({
                redirectTo: '/status'
            });
    })
    .controller('mainCtr', function ($scope, $location) {
        $scope.hraPage = function () {
            $.ajax({
                url: 'userCtr.do?hraPage',
                async: false,
                success: function (answer) {
                    if (answer.result) {
                        $location.path('/hra');
                    } else {

                    }
                },
                error: function (answer) {
                    if (answer.status == 577) {
                        alert('未登录或登录超时，请先登录');
                        $location.path('/login');
                    }
                },
                dataType: 'json'
            })
        }
    })
    .controller('loginCtr', function ($scope, $location) {
        $scope.login = function () {
            $.ajax({
                type: 'POST',
                async: false,
                url: 'userCtr.do?login',
                data: $scope.user,
                success: function (answer) {
                    if (answer.result) {
                        $location.path('/hra');
                    } else {
                        alert(answer.msg)
                    }
                },
                dataType: 'json'
            })
        }
    })
    .controller('hraCtr', function ($scope, $location) {
        $scope.hraSub = function () {
            $.ajax({
                type: 'POST',
                async: false,
                url: 'userCtr.do?hra',
                data: $scope.hra,
                success: function (answer) {
                    if (answer.result) {
                        ;
                    } else {
                        alert(answer.msg)
                    }
                },
                error: function (answer) {
                    if (answer.status == 577) {
                        alert('未登录或登录超时，请先登录');
                        $location.path('/login');
                    }
                },
                dataType: 'json'
            })
        }
    });