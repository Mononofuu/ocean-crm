/**
 * Created by Serg Alekseichenko on 1/5/2016.
 */
var newDeal = angular.module('newDeal', []);

newDeal.controller('ContactsController', function ($scope, $http) {
    $scope.contacts = [];
    $scope.selectedContacts = [];
    $http({
        method: 'GET',
        url: '../deal?action=getContacts'
    }).then(function successCallback(response) {
        $scope.contacts = response.data;
    });
    $scope.selectContact = function (data) {
        var dataArray = data.split(","), index = -1;
        var object = {id: dataArray[0], name: dataArray[1]};

        for(var i = 0, len = $scope.selectedContacts.length; i < len; i++) {
            if ($scope.selectedContacts[i].id === object.id) {
                index = i;
                break;
            }
        }
        if (index==-1){
            $scope.selectedContacts.push(object);
        }
    };
    $scope.removeContact = function (id){
        var index = -1;
        for(var i = 0, len = $scope.selectedContacts.length; i < len; i++) {
            if ($scope.selectedContacts[i].id === id) {
                index = i;
                break;
            }
        }
        if (index > -1){
            $scope.selectedContacts.splice(index, 1);
        }
    };
});

newDeal.controller('MainCtrl', function ($scope) {
    $scope.visible = false;
});

newDeal.controller('formController', function ($scope, $http) {

    // create a blank object to hold our form information
    // $scope will allow this to pass between controller and view
    $scope.formData = {};

    // process the form
    $scope.processForm = function () {
        $http({
            method: 'POST',
            url: '/deal',
            data: $.param($scope.formData),  // pass in data as strings
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}  // set the headers so angular passing info as form data (not request payload)
        })
            .success(function () {

                $scope.formData = {};
                getCompanies();
            });
    };
});


function getCompanies() {
    $.get("../deal?action=getCompanies", function (responseJson) {
        document.getElementsByClassName('companies').innerHTML = "";
        ($("<option disabled selected>")).appendTo($(".companies")).append("Выбрать компанию");
        $.each(responseJson, function (index, company) {
            ($("<option value='" + company.id + "'>")).appendTo($(".companies")).append(company.name);
        });
    });
}

function getPhoneTypes() {
    $.get("../deal?action=getPhoneTypes", function (responseJson) {
        document.getElementsByClassName('phonetype').innerHTML = "";
        ($("<option disabled selected>")).appendTo($(".phonetype")).append("Тип");
        $.each(responseJson, function (index, phoneType) {
            ($("<option value='" + phoneType + "'>")).appendTo($(".phonetype")).append(phoneType);
        });
    });
}
