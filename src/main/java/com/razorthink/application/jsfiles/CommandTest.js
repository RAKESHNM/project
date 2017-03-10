$(document).ready(function(){
    var header = "header";
   var options = ["Commit Details","Project Summary","List all methods", "List all methods having lines greater than n", "List all methods without javadocs"];

 	$('#selectCommand').empty();

	  var output = [];
	  options.forEach(function(key){
	   output.push('<option class="option_click">'+ key +'</option>');
	  })
	  console.log(output)  ;
	  $('#selectCommand').html(output.join(''));
	  $(".module-wrapper").hide();
   	  $(".lines-wrapper").hide();
	  $("#selectCommand").on("click", function(value){
	  	if((document.getElementById("selectCommand").value == options[0])||(document.getElementById("selectCommand").value == options[1])){
   				 $(".module-wrapper").hide();
   				 $(".lines-wrapper").hide();
    			}
    	else if(document.getElementById("selectCommand").value == options[3]){
   				 $(".module-wrapper").show();
   				 $(".lines-wrapper").show();
    			}
        else {
        		 $(".module-wrapper").show();
    		 	 $(".lines-wrapper").hide();

    		 }
		});

       $(".button").click(function(){
           getCommandService();
            $(".popup").addClass("showClass");
                $(".popup").show();
       })

       $(".closeIcon").click(function(){
            $(".popup").hide();
            $(".method").empty();
            $(".linemethod").empty();
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


function getCommandService(){
var result;
var errorres = [];
 var data = {};
                data.command=document.getElementById("selectCommand").value;
                data.directory=document.getElementById("directory").value;
                data.subModule=document.getElementById("module").value;
                data.file=document.getElementById("file").value;
                data.noOfLines=document.getElementById("number").value;

          $.ajax({
            url:"http://localhost:8080/inputs",
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
                console.log(result.object[0]);
                if(!(data.command===("Project Summary"))&&!(data.command===("Commit Details"))){
                multiplyNode(document.querySelector(".method"), (result.object.length), true, result.object);
                }
                else if(!(data.command===("Commit Details"))){
                console.log("Else condition");
                console.log(res.object.length);
                var wrapper = $('#wrapper'), container;
                for (var key in res){
//                container = $('<div id="summary" class="container"></div>');
//                wrapper.append(container);
//                container.append('<div class="item">' + key +'</div>');
                console.log(key);
//                multiplyNode(document.querySelector(".linemethod"), (key.length), true, key);
                $(".method").append("<li>"+ key + "</li>");
//                $(".method").empty();

                }
                }
                else{
                multiplyNode(document.querySelector(".linemethod"), (result.object.length), true, result.object);
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