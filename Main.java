import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Inputs.initialiseInputs(args);

        Driver driver = new Driver();
        driver.drive();
    }
}
