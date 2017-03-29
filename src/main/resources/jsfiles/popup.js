$(document).ready(function(){
    if(localStorage.getItem('autnentication')==="false"){
           location.href ="../htmlfiles/index.html";
    }
    else{
           var data = JSON.parse(localStorage.getItem('data'));
           var header = "header";
           getCommandService(data);

           $("#selectMethod").on('click', function(d) {
                    $(".loader").addClass("showClass");
                    var txt = $(d.target).text();

                    if(txt.indexOf('.') != -1){
                        getFileContent(d.target);
                    }
                    else{
                        getContent(d.target);
                    }
           })

           $(".closeIcon").click(function(){
                    location.href = "../htmlfiles/CommandTest.html";
                    $(".popupHeaderTextpage").empty();
                    $(".linemethod").empty();
                    $(".linemethod2").empty();
                    $(".method").empty();
           })

           $(".closeIcon1").click(function(){
                    location.href = "../htmlfiles/CommandTest.html";
                    getCommandService(data);
           })
    }
});

function getContent(d){

    var txt = $(d).text();
    var auth = {};
    auth.methodName = txt;
    auth.filePath = localStorage.getItem(txt);

    $.ajax({
            url:"/rest/methodcontents",
            type: 'POST',
            crossDomain : true,
            headers: {
                 "content-type": "application/json"
            },
            data :JSON.stringify(auth),
            dataType: 'text',
            xhrFields: {
                  withCredentials: true
            },
            success: function(res){
                  localStorage.setItem("content",res);

                  $.ajax({
                          url:"/rest/commit",
                          type: 'POST',
                          crossDomain : true,
                          headers: {
                               "content-type": "application/json"
                          },
                          data :JSON.stringify(auth.filePath),
                          dataType: 'json',
                          xhrFields: {
                               withCredentials: true
                          },
                          success: function(res1){
                               console.log("Test2 : ",res1);
                                   $(".loader").removeClass("showClass");
                               localStorage.setItem("commit",res1);
                               location.href = "../htmlfiles/Contents.html";
                               insertContents(res);
                          },
                          error: function(errorres){
                                console.log(errorres);
                                    $(".loader").removeClass("showClass");
                          }
                  });
            },
            error: function(errorres){
                  console.log(errorres);
                      $(".loader").removeClass("showClass");
            }
    });

}


function getCommandService(data){

    $(".loader").addClass("showClass");
    var result;
    var errorres = [];

    $.ajax({
            url:"/rest/inputs",
            type: 'POST',
            crossDomain : true,
            headers: {
                 "content-type": "application/json"
            },
            data :JSON.stringify(data),
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            success: function(res){
                result = res
                if(data.command===("List all methods having lines greater than n")){
                                    $(".popupHeaderTextpage").append("List all methods having lines greater than n")
                                    console.log(result);
                                    for(let i =0;i<result.object.length;i=i+3){
                                                        console.log(result.object[i]);
                                                        $(".method").append("<ul>" + "" +"</ul>");
                                                        $(".method").append("<li>" + result.object[i]+"</li>");
                                                        $(".linemethod").append("(" + result.object[i+1]+")<br>");
                                                        localStorage.setItem(result.object[i],result.object[i+2]);

                                    }

                }
                else if(data.command===("List all methods without javadocs")){
                                    $(".popupHeaderTextpage").append("List all methods without javadocs")
                                    for(let i =0;i<result.object.length;i=i+2){
                                                        $(".method").append("<ul>" + "" +"</ul>");
                                                        $(".method").append("<li>" + result.object[i]+"</li>");
                                                        localStorage.setItem(result.object[i],result.object[i+1]);
                                    }
                }
                else if(data.command===("List all files")){
                                    $(".popupHeaderTextpage").append("List all files")
                                    for(var i = 0; i < result.object.length; i++){
                                               for(var j = 0; j < result.object[i].length; j=j+3){
                                                        $(".method").append("<ul>" + "" +"</ul>");
                                                        $(".method").append("<li class =listOfMethods>"+result.object[i][j]+"</li>");
                                                        $(".linemethod").append("(" + result.object[i][j+1]+")<br>");
                                                        localStorage.setItem(result.object[i][j],result.object[i][j+2]);
                                               }
                                    }
                }
                else if(data.command===("Project Summary")){
                                    $(".popupHeaderTextpage").append("Project Summary");
                                    var wrapper = $('#wrapper'), container;
                                    printValues(result);
                                    function printValues(obj) {
                                               for (var key in obj) {
                                                        if (typeof obj[key] === "object") {
                                                            printValues(obj[key]);
                                                        }
                                                        else {
                                                            $(".linemethod2").append("<li>"+key + " : " +  obj[key] + "</li>");
                                                        }
                                               }
                                    }
                }
                else{
                                    $(".popupHeaderTextpage").append("Commit Details");
                                    for(let i =0;i<result.object.length;i=i+3){
                                               $(".linemethod2").append(result.object[i]+"<br>");
                                               $(".linemethod2").append("<font size=3><b>"+result.object[i+1]+"</b>"+" committed on "+result.object[i+2]+"</font><br>");
                                               $(".linemethod2").append("<br>");
                                    }
                }
                $(".loader").removeClass("showClass");
            },
            error: function(errorres){
                console.log(errorres);
            }
    });
}

