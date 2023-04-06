# Expired Items List Test

Follow these steps to perform an end-to-end customer test of 
retrieving a list of all expired items from stock. 

Setup
- Start the app
- Click on "Use Stub Instead of SQL" to ensure the following tests always have the same results.

Test the expired list button on initial data as an employee 
- click on "Login as Employee (Client)"
- click on "Check what's in Stock" 
- click on "Expired Items"
- ensure that "cheeto" and "oreo" remain in the list of products
- press "back" and "logout"


Order a new item, guaranteed to be unexpired, then test the expired list
button as an employee
- click on "Order More Stock"
- click on "Order"
- Enter "oreo" in the text box for "name" and "3" in the text box for "quantity"
- click on "order" to confirm the order
- click on "Back"
- click on "Logout"
- click on "Login as Employee (Client)"
- click on "check what's in stock"
- you should see that a new oreo with quantity 3 that has an expiry date after the current date is now in the product list
- click on "Expired Items"
- you should see that the added item is no longer in the product list. Only one "oreo" and one "cheeto" should remain, both of which should have dates before or equal to the current date
