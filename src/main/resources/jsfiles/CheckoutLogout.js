$(document).ready(function(){
    $(".logout").on('click',function(d) {
        $.ajax({
                url:"/rest/logout",
                type: 'POST',
                crossDomain : true,
                headers: {
                           "content-type": "application/json"
                         },
                data :JSON.stringify(d),
                xhrFields: {
                             withCredentials: true
                           },
                success: function(res){
                              location.href ="../htmlfiles/index.html";
                            }
        })
    localStorage.clear();
    })
    $(".checkout").on('click',function(d) {
        location.href ="../htmlfiles/CheckoutService.html";
    })
});