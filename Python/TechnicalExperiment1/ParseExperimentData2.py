import numpy as np
import seaborn as sns
import pandas as pd
from matplotlib import pyplot as plt
from scipy.optimize import curve_fit


def find_correlation(x, y):
    np.random.seed(100)

    #create array of 50 random integers between 0 and 10
    var1 = np.random.randint(0, 10, 50)

    #create a positively correlated array with some random noise
    var2 = var1 + 10  #+ np.random.normal(0, 10, 50)

    #calculate the correlation between the two arrays
    print("Coer", np.corrcoef(x, y))


def linear_model(x1, m, c):
    return m * x1 + c


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

def bh1750_lines(filename):
    f = open(filename, 'r')
    lines = f.readlines()
    x = []
    y = []
    for line in lines:
        values = line.strip('\n')
        values = values.split(",")
        print(values)
        voltage = int(values[0])
        lightsensor_value = float(values[1])
        x.append(lightsensor_value)
        y.append(voltage)
    #print("Sample count", len(x))
    x = np.asarray(x)
    y = np.asarray(y)
    return y, x

def process():
    #figure, axis = plt.subplots(2, 2)
    filename_red = "BH1750_log.txt"
    #filename_red_0 = "white_dac_zeroing_5k.txt"
    #filename_blue = "white_dac_zeroing_100.txt"
    # filename_blue_0 = "blue_response_pwm_zeroing.txt"
    # filename_yellow = "yellow_response_pwm.txt"
    # filename_yellow_0 = "yellow_response_pwm_zeroing.txt"
    # filename_white = "white_response_pwm.txt"
    # filename_white_0 = "white_response_pwm_zeroing.txt"
    #x_red, y_red = readlines(filename_red)
    #x_red_0, y_red_0 = readlines(filename_red_0)
    #x_blue, y_blue = readlines(filename_blue)

    # x_blue_0, y_blue_0 = readlines(filename_blue_0)
    # x_yellow, y_yellow = readlines(filename_yellow)
    # x_yellow_0, y_yellow_0 = readlines(filename_yellow_0)
    # x_white, y_white = readlines(filename_white)
    # x_white_0, y_white_0 = readlines(filename_white_0)
    filename_white = "white_dac_100.txt"
    filename_white_zeroing = "white_dac_zeroing_100.txt"
    x_white, y_white = readlines(filename_white)
    x_white_zeroing, y_white_zeroing = readlines(filename_white_zeroing)

    bh1750_filename = "BH1750_log.txt"
    x,y = bh1750_lines(bh1750_filename)
    res = 100
    plt.scatter(x/res, y, color='yellow')
    plt.scatter(x_white/res, y_white, color='red')
    plt.scatter(x_white_zeroing/res, y_white_zeroing, color='green')
    plt.xlabel("Current (mA)")
    plt.ylabel("Light Sensor Value (lux)")
    plt.title("Current vs Lux")
    plt.legend
    #plt.scatter(x_red_0,y_red_0, color='red')
    #plt.scatter(x_blue,y_blue, color='black')
    # axis[0, 0].scatter(y_red, x_red, label='RED', color='red', marker="+")
    # axis[0, 1].scatter(y_red_0, x_red_0, label='RED', color='red', marker=">")
    # axis[0, 0].scatter(y_blue, x_blue, label='Blue', color='blue')

    # axis[0, 0].scatter(y_red, x_red, label='RED', color='red', marker="+")
    # axis[0, 1].scatter(y_red_0, x_red_0, label='RED', color='red', marker=">")
    # axis[0, 0].scatter(y_blue, x_blue, label='Blue', color='blue')
    # axis[0, 1].scatter(y_blue_0, x_blue_0, label='Blue', color='blue')
    # axis[0, 0].scatter(y_yellow, x_yellow, label='Yellow', color='yellow')
    # axis[0, 1].scatter(y_yellow_0, x_yellow_0, label='Yellow', color='yellow')
    # axis[0, 0].scatter(y_white, x_white, label='White', color='gray')
    # axis[0, 1].scatter(y_white_0, x_white_0, label='White', color='gray')

    i = 0
    j = 0

    # coefficients = np.polyfit(x_white_0,y_white_0, 1)
    # slope, intercept = coefficients
    # y_fit = slope * x_white_0 + intercept
    # x_value = 320.5
    #
    # g = slope * x_value + intercept
    # print("G", g, slope, intercept)
    #
    # plt.plot(y_fit, x_white_0, label="Linear Fit", color="red")
    #
    # plt.plot(y_white_0, x_white_0, label='', color='blue')
    plt.legend(['BH1750', 'Watch ALS', 'Watch ALS with Pulsing'])
    #plt.xlabel('x')
    #plt.ylabel('y')
    #plt.title('Data Fit Example')
    #plt.show()

    # find_correlation(x, y)
    i = 0
    # while i < len(y_white_0) - 1:
    #     #print(x_white_0[i + 1] - x_white_0[i], y_white_0[i])
    #     i += 1
    plt.show()


if __name__ == '__main__':
    process()
    #find_correlation()
    print("Stared")
    data = pd.DataFrame({'A': [1, 2, 3], 'B': [4, 5, 6], 'C': [7, 8, 9]})