function getFileContent(d){
    var txt = $(d).text();
    var auth = {};
    auth.methodName = txt;
    auth.filePath = localStorage.getItem(txt);

    $.ajax({
            url:"/rest/filecontents",
            type: 'POST',
            crossDomain : true,
            headers: {
               "content-type": "application/json"
            },
            data :JSON.stringify(auth.methodName),
            dataType: 'text',
            xhrFields: {
                withCredentials: true
            },
            success: function(res){
                localStorage.setItem("content",res);
                $.ajax({
                        url:"/rest/commit",
                        type: 'POST',
                        crossDomain : true,
                        headers: {
                            "content-type": "application/json"
                        },
                        data :JSON.stringify(auth.filePath),
                        dataType: 'json',
                        xhrFields: {
                            withCredentials: true
                        },
                        success: function(res1){
                            localStorage.setItem("commit",res1);
                            location.href = "../htmlfiles/Contents.html";
                            insertContents(res);
                                $(".loader").removeClass("showClass");
                        },
                        error: function(errorres){
                            console.log(errorres);
                                $(".loader").removeClass("showClass");
                        }
                });
            },
            error: function(errorres){
                console.log(errorres);
                    $(".loader").removeClass("showClass");
            }
    });
}

function getCommit(d){
var txt = $(d).text();
console.log(txt);
var auth = {};
console.log("Test");
   auth.methodName = txt;
   console.log(auth.methodName);
            $.ajax({
            url:"/rest/commit",
            type: 'POST',
             crossDomain : true,
              headers: {
                 "content-type": "application/json"
                 },
             data :JSON.stringify(auth.methodName),
            dataType: 'text',
            xhrFields: {
                withCredentials: true
            },
            success: function(res){
              console.log(res);
              localStorage.setItem("res1",res);
              location.href = "../htmlfiles/Contents.html";
              insertContents(res);

              document.getElementById("commitText").value += res;
              $(".closeIcon1").click(function(){
                          getCommandService();

                          $(".popup").addClass("showClass");
                              $(".popup").show();
                              })

         },
          error: function(errorres){
           console.log(errorres);
          }
        });
}

$(function(){

    $('#selectMethod').mouseenter(function(d){
        var hover = $(d.target).text();
        var path = localStorage.getItem(hover);
        if(path.indexOf('+') != -1){
            path = path.substring(0,path.indexOf('+'));
            document.getElementById("text").innerHTML=path;
        }
        else
            document.getElementById("text").innerHTML=localStorage.getItem(hover);
    });
});

function hide(){
    document.getElementById("text").innerHTML="";
}
