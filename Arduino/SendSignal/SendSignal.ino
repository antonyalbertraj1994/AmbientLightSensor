int value[] = {1,1,0,0,1,1,1,1};
int delayms = 50;
void setup() {
  // put your setup code here, to run once:
pinMode(2, OUTPUT);
pinMode(4, INPUT_PULLUP);
Serial.begin(9600);
}

void loop() {
  digitalWrite(2, HIGH);
  // put your main code here, to run repeatedly:
  if(digitalRead(4) == LOW) {
    //Serial.println("A");
    while (digitalRead(4) == LOW) {

    }
    digitalWrite(2, LOW);
    delayMicroseconds(delayms);
    for(int i = 0; i < 8; i++) {
      digitalWrite(2, value[7 - i]);
      delayMicroseconds(delayms);
    }
  }

}
