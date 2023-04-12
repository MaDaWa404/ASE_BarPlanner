var app = angular.module("DrinkManagement", []);

// Controller Part
app.controller("DrinkController", function ($scope, $http) {

    $scope.drinks = [];
    $scope.username = "";
    $scope.bar = "";

    // Now load the data from server
    _refreshDrinkData();
    _resetForms()

    // HTTP GET - get all drinks
    function _refreshDrinkData() {
        $http({
            method: 'GET',
            url: '/api/drinks'
        }).then(
            function (res) { // success
                console.log(res.data)
                $scope.drinks = res.data.drinks
                $scope.bar = res.data.bar
                $scope.username = res.data.username
            },
            function (res) { // error
                console.log(res.data)
                console.log("Error: " + res.status + " : " + res.data.message);
                $scope.drinks = []
                $scope.bar = ""
                $scope.username = ""
            }
        );
    }

    // HTTP POST for adding drinks
    $scope.addDrink = function () {
        $http({
            method: "POST",
            url: '/api/drinks',
            data: angular.toJson($scope.drinkForm),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_success, _error);
    };

    $scope.register = function () {
        $http({
            method: "POST",
            url: '/api/persons/register',
            data: angular.toJson($scope.registerForm),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_success, _error);
    }
    $scope.login = function () {
        $http({
            method: "POST",
            url: '/api/persons/login',
            data: angular.toJson($scope.loginForm),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_success, _error);
    }
    $scope.logout = function () {
        $scope.username = ""
        $http({
            method: "GET",
            url: '/api/persons/logout',
        }).then(_success, _error);
    }

    function _success() {
        _refreshDrinkData();
        _resetForms();
    }

    function _error(res) {
        console.log("error" + res.data)
        alert("Error: " + res.status + " : " + res.data.message)
    }

    function _resetForms() {
        $scope.drinkForm = {
            title: "",
            price: "",
            amount: ""
        }
        $scope.registerForm = {
            username: "",
            passwordHash: "",
            lastname: "",
            firstname: ""
        }
        $scope.loginForm = {
            username: "",
            passwordHash: "",
        }
    }

    //HTTP DELETE for deleting drinks
    $scope.deleteDrink = function (title) {
        $http({
            method: "DELETE",
            url: '/api/drinks',
            data: title,
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_success, _error);
    }
})