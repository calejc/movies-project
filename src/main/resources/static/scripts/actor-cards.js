const card = document.querySelectorAll(".card-container");

for (let i = 0; i < card.length; i++) {
   card[i].addEventListener("click", function() {
     card[i].classList.toggle("card-flip");
   });
}