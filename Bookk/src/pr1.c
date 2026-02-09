const int ledPin = 12;
const int buzzerPin = 8;
const int unit = 200; // базова тривалість (крапка)

// ---------- setup ----------
void setup() {
  pinMode(ledPin, OUTPUT);
  pinMode(buzzerPin, OUTPUT);
}

// ---------- крапка ----------
void dot() {
  digitalWrite(ledPin, HIGH);
  tone(buzzerPin, 1000);
  delay(unit);

  digitalWrite(ledPin, LOW);
  noTone(buzzerPin);
  delay(unit);
}

// ---------- тире ----------
void dash() {
  digitalWrite(ledPin, HIGH);
  tone(buzzerPin, 1000);
  delay(unit * 3);

  digitalWrite(ledPin, LOW);
  noTone(buzzerPin);
  delay(unit);
}

// ---------- loop ----------
void loop() {

  // S (•••)
  dot();
  dot();
  dot();
  delay(unit * 3);

  // E (•)
  dot();
  delay(unit * 3);

  // M (——)
  dash();
  dash();
  delay(unit * 3);

  // E (•)
  dot();
  delay(unit * 3);

  // N (—•)
  dash();
  dot();

  // Пауза перед повтором слова
  delay(unit * 7);
}
