$(document).ready(function(){
if(localStorage.getItem('autnentication')==="false"){
           location.href ="../htmlfiles/index.html";
     }
     else{
var content = localStorage.getItem('content');
var commit = localStorage.getItem('commit');
console.log("Content", content)
console.log("Commit",commit)
                          console.log(content);
    $(".Contents").append(content);
    $(".Commits").append(commit);
    }
    });