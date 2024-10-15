import java.text.DecimalFormat;

public class Technician extends Provider {
    private int ratePerVisit;

    // Constructor
    public Technician(Profile profile, Location location, int ratePerVisit) {
        super(profile, location);
        this.ratePerVisit = ratePerVisit;

    }
    public String reformatRate(int rate){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedNumber = decimalFormat.format(rate);
        return String.format("$%s", formattedNumber);
    }
    // Calculate the charge for a visit with this technician
    @Override
    public int rate() {
        return ratePerVisit;
    }

    @Override
    public String toString() {
        return String.format("%s[rate: %s]",
                super.toString(),
                reformatRate(ratePerVisit));
    }
}
