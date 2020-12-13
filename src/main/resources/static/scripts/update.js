$(document).ready(function(){
    $('.dropdown-menu a').click(function(){
        var formAction = $(this).attr('id').split("-")[0];
        console.log(formAction);
        toggleForms(formAction);
    })
});

function toggleForms(formAction){
    const forms = document.querySelectorAll(".crudForm");

    for (let i = 0; i < forms.length; i++) {
       if ($(forms[i]).attr("id") == formAction){
          $(forms[i]).show();
       } else {
          $(forms[i]).hide();
       }
    }
}
