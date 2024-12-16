import numpy as np
import seaborn as sns
import pandas as pd
from matplotlib import pyplot, pyplot as plt

def readlines(filename):
    f = open(filename, 'r')
    lines = f.readlines()
    x = []
    y = []
    for line in lines:
        values = line.split(",")
        voltage = int(values[1])
        lightsensor_value = int(values[3])
        current = voltage / 220000.0
        if (lightsensor_value < 70000):
            x.append(lightsensor_value)
            y.append(voltage)
    #print("Sample count", len(x))
    x = np.asarray(x)
    y = np.asarray(y)
    return y, x


def process():
    f = open("scan_lightsensor.txt", "r")
    lines = f.readlines()
    x_start = 50
    y_start = 10
    x_end = 80
    y_end = 50
    stepsize = 1

    size_x = int((x_end - x_start) / stepsize)
    size_y = int((y_end - y_start) / stepsize)

    array = np.zeros((size_x, size_y))

    # for i in range(0,50):
    #     for j in range(0,50):
    #         array[i,j] = np.random.randint(0,10)

    for line in lines:
        values = line.split(",")
        x = int(values[0])
        y = int(values[1])
        lightvalue = int(values[5])
        array[x - x_start,y - y_start] = lightvalue

    sns.heatmap(array)
    plt.show()

if __name__ == '__main__':
    process()
    print("Stared")
    data = pd.DataFrame({'A': [1, 2, 3], 'B': [4, 5, 6], 'C': [7, 8, 9]})


