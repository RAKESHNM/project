function display(form){
    document.getElementById('buttonid').disabled = true;
    var inpObj = form.username.value;
    var inpObj2 = form.password.value;

    if (inpObj == "") {
       alert("username should not be empty");
    document.getElementById('buttonid').disabled = false;

    }
    else if(inpObj2 == ""){
        alert("password should not be empty");
    document.getElementById('buttonid').disabled = false;
    }
    else {
    var auth = {};
    auth.userName=form.username.value;
    auth.password=form.password.value;


    $.ajax({
    url: '/rest/credential',
    data:JSON.stringify(auth),
    headers: {
    "content-type": "application/json"
    },
    method: 'POST',
    crossDomain:true,
     success:function(data2){
     if(data2 == 'Success'){
     location.href = "../htmlfiles/CheckoutService.html";
     }
     },

         error:function(data1){
    document.getElementById('buttonid').disabled = false;
         alert('Invalid UserName or Password');

             if(data1.statusText == "Bad Gateway"){alert("check your network connection");}
              }
//                   location.href = "../htmlfiles/CheckoutService.html";
  });
  $(".loader").addClass("showClass");
}
}

