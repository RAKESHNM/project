
$(document).ready(function(){
var data = JSON.parse(localStorage.getItem('data'));
console.log("ready", data)
    var header = "header";
     getCommandService(data);
     $("#selectMethod").on('click', function(d) {

                     console.log(d.target);
                     var txt = $(d.target).text();
                     console.log(txt);
                     if(txt.indexOf('.') != -1){
                        console.log("File");
                        getFileContent(d.target);
    //                      getCommit(d.target);

                     }
                     else
                        getContent(d.target);
                     })
           $(".closeIcon").click(function(){
                location.href = "../htmlfiles/CommandTest.html";
                $(".popupHeaderTextpage").empty();
                $(".linemethod").empty();
                $(".linemethod2").empty()
                $(".method").empty();

            })
            $(".closeIcon1").click(function(){
                location.href = "../htmlfiles/CommandTest.html";
                getCommandService(data);
                    })

           $(".message").append("Message To Be Displayed");
    });

function multiplyNode(node, count, deep, obj) {
        for (var i = 0, copy; i < obj.length; i++) {
            copy = node.cloneNode(deep);
            copy.append(obj[i]);
            console.log(copy);
            node.parentNode.insertBefore(copy, node)
            console.log(node);
        }
    }

 function getContent(d){
// console.log(d);
var txt = $(d).text();
console.log(txt);
var auth = {};
console.log("Test");
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
                console.log("First");
              console.log(res);
              localStorage.setItem("content",res);
              $.ajax({
                                        url:"/rest/methodcommit",
                                        type: 'POST',
                                         crossDomain : true,
                                          headers: {
                                             "content-type": "application/json"
                                             },
                                         data :JSON.stringify(auth.methodName),
                                        dataType: 'json',
                                        xhrFields: {
                                            withCredentials: true
                                        },
                                        success: function(res){
                                          console.log(res);
                                          localStorage.setItem("commit",res);
                                          location.href = "../htmlfiles/Contents.html";
                                          insertContents(res);

                                          document.getElementById("commitText").value += res;

                                     },
                                      error: function(errorres){
                                       console.log(errorres);
                                      }
                                    });
              location.href = "../htmlfiles/Contents.html";
         },
          error: function(errorres){
           console.log(errorres);
          }
        });

  }
