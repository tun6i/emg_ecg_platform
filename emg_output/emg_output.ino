#include <SoftwareSerial.h>
#include <FlexiTimer2.h>
#define INPUT_CH1 A0
#define INPUT_CH2 A1
#define INPUT_CH3 A2
#define INPUT_CH4 A3
#define INPUT_CH5 A4
#define INPUT_CH6 A5

/*
 * Bluetooth connection
 * RX = receive
 * tx = transmit
 */
const byte RX_PIN = 10; 
const byte TX_PIN = 11;
SoftwareSerial btSerial(RX_PIN, TX_PIN);

/*
 * Byte buffer package
 * buffer[0] CH1 hightByte
 * buffer[1] CH1 lowByte
 * buffer[2] CH2 highByte
 * buffer[3] CH2 lowByte
 * buffer[4] CH3 highByte
 * buffer[5] CH3 lowByte
 * buffer[6] CH4 highByte
 * buffer[7] CH4 lowByte
 * buffer[8] CH5 highByte
 * buffer[9] CH5 lowByte
 * buffer[10] CH6 highByte
 * buffer[11] CH6 lowByte
 */
byte buffer[2];

void readEMG () {
  int valueCH1 = analogRead(INPUT_CH1);
  int valueCH6 = analogRead(INPUT_CH6);
  
  buffer[0] = highByte(valueCH1);
  buffer[1] = lowByte(valueCH1);
  /*buffer[10] = highByte(valueCH6);
  buffer[11] = lowByte(valueCH6);*/
  
  btSerial.write(buffer, 2);
  //Serial.write(buffer, 2);
  Serial.println(valueCH1);
  
  //btSerial.flush();
}

void setup() {
  Serial.begin(57600);
  while (!Serial) {
    ;
  }
  btSerial.begin(57600);

  /*
   * Default EMG value = 1024/2 = 512
   */
  buffer[0] = 0x02; 
  buffer[1] = 0x00;
  /*buffer[2] = 0x02;
  buffer[3] = 0x00;
  buffer[4] = 0x02;
  buffer[5] = 0x00;
  buffer[6] = 0x02;
  buffer[7] = 0x00;
  buffer[8] = 0x02;
  buffer[9] = 0x00;
  buffer[10] = 0x02;
  buffer[11] = 0x00;*/

  FlexiTimer2::set(10, readEMG);
  FlexiTimer2::start();
}

void loop() {
}


