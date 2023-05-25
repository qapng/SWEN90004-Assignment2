import pandas as pd
from matplotlib import pyplot as plt
import os


def runSimulationAndExportGraph(outputName, default, variableList, title):
    df = pd.DataFrame()
    for variable in variableList:
        os.system('java Main {} {} {} {} {} {}'.format(
            variable, default[1], default[2], default[3], default[4], default[5]))
        print('java Main {} {} {} {} {} {}'.format(
            variable, default[1], default[2], default[3], default[4], default[5]))
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
default = ["true", "95", "8", "5", "50", "0"]

runSimulationAndExportGraph(
    "isLift", default, isLiftList, "Lifting & Muscle Growth")
runSimulationAndExportGraph(
    "intensity", default, intensityList, "Training intensity & Muscle Growth")
runSimulationAndExportGraph(
    "hoursOfSleep", default, hoursOfSleepList, "Daily hours of sleep & Muscle Growth")
runSimulationAndExportGraph(
    "daysBetweenWorkout", default, daysBetweenWorkoutList, "Days of rest & Muscle Growth")
runSimulationAndExportGraph("percentageSlowTwitch",
                            default, percentageSlowTwitchList, "Slow twitch muscle ratio & Muscle Growth")
runSimulationAndExportGraph(
    "variance", default, varianceList, "Day life variance & Muscle Growth")
