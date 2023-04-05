## Price Modifier

Follow these instructions to test the Price Modifier
- Start the application
- Click “Login as Manager”
- Click “Manage Modifiers”
- In the current view you can add or remove modifiers
- For adding a modifier, fill the information for:
  - Name: The name of the product 
  - Modifier: The amount in terms of price that is going to be either increased or decreased 
  - Start and End Date: The period of time that the price will be modified 
- Click “Add Modifier”, then you can see the modifier has been added to the list
- For removing a modifier, Click “Remove Modifier”
- In the opened window, type the name of the product you want be removed
- Then press "ok" to remove the modifier from the list

The following Gifs are for better understanding:

<img src="https://user-images.githubusercontent.com/85583942/230122819-b8eafb58-ec07-4bfb-aff6-adff6ef9078d.gif" width="450" height="300"/><img src="https://user-images.githubusercontent.com/85583942/230123597-9cd15b9f-72f3-4117-a49f-b175c3f147bd.gif" width="450" height="300"/>


### Error Handling

For Adding Modifier:
- Enter "10-0" or a String in the modifer section
- "Sale/markup must be a decimal number!" will show since the modifier is not a decimal number
- Assuming we already have "oreo" in our modifier list. 
- Enter "oreo" in the name section, Enter "20.0" for modifier, and set the dates
- "There is already a sale/markup for '11'!" will show since there exist already a modifier for oreo
For Removing Modifier:
- Assuming in our list, we have 2 items, "oreo" and "apple" 
- Press "Remove Modifier"
- Enter "11" and press "ok"
- "There is no '11' to remove!" will show since there is no "11" in our list

