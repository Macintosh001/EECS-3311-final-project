# Task allocation
- Aaron Behzad (UI)
- William Jeremy (Logic, Objects)
- Lucas (Database)

# Time Spent vs Time Estimated
Task | Time Estimated | Time Spent
---|---|---
Add to Stock | 1 Point | 2 Points
Remove from Stock | 1 Point | 2 Points
View Items | 2 Points | 1 Points
Update Properties (only in backend right now) | 2 Points | 2 Points

# Dev tasks per small user story

### Add to Stock
- Add Function in Database Stub
- Add Function in Logic
- Add Button/Dialog Box in GUI

### Remove from Stock
- Remove Function in Database Stub
- Remove Function in Logic
- Remove Button/Dialog in GUI

### View Items
- Function to get product list in Database Stub
- Hold Data in Database Stub
- Function to prepare data for GUI in the Domain Object
- Display Data in a Table in GUI

### Update Product
- Function to replace product in Database Stub
- Logic Functions to update each field in a product
- (not implemented) GUI to call the update functions

# Work Log:

# Friday, January 27th

## Team
- Team meeting to plan out the UML for iteration 1.
- A simple design following the application/display/logic/persistence/objects pattern discussed in class was used.

# Saturday, January 28th

## Aaron
- Added some dependencies to the build.gradle file for javafx. Considering using javafx to handle the UI provided it has some advantage over java.swing
## Lucas 
- Added gradle dependency for database connection
- Implemented some database fundamentals, which turned out to be for later iterations
- Established connection method to database and wrote some code to initialize database

# Monday, January 30th

## Aaron
- Decided along with Behzad to go with java.swing to implement the UI functionality. Was able to render a simple Table that will display the inventory. At this point I decided to implement the UI in one big class in order to have an MVP ready for delivery, however I am researching what patterns I may use to better separate the UI code to promote maintainability and extensability for future iterations.

# Wednesday, February 1st

## William
- Began work on the business logic class based on the current design.

## Aaron
- Tweaked the Display class to utilize the stub

# Friday, February 3rd

## Lucas
- Draft of database interface
- Draft of database stub functionality, including add/remove/etc. 

# Saturday, February 4th

## Lucas
- reworked UML/plan for database design to match code

# Sunday, February 5th

## Aaron
- Added buttons (add and remove) to the Table and changed the Display class to interact with the ILogic interface rather than the concrete logic classes. 
- Also added dialog box pop up when the buttons were clicked

## William
- Added the ILogic interface that can be used by the display, and finished implementing the nessecary business logic up to this point.

# Monday, February 6th

## Aaron
- Add and remove buttons now are fully functional

# Tuesday, February 7th

## Team
- In the meeting we discussed bugs we've found so far and other things we may have missed or looked over during implementation. Issues were created in github and these were assigned to developers accordingly. We also discussed finalization of the documentation.

## Lucas
- Bug fixes in dbStub, dead code removal, etc.

# Wednesday, February 8th

## William
- Added input validation to the feature that adds a new product to the database.

## Aaron
- Modified a few files for the app to start with 0 entries in order to observe barcode creation is handled correctly

# Thursday, February 9th

## Jeremy
- Added a funciton to ensure that items with a duplicate name will not be added.

## Behzad
- Fixed the date input issue.
- Added the meetings log files into the main
- Added more comments to the Display.java

# Saturday, February 11th

## Behzad
- Our Dev team shared the .jar file with us.

# Sunday, February 12th

## Behzad 
- We shared our project(.jar file) with our client team with the description.

## Aaron
- Utilize a Date Picker to handle inputting dates in the UI

# Wednesdaym February 15th
## Lucas
- Added to database manager
- Added persistence for products with some db functionality

# Thursday, February 16th

## William
- Added some basic unit tests
- Refactored the UI to look less ugly

## Aaron
- Fixed small bugs in the UI

## Lucas
- Added queries for product persistence
- Started work on filters

# Saturday, February 19

## Lucas
- work on filters

# Sunday, February 20

## Lucas
- finish filters
- stub filters implemented

# Monday, February 21st

## Aaron
- Added multiple views and dialogs, some fully implemented and some empty

## Lucas
- filter bugs worked out 

# Tuesday, February 22nd

## William
- Added the "Result Type" so functions can return objects or errors depending on the inputs

# Thursday, February 24th

## Lucas
- Orderable and Coupon Tables added to database


# Thursday, March 2nd

## Aaron
- Thinking about using the Builder Pattern to build UI components which live in a Frame
- Made some interfaces ect. NOTE we decided to move this to the next iteration

## Lucas
- persistence package complete restructure
- new interfaces and stubs for orderable and coupon
- database manager methods added
- Orderable and coupon persistence fully implemented
- queries for orderable and coupon
- modifications methods for coupon and orderable databses

# Friday, March 3rd

## William
- Created a domain object for Coupon and Orderable
- Created a bunch of Validator classes that will validate string inputs from the UI. This helps lower code duplication and increase cohesion
- Added the Stock Checking and Stock Managing Logic
- Added the Coupon Manager Logic
- Added the Order Logic
- Create a TableEntryGenerator class that turns lists of domain objects into 2D String arrays to be used in a JTable. THis decreases code duplication.
- (A lot of this was probably written earlier but today is when they were commited.)

## Lucas
- persistence commented

## Aaron
- Completed CouponManagerView

# Sunday, March 5th

## Behzad
- Working on the layouts and the views on UI
- making StockManagingView and StockCheckingView

## Aaron
- Worked on fleshing out the UI classes and their buttons with Behzad

# Monday, March 6th

## Aaron
- Fixed bugs found in the UI
- continued adding action listeners to the buttons

# Tuesday, March 7th

## William
- Added some more unit tests for the logic classes

## Aaron
- got Initial view working
- added init() method in App class to encapsulate initialization
- added back buttons to all applicable views, still need to add action listeners

## Lucas
- persistence constructors changed for password/username arguments
- bug fixes

# Wednesday, March 8th

## William
- Implemented a whole new system for managing different UI components and transfered the exsisted UI code into the new system.
- Added a UI element that gets the username and password for the local SQL Database
- A whole bunch of bug fixes

## Aaron
- got back buttons working except for coupon manager
- fixed some overlapping fields

## Lucas
- bug fixes
- SQLException handling changed
- unit stub tests for all stubs 

# Thuesday, March 9th

## William
- A few more bug fixes

## Behzad
- Worked with William on the UML Diagrams
- Merge all the UML diagrams and commit them
- Uploaded the updated version of Meeting Log file

## Aaron
- added date filters in the Stock classes, utilizing the date picker

## Lucas
- bug fixes
- Integration tests for three persistence classes

