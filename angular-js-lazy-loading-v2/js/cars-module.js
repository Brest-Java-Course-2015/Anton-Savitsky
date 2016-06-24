/**
 * Created by antonsavitsky on 4/5/16.
 */
//var PREFIX_URL = "/rest-service-provider-1.0-SNAPSHOT";
//var PREFIX_URL="http://localhost:8085/rest-service-provider-1.0-SNAPSHOT";
var PREFIX_URL="http://localhost:8081/rest";
var CAR_DTO_URL="/car/dto";
var PRODUCER_DTO_URL="/producer/dto";
var CAR_URL="/car";
var WEBSOCKET_ENDPOINT_URL = PREFIX_URL + '/endpoint';
var NEXT_PAGE="/nextpage";


var app = angular.module('cars-module', ['ngStomp', 'ngRoute', 'sf.virtualScroll']);

//dependency injection of http service
app.controller('CarsTableController', ['$scope', '$http', '$stomp', '$route', function ($scope, $http, $stomp, $route) {
    var carDto = this;

    $stomp.connect(WEBSOCKET_ENDPOINT_URL).then(function () {
        var subscriptionToUpdate = $stomp.subscribe('/topic/car/update', function (car) {
            for (var i = 0; i < carDto.cars.length; i++)
                if (carDto.cars[i].carId == car.carId) {
                    carDto.cars[i] = car;
                    $route.reload();
                    break;
                }
        });


        var subscriptionToDelete = $stomp.subscribe('/topic/car/delete', function (id) {

            window.setTimeout(function () {
                for (var i = 0; i < carDto.cars.length; i++)
                    if (carDto.cars[i].carId == id) {
                        carDto.cars.splice(i, 1);
                        --carDto.total;
                        $route.reload();
                        return;
                    }
            }, 200);

        });

        var subscriptionToAdd = $stomp.subscribe('/topic/car/add', function (car) {

            window.setTimeout(function () {
                for (var i = 0; i < carDto.cars.length; i++)
                    if (carDto.cars[i].carId == car.carId) return;
                carDto.cars.push(car);
                $route.reload();
            }, 200);

        });
    });

    carDto.cars=[];

    carDto.total=0;

    carDto.producers=[];

    $http.get(PREFIX_URL+PRODUCER_DTO_URL)
        .success(function(data){
            carDto.producers=data.producers;
        }).error(function(){
        alert('Can\'t get data from server!');
    });

    carDto.addCar=function(){
        var data={
            carId: null,
            carName: carDto.carName,
            dateOfCreation: carDto.dateOfCreation,
            producer: {
                producerId: carDto.producerId,
                producerName: null,
                country: null
            }
        };

        $http.post(PREFIX_URL + CAR_URL, data).success(
            function (id) {
                data.carId=id;
                data.producer.producerName=carDto.producerName;
                carDto.cars.push(data);
                ++carDto.total;

                carDto.carId='';
                carDto.carName='';
                carDto.dateOfCreation='';
                carDto.producerId='';
                carDto.producerName='';
            }).error(function (response) {
                alert('Server error!');
            });
    };

    carDto.deleteCar=function(car){
        $http.delete(PREFIX_URL + CAR_URL + "/" + car.carId).success(
            function(response){
                carDto.cars.splice(carDto.cars.indexOf(car), 1);
                --carDto.total;
            }).error(function (response) {
                alert('Server error!');
            });
    };

    carDto.updateCar=function(car){
        $http.put(PREFIX_URL+CAR_URL,car).then(
            function(response){
                for(var i=0;i<carDto.cars.length;i++)
                    if (carDto.cars[i].carId == car.carId)
                        carDto.cars[i] = car;
            },
            function (response) {
                alert('Server error!')
            });
    };
    
    $scope.step = 20;

    $scope.rowHeightPx = 40;

    $scope.pages = [];

    $scope.currentPageNum = 0;
    
    $scope.getFirstChunk = function(){
        $http.get(PREFIX_URL + CAR_URL + NEXT_PAGE + "?from=" + 0 + "&to=" + ($scope.step-1))
            .success(function (data) {
                carDto.cars = data.pageList;
                carDto.total = data.total;
                var numberOfPages = Math.floor(data.total/$scope.step);

                for(var i=0; i<numberOfPages; i++)
                    $scope.pages.push(i);

            }).error(function () {
            alert('Can\'t get data from server!');
        });
    };
    
    
    $scope.getChunk=function(from, to) {
        $http.get(PREFIX_URL + CAR_URL + NEXT_PAGE + "?from=" + from + "&to=" + to)
            .success(function (data) {
                for (var i = 0; i < carDto.cars.length; i++) {
                    carDto.cars[i].carId = data.pageList[i].carId;
                    carDto.cars[i].carName = data.pageList[i].carName;
                    carDto.cars[i].dateOfCreation=data.pageList[i].dateOfCreation;
                    carDto.cars[i].producer=data.pageList[i].producer;
                }
            }).error(function () {
            alert('Can\'t get data from server!');
        });
    };


    $scope.getFirstChunk();

    carDto.detailedInfoForSelectedCar = null;

    $scope.selectedElementId = null;

    $scope.currentSelectedRow = null;

    carDto.getDetailedInfo = function(id){
        $http.get(PREFIX_URL + CAR_URL  + "/" + id)
            .success(function (data) {
                carDto.detailedInfoForSelectedCar=data;
            }).error(function () {
            alert('Can\'t get data from server!');
        });
    };

    $scope.setClickedRow = function(index){
        $scope.currentSelectedRow = index;
        $scope.selectedElementId = carDto.cars[index].carId;
        carDto.getDetailedInfo($scope.selectedElementId);
    };

    $scope.setSelectedRow = function(caze){
            if (caze == 1 && $scope.currentSelectedRow < carDto.total-1) {
                ++$scope.currentSelectedRow;
            } else if (caze == 2 && $scope.currentSelectedRow !=0) {
                --$scope.currentSelectedRow;
            }
            $route.reload();
    };

    $(document).keydown(function(event){
        if($(".selected").length!=0) {
            if (event.which == 40) {
                $scope.setSelectedRow(1);
            } else if (event.which == 38) {
                $scope.setSelectedRow(2);
            }
        }
    });

    $(document).keyup(function(event){
        if($(".selected").length!=0) {
            if (event.which == 40 || event.which == 38) {
                var id= carDto.cars[$scope.currentSelectedRow].carId;
                carDto.getDetailedInfo(id);
            }
        }
    });

}]);

var timer;

app.directive('uponscroll', function () {
    return {
        restrict: "A",
        link: function (scope, element) {
            element.scroll(function () {
                if(timer) {
                    window.clearTimeout(timer);
                }
                timer = window.setTimeout(
                    function(){
                        var scrollTop=element.scrollTop();
                        var newPageNum = Math.floor(scrollTop/(scope.step * scope.rowHeightPx));

                        if(scope.currentPageNum != newPageNum){
                            var newFrom = newPageNum*scope.step;
                            var newTo = newFrom + scope.step;
                            scope.currentPageNum = newPageNum;
                            scope.getChunk(newFrom, newTo);
                        }
                    }, 100);
            });
        }
    };
});