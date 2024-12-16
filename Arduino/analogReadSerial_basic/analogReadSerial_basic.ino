void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  analogReference(INTERNAL);
}

void loop() {
  // put your main code here, to run repeatedly:
unsigned long time1 = millis();
int count = 0;
// while(millis()-time1<200) {
//   int val = analogRead(A1);
//   count++;
// // if(val>39){
// // Serial.println(255);
// // }
// // else{
// // Serial.println(0);
// // }
// //Serial.println(val);
// //delay(100);
// }
int val = analogRead(A1);
Serial.println(val);
delay(10);
//while(1);
}
