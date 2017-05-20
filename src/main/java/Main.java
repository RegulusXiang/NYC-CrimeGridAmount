/**
 * Created by regulus on 5/9/17.
 */

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.functions;

import org.apache.spark.api.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class Main implements Serializable {

    public void parseRow(Row row, HashSet<String> dates) {
        //System.out.println("Call function: parseRow()");

        String[] strs = row.toString().split(",");


        String date = strs[1];
        //System.out.println(date);

        dates.add(date);
    }

    public static void main(String[] args) {
        GridReader gr = new GridReader("./data/grid_bounds.csv");
        gr.readGridFromFile();

        for (Grid g : gr.getGridList()) {
            g.printGrid();
        }

        SparkSession spark = SparkSession
                .builder()
                .appName("Crime Grid Counter")
                .master("local[*]")
                .config("spark.some.config.option", "some-value")
                .getOrCreate();

        Dataset<Row> crime = spark.read().csv("./data/NYPD_Complaint_Data_Historic.csv");
        crime.show();

        crime.createOrReplaceTempView("CRIME");

//        Dataset<Row> date = spark.sql("SELECT _c0, _c1 from CRIME");
//        date.createOrReplaceTempView("DATE");
//        date.show();

        //delete the unnecessary information in CRIME table
        crime = spark.sql("select _c0, _c1, _c21, _c22 from CRIME");
        crime.show();


//        Hashtable<Integer, Long> gridTable = new Hashtable<>();
//        //Initialize the hash table
//        for (int i = 0; i < 100; i++) {
//            gridTable.put(i, Long.MIN_VALUE);
//        }


        //override the for each function of the data frame
        /*crime.foreach((ForeachFunction<Row>) row ->
        {
            //System.out.println("Call function: parseRow()");

            String[] strs = row.toString().split(",");

            String d = strs[1];

            double latitude = 0;
            double longitude = 0;

            if (!strs[2].equals("null") && !strs[2].equals("Latitude")) {
                latitude = Double.parseDouble(strs[2]);
            }

            if (!strs[3].equals("null]") && !strs[3].equals("Longitude]")) {
                //delete the ']' in the tail
                longitude = Double.parseDouble(strs[3].substring(0, strs[3].length() - 1));
            }

            //determin which grid does this row of crime belongs to
            for (Grid g : gr.getGridList()) {
                if (latitude == 0 || longitude == 0) break;

//                System.out.println("Latitude: " + latitude);
//                System.out.println("Longitude: " + longitude);
//                System.out.println("Latitude South: " + g.getLatitudeSouth());
//                System.out.println("Latitude North: " + g.getLatitudeNorth());
//                System.out.println("Longitude East: " + g.getLongitudeEast());
//                System.out.println("Longitude West: " + g.getLongitudeWest());

                if (latitude <= g.getLatitudeSouth() && latitude >= g.getLatitudeNorth()) {
                    //System.out.println("Latitude in the grid!");

                    if (longitude >= g.getLongitudeWest() && longitude <= g.getLongitudeEast()) {
                        //System.out.println("Longitude in the grid!");
                        //System.out.println("Belongs to grid: " + g.index);
//                        gridTable.put(g.index, gridTable.get(g.index)+1);
//
//                        dates.add(d);
                        break;
                    }
                }
            }

        });
        */

//        System.out.println("Size of Dates: " + dates.size());
//
//        for(int i = 0;i < 100;i++)
//        {
//            System.out.println("Grid " + i + ": " + gridTable.get(i));
//        }

        //test joda time
        LocalDate start = new LocalDate(2015,1,1);
        LocalDate end = new LocalDate(2016,11,30);

        ArrayList<GridDateCount> gdc = new ArrayList<>();

        for(Grid g: gr.getGridList())
        {
            System.out.println("Grid: " + g.index);

            String latitudeSouth = Double.toString(g.getLatitudeSouth());
            String latitudeNorth = Double.toString(g.getLatitudeNorth());
            String longitudeEast = Double.toString(g.getLongitudeEast());
            String longitudeWest = Double.toString(g.getLongitudeWest());

            for(LocalDate i =  start;i.isBefore(end);i = i.plusDays(1))
            {
                String date = i.toString("MM/dd/yyyy");

                //count the number of crimes in this grid
                long count = spark.sql(
                        "select * from CRIME " +
                                "where _c21 >= " + latitudeNorth + " and  " +
                                "_c21 <= " + latitudeSouth + " and " +
                                "_c22 >= " + longitudeWest + " and " +
                                "_c22 <= " + longitudeEast + " and " +
                                "_c1 = " + date
                ).count();

                gdc.add(new GridDateCount(g.index,date,count));
            }

            //gridTable.put(g.index, count);

        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./output/GridCount.csv"))) {

            for(int i = 0;i < gdc.size();i++)
            {
                String s = Integer.toString(gdc.get(i).getGridNum());
                s = s.concat(",");
                s = s.concat(gdc.get(i).getDate());
                s = s.concat(",");
                s = s.concat(Long.toString(gdc.get(i).getAmount()));
                s = s.concat("\n");

                bw.write(s);
            }

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
