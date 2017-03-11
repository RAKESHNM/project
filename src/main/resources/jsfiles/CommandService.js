function getCommandServiceResult(){
            var data = {};
                data.command=document.getElementById("selectCommand").value;
                data.directory=document.getElementById("directory").value;
                data.subModule=document.getElementById("module").value;
                data.file=document.getElementById("file").value;
                data.noOfLines=document.getElementById("number").value;

           $.ajax({
            url:"/rest/inputs",
            type: 'POST',
             crossDomain : true,
              headers: {
                 "content-type": "appl•••••••••ication/json"
                 },
             data :JSON.stringify(data),
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            success: function(res){

                console.log(res);

},

          error: function(errorres){
           console.log(errorres);
          }
        });

        }