function myFunctions(){

    $.ajax({
            url:"/rest/repositories",
            type: 'GET',
            crossDomain : true,
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            success: function(res){
                $('#selectRepo').empty();
                var output = [];
                var selectRepo = 'Select Repository';
                output.push('<option >'+ selectRepo +'</option>');
                res.forEach(function(key){
                   output.push('<option >'+ key +'</option>');
                })
                $('#selectRepo').html(output.join(''));
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
            url:"/rest/branch",
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
                $('#selectBranch').empty();
                var output = [];
                var selectBranch = 'Select Branch';
                output.push('<option >'+ selectBranch +'</option>');
                res.forEach(function(key){
                   output.push('<option >'+ key +'</option>');
                })
                $('#selectBranch').html(output.join(''));
            },
            error: function(errorres){
                console.log(errorres);
            }
    });
}

$(document).ready(function(){

     if(localStorage.getItem('autnentication')==="false"){
           location.href ="../htmlfiles/index.html";
     }
     else{
         myFunctions();
         $("#selectBranch").empty();
     }
});

function getSelectedValue(){

    var repo = (document.getElementById("selectRepo").value);
    $("#selectBranch").empty();
    $("#selectBranch").append($('<option>', {    value: 1,    text: 'Loading . . .'}));
    getBranches(repo);
}

function gitCheckout(){
    document.getElementById('logout').disabled=true;
    document.getElementById('checkoutbutton').disabled=true;
    var d = {};
    d.branch = (document.getElementById("selectBranch").value);
    d.remoteRepo = (document.getElementById("selectRepo").value);
    d.dir = (document.getElementById("dir").value);

    $.ajax({
            url:"/rest/checkout",
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
                if(res == "false"){
                   alert("Specified repositories does not exists");
                    location.href = "../htmlfiles/CheckoutService.html";
                }
                else if(res!=="true"){
                    var temp = confirm("Repository already exist under \n"+ res + "\n do you want to clone again ?")
                    if(temp == true){

                        $.ajax({
                                url:"/rest/clone",
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
                                    location.href = "../htmlfiles/CommandTest.html";
                                }
                        })
                    }
                    else{
                        location.href = "../htmlfiles/CommandTest.html";
                    }
                }
                else{
                    location.href = "../htmlfiles/CommandTest.html";
                }
            },
            error: function(errorres){
                console.log(errorres);
                document.getElementById('checkoutbutton').disabled=false;
                document.getElementById('logout').disabled=false ;
            }
    });

    $(".loader").addClass("showClass");
    $(".contentWrapper").addClass("shadowClass");
}
