com.powerdata.openpa
====================
Java classes sub-module for Open Source Power Apps and Utilities

The goal of the API is to make model access simple and fast regardless of the back-end being used.

For details please check the [Javadoc](http://powerdata.github.io/com.powerdata.openpa) Reference.
Usage
-----
To open a model use the *PsseModel.OpenInput()* function.  The parameter to this function is a URI that
describes the back-end storage where the data will come from.  For example:
```java
PsseModel eq = PsseModel.OpenInput("pd2cim:db=/tmp/wecc.pddb&inputctx=Ots");
```
This opens the model using the PD2 CIM database named */tmp/wecc.pddb*.

To use a PSSE file named */tmp/WECC.raw* directly:
```java
PsseModel eq = PsseModel.OpenInput("psseraw:file=/tmp/WECC.raw");
```
or to use a directory of CSV files named */tmp/testdata/db*:
```java
PsseModel eq = PsseModel.OpenInput("pssecsv:path=/tmp/testdata/db");
```
The main point is that the code doesn't need to change, only the URI string.

Once we have the *PsseModel* object, *eq* in our examples, we can access lists of equipment.  For example,
to examine every line in the model you could use:
```java
for(Line line : eq.getLines())
{
    // the variable line will be set to the next line in the list
   ... do some work ...
    // at the end of the loop, when I hit the bracket '}' the line variable
    // becomes the next line and control goes back to the top of the loop.
}
```
Some equipment also has it's own lists.  For example, from the Line object we can access the fromBus()
and from the fromBus() we can find all of the breakers needed to isolate the bus (which of course would
isolate the line as well):
```java
Bus frombus = line.getFromBus();
SwitchList switches = frombus.isolate();
for(Switch s : switches)
{
    s.setState(SwitchState.Open);
}
```
By now you may have noticed that the model representation is based on PSS/e but is not exactly PSS/e.  We
have included Switches as an extension to allow for more accurate representation of a poweer system.
