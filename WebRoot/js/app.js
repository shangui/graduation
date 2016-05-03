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
    .controller('loginCtr', function ($scope, $http, $location) {
        $scope.login = function () {
            $.ajax({
                type: 'POST',
                async: false,
                url: 'userCtr.do?login',
                data: {
                    name: $scope.user.name,
                    password: $scope.user.password
                },
                success: function (answer) {
                    if (answer.result == true) {
                        $location.path('/hra');
                    } else {
                        alert(answer.msg)
                    }
                },
                dataType: 'json'
            });
        }
    })
    .controller('hraCtr', function ($scope, $http, $location) {
    });