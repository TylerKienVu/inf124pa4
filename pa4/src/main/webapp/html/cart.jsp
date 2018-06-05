<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta author="Tyler Vu">
		<title>INF 124</title>
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"
		integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
		crossorigin="anonymous"></script>
	  	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
		<script defer src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.17.0/dist/jquery.validate.js"></script>
		<script src="../js/scripts.js"></script>	
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<link rel="stylesheet" href="../css/animate.css">    
		<link rel="stylesheet" href="../css/hover.css">    	
	    <link rel="stylesheet" href="../css/styles.css">
	</head>
	<body>
            <nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light">
                    <a class="navbar-brand" href="../">Rock Stop</a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="navbar-nav mr-auto">
                                    <li class="nav-item">
                                            <a class="nav-link" href="../">Home</a>
                                    </li>
                                    <li class="nav-item active">
                                            <a class="nav-link" href="#">Cart<span class="sr-only">(current)</span></a>
                                    </li>
                            </ul>
                    </div>
            </nav>
            <h2 class="product-table-title">Cart Items</h2>
            <table class="product-table table">
                <thead>
                    <tr>
                        <th scope="col">Rock #</th>
                        <th scope="col">Number of orders</th>
                        <th scope="col">Price per order</th>			    	
                    </tr>
                </thead>
                <tbody id="product-list">	    		
                    <%-- Grab all of the rocks in the db --%>
                </tbody>
            </table>   
            <div id="price-container">
                <h5>Total Price</h5>
                <div class="price-display">
                      $0.00
                </div>    
            </div>
            <div id="order-div">
                    <form action="../order" method="post" id="purchase-form" class="needs-validation" novalidate>
                      <h5>Order Details</h5>
