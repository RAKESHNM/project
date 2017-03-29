var data = {};
$(document).ready(function(){

    if(localStorage.getItem('autnentication')==="false"){
        location.href ="../htmlfiles/index.html";
    }
    else{
        var header = "header";
        var options = ["Commit Details","Project Summary","List all files", "List all methods having lines greater than n", "List all methods without javadocs"];
        $('#selectCommand').empty();
        var output = [];

        options.forEach(function(key){
            output.push('<option class="option_click">'+ key +'</option>');
        })

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
                 $("#label3").show();
                 $("#module").show();
                 $("#directory").hide();
                 $("#file").show();
                 $(".lines-wrapper").show();
                 $(".size-wrapper").hide();
                 getModules();
            }
            else if(document.getElementById("selectCommand").value == options[2]){
                 $(".module-wrapper").show();
                 $("#module").show();
                 $("#label1").show();
                 $("#label2").hide();
                 $("#label3").show();
                 $("#directory").hide();
                 $("#file").show();
                 $(".size-wrapper").show();
                 $(".lines-wrapper").hide();
                 getModules();
            }
            else {
                 $(".module-wrapper").show();
                 $("#module").show();
                 $("#label1").show();
                 $("#label2").hide();
                 $("#label3").show();
                 $("#directory").hide();
                 $("#file").show();
                 $(".lines-wrapper").hide();
                 $(".size-wrapper").hide();
                 getModules();
            }
        });

        $("#popUpButton").click(function(){

            $(".loader").addClass("showClass");
            data.command=document.getElementById("selectCommand").value;
            data.directory=document.getElementById("directory").value;
            data.subModule=document.getElementById("module").value;
            data.file=document.getElementById("file").value;
            data.noOfLines=document.getElementById("number").value;
            data.filesize=document.getElementById("size").value;
            window.localStorage.clear();
            localStorage.setItem('data', JSON.stringify(data));
            location.href = "../htmlfiles/PopUp.html";
        })

        $(".message").append("Message To Be Displayed");
    }
});

function getModules(){

    $.ajax({
        url:"/rest/getmodule",
        type: 'GET',
        crossDomain : true,
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: function(res){
            $('#module').empty();
            var output = [];
            var module = 'Select Module';
            output.push('<option >'+ module +'</option>');
            res.forEach(function(key){
                output.push('<option >'+ key +'</option>');
            })
            $('#module').html(output.join(''));
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