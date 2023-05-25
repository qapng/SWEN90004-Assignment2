class Inputs {

    // isLift represent whether the person does weight training
    static boolean IS_LIFT = true;
    /*
     * INTENSITY represent the intensity of the training session, this effects how
     * many muscle fiber
     * will be fatigued after each training session
     */
    static double INTENSITY = 95; // 0-100
    // HOURS_OF_SLEEP represent the number of hours of sleep the person gets each
    // day
    static double HOURS_OF_SLEEP = 8; // 0-12
    // DAYS_BETWEEN_WORKOUTS represent the number of days between each workout
    static int DAYS_BETWEEN_WORKOUTS = 5; // 0-30
    // PERCENTAGE_SLOW_TWITCH_MUSCLE represent the percentage of slow twitch muscle
    static double PERCENTAGE_SLOW_TWITCH_MUSCLE = 50; // 0-100
    // VARIANCE represent how much the person deviates from sleep and workout
    // schedule.
    static double VARIANCE = 0; // 0-0.5

    static void initialiseInputs(String[] args) {
        if (args.length != 6 && args.length != 0) {
            System.out.println("Invalid number of arguments");
            System.exit(0);
        }
        if (args.length == 6) {

            IS_LIFT = Boolean.parseBoolean(args[0]);
            INTENSITY = Double.parseDouble(args[1]);
            HOURS_OF_SLEEP = Double.parseDouble(args[2]);
            DAYS_BETWEEN_WORKOUTS = Integer.parseInt(args[3]);
            PERCENTAGE_SLOW_TWITCH_MUSCLE = Double.parseDouble(args[4]);
            VARIANCE = Double.parseDouble(args[5]);

        }
        System.out.println(IS_LIFT);
        System.out.println(INTENSITY);
        System.out.println(HOURS_OF_SLEEP);
        System.out.println(DAYS_BETWEEN_WORKOUTS);
        System.out.println(PERCENTAGE_SLOW_TWITCH_MUSCLE);
        System.out.println(VARIANCE);
    }

}