<!--                      <div class="form-row">
                        <div class="form-group col-md-3">
                          <label for="inputItem">Item Number</label>
                          <input type="text" pattern="^([1-9]|10)$" class="form-control" id="inputItem" name="rockNum" placeholder="Rock Number 1-10" onblur="dynamicPrice()" required>
                          <div class="invalid-feedback">
                            Please choose a number between 1-10
                          </div>
                        </div>	
                        <div class="form-group col-md-3">
                          <label for="inputNumberOfOrders">Number of Orders</label>
                          <input type="text" class="form-control" name="quantity" id="inputNumberOfOrders" pattern="^[1-9]|[1-9][0-9]$" value=1 onblur="dynamicPrice()" required>
                          <div class="invalid-feedback">
                                    Please choose a number between 1-99
                          </div>
                        </div>			    	  	
                      </div>		  -->
                      <div class="form-row">
                        <div class="form-group col-md-3">
                          <label for="inputFirstName">First Name</label>
                          <input type="text" class="form-control" id="inputFirstName" name="fname" pattern="^[a-zA-Z ,.'-]+$" required>
                          <div class="invalid-feedback">
                                    Valid characters are a-z A-Z ,.'-
                          </div>
                        </div>	
                        <div class="form-group col-md-3">
                          <label for="inputLastName">Last Name</label>
                          <input type="text" class="form-control" id="inputLastName" name="lname" pattern="^[a-zA-Z ,.'-]+$" required>
                          <div class="invalid-feedback">
                                    Valid characters are a-z A-Z ,.'-
                          </div>		      
                        </div>			    	  	
                        <div class="form-group col-md-3">
                          <label for="inputEmail4">Email</label>
                          <input type="email" class="form-control" id="inputEmail4" name="email" placeholder="Email" required>
                          <div class="invalid-feedback">
                            Please provide a valid email
                          </div>
                        </div>
                        <div class="form-group col-md-3">
                                    <label for="inputPhone">Phone Number</label>
                                    <input type="tel" class="form-control" id="inputPhone" name="phone" placeholder="XXX-XXX-XXXX" pattern="^\d{10}$" required>
                                    <div class="invalid-feedback">
                                            Please provide a valid phone number e.g XXXXXXXXXX
                                    </div>
                        </div>
                      </div>
                      <div class="form-group">
                        <label for="inputAddress">Shipping Address</label>
                        <input type="text" class="form-control" id="inputAddress" name="shipAddress" placeholder="1234 Main St" required>
                      </div>
                      <div class="form-group">
                        <label for="inputAddress2">Shipping Address 2</label>
                        <input type="text" class="form-control" id="inputAddress2" name="shipAddress2" placeholder="Apartment, studio, or floor">
                      </div>
                      <div class="form-row">
                        <div class="form-group col-md-6">
                          <label for="inputCity">City</label>
                          <input type="text" class="form-control" id="inputCity" name="city" pattern="^[a-zA-Z  ']+$" required>
                          <div class="invalid-feedback">
                                    Valid characters are a-z A-Z
                          </div>
                        </div>
                        <div class="form-group col-md-4">
                          <label for="inputState">State</label>
                          <input type="text" id="inputState" class="form-control" name="state" required>
                        </div>
                        <div class="form-group col-md-2">
                          <label for="inputZip">Zip</label>
                          <input type="number" class="form-control" id="inputZip"  name="zip" pattern="/(^\d{5}$)|(^\d{5}-\d{4}$)/" required>
                          <div class="invalid-feedback">
                                    Please provide a valid Zip Code
                          </div>
                        </div>
                      </div>
                      <h5>Payment Method</h5>
                      <div class="form-row">
                        <div class="form-group col-md-4">
                          <label for="inputCardName">Name on Card</label>
                          <input type="text" class="form-control" id="inputCardName" name="cardName" pattern="^[a-zA-Z ,.'-]+$" required>
                          <div class="invalid-feedback">
                                    Valid characters are a-z A-Z ,.'-
                          </div>
                        </div>		    	  	
                        <div class="form-group col-md-5">
                          <label for="inputCardNumber">Card Number</label>
                          <input type="text" class="form-control" id="inputCardNumber" name="cardNumber" placeholder="XXXXXXXXXXXXXXXX" pattern="^\d{16}$" required>
                          <div class="invalid-feedback">
                                    Please provide a valid card number e.g XXXXXXXXXXXXXXXX
                          </div>
                        </div>
                        <div class="form-group col-md-2">
                          <label for="inputExpiration">Expiration Date</label>
                          <input type="text" class="form-control" id="inputExpiration" name="expiration" placeholder="XX/XX" pattern="^\d{2}/\d{2}$" required>
                          <div class="invalid-feedback">
                                    Please provide a valid date e.g XX/XX
                          </div>
                        </div>		
                        <div class="form-group col-md-1">
                          <label for="inputCSC">CSC</label>
                          <input type="number" class="form-control" id="inputCSC" name="csc" placeholder="XXX" pattern="^\d{3}$" required>
                          <div class="invalid-feedback">
                                    Please provide a valid CSC e.g XXX
                          </div>
                        </div>		        
                      </div>	
                      <div class="form-group">
                        <label for="inputBillingAddress">Billing Address</label>
                        <input type="text" class="form-control" id="inputBillingAddress" name="billAddress" placeholder="1234 Main St" required>
                      </div>
                      <div class="form-group">
                        <label for="inputBillingAddress2">Billing Address 2</label>
                        <input type="text" class="form-control" id="inputBillingAddress2" name="billAddress2" placeholder="Apartment, studio, or floor">
                      </div>
                      <div class="form-row">
                        <div class="form-group col-md-6">
                          <label for="inputBillingCity">City</label>
                          <input type="text" class="form-control" id="inputBillingCity" name="billCity" pattern="^[a-zA-Z ']+$" required>
                        </div>
                        <div class="form-group col-md-4">
                          <label for="inputBillingState">State</label>
                          <input type="text" id="inputBillingState" class="form-control" name="billState" required>
                        </div>
                        <div class="form-group col-md-2">
                          <label for="inputBillingZip">Zip</label>
                          <input type="number" class="form-control" id="inputBillingZip" pattern="/(^\d{5}$)|(^\d{5}-\d{4}$)/" name="billZip" required>
                          <div class="invalid-feedback">
                                    Please provide a valid Zip Code
                          </div>
                        </div>
                      </div>	
                      <h5>Total Price</h5>
                      <div class="form-group price-display">
                            $0.00
                      </div>	  	  	  
                      <button type="submit" class="btn btn-primary">Place Order</button>
                    </form>		
            </div>
	</body>
</html>