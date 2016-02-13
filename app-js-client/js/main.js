// The root URL for the RESTful services
$("head").append($('<script type="text/javascript" src="js/properties.js"></script>'));
var CAR_URL = "/car";
var CARDTO_URL = "/carsdto";
var CARPAGING_DTO="/initpaging";
var CARDTOBYDATE_URL="/carsdtobydate";
var PRODUCERDTO_URL="/producersdto";
var producerdto=null;
var NEXT_PAGE="/nextpage";
var previousPage=null;
var currentPage=null;

getProducerDto();

getFirstPage(0,2);

function getFirstPage(from, to) {
    console.log('getFirstPage('+from+', '+to+')');
    var url = PREFIX_URL + CAR_URL +CARPAGING_DTO+"?from="+from+"&to="+to;
    $.ajax({
        type: 'GET',
        url: url,
        dataType: "json",
        success: function(data){
            renderList(data);
            console.log("total: "+data.total);
            redrawPaginationFooter(data.total, to);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('Произошла ошибка получения данных из БД!');
        }
    });
}

function getNextPage(from, to) {
    console.log('getNextPage('+from+', '+to+')');
    var url = PREFIX_URL + CAR_URL +NEXT_PAGE+"?from="+from+"&to="+to;
    $.ajax({
        type: 'GET',
        url: url,
        dataType: "json",
        success: function(data){
            currentPage=data;
            renderNextList(data);
            console.log("total: "+data.total);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('Произошла ошибка получения данных из БД!');
        }
    });
}

function renderList(data) {
    cleanOldData();
    var dto = data.pageList == null ? [] : (data.pageList instanceof Array ? data.pageList : [data.pageList]);
    var total = data.total;
    $.each(dto, function (index, car) {
        drawRow(car);
    });
    $('#carTotal').append($("<p>Всего автомобилей: " + total + "</p>"));
}

function renderNextList(data) {
    $("#carList tr").remove();
    var dto = data.pageList == null ? [] : (data.pageList instanceof Array ? data.pageList : [data.pageList]);
    var total = data.total;
    $.each(dto, function (index, car) {
        drawRow(car);
    });
    //$('#carTotal').append($("<p>Всего автомобилей: " + total + "</p>"));
}

function redrawPaginationFooter(total, max) {
    //remove old page numbers
    /*var paginationFooter=document.getElementById("paginationFooter");
    while(paginationFooter.firstChild){
        paginationFooter.removeChild(paginationFooter.firstChild);
    }*/

    // calculate number of pages
    var numOfPages = total % max == 0 ? total / max : Math.floor(total / max) + 1;
    console.log("num of pages: " + numOfPages);

    //draw pagination footer
    for (var i = 1; i <= numOfPages; i++) {
        $("#pages").append($("<li id='"+'pagenum'+i+"'><a href='javascript:previousPage=currentPage; getNextPage(" + (max*(i - 1)+1) + ',' + (max*i) + ");'>" + i + "</a></li>"));
        document.getElementById("pagenum"+i).onclick=function(){
            var arrOfElements=document.getElementsByClassName("active");

            $.each(arrOfElements, function(index, elem){
                elem.removeAttribute("class");
            });

            this.className="active";
        };
    }
    document.getElementById("pagenum"+1).className="active";
}


function getProducerDto(){
    console.log('getProducerDto');
    var url = PREFIX_URL + PRODUCERDTO_URL;
    $.ajax({
        type: 'GET',
        url: url,
        dataType: "json",
        success: function(data){
            producerdto= data.producers == null ? [] : (data.producers instanceof Array ? data.producers : [data.producers]);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
        }
    });
}


function gotoAddCar() {
    window.location = "addCar.html";
}


function gotoUpdateCar(carId,carName,producerId,dateOfCreation, producerName)
{
    sessionStorage.setItem('carId', carId);
    sessionStorage.setItem('carName', carName);
    sessionStorage.setItem('producerId', producerId);
    sessionStorage.setItem('dateOfCreation', dateOfCreation);
    sessionStorage.setItem('producerName', producerName);
    window.location="updateCar.html";
}

function getProducerNameById(producerid){
    var retval="---";
    $.each(producerdto, function (index, producer) {
       if(producer.producerId==producerid) {
           retval=producer.producerName;
       }
    });
    return retval;
}


function drawRow(car) {
    var row = $("<tr id='row"+car.carId+"'/>");
    $("#carList").append(row);
    row.append($("<td>" + car.carName+"</td>"));
    var prodName=getProducerNameById(car.producerId);
    row.append($("<td>" + prodName.toString()+"</td>"));
    row.append($("<td>" + car.dateOfCreation +"</td>"));
    row.append($("<td>" + '<button id="delete'+car.carId+'" class="mybutton"><span class="glyphicon glyphicon-trash"></span></button>' + "</td>"));
    $("#delete"+car.carId).click(function(){
        deleteCar(car.carId, car.carName);
    });
    row.append($("<td>"+'<button id="update'+car.carId+'"updateButton" class="mybutton" type="button"><span class="glyphicon glyphicon-pencil"></span></button>' + "</td>"));
    $("#update"+car.carId).click(function(){
        gotoUpdateCar(car.carId, car.carName, car.producerId, car.dateOfCreation, prodName.toString());
    });
}


function cleanOldData(){
    $("#carList tr").remove();
    $('#carTotal p').remove();
}


function lookupCarsByDate(){
        var dateBefore=$("#dateBefore").val();
        var dateAfter=$("#dateAfter").val();
        var url=PREFIX_URL+CARDTOBYDATE_URL+"?dateBefore="+dateBefore.toString()+"&dateAfter="+dateAfter.toString();
        $.ajax({
            type: 'GET',
            url: url,
            dataType: "json",
            success: renderList,
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR, textStatus, errorThrown);
                alert('Ошибка поиска по дате!\nПроверьте введенные даты.');
            }
        });
}

/*
function lookupCarsByDate(){
    var dateBefore=new Date($("#dateBefore").val());
    var dateAfter=new Date($("#dateAfter").val());
    console.log("dateBefore="+dateBefore.toString()+", dateAfter="+dateAfter.toString());
    var page = currentPage.pageList == null ? [] : (currentPage.pageList instanceof Array ? currentPage.pageList : [currentPage.pageList]);
    //$.each(page, function(index, row){
        //var d=new Date(row.dateOfCreation);
        //if(d>dateAfter||d<dateBefore)
    page.splice(1, 1);
    $.each(page, function(index, car) {
        console.log(car.carName);
    });
    //});
    currentPage.pageList=page;
    renderNextList(currentPage);
}
*/

function undoLookUp(){
    //findAll();
    $("#dateBefore").val("");
    $("#dateAfter").val("");
}


function deleteCar(carId, carName) {
    if (confirm("Вы уверены, что хотите удалить автомобиль "+carName+"?"))
    {
        console.log('deleteCar' + carId);
        var url = PREFIX_URL + CAR_URL + "/" + carId;
        $.ajax({
            type: 'DELETE',
            url: url,
            success: function (data, textStatus, jqXHR) {
                var elem=document.getElementById("carList");
                elem.removeChild(document.getElementById("row"+carId));
                if($.trim($("#carList").text()).length==0) renderNextList(previousPage);
                alert('Автомобиль успешно удален!');
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('Ошибка удаления автомобиля!');
            }
        });
    }
}