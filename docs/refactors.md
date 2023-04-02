## Builder Pattern in the UI
**Problem**: As the codebase grew and more Views were needed, we found ourselves duplicating a lot of code.
Mainly ui components like buttons and labels ect.

**Solution**: Perhaps we could abstract the process of creating components in a way to reduce the lines of code,
and also better satisfy SRP.The Builder pattern stood out to us as a potential solution to this problem, although
there are perhaps other approaches or patterns that can be applied as well. The Builder is responsible for building each component
except for JPanels and JFrames. This lead to a reduction in code by about 1 to 2 lines of code per component and in the case of the DatePicker
6-8 lines of code.

## MigLayout manager in the UI
**Problem**: The process building Views, mainly the most complex ones, became a process of trial and error sometimes.
This was due to our choice of hardcoding positions of every component in a particular view. As the Views and Dialogs
increase in number so does the impact of this design decision on the speed of development.

**Solution**: Perhaps delegating the components positioning to a Layout manager in Swing would help us achieve 
greater development speed. We did try to use some Layout managers like GridLayout but they often brought their 
own level complexity and learning curve. Fortunately through research we found an open source library MigLayout
that made it very easy to position components in a View. Creating new Views or modify already made ones became very
easy to do.

## Table Entry Generator
**Problem**: Whenever we turn a list of items from the database into a `String[][]` that can be used 
to populate a `JTable`, we end up writing the same code over again in several different places.

**Solution**: Refactor the table generation code into a seperate class which we called `TableEntryGenerator`.
This single class is responsible for generating table entry data for all the logic classes.

## Validator Classes
**Problem**: Whenever the logic classes accept user input, it has to be validated. This validation code has to 
be written the same way in severals places because there are multiple classes that all have to validate the
same kind of text input.

**Solution**: Create a generic `Validator` interface and implement a concrete validator class (for example, 
`BarcodeValidator` for each type of text input. That way, you simply have to call the `validate()` function
and it will return the input in the correct datatype (for the barcode, that's an `Integer`), or, if the 
input is invalid, it will return a list of error messages that can be passed on to the UI and displayed
to the user.

## ErrorDialog
**Problem**: The first iteration of the code to display errors had two problems:
1. It could only handle one error message at a time.
2. The same code would have to be used in several different display classes.

**Solution**: A single class called `ErrorDialog` was created. An instance of `ErrorDialog` can be built
by passing in a list of `ErrorMsg`, and the result will be a window that list all of the errors being 
displayed to the user. When the user clicks the OK button, the dialog closes and the user can resume 
normal operation, or the user can leave the dialog open and use it as reference to fix all the errors 
in thier inputs.
