/**
 * Created by regulus on 5/9/17.
 */
public class GridDateCount {

    //the number of the grid
    private int gridNum;

    //the date of crime
    private String date;

    //the amount of crimes in this grid this day
    private long amount;

    public GridDateCount(int num, String date)
    {
        this.gridNum = num;
        this.date = date;
        amount = 0;
    }

    public GridDateCount(int num, String date, long amount)
    {
        this.gridNum= num;
        this.date = date;
        this.amount = amount;
    }


    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getGridNum() {
        return gridNum;
    }

    public void setGridNum(int gridNum) {
        this.gridNum = gridNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
