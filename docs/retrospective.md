### Results from our Retrospective Meeting

In this meeting we put our heads together as team to look back, review, and analyze the development process as it progressed over time. The main point of retrospection was answering the question, "what went wrong?". Within the meeting, we came up with the five areas of improvement, along with a possible solution and success criteria for each.

First, only one error was being reported at a time, when it's possible for multiple erros to be present in the user input. To solve this, we decided that we would simply utalize the `ErrorDialog` class correctly and ensure that all the errors that can be displayed are being displayed. We know that this solution is successful when there is no error present that goes unreported.

Second, there are two different methods being used to build the UI, hardcoded locations and the layout manager. To solve this problem we have to rewrite all the code that used the hardcoding technique with code that uses the layout manager technique. We know that this solution is successful when only one method of writitng UI classes is being used.

Thirdly, the UI team has to wait for the Logic team to finish implementation, and the Logic team has to wait for the Database team. To solve this, we will commit to having the UML class diagrams planned in advnace, and also to have emply classes/interfaces created ahead of time. That way each team can implement based on the blank classes without needing to wait for the implementations of those classes to actually be finished. We know that this solution is successful when no team is waiting for another team to finish an implementation.

Fourth, each database table created three new classes which often creates code duplication. To solve this, we might try to create abstract classes that have the common implementations in the abstract class itself, and the specific implementations in the subclasses. We know that this solution is successful when the number of classes is redocued and there is no more code duplication.

Fifth, the `DatabaseManager` class violates the Open-Closed principle because every time a database table is added, the `DatabaseManager` class has to be modified. To solve this, we might try to create a `DatabaseManager` super-class which contains common functionality, and then each table has a tiny subclass associated with it that has the extra implementation unique to that table. We know this solution is successful when the `DatabaseManager` class is closed for modification but open to extension.

With those five areas of improvement considered, we have a lot that we could potentially accomplish for a Release 2 that would greatly improve the quality of the existing code base, so that all future iterations of T.I.M. can be flixibly developed and easily maintained for the forseeable future.
