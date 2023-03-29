## Builder Pattern in the UI

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
