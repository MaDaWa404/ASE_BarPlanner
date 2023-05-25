var app = angular.module("DrinkManagement", []);

// Controller Part
app.controller("DrinkController", function ($scope, $http) {

    $scope.drinks = [];
    $scope.username = "";
    $scope.bar = "";

    // Now load the data from server
    _getUsername();
    _refreshDrinkData();
    _resetForms()


    // HTTP GET - get username
    function _getUsername() {
        $http({
            method: 'GET',
            url: '/api/persons'
        }).then(
            function (res) { // success
                $scope.username = res.data.username
            },
            function () { // error
                $scope.username = ""
            }
        )
    }

    // HTTP GET - get all drinks
    function _refreshDrinkData() {
        $http({
            method: 'GET',
            url: '/api/drinks'
        }).then(
            function (res) { // success
                $scope.drinks = res.data.drinks
                $scope.bar = res.data.bar
            },
            function (res) { // error
                _error(res)
                $scope.drinks = []
                $scope.bar = ""
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
        }).then(_successUser, _errorUser);
    }

    $scope.login = function () {
        $http({
            method: "POST",
            url: '/api/persons/login',
            data: angular.toJson($scope.loginForm),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_successUser, _errorUser);
    }

    $scope.logout = function () {
        $scope.username = ""
        $http({
            method: "GET",
            url: '/api/persons/logout',
        }).then(_successUser, _errorUser);
    }

    function _success() {
        _refreshDrinkData()
        _resetForms()
    }

    function _successUser(res) {
        $scope.username = res.data.username
        _success(res)
    }

    function _error(res) {
        console.log("Error: " + res.status)
    }

    function _errorUser(res) {
        $scope.username = ""
        _error(res)
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
        $scope.registerBarForm = {
            title: "",
            zip: "",
            city: "",
            street: "",
            number: ""
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

    $scope.registerBar = function () {
        $http({
            method: "POST",
            url: '/api/bars',
            data: angular.toJson($scope.registerBarForm),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_success, _error);
    }

    $scope.deleteBar = function () {
        $http({
            method: "DELETE",
            url: '/api/bars',
        }).then(_success, _error);
    }
})