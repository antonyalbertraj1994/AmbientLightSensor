#include <TimerOne.h>
int buffer[10] = {0,0,0,0,0,0,0,0,0,0};
int i =0;
volatile unsigned long state = 0;
unsigned long state_copy=0;

int total = 0;
double time1 = 0, time2 = 0, elapsed = 0;
void setup() {
  // put your setup code here, to run once:
pinMode(2, INPUT_PULLUP);
pinMode(3, OUTPUT);
pinMode(13, OUTPUT);

attachInterrupt(digitalPinToInterrupt(2), blink, FALLING);
//Timer1.initialize(100000);

Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
// int val = digitalRead(2);
// Serial.println(val);
//delay(100);
// if(digitalRead(2) == LOW and state == 0) {
//   state = 1;
//   delay(50);
//   Timer1.attachInterrupt(timer1);

// }

// if(i == 10) {
//     for(int i =0;i<9; i++) {
//       Serial.print(buffer[i]);
//     }
//     Serial.println("");
//     i=0;
// }


unsigned long state2;

noInterrupts();
// state_copy = state;
state2 = state; 
interrupts();

if(state2 == 1 && state_copy == 0) {
  state_copy = 1;
}
//Serial.println(state_copy);

// while digitalRead(2)==

if(state_copy == 1) {
    time1 = millis();
    //Serial.println("state");
    state_copy = 2;
    //Serial.println("A");
}
unsigned long end = 50;
int count = 0;
while(state_copy == 2) {

  elapsed =  millis() - time1;
  //Serial.println(time1);
  //Serial.println(millis());
  //Serial.println(elapsed);
  //Serial.println(",");
  //Serial.println(digitalRead(2));

  if(elapsed > end && elapsed < end + 50) {
    if(digitalRead(2)){
      total += 1;
    }
  } else if(elapsed >= end + 50) {
    //Serial.println(total);
    if(total>500){
      buffer[count] = 1;
    }
    total = 0;
    count++;
    end = end + 50;
  }

  if (count == 9){
    for(int i = 0; i<9;i++){
      Serial.print(buffer[i]);
    }
    Serial.println("");
    state_copy = 3;
  }
}

}


void blink(void) {
  //Timer1.start();
  //digitalWrite(13, HIGH);
  state = 1;
  //Serial.println(time1);
//Timer1.attachInterrupt(timer1);

}