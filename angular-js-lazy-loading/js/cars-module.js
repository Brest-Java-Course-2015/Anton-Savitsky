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


var app = angular.module('cars-module', ['ngStomp', 'ngRoute']);

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

    carDto.step = 20;

    carDto.currentFromPageNumber = 0;

    carDto.currentToPageNumber = carDto.step -1;
    
    carDto.getNextChunk=function() {
        $http.get(PREFIX_URL + CAR_URL + NEXT_PAGE + "?from=" + carDto.currentFromPageNumber + "&to=" + carDto.currentToPageNumber)
            .success(function (data) {
		        if(data.pageList.length != 0){
		            carDto.currentFromPageNumber=carDto.currentToPageNumber+1;
		            carDto.currentToPageNumber += carDto.step-1;
		        }
                carDto.cars = carDto.cars.concat(data.pageList);
                carDto.total = data.total;
            }).error(function () {
            alert('Can\'t get data from server!');
        });

    }

    carDto.getNextChunk();

    $http.get(PREFIX_URL+PRODUCER_DTO_URL)
        .success(function(data){
            carDto.producers=data.producers;
        }).error(function(){
            alert('Can\'t get data from server!');
        });


    $scope.loadMoreRecords = function() {
        carDto.getNextChunk();
    };

    carDto.producers=[];

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

    $scope.setClickedRow = function(index){  //function that sets the value of selectedRow to current index
        $scope.currentSelectedRow = index;
        $scope.selectedElementId = carDto.cars[index].carId;
        carDto.getDetailedInfo($scope.selectedElementId);
    };

    $scope.setSelectedRow = function(caze){
            console.log("current row number: "+$scope.currentSelectedRow);
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
            console.log($(".selected").length);
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

app.directive('whenscrollends', function () {
    return {
        restrict: "A",
        link: function (scope, element, attrs) {

            var visibleHeight = element.height();
            var threshold = 50;

            element.scroll(function () {
                if(timer) {
                    window.clearTimeout(timer);
                }
                timer = window.setTimeout(
                    function() {
                        var scrollableHeight = element.prop('scrollHeight');
                        var hiddenContentHeight = scrollableHeight - visibleHeight - element.scrollTop();

                        if (hiddenContentHeight <= threshold) {
                            scope.$apply(attrs.whenscrollends);
                        }
                    }, 100);
            });
        }
    };
});