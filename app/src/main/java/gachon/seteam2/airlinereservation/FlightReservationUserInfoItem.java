package gachon.seteam2.airlinereservation;

public class FlightReservationUserInfoItem {
    private String seatClass;
    private String customerName;
    private String customerEmail;

    public void setSeatClass(String seatClass1)
    {
        seatClass = seatClass1;
    }
    public void setCustomerName(String customerName1) { customerName = customerName1; }
    public void setCustomerEmail(String customerEmail1) { customerEmail = customerEmail1; }
    public String getSeatClass() { return seatClass; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
}
