var app = angular.module("DrinkManagement", []);

// Controller Part
app.controller("DrinkController", function ($scope, $http) {

    const apiUrl = '/api/drinks'

    $scope.drinks = [];
    $scope.drinkForm = {
        title: "",
        price: "",
        amount: ""
    }

    // Now load the data from server
    _refreshDrinkData();

    // HTTP GET - get all drinks
    function _refreshDrinkData() {
        $http({
            method: 'GET',
            url: '/api/drinks'
        }).then(
            function (res) { // success
                $scope.drinks = res.data;
            },
            function (res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }

    // HTTP POST for adding drinks
    $scope.addDrink = function () {
        $http({
            method: "POST",
            url: apiUrl,
            data: angular.toJson($scope.drinkForm),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_success, _error);
    };

    function _success() {
        _refreshDrinkData();
        _clearFormData();

    }

    function _error(res) {
        const message = res.data.message;
        const status = res.status;
        alert("Error " + status + ": " + message);
    }

    // Clear the form
    function _clearFormData() {
        $scope.drinkForm.title = "";
        $scope.drinkForm.price = "";
        $scope.drinkForm.amount = ""
    }

    //HTTP DELETE for deleting drinks
    $scope.deleteDrink = function (title) {
        $http({
            method: "DELETE",
            url: apiUrl,
            data: title,
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_success, _error);
    }
})