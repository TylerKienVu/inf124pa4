$(document).ready(function(){
    $("#add-to-cart").click(function(){
        addToCart();
    }); 
});  

//takes care of form validation
(function() {
  'use strict';
  window.addEventListener('load', function() {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
//        else {
//          event.preventDefault();
//        }
        form.classList.add('was-validated');
      }, true);
    });
  }, false);
})();

function addToCart() {
    console.log("addToCart");
    var url_string = window.location.href;
    var url = new URL(url_string);
    var rock_id = url.searchParams.get("rock_id");
    
    $.ajax({url: "../cart?rock_id=" + rock_id, success: function(result){
        console.log("success: addToCart");
        $("#cart-alert-container").html("<div class=\"alert alert-success\" role=\"alert\">Item added to cart. Your cart has a total of " +
                result + " items.</div>");
    }});   
}
