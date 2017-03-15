var data = {};
$(document).ready(function(){
    var header = "header";

   var options = ["Commit Details","Project Summary","List all files", "List all methods having lines greater than n", "List all methods without javadocs"];
   $('#selectCommand').empty();
     var output = [];
     options.forEach(function(key){
      output.push('<option class="option_click">'+ key +'</option>');
     })
     console.log(output)  ;
     $('#selectCommand').html(output.join(''));
     $(".module-wrapper").hide();
     $(".lines-wrapper").hide();
     $(".size-wrapper").hide();
     $("#selectCommand").on("click", function(value){
      if((document.getElementById("selectCommand").value == options[0])||(document.getElementById("selectCommand").value == options[1])){
                 $(".module-wrapper").hide();
                 $(".lines-wrapper").hide();
             }
       else if(document.getElementById("selectCommand").value == options[3]){
                 $(".module-wrapper").show();
                 $(".lines-wrapper").show();
                 $(".size-wrapper").hide();
             }
        else if(document.getElementById("selectCommand").value == options[2]){
                $(".module-wrapper").show();
                $(".size-wrapper").show();
                $(".lines-wrapper").hide();
            }
        else {
               $(".module-wrapper").show();
               $(".lines-wrapper").hide();
               $(".size-wrapper").hide();
           }
      });
       $(".button").click(function(){
           getCommandService();

            $(".popup").addClass("showClass");
                $(".popup").show();
       })
//       $(".method").onclick(function(){
//            getContent();
//       })
       $("#selectMethod").on('click', function(d) {
                 console.log(d.target);
                 var txt = $(d.target).text();
                 console.log(txt);
                 if(txt.indexOf('.') != -1){
                    console.log("File");
                    getFileContent(d.target);
                 }
                 else
                    getContent(d.target);
                 })
       $(".closeIcon").click(function(){
            $(".popup").hide();
            $(".wrapper").empty();
            $(".wrapper").append('<div id="summary"></div><div class="linemethod"></div><table style=margin-left: auto; margin-right: auto><tr><td><a id="selectMethod" href="#"  class="method"></a></td><td><div class="linemethod1"></div></td></tr></table></div>');








//            $(".method").empty();
//            $(".linemethod").empty();
        })
        $(".closeIcon1").click(function(){
            getCommandService();

            $(".popup").addClass("showClass");
                $(".popup").show();
                })

       $(".message").append("Message To Be Displayed");

//    getCommandService();
});
function multiplyNode(node, count, deep, obj) {
        for (var i = 0, copy; i < obj.length; i++) {
            copy = node.cloneNode(deep);
            copy.append(obj[i]);
            console.log(copy);
            node.parentNode.insertBefore(copy, node)
            console.log(node);
        }
//        $(".method").append(obj);
//        $(".method").append(" ");
    }
 function getContent(d){

// console.log(d);
var txt = $(d).text();
console.log(txt);
var auth = {};
console.log("Test");
   auth.methodName = txt;
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
              console.log(res);
              localStorage.setItem("res",res);
              location.href = "../htmlfiles/loginService.html";
              insertContents(res);

              document.getElementById("alltext").value += res;
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
function getCommandService(){
var result;
var errorres = [];
 var data = {};
                data.command=document.getElementById("selectCommand").value;
                data.directory=document.getElementById("directory").value;
                data.subModule=document.getElementById("module").value;
                data.file=document.getElementById("file").value;
                data.noOfLines=document.getElementById("number").value;
                data.filesize=document.getElementById("size").value;
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
//                console.log(res);
                result = res
                if(data.command===("List all methods having lines greater than n")){
                                    console.log(result);
                                    for(let i =0;i<result.object.length;i=i+3){
                                                        console.log(result.object[i]);
                                                         $(".method").append("<li>"+result.object[i]+"</li>");
                                                         $(".linemethod1").append("(" + result.object[i+1]+")<br>");
                                                    }

                }
                else if(data.command===("List all methods without javadocs")){
                    for(let i =0;i<result.object.length;i++){
                          console.log(result.object);
                          $(".method").append("<li>"+result.object[i]+"</li>");
                    }
                }
                else if(data.command===("List all files")){
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
                            $(".method").append("<li>"+result.object[i][j]+"</li>");
                            $(".linemethod1").append("(" + result.object[i][j+1]+")<br>");
//                          console.log(result.object[i][j]);
                        }
                     }


//                multiplyNode(document.querySelector(".method"), (result.object.length), true, result.object);
                }
                else if(data.command===("Project Summary")){
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
                                $(".linemethod").append("<li>"+key + " : " +  obj[key] + "</li>");
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
                for(let i =0;i<result.object.length;i=i+3){
                    $(".linemethod").append(result.object[i]+"<br>");
                    $(".linemethod").append("<font size=3><b>"+result.object[i+1]+"</b>"+" committed on "+result.object[i+2]+"</font><br>");
                    $(".linemethod").append("<br>");
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
              localStorage.setItem("res",res);
              location.href = "../htmlfiles/loginService.html";
              insertContents(res);

              document.getElementById("alltext").value += res;
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

//function getContent(){
//$.ajax({
//            url:"/rest/checkout",
//            type: 'POST',
//             crossDomain : true,
//              headers: {
//                 "content-type": "application/json"
//                 },
//             data :JSON.stringify(d),
//            xhrFields: {
//                withCredentials: true
//            },
//            success: function(res){
//            console.log("hi");
//           location.href = "../htmlfiles/CommandTest.html";
//              },
//
//          error: function(errorres){
//           console.log(errorres);
//          }
//        });
//}