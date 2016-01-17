/**
 * Created by Serg Alekseichenko on 1/5/2016.
 */
var newCompany = angular.module('newCompany', []);

newCompany.controller('ContactController', function ($scope, $http, $filter, $window) {
    angular.element(document).ready(function () {
        getContacts($http, $scope);
        getPhoneTypes($http, $scope);
        getTaskTypes($http, $scope);
        getUsers($http, $scope);
        getDealStatuses($http, $scope);

    });
    $scope.formData = {};
    $scope.formData.contactlist = [];
    $scope.formData.addedDeals = [];
    $scope.addedDeals = [];
    $scope.selectedContacts = [];


    $scope.selectContact = function (data) {
        var dataArray = data.split(","), index = -1;
        var object = {id: dataArray[0], name: dataArray[1]};

        for (var i = 0, len = $scope.selectedContacts.length; i < len; i++) {
            if ($scope.selectedContacts[i].id === object.id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            $scope.selectedContacts.push(object);
            $scope.formData.contactlist.push(object.id);
        }
    };
    $scope.removeContact = function (id) {
        var index = -1;
        for (var i = 0, len = $scope.selectedContacts.length; i < len; i++) {
            if ($scope.selectedContacts[i].id === id) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            $scope.selectedContacts.splice(index, 1);
            $scope.formData.contactlist.splice(index, 1);
        }
    };

    $scope.addDeal = function () {
        $scope.addedDeals.push({
            name: $scope.formData.dealname,
            status: $scope.formData.dealstatus,
            budget: $scope.formData.dealbudget
        });
        $scope.formData.dealname = '';
        $scope.formData.dealstatus = '';
        $scope.formData.dealbudget = '';

    };
    $scope.removeDeal = function (deal) {
        var index = -1;
        for (var i = 0, len = $scope.addedDeals.length; i < len; i++) {
            if ($scope.addedDeals[i] === deal) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            $scope.addedDeals.splice(index, 1);
        }
    };
    $scope.getTotal = function(){
        var total = 0;
        for(var i = 0; i < $scope.addedDeals.length; i++){
            var deal = $scope.addedDeals[i];
            total += Number(deal.budget);
        }
        return total;
    };


    $scope.processForm = function (type) {
        for (var i = 0; i < $scope.addedDeals.length; i++) {
            var text = $scope.addedDeals[i].name + ';' + $scope.addedDeals[i].status + ';' + $scope.addedDeals[i].budget;
            $scope.formData.addedDeals.push(text)
        }
        $http({
            method: 'POST',
            url: '/newcompany?action=' + type,
            data: $.param($scope.formData),  // pass in data as strings
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}  // set the headers so angular passing info as form data (not request payload)
        })
            .success(function () {
                if (type == 'newcompany') {
                    $window.location.href = '/';
                } else {
                    $scope.formData = {};
                    $scope.selectedContacts = [];
                    $scope.addedDeals = [];
                    getContacts($http, $scope);
                }
            });
    };
});


function getContacts($http, $scope) {
    $scope.contacts = [];
    $http({
        method: 'GET',
        url: '../newcompany?action=getContacts'
    }).then(function successCallback(response) {
        $scope.contacts = response.data;
    });
}

function getPhoneTypes($http, $scope) {
    $http({
        method: 'GET',
        url: '../newcompany?action=getPhoneTypes'
    }).then(function successCallback(response) {
        $scope.phoneTypes = response.data;
    });
}

function getTaskTypes($http, $scope) {
    $http({
        method: 'GET',
        url: '../newcompany?action=getTaskTypes'
    }).then(function successCallback(response) {
        $scope.taskTypes = response.data;
    });
}

function getUsers($http, $scope) {
    $http({
        method: 'GET',
        url: '../newcompany?action=getUsers'
    }).then(function successCallback(response) {
        $scope.users = response.data;
    });
}

function getDealStatuses($http, $scope) {
    $http({
        method: 'GET',
        url: '../newcompany?action=getDealStatuses'
    }).then(function successCallback(response) {
        $scope.dealStatuses = response.data;
    });
}
