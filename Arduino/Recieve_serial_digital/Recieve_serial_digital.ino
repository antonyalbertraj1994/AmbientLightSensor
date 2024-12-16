#include <TimerOne.h>
int buffer[10] = {0,0,0,0,0,0,0,0,0,0};
int i =0;
volatile unsigned long state = 0;
unsigned long state_copy=0;
int delayms = 10;

int total = 0;
double time1 = 0, time2 = 0, elapsed = 0;
void setup() {
  // put your setup code here, to run once:
pinMode(2, INPUT);
pinMode(3, OUTPUT);
pinMode(13, OUTPUT);
pinMode(A1, INPUT);
pinMode(A2, INPUT);

//attachInterrupt(digitalPinToInterrupt(2), blink, FALLING);
Serial.begin(9600);
analogReference(INTERNAL);

}

void loop() {
  int thresh = 275;
  //Serial.println(digitalRead(2));
  //Serial.println("A");
  //Serial.println(analogRead(A1));

  while(digitalRead(2) == 1) {
  }

  delayMicroseconds(delayms/2);
  //Serial.println(analogRead(A1));
  int r = digitalRead(2);
  if(r  == 0) {
    byte recevieddata  = 0x00;
    for(int i = 0; i <8; i++) {
      delayMicroseconds(delayms);
      int val;
      val = digitalRead(2);

      //int val = digitalRead(2);
      recevieddata |= (val << i);
      //buffer[i] = val;
    }
    delayMicroseconds(delayms);
    while(digitalRead(2) == 0) {
    }

    for(int i = 7; i >= 0; i--) {
      Serial.print(bitRead(recevieddata, i));
    }
      Serial.println("");
  }
}

