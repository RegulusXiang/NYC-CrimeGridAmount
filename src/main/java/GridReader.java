import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;

//class for reading the gird information
class GridReader implements Serializable
{
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //the path to the input file
    private String url;

    public ArrayList<Grid> getGridList() {
        return gridList;
    }

    public void setGridList(ArrayList<Grid> gridList) {
        this.gridList = gridList;
    }

    private ArrayList<Grid> gridList;

    public GridReader(String url)
    {
        this.url = url;
        gridList = new ArrayList<>();
    }

    private void parseLine(String line)
    {
        String[] strs = line.split(",");
        int num = Integer.parseInt(strs[0]);
        double aSouth = Double.parseDouble(strs[1]);
        double aNorth = Double.parseDouble(strs[2]);
        double lWest = Double.parseDouble(strs[3]);
        double lEast = Double.parseDouble(strs[4]);

        Grid g = new Grid(num, aSouth, aNorth, lWest, lEast);
        gridList.add(g);
    }

    public void readGridFromFile()
    {
        int num = 0;

        File file = new File(url);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.

                //avoid the first line
                if(num != 0)
                    parseLine(line);

                num++;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}