/**
 * Created by antonsavitsky on 4/8/16.
 */
var PREFIX_URL="http://localhost:8080/rest-service-provider-1.0-SNAPSHOT";
//var PREFIX_URL="http://localhost:8081/rest";
var PRODUCER_DTO_URL="/producer/dto";
var PRODUCER_URL="/producer";

var app=angular.module('producers-module', []);

//dependency injection of http service
app.controller('ProducersTableController', ['$http', function($http){
    var producerDto = this;

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

        $http.post(PREFIX_URL+PRODUCER_URL, data).then(
            function (id) {
                data.producerId=id;
                data.countOfCars=0;
                producerDto.producers.push(data);
                ++producerDto.total;

                producerDto.producerName='';
                producerDto.country='';
            },
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
            $http.delete(PREFIX_URL+PRODUCER_URL+"/" + producer.producerId).then(
                function (response) {
                    producerDto.producers.splice(producerDto.producers.indexOf(producer), 1);
                    --producerDto.total;
                },
                function (response) {
                    alert('Server error!');
                }
            );
        };

    producerDto.updateProducer=function(producer){
        $http.put(PREFIX_URL+PRODUCER_URL,producer).then(
            function(){
                for(var i=0;i<producerDto.producers.length;i++)
                    if (producerDto.producers[i].producerId == producer.producerId)
                        producerDto.producers[i] = producer;
            },
            function (response) {
                alert('Server error!');
            });
    };

}]);
