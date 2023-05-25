import pandas as pd
from matplotlib import pyplot as plt
import os


def runSimulationAndExportGraph(outputName, variableList, title, position):
    df = pd.DataFrame()
    for variable in variableList:
        default = ["true", "95", "8", "5", "50", "0"]
        default[position] = variable
        os.system('java Main {} {} {} {} {} {}'.format(
            default[0], default[1], default[2], default[3], default[4], default[5]))
        print('java Main {} {} {} {} {} {}'.format(
            default[0], default[1], default[2], default[3], default[4], default[5]))
        columns = ['Muscle Mass']
        dfTemp = pd.read_csv("output.csv", usecols=columns)
        dfTemp.columns = [variable]
        df = pd.concat([df, dfTemp], axis=1)
    print(df.head())
    df.plot(title=title)
    plt.xlabel('Ticks')
    plt.ylabel('Muscle Mass')
    plt.savefig("output" + "_" + outputName + ".png")


isLiftList = ["true", "false"]
intensityList = ["50", "60", "70", "90", "100"]
hoursOfSleepList = ["0", "4", "8", "12"]
daysBetweenWorkoutList = ["1", "7", "14", "21", "30"]
percentageSlowTwitchList = ["60", "70", "90", "100"]
varianceList = ["0", "0.1", "0.3", "0.5"]
os.system('javac Main.java')
runSimulationAndExportGraph(
    "isLift", isLiftList, "Lifting & Muscle Growth", 0)
runSimulationAndExportGraph(
    "intensity", intensityList, "Training intensity & Muscle Growth", 1)
runSimulationAndExportGraph(
    "hoursOfSleep", hoursOfSleepList, "Daily hours of sleep & Muscle Growth", 2)
runSimulationAndExportGraph(
    "daysBetweenWorkout", daysBetweenWorkoutList, "Days of rest & Muscle Growth", 3)
runSimulationAndExportGraph("percentageSlowTwitch",
                            percentageSlowTwitchList, "Slow twitch muscle ratio & Muscle Growth", 4)
runSimulationAndExportGraph(
    "variance", varianceList, "Day life variance & Muscle Growth", 5)
