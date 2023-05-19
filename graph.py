import pandas as pd
from matplotlib import pyplot as plt

# Set the figure size
plt.rcParams["figure.figsize"] = [7.00, 3.50]
plt.rcParams["figure.autolayout"] = True

# Make a list of columns
columns = ['Anabolic', 'Catabolic', 'Muscle Mass']

# Read a CSV file
df = pd.read_csv("output.csv", usecols=columns)

print(df.head())
# Plot the lines
df.plot()

plt.show()
