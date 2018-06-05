$(document).ready(function(){
    console.log(window.location.pathname);
    if(window.location.pathname === "/pa3/") {
        loadRocks();
    }
    else if (window.location.pathname.includes("details.jsp")) {
        loadDetails();
    }
    else if (window.location.pathname.includes("cart.jsp")) {
        loadCart();
    }
    else if (window.location.pathname.includes("order")) {
        loadCart();
    }
    else if (window.location.pathname.includes("index.jsp")) {
        loadRocks();
    }
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

function loadRocks() {
    console.log("loadRocks");
    
    $.ajax({url: "rocks", success: function(result){
        console.log("success: loadRocks");
        var rocks = result.split("history");
        
        if(rocks[1] !== undefined){
            var historyArray = rocks[1].split("\n");
            for(var x in historyArray){
                $("#product-view-history").append(historyArray[x]);
//                console.log(historyArray[x]);
            }
        }

        var productsArray = rocks[0].split("\n");
        for(var x in productsArray){
            $("#product-list").append(productsArray[x]);
        }
    }});     
}

function loadDetails() {
    console.log("loadDetails");
    var url_string = window.location.href;
    var url = new URL(url_string);
    var rock_id = url.searchParams.get("rock_id");
    
    $.ajax({url: "../details?rock_id=" + rock_id, success: function(result){
        console.log("success: loadDetails");
        $(".product-detail-container").append(result);
        
        //Add listener for add cart button after details load
        $("#add-to-cart").click(function(){
//           console.log("click");
           addToCart();
        });        
    }});   
}

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

function loadCart() {
    console.log("loadCart");
    if(window.location.pathname.includes("order")){
        var url = "grabcart";
    }
    else {
        var url = "../grabcart"; 
    }
    $.ajax({url: url, success: function(result){
        console.log("success: loadCart");
        var cartDictionary = {};
        var resultArray = result.split("\n");
        var totalPrice = 0;
        
        //build cart dict
        for(var x in resultArray){
            var rock_info = resultArray[x].split(" ");
//            console.log("rockid: " + rock_info[0]);
//            console.log("rockprice: " + rock_info[1]);
            if(rock_info[0] !== ""){
                if(cartDictionary[rock_info[0]] === undefined){
                    cartDictionary[rock_info[0]] = [1,rock_info[1]];
                }
                else{
                    cartDictionary[rock_info[0]][0] += 1;
                }
                totalPrice += parseFloat(rock_info[1]);
            }
        }
        
        for(var key in cartDictionary){
            $("#product-list").append("<tr>");
            $("#product-list").append("<th>"+key+"</th>");
            $("#product-list").append("<td>"+cartDictionary[key][0]+"</td>");
            $("#product-list").append("<td>"+cartDictionary[key][1]+"</td>");
            $("#product-list").append("</tr>");
        }
        
        console.log(Math.round(totalPrice * 100) / 100);
        $(".price-display").html("$" + Math.round(totalPrice * 100) / 100);
        
    }});  
    
}