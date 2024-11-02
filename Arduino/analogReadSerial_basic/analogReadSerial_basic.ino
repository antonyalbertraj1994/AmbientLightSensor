void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  analogReference(INTERNAL);
}

void loop() {
  // put your main code here, to run repeatedly:
int val = analogRead(A1);
// if(val>39){
// Serial.println(255);
// }
// else{
// Serial.println(0);
// }
Serial.println(val);
delay(1000);
}
