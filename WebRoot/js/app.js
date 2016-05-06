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
            $location.path('/hra')
        }
    })
    .controller('loginCtr', function ($scope, $rootScope, $location) {
        $scope.login = function () {
            $.ajax({
                type: 'POST',
                async: false,
                url: 'userCtr.do?login',
                data: $scope.user,
                success: function (answer) {
                    if (answer.result) {
                        $rootScope.ifLogin = true;
                        $rootScope.userName = answer.obj;
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
        $scope.types = [{name: '冠心病', value: '1'}, {name: '心脏病', value: '2'}, {name: '肺癌', value: '3'},
            {name: '脑血管病', value: '4'}];
        $scope.dms = [{name: '有', value: '1'}, {name: '有，但已控制', value: '2'}, {name: '无', value: '3'}];
        $scope.sportsTo1 = [{name: '坐着工作和娱乐', value: '1'}, {name: '有些活动的工作', value: '2'},
            {name: '中度锻炼', value: '3'}, {name: '较强度锻炼', value: '4'},
            {name: '坐着工作，有定期锻炼', value: '5'}, {name: '其他工作，有定期锻炼', value: '6'}];
        $scope.sportsTo2 = [{name: '坐着工作和娱乐', value: '1'}, {name: '极少活动', value: '2'},
            {name: '适当活动', value: '3'}, {name: '经常活动', value: '4'}];
        $scope.familysTo1 = [{name: '父母亲60岁前死于冠心病', value: '1'},
            {name: '父母二人之一60岁前死于冠心病', value: '2'}, {name: '父母建在（<60岁）', value: '3'},
            {name: '父母建在（>=60岁）', value: '4'}];
        $scope.familysTo2 = [{name: '父母亲70岁前死于心脏病', value: '1'}, {name: '父母二人之一死于心脏病', value: '2'},
            {name: '无心脏病家族史', value: '3'}];
        $scope.smokesTo1 = [{name: '>=10支/日', value: '1'}, {name: '<10支/日', value: '2'},
            {name: '吸雪茄或烟斗', value: '3'}, {name: '戒烟（不足10年）', value: '4'}, {name: '不吸或戒烟10年以上', value: '5'}];
        $scope.smokesTo2 = [{name: '40支以上', value: '1'}, {name: '20-39支', value: '2'}, {name: '10-19支', value: '3'},
            {name: '1-9支', value: '4'}, {name: '无', value: '5'}];
        $scope.smokesTo3 = [{name: '有', value: '1'}, {name: '无', value: '2'}];
        $scope.weightsTo1 = [{name: '超重75%', value: '1'}, {name: '超重50%', value: '2'}, {name: '超重15%', value: '3'},
            {name: '超重10%以下', value: '4'}, {name: '降到平均体重', value: '5'}];
        $scope.weightsTo2 = [{name: '超重60%', value: '1'}, {name: '超重40%', value: '2'}, {name: '超重20%', value: '3'},
            {name: '正常', value: '4'}, {name: '低体重', value: '5'}];
        $.ajax({
            url: 'userCtr.do?hraPage',
            async: false,
            success: function (answer) {
                if (answer.result) {
                    $scope.hra = answer.obj;
                    $scope.hra.dm = answer.obj.dm.toString();
                    $scope.hra.sport = answer.obj.sport.toString();
                    $scope.hra.smoke = answer.obj.smoke.toString();
                    $scope.hra.family = answer.obj.family.toString();
                    $scope.hra.weight = answer.obj.weight.toString();
                } else {
                    alert("出现未知错误，请刷新页面")
                }
            },
            error: function (answer) {
                if (answer.status == 577) {
                    alert('未登录或登录超时，请先登录');
                    $location.path('/login');
                }
            },
            dataType: 'json'
        });
        $scope.hraSub = function () {
            console.info($scope.hra.type);
            $.ajax({
                type: 'POST',
                async: false,
                url: 'userCtr.do?hra',
                data: $scope.hra,
                success: function (answer) {
                    if (answer.result) {
                        $scope.illResult = answer.obj;
                    } else {
                        alert(answer.msg)
                    }
                },
                dataType: 'json'
            })
        }
    });