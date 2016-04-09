/**
 * Created by antonsavitsky on 4/8/16.
 */
var PREFIX_URL="http://localhost:8080/rest-service-provider-1.0-SNAPSHOT";
//var PREFIX_URL="http://localhost:8081/rest";
var PRODUCER_DTO_URL="/producer/dto";
var PRODUCER_URL="/producer";
var WEBSOCKET_ENDPOINT_URL = 'http://localhost:8080/rest-service-provider-1.0-SNAPSHOT/endpoint';


var app = angular.module('producers-module', ['ngStomp', 'ngRoute']);

//dependency injection of http service
app.controller('ProducersTableController', ['$http', '$stomp', '$route', function ($http, $stomp, $route) {
    var producerDto = this;

    $stomp.connect(WEBSOCKET_ENDPOINT_URL).then(function () {

        var subscriptionToUpdate = $stomp.subscribe('/topic/producer/update', function (producer) {
            for (var i = 0; i < producerDto.producers.length; i++)
                if (producerDto.producers[i].producerId == producer.producerId) {
                    producerDto.producers[i] = producer;
                    $route.reload();
                    break;
                }
        });


        var subscriptionToDelete = $stomp.subscribe('/topic/producer/delete', function (id) {

            window.setTimeout(function () {
                for (var i = 0; i < producerDto.producers.length; i++)
                    if (producerDto.producers[i].producerId == id) {
                        producerDto.producers.splice(i, 1);
                        --producerDto.total;
                        $route.reload();
                        return;
                    }
            }, 200);

        });

        var subscriptionToAdd = $stomp.subscribe('/topic/producer/add', function (producer) {

            window.setTimeout(function () {
                for (var i = 0; i < producerDto.producers.length; i++)
                    if (producerDto.producers[i].producerId == producer.producerId) return;
                producerDto.producers.push(producer);
                $route.reload();
            }, 200);

        });
    });

    producerDto.producers=[];

    producerDto.total=0;


    $http.get(PREFIX_URL+PRODUCER_DTO_URL)
        .success(
            function(data){
                producerDto.producers=data.producers;
                producerDto.total=data.total;
            }).error(
            function(){
                alert('Can\'t get data from server!');
            });

    producerDto.addProducer=function(){
        var data={
            producerId: null,
            producerName: producerDto.producerName,
            country: producerDto.country,
            countOfCars: null
        };

        $http.post(PREFIX_URL + PRODUCER_URL, data).success(
            function (id) {
                data.producerId=id;
                data.countOfCars=0;
                producerDto.producers.push(data);
                ++producerDto.total;

                producerDto.producerName='';
                producerDto.country='';
            }).error(
            function (response) {
                alert('Server error!');
            });
    };

    producerDto.deleteProducer=function(producer){
        if(producer.countOfCars>0) {
            if(confirm('There are '+producer.countOfCars+' recorded for this producer!'))
                producerDto.proceedDelete(producer);
        } else producerDto.proceedDelete(producer);
    };

        producerDto.proceedDelete=function(producer){
            $http.delete(PREFIX_URL + PRODUCER_URL + "/" + producer.producerId).success(
                function (response) {
                    producerDto.producers.splice(producerDto.producers.indexOf(producer), 1);
                    --producerDto.total;
                }).error(
                function (response) {
                    alert('Server error!');
                });
        };

    producerDto.updateProducer=function(producer){
        $http.put(PREFIX_URL + PRODUCER_URL, producer).success(
            function(){
                for(var i=0;i<producerDto.producers.length;i++)
                    if (producerDto.producers[i].producerId == producer.producerId)
                        producerDto.producers[i] = producer;
            }).error(
            function (response) {
                alert('Server error!');
            });
    };

}]);
