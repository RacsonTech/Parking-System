

#ifndef _LED_MATRIX_H    /* Guard against multiple inclusion */
#define _LED_MATRIX_H


#define BIT0    0x0001  // 0001
#define BIT1    0x0002  // 0010
#define BIT2    0x0004  // 0100
#define BIT3    0x0008  // 1000

/* Constants use to easily remember the index of the font 2D array. For example,
 * it is easier to remember this: display(123, ARROW_UP) than display(123, 11)
*/
#define BLANK       10
#define ARROW_UP    11
#define ARROW_DOWN  12
#define ARROW_RIGHT 13
#define ARROW_LEFT  14


// Function prototypes
void inputRowAddress(uint8_t addr);
void enableLedControllers();
void disableLedControllers();
void latch();
void toggleLeds();
void delay(int number_of_seconds);
void clockPulse();
void clearLeds();
void fillRowWithZeros();
void clearDisplay();
void drawNum(uint8_t num, uint32_t i);
void display(uint16_t num, uint16_t direction);
void MCU_ini();



    /* Provide C++ Compatibility */
#ifdef __cplusplus
}
#endif

#endif /* _EXAMPLE_FILE_NAME_H */

/* *****************************************************************************
 End of File
 */
