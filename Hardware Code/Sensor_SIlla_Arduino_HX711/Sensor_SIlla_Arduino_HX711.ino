//Sensor_Silla_Arduino_HX711.ino
#include <HX711_ADC.h>
#include <SoftwareSerial.h>

HX711_ADC LoadCell(7,8); //Se establece los pines que recibirán la lectura del HX711.
SoftwareSerial BT1(3,2); //Se establece la conexión serial entre el Arduino y el ESP8266.
int hxreading; //Se define la variable que contendrá la lectura del sensor HX711.
boolean ocupado = false; //Se define una variable booleana para definir el estado actual de la silla, al iniciar estará en false.
int normal = 10; //Se define el valor por default que el sensor va a presentar a una carga igual a cero.

void setup() {
  float calValue = 1120.48; //Factor de calibración del HX711.
  Serial.begin(115200);
  BT1.begin(115200); //Se abre el puerto serial a 115200 baudios.
  LoadCell.begin();
  long stabilisingtime = 2000; // tare preciscion can be improved by adding a few seconds of stabilising time.
  LoadCell.start(stabilisingtime);
  if(LoadCell.getTareTimeoutFlag()) {
    Serial.println("Tare timeout, check MCU>HX711 wiring and pin designations");
  }
  else {
    LoadCell.setCalFactor(calValue); // set calibration value (float).
    Serial.println("Startup + tare is complete");
  }
}

void loop() {
  LoadCell.update();
  hxreading = LoadCell.getData(); //Se toma la lectura actual del sensor.
  Serial.print("Load_cell output val: ");
  Serial.println(hxreading);
  if(hxreading>normal && ocupado==false){ //Si la lectura actual sobrepasa el límite normal y el asiento esta desocupado(false) entra al bucle.
    Serial.println("- Asiento ocupado");
    Serial.println(hxreading);
    ocupado = true; //El estado del asiento cambia  a ocupado (true).
    BT1.print("1\n"); //Se envia el valor de 1 al ESP por medio de la conexión serial.
    Serial.flush();
  } 
  else if(hxreading<=normal && ocupado==true){ //Si la lectura actual es menor al límite normal y el asiento esta ocupado(true) entra al bucle.
    Serial.println("- Asiento desocupado");
    Serial.println(hxreading);
    ocupado = false; //El estado del asiento cambia  a desocupado (false).
    BT1.print("0\n"); //Se envia el valor de 1 al ESP por medio de la conexión serial.
    Serial.flush();
  }
  delay(1000);
}
