import time

import serial
ser = serial.Serial(port='COM14', baudrate='9600', timeout=5)




def start_logging():
    filename_white = "BH1750_log.txt"
    file = open(filename_white, 'w')
    led_value = 0
    step = 10
    while (ser.is_open == False):
        pass

    while led_value <= 1000:
        send_string = str(led_value) + "\n"
        ser.write(send_string.encode('utf-8'))
        #ser.flush()
        read_string = ser.readline()
        read_string = read_string.decode('utf-8')
        #read_string = "DFgd"
        #print("Light Value", read_string)
        if read_string:
            write_string = str(led_value) + "," + str(read_string.rstrip()) + "\n"
            print(write_string)
            file.write(write_string)
            led_value = led_value + step

        time.sleep(0.5)
    file.close()







if __name__ == '__main__':
    start_logging()

