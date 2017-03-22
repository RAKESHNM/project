$(document).ready(function(){
if(localStorage.getItem('autnentication')==="false"){
           location.href ="../htmlfiles/index.html";
     }

    $(".logout").on('click',function(d) {
        $.ajax({
                url:"/rest/logout",
                type: 'POST',
                crossDomain : true,
                headers: {
                           "content-type": "application/json"
                         },
                xhrFields: {
                             withCredentials: true
                           },
                success: function(res){
                              location.href ="../htmlfiles/index.html";
                                   localStorage.setItem('autnentication',"false");

                            }
        })
    localStorage.clear();
    })
    $(".checkout").on('click',function(d) {
        location.href ="../htmlfiles/CheckoutService.html";
    })
});