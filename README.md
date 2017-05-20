NYC-CrimeGridAmount

Count the amount of crimes in NYC in specific geo-grid and date - based on Apache Spark.

Introduction

This is part of the code of our project Time Series Prediction with Twitter: A Case Study of Crime in New York City.

The function of this program is to read in the information of geo-grids in NYC from file ./data/grid_bounds.csv and crime complaints information from NYC open data stored in file ./data/NYPD_Complaint_Data_Historic.csv, then output the amounts of crimes in each grid of the city in specific dates (in file ./output/GridCount.csv).

The output format:

  GRID_NUM	Date      	Amount
  26      	03/18/2016	5     
  78      	11/07/2015	23    



Compile & Run

To compile the project, you need

1. Apache Spark (version 2.10+, with Spark SQL, MLlib libraries);
2. Maven, for auto-detecting the dependencies of the Java project;
3. Java SE 7+ JRE;
4. Joda-Time Library, whose dependencies will be solved by Maven;
5. Make sure there are input csv files under the data folder;
6. Make sure there is a output folder under the root folder of the repository;

After all dependencies are set, to compile, just type in the terminal:

    javac Main.java



To run the project, go to the target folder and type in the terminal:

    java Main



Wish you good luck!
