
function myFunctions(){
            $.ajax({
            url:"http://localhost:8080/repositories",
            type: 'GET',
             crossDomain : true,
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            success: function(res){

                console.log(res);
                        $('#selectRepo').empty();
                        var output = [];

                        res.forEach(function(key){
                            output.push('<option >'+ key +'</option>');
                        })


                        $('#selectRepo').html(output.join(''));
                        console.log(document.getElementById("selectRepo").value);


        },

          error: function(errorres){
           console.log(errorres);
          }
        });

        }

function getBranches(repo){
            var auth = {};
                auth.remoteRepo=repo;
            $.ajax({
            url:"http://localhost:8080/branch",
            type: 'POST',
             crossDomain : true,
              headers: {
                 "content-type": "application/json"
                 },
             data :JSON.stringify(auth),
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            success: function(res){

                console.log(res);
                        $('#selectBranch').empty();
                        var output = [];

                        res.forEach(function(key){
                            output.push('<option >'+ key +'</option>');
                        })


                        $('#selectBranch').html(output.join(''));
                       // console.log(document.getElementById("selectBranch").value);


        },

          error: function(errorres){
           console.log(errorres);
          }
        });

        }
$(document).ready(function(){
     myFunctions();
     console.log("Call func");
});
function getSelectedValue(){
var repo = (document.getElementById("selectRepo").value);
console.log(repo);
getBranches(repo);
console.log(document.getElementById("selectBranch").value);
}

function gitCheckout(){
alert('Please wait . . . checking out');
var d = {};
d.branch = (document.getElementById("selectBranch").value);
d.remoteRepo = (document.getElementById("selectRepo").value);
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

}

var myApp;
myApp = myApp || (function () {
    var pleaseWaitDiv = $('<div class="modal hide" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false"><div class="modal-header"><h1>Processing...</h1></div><div class="modal-body"><div class="progress progress-striped active"><div class="bar" style="width: 100%;"></div></div></div></div>');
    return {
        showPleaseWait: function() {
            pleaseWaitDiv.modal();
        },
        hidePleaseWait: function () {
            pleaseWaitDiv.modal('hide');
        },

    };
})();