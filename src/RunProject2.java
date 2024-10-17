import clinic.ClinicManager;

public class RunProject2 {
    public static void main(String [] args) {
        try{
            new ClinicManager().run();}
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
