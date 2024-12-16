/*
 *  PinDefinitionsAndMore.h
 *
 *  Contains pin definitions for IRremote examples for various platforms
 *  as well as definitions for feedback LED and tone() and includes
 *
 *  Copyright (C) 2021-2023  Armin Joachimsmeyer
 *  armin.joachimsmeyer@gmail.com
 *
 *  This file is part of IRremote https://github.com/Arduino-IRremote/Arduino-IRremote.
 *
 *  Arduino-IRremote is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/gpl.html>.
 *
 */

/*
 * Pin mapping table for different platforms
 *
 * Platform     IR input    IR output   Tone      Core/Pin schema
 * --------------------------------------------------------------
 * DEFAULT/AVR  2           3           4         Arduino
 * ATtinyX5     0|PB0       4|PB4       3|PB3     ATTinyCore
 * ATtiny167    3|PA3       2|PA2       7|PA7     ATTinyCore
 * ATtiny167    9|PA3       8|PA2       5|PA7     Digispark original core
 * ATtiny84      |PB2        |PA4        |PA3     ATTinyCore
 * ATtiny88     3|PD3       4|PD4       9|PB1     ATTinyCore
 * ATtiny3217  18|PA1      19|PA2      20|PA3     MegaTinyCore
 * ATtiny1604   2           3|PA5       %
 * ATtiny816   14|PA1      16|PA3       1|PA5     MegaTinyCore
 * ATtiny1614   8|PA1      10|PA3       1|PA5     MegaTinyCore
 * SAMD21       3           4           5
 * ESP8266      14|D5       12|D6       %
 * ESP32        15          4          27
 * ESP32-C3     2           3           4
 * BluePill     PA6         PA7       PA3
 * APOLLO3      11          12          5
 * RP2040       3|GPIO15    4|GPIO16    5|GPIO17
 */
//#define _IR_MEASURE_TIMING // For debugging purposes.



// For use with ATTinyCore
#define IR_RECEIVE_PIN  PIN_PA3 // On Digispark board labeled as pin 9 - INT0 is connected to USB+ on DigisparkPro boards
#define IR_SEND_PIN     PIN_PA2 // On Digispark board labeled as pin 8
#define TONE_PIN        PIN_PA7 // On Digispark board labeled as pin 5




#include <Arduino.h>

// tone() is included in ESP32 core since 2.0.2
#if !defined(ESP_ARDUINO_VERSION_VAL)
#define ESP_ARDUINO_VERSION_VAL(major, minor, patch) 12345678
#endif
#if ESP_ARDUINO_VERSION  <= ESP_ARDUINO_VERSION_VAL(2, 0, 2)
#define TONE_LEDC_CHANNEL        1  // Using channel 1 makes tone() independent of receiving timer -> No need to stop receiving timer.



#define tone(...) void()    // no tone() available
#define noTone(a) void()
#define TONE_PIN           42 // Dummy for examples using it

#else
#warning Board / CPU is not detected using pre-processor symbols -> using default values, which may not fit. Please extend PinDefinitionsAndMore.h.
// Default valued for unidentified boards
#define IR_RECEIVE_PIN      2
#define IR_SEND_PIN         3
#define TONE_PIN            4
#define APPLICATION_PIN     5
#define ALTERNATIVE_IR_FEEDBACK_LED_PIN 6 // E.g. used for examples which use LED_BUILDIN for example output.
#define _IR_TIMING_TEST_PIN 7
#endif // defined(ESP8266)

#if defined(ESP32) || defined(ARDUINO_ARCH_RP2040) || defined(PARTICLE) || defined(ARDUINO_ARCH_MBED)
#define SEND_PWM_BY_TIMER // We do not have pin restrictions for this CPU's, so lets use the hardware PWM for send carrier signal generation
#else
# if defined(SEND_PWM_BY_TIMER)
#undef IR_SEND_PIN // SendPin is determined by timer! This avoids warnings in IRremote.hpp and IRTimer.hpp
#  endif
#endif

#if !defined (FLASHEND)
#define FLASHEND 0xFFFF // Dummy value for platforms where FLASHEND is not defined
#endif

/*
 * Helper macro for getting a macro definition as string
 */
#if !defined(STR_HELPER)
#define STR_HELPER(x) #x
#define STR(x) STR_HELPER(x)
#endif
