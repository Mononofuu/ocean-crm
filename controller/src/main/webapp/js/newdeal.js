/**
 * Created by Serg Alekseichenko on 1/5/2016.
 */
var newDeal = angular.module('newDeal', []);

newDeal.controller('DealController', function ($scope, $http) {
    angular.element(document).ready(function () {
        getCompanies($http, $scope);
        getContacts($http, $scope);
        getPhoneTypes($http, $scope);
    });
    $scope.selectedContacts = [];
    $scope.formData = {};

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
        }
    };


    $scope.processForm = function (type) {
        $http({
            method: 'POST',
            url: '/deal?action=' + type,
            data: $.param($scope.formData),  // pass in data as strings
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}  // set the headers so angular passing info as form data (not request payload)
        })
            .success(function () {
                $scope.formData = {};
                getContacts($http, $scope);
                getCompanies($http, $scope);
            });
    };
});


function getContacts($http, $scope) {
    $scope.contacts = [];
    $http({
        method: 'GET',
        url: '../deal?action=getContacts'
    }).then(function successCallback(response) {
        $scope.contacts = response.data;
    });
}

function getCompanies($http, $scope) {
    $http({
        method: 'GET',
        url: '../deal?action=getCompanies'
    }).then(function successCallback(response) {
        $scope.companies = response.data;
    });
}

function getPhoneTypes($http, $scope) {
    $http({
        method: 'GET',
        url: '../deal?action=getPhoneTypes'
    }).then(function successCallback(response) {
        $scope.phoneTypes = response.data;
    });
}
