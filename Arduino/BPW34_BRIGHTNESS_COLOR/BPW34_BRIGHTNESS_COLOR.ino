void setup() {
  // put your setup code here, to run once:
Serial.begin(115200);
pinMode(A0, INPUT);
analogReference(INTERNAL);
}

void loop() {
  // put your main code here, to run repeatedly:
    if(Serial.available() > 0) {
        String value2  = Serial.readStringUntil('\n');
        int analogValue = analogRead(A0);
        Serial.println(analogValue);
    }

}
