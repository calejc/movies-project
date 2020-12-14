//const card = document.querySelectorAll(".card-container");

//for (let i = 0; i < card.length; i++) {
//   card[i].addEventListener("click", function() {
//     card[i].classList.toggle("card-flip");
//   });
//}

$(document).ready(function(){
    $(".cardFlipTrigger").click(function(){
        $(".card-container").toggleClass("card-flip");
    });
    $("#editActorButton").click(function(){
            console.log("click worked");
            $(document).ready(function(){
                $("#editActor").show();
            });
        });
});


$(function(){
    $("#editActorButton").click(function(){
        console.log("click worked");
        $(document).ready(function(){
            $("#editActor").show();
        });
    });
});


