$(document).ready(function(){
    var page = $(location).attr("pathname");
    switch(true){
        case page.length == 1:
        toggleHome();
        break;
        case page.includes("movie"):
        toggleActiveNavLink("movie");
        break;
        case page.includes("actor"):
        toggleActiveNavLink("actor");
        break;
        case page.includes("about"):
        toggleActiveNavLink("about");
        break;
        case page.includes("update"):
        toggleActiveNavLink("update");
        break;
        default:
        console.log('');
    }
});


function toggleActiveNavLink(link){
    $('.navbar-nav').find('li.active').removeClass('active');
    $("#" + link).addClass( 'active' );
}

function toggleHome(){
    $('.navbar-brand').addClass('active');
}