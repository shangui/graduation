angular.module('loginApp', [])
    .controller('loginCtr', function ($scope, $http) {
        $scope.login = function () {
            $http.post('userCtr.do?login', {user: $scope.user})
                .success(function () {
                    window.location.href = '${pageContext.request.contextPath}/home.html';
                })
                .error(function (msg) {
                    alert('');
                })
        }
    });
