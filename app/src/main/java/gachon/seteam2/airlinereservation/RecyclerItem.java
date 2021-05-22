package gachon.seteam2.airlinereservation;

public class RecyclerItem {
    private String airplane;
    private String odate;
    private String destination;
    private String source;

    public void setairplane(String airplane1)
    {
        airplane = airplane1;
    }
    public void setodate(String odate1)
    {
        odate = odate1;
    }
    public void setdestination(String destination1)
    {
        destination = destination1;
    }
    public void setsource(String source1)
    {
        source = source1;
    }
    public String getodate()
    {
        return odate;
    }
    public String getdestination()
    {
        return destination;
    }
    public String getsource()
    {
        return source;
    }
    public String getairplane()
    {
        return airplane;
    }
}
