function display(form){
    var inpObj = form.username.value;
    var inpObj2 = form.password.value;
   
    if (inpObj == "") {
       alert("username should not be empty");
    }
    else if(inpObj2 == ""){
        alert("password should not be empty");
    }
    else {
    var auth = {};
    auth.userName=form.username.value;
    auth.password=form.password.value; 
    $.ajax({
    url: 'http://localhost:8080/credential',
    data:JSON.stringify(auth),
    headers: {
    "content-type": "application/json"
    },
    method: 'POST',
     success:function(data2){
     if(data2 == 'Success'){
     location.href = "../htmlfiles/sample.html";
     }
     },

         error:function(data1){
         alert('Invalid UserName or Password');

             if(data1.statusText == "Bad Gateway"){alert("check your network connection");}
              }
  });   
}
}

//function getAllRepos(){
// $.ajax({
//                   url: 'http://localhost:8080/repositories',
//                   method: 'GET',
//                   crossDomain : true,
//                     dataType: 'json',
//                      xhrFields: {
//                             withCredentials: true
//                         },
//                    success:function(data3){
//                        console.log(data3);
//                        alert(data3);
//                        }
//       });
//}
