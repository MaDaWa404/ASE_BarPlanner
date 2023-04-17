var app = angular.module("GuestManagement", []);

// Controller Part
app.controller("GuestController", function ($scope, $http) {

    $scope.drinks = [];
    $scope.username = "";
    $scope.bar = "";
    $scope.bars = [];

    // Now load the data from server
    _getBars();
    _getDrinks();


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

    // HTTP GET - get all drinks
    function _getDrinks() {
        $http({
            method: 'GET',
            url: '/api/drinks',
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

    function _error(res) {
        if (res.data.alert) {
            alert("Error: " + res.status + " : " + res.data.message)
        }
        console.log("Error: " + res.status + " : " + res.data.message)
    }


})