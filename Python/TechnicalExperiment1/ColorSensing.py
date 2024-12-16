import numpy as np


def process():
    green = [3365, 5707, 7363, 8378, 7704, 6738, 5623, 6326, 6159, 5143, 3921, 3396, 3037]
    red = [1844,    1869, 1819, 1160, 766.5,670, 576, 611, 611, 551, 602, 606, 551 ]
    print("len", len(red), len(green))
    value = [3261, 576] # green, red
    error_array = []
    for i in range(12):
        error = (green[i] - value[0])**2 +  (red[i] - value[1])**2
        error = np.sqrt(error)
        error_array.append(error)
    ph = np.argmin(error_array)
    print("ph", ph + 1)

    glucose_array = [11193, 8454, 7786, 7117, 6632, 4874, 4113]
    glucoseval = 4126
    error_array1 = []

    for i in range(7):
        error = np.abs(glucose_array[i] - glucoseval)
        #error = np.sqrt(error)
        print("errror", error)
        error_array1.append(error)

    glucose_out = np.argmin(error_array1)
    print("glucoseout", glucose_out)

if __name__ == '__main__':
    process()
    #find_correlation()