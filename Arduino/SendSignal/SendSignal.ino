int value[] = {1,0,1,0,1,0,1,0};
int delayms = 5;
void setup() {
  // put your setup code here, to run once:
pinMode(2, OUTPUT);
pinMode(4, INPUT_PULLUP);
Serial.begin(9600);
}

void loop() {
   digitalWrite(2, HIGH);
  // delay(delayms);
  // digitalWrite(2, LOW);
  // delay(delayms);

  if(digitalRead(4) == LOW) {
    Serial.println("A");
    while (digitalRead(4) == LOW) {
    }
    digitalWrite(2, LOW);
    delay(delayms);
    for(int i = 0; i < 8; i++) {
      digitalWrite(2, value[7 - i]);
      delay(delayms);
    }
    digitalWrite(2, HIGH);
    delay(delayms);
  }

}
