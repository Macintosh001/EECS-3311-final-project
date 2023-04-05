# Coupon Test

Follow the instructions to complete the test:

- Start the application.
- Click "Use Stub Instead of SQL" (This is to ensure the test is always the same)
- Click on "Login as Manager (Client)"
- Click on "Manage Coupons"

We will now test to see if when the manager add/remove/update the coupons, all the functions work well.

Add Coupon

- Click "Add Coupon"
- Enter "abcd" in the "Code:" and enter "50" in the "Percentage off:"
- Click "Add Coupon"
- Verify that "abcd" on the list of Barcode and "50" on the list of Discount


Next, we will test the Update Coupon

- Click "Update Coupon"
- Enter "abcd" in the "Code:" and enter "20" in the "Percentage off:"
- Click "Update Coupon"
- Verify that "abcd" on the list of Barcode and "20" on the list of Discount

Next, we will test the Remove Coupon
- Click "Remove Coupon"
- Enter "abcd" in the "Code:"
- Click "Remove Coupon"
- Verify that now the "abcd" coupon is not on the list

We will now check the error handling:

- Click "Add Coupon"
- Only enter "50" in the "Percentage off:"
- Click "Add Coupon"
- Verify that the error message that pops up is "Code cannot be empty!"
- Click "Add Coupon"
- Enter "abcd" in the "Code:" and enter "test" in the "Percentage off:"
- Click "Add Coupon"
- Verify that the error message that pops up is "Discount must be a decimal number!"
- Click "Update Coupon"
- Do not enter anything, or just enter "50" in the "Percentage off:"
- Click "Update Coupon"
- Verify that the error message that pops up is "Code cannot be empty!"
- Click "Update Coupon"
- Enter "test" in the "Code:" and enter "50" in the "Percentage off:"
- Click "Update Coupon"
- Verify that the error message that pops up is "This code doesn't exists!"
- Click "Update Coupon"
- Enter "coup1" in the "Code:" and enter "test" in the "Percentage off:"
- Click "Update Coupon"
- Verify that the error message that pops up is "Discount must be a decimal number!"
- Click "Remove Coupon"
- Do not enter anything
- Click "Remove Coupon"
- Verify that the error message that pops up is "Code cannot be empty!"
- Click "Remove Coupon"
- Enter "test" in the "Code:"
- Click "Remove Coupon"
- Verify that the error message that pops up is "This code doesn't exists!"

You have now completed the customer test!
