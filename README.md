com.powerdata.openpa
====================

The goal of the OpenPA API is to make model access simple and fast regardless of the back-end being used,
and provide some basic utilities to help operate on a model.  Our aim is to help application engineers:

* Focus development effort on core algorithm
* Access to real-world models during development
* Avoid dependencies with database, data formats, and proprietary systems
* Static and real-time data packaged in simple well-defined objects
* Consistent API promotes reduced integration effort
* reduced application footprint

A set of overview slides can be found at https://docs.google.com/presentation/d/1_DKizCW9ad2nULGHm8U6IQ3n_LDC1RXziFQ6xcX4F2I/edit?usp=sharing

Javadoc
-------
The [Javadoc](http://powerdata.github.io/com.powerdata.openpa) is not complete
in the sense that every method and attribute has a description,
but many of the descriptions are in place and
the class hierarchies are easier to traverse.

[http://powerdata.github.io/com.powerdata.openpa]


Status of this README
-----
This README file and JavaDoc both need work, but we will try to at least provide enough information to load a 
test model and work with the library.

Dependencies
------
* Java 8
* [Trove Collections](https://bitbucket.org/trove4j/trove)


Data source formats
------

OpenPA Models can be loaded from multiple sources.  The primary source we use is the PowerSimulator Model and Case format.

[Download PowerSimulator Model Format Specification](http://powerdata.github.io/com.powerdata.openpa/PowerSimulatorModelFormats.pdf)

[Download PowerSimulator Case Format Specification](http://powerdata.github.io/com.powerdata.openpa/PowerSimulatorCaseFormats.pdf)

A sample model is available:

[24-bus model](http://powerdata.github.io/com.powerdata.openpa/psmfmtmodels/24-bus.zip)


*PSS/e Files*

PSS/e files for version 30 can be converted to the PowerSimulator CSV format using com.powerdata.openpa.psseraw.Psse2PsmFmt


PAModel Usage
-----

A PAModel provides access to a transient view the power system network and equipment, which is both lightweight and fast.  

Implementations of the ModelBuilder interface are used to create models appropriate to application requirements
and data source.

The PFlowModelBuilder abstract class provides concrete ways to load models for power applications.
The *PFlowModelBuilder.Create()* static method provides a way to create a builder allowing the code to be free of 
any data back-end dependencies.

An example of loading PowerSimulator formatted CSV files:

```java
String uri = "psmfmt:dir=/path/to/unzipped/csv_files";
PAModel model = PflowModelBuilder.Create(uri).load();
```

The model object can now be used to access data both for reading and writing.  

A simple example can be found in the main() method of com.powerdata.openpa.pwrflow.TestModel.java

To run it:

1. build openpa.jar from the source
2. unzip the model data into a directory (/home/chris/cascadia in this example)
3. provide a directory for output files (leave blank to assume the current working dir)

java -cp openpa.jar com.powerdata.openpa.pwrflow.TestModel --uri psmfmt:dir=/home/chris/cascadia

This should output:

1. buses.txt - a dump of all the equipment on each connectivity bus
2. tnode.txt - a similar dump, but using a single-bus topology common for power apps.

Changes
------
2016-01-13.  Add 24-bus model, clean up links for PSIM formats

2015-04-08.  Remove OpenPA version 1 psse code.  The com.powerdata.openpa.psse\*.\* packages have been removed.  

TO-DO:
------
* Cleanup JavaDoc and Generate and add link
* Document applications and utilities (sparse matrix, power calculations, AC & DC power flows)
* add section on performance best practices
* Explain how to contribute

