# Filter Test

Follow the instructions to complete the test:

- Start the application.
- Click "Use Stub Instead of SQL" (This is to ensure the test is always the same)
- Click on "Login as Employee (Client)"
- Click on "Check what's in stock"

Here we will check that each filter works one at a time. We will start with the 
price filters.

- Enter "0" into the "Min:" of "Filter by Price"
- Enter "3.5" into the "Max:" of "Filter by Price"
- Click "Apply Filter"
- Verify that only "cheeto" is left in the list of products
- Clear all the filters so that they are all black
- Click "Apply Filter"
- Verify that both "oreo" and "cheeto" are now in stock

Next, we will test the Quantity Filters

- Enter "50" into the "Min:" of "Filter by Quantity"
- Enter "100" into the "Max:" of "Filter by Quantity"
- Click "Apply Filter"
- Verify that only "oreo" is left in the list of products
- Clear all the filters so that they are all black
- Click "Apply Filter"
- Verify that both "oreo" and "cheeto" are now in stock

Next we will test the Expiry Date Filters

- Choose yesterday's date as the "Min:" of "Filter by Expiry Date"
- Choose tomorrow's date as the "Min:" of "Filter by Expiry Date"
- Click "Apply Filter"
- Verify that only "oreo" is left in the list of products
- Clear all the filters so that they are all black
- Click "Apply Filter"
- Verify that both "oreo" and "cheeto" are now in stock

We will now check the error handling:

- Enter "test" into "Min:" of "Filter by Price"
- Click "Apply Filter"
- Verify that the error message that pops up is "Price must be a decimal number!"
- Enter "-100" into "Min:" of "Filter by Price"
- Click "Apply Filter"
- Verify that the error message that pops up is "Price cannot be negative!"
- Enter "test" into "Min:" of "Filter by Quantity"
- Click "Apply Filter"
- Verify that the error message that pops up is "Quantity must be a whole number!"
- Enter "2.5" into "Min:" of "Filter by Quantity"
- Click "Apply Filter"
- Verify that the error message that pops up is "Quantity must be a whole number!"
- Enter "-100" into "Min:" of "Filter by Quantity"
- Click "Apply Filter"
- Verify that the error message that pops up is "Quantity cannot be negative!"
- Close the application.

You have now completed the customer test!
