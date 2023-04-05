# PointOfSale Test

Follow the instructions to complete the test:

- Start the application.
- Click "Use Stub Instead of SQL" (This is to ensure the test is always the same)
- Click on "Login as Employee (Client)"
- Click on "Point of Sale"

We will now test to see if when the employee start billing for the customers, all the functions work well.

Scan

- Click "Scan"
- Enter "0"
- Click "Scan"
- Verify only "oreo" on the list
- Click "Scan"
- Verify the quantity of "oreo" is increased by 1
- Enter "1"
- Click "Scan"
- Verify that both "oreo" and "cheeto" are on the list
- Click "Done"
- Verify the scan window turn off
- Verify the "Total Cost" increased by the right value

Next, we will test the Apply Coupon

- Click "Apply Coupon"
- Enter "coup1"
- Click "Apply Coupon"
- Verify that "Total Cost" is changed from $15.46 to $13.14

Next, we will test the Clear Cart
- Click "Clear Cart"
- Verify that the shopping cart list is reset and coupon too

Next we will test the Buy
- Click "Scan"
- Enter "0"
- Click "Scan"
- Click "Scan"
- Enter "1"
- Click "Scan"
- Click "Apply Coupon"
- Enter "coup1"
- Click "Buy"
- Verify that the shopping cart list is reset and coupon too
- Click "Back"
- Click "Check what's in Stock"
- Verify that the quantity of "oreo" decreased by 2 and the quantity of "cheeto" decreased by 1

We will now check the error handling:

- Click "Scan"
- Enter "test"
- Click "Scan"
- Verify that the error message that pops up is "Invalid barcode format"
- Enter "5"
- Click "Scan"
- Verify that the error message that pops up is "Product not found in inventory"
- Click "Apply Coupon"
- Enter "test"
- Click "Apply Coupon"
- Verify that the error message that pops up is "Invalid coupon code"
- Click "Apply Coupon"
- Enter "coup1"
- Click "Apply Coupon"
- Click "Apply Coupon"
- Enter "coup1"
- Click "Apply Coupon"
- Verify that the error message that pops up is "Coupon already applied"
- Click "Apply Coupon"
- Enter "coup2"
- Click "Apply Coupon"
- Verify that the error message that pops up is "Coupon already applied"
- Click "Scan"
- Enter "1"
- Click "Scan" 50 times
- Click "Buy"
- Verify that the error message that pops up is "Product out of stock"

You have now completed the customer test!
