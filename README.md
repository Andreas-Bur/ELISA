# ELISA (Ein Leicht Intelligenter SprachAssistent)

ELISA is a german virtual assistant that I programmed in Java for my matura thesis. 
It's use is to control some basic functions of a windows based computer using voice commands. 
Furthermore ELISA can control specific functions of the Microsoft Office applications "Word", "Excel" and "PowerPoint".
For a comprehensive list of all possible commands please see the file "Befehle.html" (german).

The following libraries are being used:

* "CMU Sphinx" for the speech recognition
* "Java Native Access" (JNA) for some low-level communication between ELISA and the operating system as well as the Microsoft Office programs.
* Apache commons math libraries (for Sphinx)

Requirements for ELISA to work:

* Windows 7 or higher
* Some kind of microphone (eg. a headset)
* Microsoft Office programs "Excel", "Word", and "PowerPoint"

Functionality:

* A spoken voice command is recognized by the speech recognition using a custom dictionary and grammar.
* The parser interprets the recognized text and filters out relevant information.
* The given command is executed.
* Feedback is given to the user in different ways to inform them about the outcome of the command.
