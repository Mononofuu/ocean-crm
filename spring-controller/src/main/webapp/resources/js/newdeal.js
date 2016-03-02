/**
 * Created by Serg Alekseichenko on 1/5/2016.
 */


var dealFormAjaxApp = angular.module("dealFormAjaxApp", []);

dealFormAjaxApp.controller("DealController", ['$scope', '$http', '$filter', function($scope, $http, $filter) {
    $scope.deal = {};
    getUsers($scope, $http);

    $scope.addDealAsyncAsJSON = function(){
        $scope.deal.dateCreated = $filter("date")($scope.deal.dateCreated, 'yyyy-MM-dd');
        var dataObj = $scope.deal;
        var res = $http.put('/rest/deals/', dataObj);
        res.success(function(data, status, headers, config) {
            $scope.result = data;
        });
        res.error(function(data, status, headers, config) {
            alert( "failure message: " + JSON.stringify({data: data}));
        });

        $scope.deal = {};
    };

    function getUsers($scope, $http) {
        $http({
            method: 'GET',
            url: '../rest/users'
        }).then(function successCallback(response) {
            $scope.users = response.data;
        });
    }
}]);