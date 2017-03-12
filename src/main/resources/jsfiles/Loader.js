/*
$(document).ready(function(){
     var d = {};
     d.branch = localStorage.getItem("branch");
     d.remoteRepo = localStorage.getItem("repository");
       $.ajax({
                 url:"http://localhost:8080/checkout",
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
                 console.log("hi");
                location.href = "../htmlfiles/CommandService.html";
                   },

               error: function(errorres){
                console.log(errorres);
               }
             });
});*/
