/**
 * Created by antonsavitsky on 4/5/16.
 */
var PREFIX_URL="http://localhost:8080/rest-service-provider-1.0-SNAPSHOT";
//var PREFIX_URL="http://localhost:8081/rest";
var CAR_DTO_URL="/car/dto";
var PRODUCER_DTO_URL="/producer/dto";
var CAR_URL="/car";

var app=angular.module('cars-module', []);

//dependency injection of http service
app.controller('CarsTableController', ['$http', function($http){
    var carDto = this;

    carDto.cars=[];

    carDto.total=0;

    $http.get(PREFIX_URL+CAR_DTO_URL)
        .success(function(data){
            carDto.cars=data.cars;
            carDto.total=data.total;
        }).error(function () {
            alert('Can\'t get data from server!');
        });

    $http.get(PREFIX_URL+PRODUCER_DTO_URL)
        .success(function(data){
            carDto.producers=data.producers;
        }).error(function(){
            alert('Can\'t get data from server!');
        });

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

        $http.post(PREFIX_URL+CAR_URL, data).then(
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
            },
            function (response) {
                alert('Server error!');
            });
    };

    carDto.deleteCar=function(car){
        $http.delete(PREFIX_URL+CAR_URL+"/"+car.carId).then(
            function(response){
                carDto.cars.splice(carDto.cars.indexOf(car), 1);
                --carDto.total;
            },
            function(response){
                alert('Server error!');
            }
        );
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

}]);
