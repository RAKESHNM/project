$(document).ready(function(){
     var d = {};
     d.branch = returnRemoteBranch;
     d.remoteRepo = returnRemoteRepo;
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
});

function returnRemoteRepo(){
  return (document.getElementById("selectRepo").value);
}

function returnRemoteBranch(){
  return (document.getElementById("selectBranch").value);
}