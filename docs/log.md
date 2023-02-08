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
-reworked UML/plan for database design to match code

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
-Bug fixes, dead code removal, etc.

# Wednesday, February 8th

## William
- Added input validation to the feature that adds a new product to the database.

## Aaron
- Modified a few files for the app to start with 0 entries in order to observe barcode creation is handled correctly
