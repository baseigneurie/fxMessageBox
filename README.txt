fxMessageBox

Author: Broc Seigneurie
Created: 10/31/2014



INSTRUCTIONS
------------
This is a message dialog box similar to the message box found in Visual basic. This is a developer tool designed to be used with the JavaFX framework.

To use the fxMessageBox, Simply add the library to your project and call an instance of the class with the following parameters:

MessageBox([String Title], [String Message], [String ButtonConfig(Optional)]);

All instances require atleast a title and message. If you do not include one of these you will receive an error. The title can be as long as you choose, but will be truncated once the title bar runs out of space. The message has a total of 360 characters, and will be truncated if the count is over the threshold.

ButtonConfig is optional, and if not provided the default will be selected. See below for config options:

String      --   Button Layout
------------------------------
"Ok"          -> [Ok]
"SaveCancel"  -> [Save][Cancel]
"YesNoCancel" -> [Yes][No][Cancel]
Default       -> [Ok][Cancel]


To display the message box, simply call "MessageBox.Show();". This will return a boolean value, which can be used as a prompt from users. If the user selects Yes, Save, or Ok, a result of true will be returned. If a user selects No or Cancel, a result of false will be returned. Once a button is clicked, the message box window is closed.



REQUIREMENTS
------------
Java Runtime Enviroment 8






