/**
 * Created by Serg Alekseichenko on 1/5/2016.
 */
var newDeal = angular.module('newDeal', []);

newDeal.controller('DealController', function ($scope, $http, $filter, $window) {
    angular.element(document).ready(function () {
        getCompanies($http, $scope);
        getContacts($http, $scope);
        getPhoneTypes($http, $scope);
        getTaskTypes($http, $scope);
        getUsers($http, $scope);
        getDealStatuses($http, $scope);
    });
    $scope.selectedContacts = [];
    $scope.formData = {};
    $scope.formData.dealcontactlist = [];


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
            $scope.formData.dealcontactlist.push(object.id);
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
            $scope.formData.dealcontactlist.splice(index, 1);
        }
    };


    $scope.processForm = function (type) {
        $scope.formData.dealcreated = $filter("date")($scope.formData.dealcreated, 'yyyy-MM-dd');
        $scope.formData.taskduedate = $filter("date")($scope.formData.taskduedate, 'MM/dd/yyyy HH:mm');
        $http({
            method: 'PUT',
            url: '/rest/' + type,
            data: $.param($scope.formData),  // pass in data as strings
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}  // set the headers so angular passing info as form data (not request payload)
        })
            .success(function () {
                if (type == 'deals') {
                    $window.location.href = '/';
                } else {
                    $scope.formData = {};
                    getContacts($http, $scope);
                    getCompanies($http, $scope);
                }
            });
    };
});


function getContacts($http, $scope) {
    $scope.contacts = [];
    $http({
        method: 'GET',
        url: '../rest/contacts'
    }).then(function successCallback(response) {
        $scope.contacts = response.data;
    });
}
//Demo function
function getCountry($http, $scope) {
    $scope.country = [];
    $http({
        method: 'GET',
        url: 'http://ip-api.com/json/'
    }).then(function successCallback(response) {
        $scope.country = response.data;
    });
}

function getCompanies($http, $scope) {
    $http({
        method: 'GET',
        url: '../rest/companies'
    }).then(function successCallback(response) {
        $scope.companies = response.data;
    });
}

function getPhoneTypes($http, $scope) {
    $http({
        method: 'GET',
        url: '../rest/contacts/phonetypes'
    }).then(function successCallback(response) {
        $scope.phoneTypes = response.data;
    });
}

function getTaskTypes($http, $scope) {
    $http({
        method: 'GET',
        url: '../rest/tasks/tasktypes'
    }).then(function successCallback(response) {
        $scope.taskTypes = response.data;
    });
}

function getUsers($http, $scope) {
    $http({
        method: 'GET',
        url: '../rest/users'
    }).then(function successCallback(response) {
        $scope.users = response.data;
    });
}

function getDealStatuses($http, $scope) {
    $http({
        method: 'GET',
        url: '../rest/deals/dealstatuses'
    }).then(function successCallback(response) {
        $scope.dealStatuses = response.data;
    });
}

