#include <TimerOne.h>
#include <IRremote.h>

const byte IRPin = 1; // LED output

int buffer[10] = {0,0,0,0,0,0,0,0,0,0};
int i =0;
volatile unsigned long state = 0;
unsigned long state_copy=0;
int delayms = 40;

int total = 0;
double time1 = 0, time2 = 0, elapsed = 0;
void setup() {
  // put your setup code here, to run once:
pinMode(3, INPUT);


IrSender.begin(IRPin);
//attachInterrupt(digitalPinToInterrupt(2), blink, FALLING);
//Serial.begin(9600);
analogReference(INTERNAL);

}

uint16_t sAddress = 0x0103;
uint8_t sCommand = 0b10101011;
uint16_t s16BitCommand = 0x5634;
uint8_t sRepeats = 0;

void loop() {
  int thresh = 200;
  //Serial.println(digitalRead(2));
  //Serial.println("A");
  //Serial.println(analogRead(A1));
  int pinselected = 3;
  while(true) {
    int g = analogRead(pinselected);
    if (g < thresh) {
      break;
    }
  }
  //Serial.println("gege");
  delay(delayms/2);
  //Serial.println(analogRead(A1));
  int r = analogRead(pinselected);
  if(r < thresh) {
    byte recevieddata  = 0x00;
    for(int i = 0; i <8; i++) {
      delay(delayms);
      int val;
      int f = analogRead(pinselected);
      //Serial.println(f);
      if(f > thresh) {
        val = 1;
      } else{
        val = 0;
      }
      //Serial.println(val);

      //int val = digitalRead(2);
      recevieddata |= (val << i);
      //buffer[i] = val;
    }
    delay(delayms);
    
    while(analogRead(pinselected) <= thresh) {
    }
    for(int i = 7; i >= 0; i--) {
      //Serial.print(bitRead(recevieddata, i));
    }
    //IrSender.sendNEC(recevieddata, 8);
    IrSender.sendSony(recevieddata & 0x1F, sCommand, sRepeats);

      //Serial.print(recevieddata,BIN);

      //Serial.println("");
  }
}
