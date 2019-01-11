# ELISA (Ein Leicht Intelligenter SprachAssistent)

ELISA ist Sprachassistent, den ich zwecks meiner Maturarbeit in Java für Computer mit dem Betriebssystem Windows entwickelt habe.
Damit können mit deutschen Sprachbefehlen einige simple Windows-Funktionen, sowie spezifische Funktionen der Microsoft Office Programmen "Word", "Excel" und "PowerPoint" gesteuert werden.
Eine Liste mit allen Befehlen befindet sich in der Datei "Befehle.html".

Die folgenden Bibliotheken werden verwendet:

* "CMU Sphinx" für die Spracherkennung
* "Java Native Access" (JNA) für systemnahe Kommunikation zwischen Java und Windows sowie den Microsoft Office Programmen.
* "Apache commons" Mathematik-Bibliotheken für Sphinx

Voraussetzungen:

* Windows 7 oder höher
* Ein Mikrofon
* Die Microsoft Office Programme "Word", "Excel" und "PowerPoint"

Funktionsweise:

* Ein Sprachbefehl wird von der Spracherkennung mit Hilfe eines angepassten Wörterbuches und Grammatik-Datei in Text umgewandelt.
* Der Parser untersucht den Text auf seine Bedeutung und sammelt daraus wichtige Information.
* Je nach erhaltener Information wird der entsprechende Befehl ausgeführt.
* Dem Nutzer wird auf verschiedene Weisen Feedback über die Befehlsausführung gegeben.

-------------------------------------------------------------------------------------------------------------------


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
* Feedback is given to the user in various ways to inform them about the outcome of the command.
