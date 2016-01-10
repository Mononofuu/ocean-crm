/**
 * Created by Serg Alekseichenko on 1/5/2016.
 */
var app = angular.module('newDeal', []);

app.controller('MainCtrl', function ($scope) {
    $scope.visible = false;
});

app.controller('formController', function ($scope, $http) {

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

var contacts = [];
function getContacts() {
    $.get("../deal?action=getContacts", function (responseJson) {
        document.getElementsByClassName('contacts').innerHTML = "";
        ($("<option disabled selected>")).appendTo($(".contacts")).append("Выбрать контакт");
        $.each(responseJson, function (index, contact) {
            contacts.push({id: contact.id,name: contact.name});

           // ($("<option value='" + contact.id + "'>")).appendTo($(".contacts")).append(contact.name);
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

app.controller('ContactController', function () {
    var todoList = this;
    todoList.todos = contacts;

    todoList.addTodo = function (element) {
        todoList.todos.push({id: element});
        todoList.todoText = '';
    };
});
