$(document).ready(function(){
var content = localStorage.getItem('content');
var commit = localStorage.getItem('commit');
console.log("Content", content)
console.log("Commit",commit)
//for(var i =0;i<content.object;i++){
                          console.log(content);
//                          $(".method").append("<li>"+result.object[i]+"</li>");
//                    }
    $(".Contents").append(content);
    $(".Commits").append(commit);
    });