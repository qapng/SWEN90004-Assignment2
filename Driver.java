import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    public int tick;
    public Hormones hormones;
    public double total_muscle_mass;
    public Recorder recorder;
    public ArrayList<Muscle> muscles;

    public Driver() {
        tick = 0;
        total_muscle_mass = 0;
        hormones = new Hormones();
        recorder = new Recorder();
        muscles = new ArrayList<Muscle>();
    }

    public void drive() {
        recorder.clear_file_content(Params.OUTPUT_FILE_NAME);
        String[] headers = { "Ticks", "Anabolic", "Catabolic", "Muscle Mass" };
        try {
            recorder.output_data_to_csv(Params.OUTPUT_FILE_NAME, headers);
            recorder.output_data_to_csv(Params.OUTPUT_FILE_NAME, headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialise_muscles();
        update_total_muscle_mass();
        System.out.println(total_muscle_mass);
    }

    public void update_total_muscle_mass() {
        int sum = 0;
        for (Muscle muscle : muscles) {
            sum += muscle.get_fiber_size();
        }
        total_muscle_mass = sum;
    }

    public void initialise_muscles() {
        for (int i = 0; i < Params.NUM_MUSCLE; i++) {
            muscles.add(new Muscle());
        }
    }
}
