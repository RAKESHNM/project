$(document).ready(function(){
    if(localStorage.getItem('autnentication')==="false"){
        location.href ="../htmlfiles/index.html";
    }
    else{
        var content = localStorage.getItem('content');
        var commit = localStorage.getItem('commit');
        $(".Contents").append(content);
        $(".Commits").append(commit);
    }
});