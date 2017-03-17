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
                 $(".size-wrapper").hide();
             }
       else if(document.getElementById("selectCommand").value == options[3]){
                 $(".module-wrapper").show();
                 $("#label1").show();
                 $("#label2").hide();
                 $("#label3").hide();
                 $("#module").show();
                 $("#directory").hide();
                 $("#file").hide();
                 $(".lines-wrapper").show();
                 $(".size-wrapper").hide();
             }
        else if(document.getElementById("selectCommand").value == options[2]){
                $(".module-wrapper").show();
                $("#module").show();
                $("#label1").show();
                                 $("#label2").hide();
                                 $("#label3").hide();
                                 $("#directory").hide();
                                 $("#file").hide();
                $(".size-wrapper").show();
                $(".lines-wrapper").hide();
            }
        else {
               $(".module-wrapper").show();
                $("#module").show();
                $("#label1").show();
                                 $("#label2").hide();
                                 $("#label3").hide();
                                 $("#directory").hide();
                                 $("#file").hide();
               $(".lines-wrapper").hide();
               $(".size-wrapper").hide();
           }
      });
       $("#popUpButton").click(function(){
                       data.command=document.getElementById("selectCommand").value;
                data.directory=document.getElementById("directory").value;
                data.subModule=document.getElementById("module").value;
                data.file=document.getElementById("file").value;
                data.noOfLines=document.getElementById("number").value;
                data.filesize=document.getElementById("size").value;
                localStorage.setItem('data', JSON.stringify(data));
           location.href = "../htmlfiles/PopUp.html";
//           getCommandService();
//            $(".popup").addClass("showClass");
//                $(".popup").show();
       })
//       $("#selectMethod").on('click', function(d) {
//
//                 console.log(d.target);
//                 var txt = $(d.target).text();
//                 console.log(txt);
//                 if(txt.indexOf('.') != -1){
//                    console.log("File");
//                    getFileContent(d.target);
////                      getCommit(d.target);
//
//                 }
//                 else
//                    getContent(d.target);
//                 })
//       $(".closeIcon").click(function(){
////            $(".popup").hide();
//            history.go(-1);
//            $(".linemethod").empty();
//            $(".linemethod1").empty()
//            $(".method").empty();
////            history.go(-1);
////            $(".wrapper").empty();
////            $(".wrapper").append('<div id="summary"></div><a id="selectMethod" href="#"  class="method"></a><div class="linemethod"></div>');
//
//
////            $(".wrapper").append('<div id="summary"></div><div class="linemethod"></div><table style=margin-left: auto; margin-right: auto><tr><td><a id="selectMethod" href="#"  class="method"></a></td><td><div class="linemethod1"></div></td></tr></table></div>');
//
//
//
//
//
//
////        <a href="##" onClick="history.go(-1); return false;">GoBack</a>
//
////            $(".method").empty();
////            $(".linemethod").empty();
//        })
//        $(".closeIcon1").click(function(){
//            location.href = "../htmlfiles/CommandTest.html";
//            getCommandService();
//
////            $(".popup").addClass("showClass");
////                $(".popup").show();
//                })

       $(".message").append("Message To Be Displayed");

//    getCommandService();
});



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