function getCommandService(data){
console.log("here", data)
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
                console.log(res);
                result = res
                if(data.command===("List all methods having lines greater than n")){
                                    $(".popupHeaderTextpage").append("List all methods having lines greater than n")
                                    console.log(result);
                                    for(let i =0;i<result.object.length;i=i+3){
                                                        console.log(result.object[i]);
                                                         $(".method").append("<li>"+result.object[i]+"</li>");
                                                         $(".linemethod").append("(" + result.object[i+1]+")<br>");
                                                         localStorage.setItem(result.object[i],result.object[i+2]);

                                               }
                                                $( "li" ).hover(
                                                         function() {
                                                         $( this ).val( "Rakesh" );
                                                       }, function() {
                                             $( this ).find( "span:last" ).remove();
                                    } );
}
                else if(data.command===("List all methods without javadocs")){
                        $(".popupHeaderTextpage").append("List all methods without javadocs")
                    for(let i =0;i<result.object.length;i=i+2){
                          console.log(result.object);
                          $(".method").append("<li>"+result.object[i]+"</li>");
                          localStorage.setItem(result.object[i],result.object[i+1]);
                    }
                }
                else if(data.command===("List all files")){
                        $(".popupHeaderTextpage").append("List all files")
                console.log(result.object);
//                for(var i = 0;i<result.object.length;i++){
//                for(var j=0;i<result.object[i].length;j++){
//                console.log("result.object.length",result.object.length)
//                console.log(result.object[i]);
//                console.log("rsult.object[i].length",result.object[i].length);
//                $(".method").append("<li>"+ result.object[i].[j]  + "</li>");
//}
//                $(".linemethod1").append("<br>");
                    for(var i = 0; i < result.object.length; i++){
                        for(var j = 0; j < result.object[i].length; j=j+3){
                            $(".method").append("<li class =listOfMethods>"+result.object[i][j]+"</li>");
                            $(".linemethod").append("(" + result.object[i][j+1]+")<br>");
                            localStorage.setItem(result.object[i][j],result.object[i][j+2]);
//                          console.log(result.object[i][j]);
                        }
                     }


//                multiplyNode(document.querySelector(".method"), (result.object.length), true, result.object);
                }
                else if(data.command===("Project Summary")){
                                        $(".popupHeaderTextpage").append("Project Summary")
                console.log("Else condition");
                console.log(res.object.length);
                var wrapper = $('#wrapper'), container;
                 printValues(result);
                 function printValues(obj) {
                 for (var key in obj) {
                      if (typeof obj[key] === "object") {
//                                console.log(key ," : ");
//                                $(".method").append("<li>"+ key +" ; " + "</li>");
                                printValues(obj[key]);
                      }
                      else {
                                $(".linemethod2").append("<li>"+key + " : " +  obj[key] + "</li>");
                                console.log(obj[key]);
                             }
                      }
                      }

//                for (var key in res){//                container = $('<div id="summary" class="container"></div>');
//                wrapper.append(container);
//                container.append('<div class="item">' + key +'</div>');
//                console.log(key);
//                multiplyNode(document.querySelector(".linemethod"), (key.length), true, key);
//                $(".method").append("<li>"+ key + "</li>");
//                $(".method").empty();
//                }

                }
                else{
                  $(".popupHeaderTextpage").append("Commit Details")

                for(let i =0;i<result.object.length;i=i+3){
                    $(".linemethod2").append(result.object[i]+"<br>");
                    $(".linemethod2").append("<font size=3><b>"+result.object[i+1]+"</b>"+" committed on "+result.object[i+2]+"</font><br>");
                    $(".linemethod2").append("<br>");
                }
//                multiplyNode(document.querySelector(".linemethod"), (result.object.length), true, result.object);
                }
//                for(var i=0; i<result.object.length;i++){
//                $(".method").append(result.object);
//                }
            },
            error: function(errorres){
                console.log(errorres);
            }
        });
        /*return errorres;*/
}

function getFileContent(d){
var txt = $(d).text();
console.log(txt);
var auth = {};
console.log("Test");
   auth.methodName = txt;
   console.log(auth.methodName);
   console.log(auth.methodcontents);
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
              console.log(res);
              localStorage.setItem("content",res);
              $.ajax({
                          url:"/rest/commit",
                          type: 'POST',
                           crossDomain : true,
                            headers: {
                               "content-type": "application/json"
                               },
                           data :JSON.stringify(auth.methodName),
                          dataType: 'json',
                          xhrFields: {
                              withCredentials: true
                          },
                          success: function(res1){
                            console.log(res1);
                                localStorage.setItem("commit",res1);
                            location.href = "../htmlfiles/Contents.html";
                            insertContents(res);

                            document.getElementById("commitText").value += res;

                       },
                        error: function(errorres){
                         console.log(errorres);
                        }
                      });
              location.href = "../htmlfiles/Contents.html";


         },
          error: function(errorres){
           console.log(errorres);
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

/*function showFilePathPath(text){
$(function(){

    $('#selectMethod').mouseenter(function(d){
    var hover = $(d.target).text();
        alert(hover);
    });

});
    document.getElementById("text").innerHTML=text;
}
function hide(){
    document.getElementById("text").innerHTML="";
}*/

$(function(){

    $('#selectMethod').mouseenter(function(d){
    var hover = $(d.target).text();
    document.getElementById("text").innerHTML=localStorage.getItem(hover);
        //alert(hover);
    });



});
function hide(){
        document.getElementById("text").innerHTML="";
    }
