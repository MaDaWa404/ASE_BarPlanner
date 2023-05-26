var app = angular.module("GuestManagement", []);

// Controller Part
app.controller("GuestController", function ($scope, $http) {

    $scope.selectedBar = ""
    $scope.drinks = []
    $scope.username = ""
    $scope.bar = ""
    $scope.bars = []
    $scope.cart = []

    // Now load the data from server
    _getUsername()
    _getBars()
    _refreshDrinkData()


    function _getBars() {
        $http({
            method: 'GET',
            url: '/api/bars'
        }).then(
            function (res) { // success
                $scope.bars = res.data.bars
            },
            function (res) { // error
                _error(res)
                $scope.bars = []
            }
        );
    }

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
            url: '/api/drinks?selectedBar=' + $scope.selectedBar,
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

    $scope.selectBar = function (title) {
        $scope.selectedBar = title
        _refreshDrinkData()
    }

    $scope.order = function (drink) {
        console.log($scope.selectedBar)
        $http({
            method: 'POST',
            url: '/api/purchases',
            data: angular.toJson({title: drink.title, selectedBar: $scope.selectedBar})
        }).then(
            function (res) { // success
                $scope.cart = res.data.cart
            },
            function (res) { // error
                _error(res)
            }
        );
    }


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
        console.log("Error: " + res.status + ": " + res.data)
    }

    function _errorUser(res) {
        alert("Username or password incorrect")
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

})