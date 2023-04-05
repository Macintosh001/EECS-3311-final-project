# Permenant Record of Stock Test

Follow the instructions to complete the test.

- Start the application.
- Enter you local SQL username (probably "root") and password.
- Click "Login and Connect to DB"

We will now test to see if when you order something, and then close the application,
that the record of stock is saved when you reopen the program.

- Click "Login as Manager (Client}"
- Click "Order more Stock"
- If there are no orderables, you can add one as follows:
  - Click "Add"
  - Fill in "Name", "Price", and "Shelf Life" to whatever.
  - Click "Add Order" and make sure there are no errors reported.
  - Verify that the orderable you added is actually in the list now.
- Click Order
- Fill in the name of an existing orderable in "name".
- Fill in "100" in "Quantity"
- Click "Order"

Now we can check to see if it was actually ordered.

- Click "Back"
- Click "Manager what's in Stock"
- The entry in the table with the highest barcode should be what you just ordered.
- Verify that it has a quantity on 100.
- Close the application (Use the X in the top corner of the window.)

Now when we reopen the program, the stock you ordered should still be there.

- Start the application again.
- Login with your SQL username again.
- Click "Login as Manager (Client)"
- Click "Manager what's in Stock"
- Verify that what you ordered is still there.
- Close the application.

You have now completed the customer test!
