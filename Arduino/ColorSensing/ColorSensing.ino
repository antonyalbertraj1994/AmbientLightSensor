/*
    This sketch demonstrates how we can output a value in both channels of MCP4822 or MCP4812 or MCP4802.
*/

#include <MCP48xx.h>

// Define the MCP4822 instance, giving it the SS (Slave Select) pin
// The constructor will also initialize the SPI library
// We can also define a MCP4812 or MCP4802
MCP4822 dac(10);

// We define an int variable to store the voltage in mV so 100mV = 0.1V
int voltage = 100;

void setup() {
    // We call the init() method to initialize the instance
    Serial.begin(115200);
    dac.init();

    // The channels are turned off at startup so we need to turn the channel we need on
    dac.turnOnChannelA();
    dac.turnOnChannelB();
    pinMode(3, OUTPUT);
    // We configure the channels in High gain
    // It is also the default value so it is not really needed
    dac.setGainA(MCP4822::High);
    dac.setGainB(MCP4822::High);
    analogReference(DEFAULT);
}

// We loop from 100mV to 2000mV for channel A and 4000mV for channel B
void loop() {
//    if(Serial.available() > 0) {
//      String value1 = Serial.readStringUntil('\n');
//      int value2 = value1.toInt();
//      //Serial.println(value1);
//      dac.setVoltageA(2000);
//      dac.updateDAC();
//      delay(100);
//      dac.setVoltageA(value2);
//      dac.updateDAC();
//    }
    digitalWrite(3, HIGH);
    delay(400);
    //digitalWrite(3, LOW);
    //delay(200);
   


}
