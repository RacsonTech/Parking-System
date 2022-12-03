/* 
 * File:   newmain.c
 * Author: vinnieg
 *
 * Created on October 10, 2022, 3:39 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <xc.h>

#define BIT0    0x0001
#define BIT1    0x0002
#define BIT2    0x0004
#define BIT3    0x0008

// Function prototypes
void inputRowAddress(uint16_t addr);

/*
 * 
 */
int main(int argc, char** argv) {

    uint32_t *led_ptr;
    volatile uint32_t i = 0;
    volatile uint32_t delay;
    volatile uint32_t time_delay;
    volatile uint16_t j;

    
    // disable watchdog timer
//    WDT_REGS->WDT_MR

//    led_ptr = (uint32_t *)0x400E1470;
////    0x400E1470
//    *led_ptr = 0x8;

    // Is this not necessary?
//    PIOA_REGS->PIO_WPMR &= ~0x1;    // clear bit 0 of write protection (allows write to pio en)

//    PIOA_REGS->PIO_PER |= PIN_PA3;  // enable desired pin
//    PIOA_REGS->PIO_OER |= PIN_PA3;  // output enable desired pin
//    PIOA_REGS->PIO_OWER |= PIN_PA3; // output write enable desired pin
    
//    PIOA_REGS->PIO_OWDR |= ~0x0; // output write disable desired pin

    // pins to enable
    // CLK, STB (driver latch), OE (output enable)
    // demux A, B, C, and ?D
    // R0, R1
    
    // R0 -> PA3
    // R1 -> PA7
    // B0 -> PA5
    // B1 -> PA9
    // G0 -> PA4
    // G1 -> PA8
    // Demux A -> PA10
    // Demux B -> PA11
    // Demux C -> PA12
    // Demux D -> PA24
    // CLK -> PA13
    // Latch -> PA14
    // OE -> PA21
    
//    PIOA_REGS->PIO_PER |= PIO_PA3;  // enable desired pin
//    PIOA_REGS->PIO_OER |= PIO_PA3;  // output enable desired pin
//    PIOA_REGS->PIO_OWER |= PIO_PA3; // output write enable desired pin
//    
//    // clock enable
//    PIOA_REGS->PIO_IFSCER |= PIO_PA7; // enable slow clock register
//    PIOA_REGS->PIO_IFSCER |= PIO_PA7; // enable slow clock register
//    PIOA_REGS->PIO_ |= PIO_PA7; // enable slow clock register
//    PMC_REGS->PMC_PCER0 |= PIO_PID10;   // 
//    
//    PIOA_REGS->PIO_PER |= PIO_PA7;  // enable desired pin
//    PIOA_REGS->PIO_OER |= PIO_PA7;  // output enable desired pin
//    PIOA_REGS->PIO_OWER |= PIO_PA7; // output write enable desired pin
//    
//    PIOA_REGS->PIO_PER |= PIO_PA10;  // enable desired pin
//    PIOA_REGS->PIO_OER |= PIO_PA10;  // output enable desired pin
//    PIOA_REGS->PIO_OWER |= PIO_PA10; // output write enable desired pin
//    
//    PIOA_REGS->PIO_PER |= PIO_PA11;  // enable desired pin
//    PIOA_REGS->PIO_OER |= PIO_PA11;  // output enable desired pin
//    PIOA_REGS->PIO_OWER |= PIO_PA11; // output write enable desired pin
//    
//    PIOA_REGS->PIO_PER |= PIO_PA12;  // enable desired pin
//    PIOA_REGS->PIO_OER |= PIO_PA12;  // output enable desired pin
//    PIOA_REGS->PIO_OWER |= PIO_PA12; // output write enable desired pin
    
    // clock
    PIOA_REGS->PIO_PER |= PIO_PA13;  // enable desired pin
    PIOA_REGS->PIO_OER |= PIO_PA13;  // output enable desired pin
    PIOA_REGS->PIO_OWER |= PIO_PA13; // output write enable desired pin
//    PIOA_REGS->PIO_ODSR &= ~PIO_PA13;
    PIOA_REGS->PIO_ODSR |= PIO_PA13;    // start high before being toggled

//  

    PIOA_REGS->PIO_PER |= PIO_PA4;  // enable desired pin
    PIOA_REGS->PIO_OER |= PIO_PA4;  // output enable desired pin
    PIOA_REGS->PIO_OWER |= PIO_PA4; // output write enable desired pin
    PIOA_REGS->PIO_ODSR |= PIO_PA4; // drive G0 high
//    PIOA_REGS->PIO_ODSR &= ~PIO_PA4; // drive G0 low
    
    PIOA_REGS->PIO_PER |= PIO_PA8;  // enable desired pin
    PIOA_REGS->PIO_OER |= PIO_PA8;  // output enable desired pin
    PIOA_REGS->PIO_OWER |= PIO_PA8; // output write enable desired pin
    PIOA_REGS->PIO_ODSR |= PIO_PA8; // drive G1 high
//    PIOA_REGS->PIO_ODSR &= ~PIO_PA8; // drive G1 low

    
//    PIOA_REGS->PIO_PER |= PIO_PA5;  // enable desired pin
//    PIOA_REGS->PIO_OER |= PIO_PA5;  // output enable desired pin
//    PIOA_REGS->PIO_OWER |= PIO_PA5; // output write enable desired pin
//    PIOA_REGS->PIO_ODSR |= PIO_PA5; // drive B0 high
////    PIOA_REGS->PIO_ODSR &= ~PIO_PA5; // drive B0 low
    
    PIOA_REGS->PIO_PER |= PIO_PA5;  // enable desired pin
    PIOA_REGS->PIO_OER |= PIO_PA5;  // output enable desired pin
    PIOA_REGS->PIO_OWER |= PIO_PA5; // output write enable desired pin
//    PIOA_REGS->PIO_ODSR |= PIO_PA5; // drive B0 high
    PIOA_REGS->PIO_ODSR &= ~PIO_PA5; // drive B0 low
    
    PIOA_REGS->PIO_PER |= PIO_PA9;  // enable desired pin
    PIOA_REGS->PIO_OER |= PIO_PA9;  // output enable desired pin
    PIOA_REGS->PIO_OWER |= PIO_PA9; // output write enable desired pin
//    PIOA_REGS->PIO_ODSR |= PIO_PA5; // drive B1 high
    PIOA_REGS->PIO_ODSR &= ~PIO_PA9; // drive B1 low

    
    PIOA_REGS->PIO_PER |= PIO_PA3;  // enable desired pin
    PIOA_REGS->PIO_OER |= PIO_PA3;  // output enable desired pin
    PIOA_REGS->PIO_OWER |= PIO_PA3; // output write enable desired pin
    PIOA_REGS->PIO_ODSR &= ~PIO_PA3; // drive R0 low
//    PIOA_REGS->PIO_ODSR |= PIO_PA3; // drive R0 high

    
    PIOA_REGS->PIO_PER |= PIO_PA7;  // enable desired pin
    PIOA_REGS->PIO_OER |= PIO_PA7;  // output enable desired pin
    PIOA_REGS->PIO_OWER |= PIO_PA7; // output write enable desired pin
    PIOA_REGS->PIO_ODSR &= ~PIO_PA7; // drive R1 low
//    PIOA_REGS->PIO_ODSR |= PIO_PA7; // drive R1 high

    // Latch
    PIOA_REGS->PIO_PER |= PIO_PA14;  // enable desired pin
    PIOA_REGS->PIO_OER |= PIO_PA14;  // output enable desired pin
    PIOA_REGS->PIO_OWER |= PIO_PA14; // output write enable desired pin
    PIOA_REGS->PIO_ODSR &= ~PIO_PA14;   // start latch low
    
    PIOA_REGS->PIO_PER |= PIO_PA21;  // enable desired pin
    PIOA_REGS->PIO_OER |= PIO_PA21;  // output enable desired pin
    PIOA_REGS->PIO_OWER |= PIO_PA21; // output write enable desired pin
    PIOA_REGS->PIO_ODSR &= ~PIO_PA21; // OE
//    PIOA_REGS->PIO_ODSR |= PIO_PA21;

    
    // Drive all demux high
    PIOA_REGS->PIO_PER |= PIO_PA10;  // enable desired pin
    PIOA_REGS->PIO_OER |= PIO_PA10;  // output enable desired pin
    PIOA_REGS->PIO_OWER |= PIO_PA10; // output write enable desired pin
    PIOA_REGS->PIO_ODSR |= PIO_PA10;
    
    PIOA_REGS->PIO_PER |= PIO_PA11;  // enable desired pin
    PIOA_REGS->PIO_OER |= PIO_PA11;  // output enable desired pin
    PIOA_REGS->PIO_OWER |= PIO_PA11; // output write enable desired pin
    PIOA_REGS->PIO_ODSR |= PIO_PA11;
    
    PIOA_REGS->PIO_PER |= PIO_PA12;  // enable desired pin
    PIOA_REGS->PIO_OER |= PIO_PA12;  // output enable desired pin
    PIOA_REGS->PIO_OWER |= PIO_PA12; // output write enable desired pin
    PIOA_REGS->PIO_ODSR |= PIO_PA12;
    
    // D might actually be PA24
    PIOA_REGS->PIO_PER |= PIO_PA27;  // enable desired pin
    PIOA_REGS->PIO_OER |= PIO_PA27;  // output enable desired pin
    PIOA_REGS->PIO_OWER |= PIO_PA27; // output write enable desired pin
    PIOA_REGS->PIO_ODSR |= PIO_PA27;
    
    // panels operate at 1:16 scan rate
    // leds to control = 32 x 64 x 3 = 6144
    // either 12 or 24 led controllers (for now assuming 24 based on what I counted)
    // each controller is 16 bit
    // so 24 x 16 = 384
    // 6144 / 384 = 16 times less bits than we need
    // scan rate should handle that, but not sure what should happen for daisy chained displays
    
    // I should check that the processor is actually running at 300 MHz
    
    // pump in all the bits I want one time (384 bits per line)
    
    // 384 * 2 = 768 (clock enable every other cycle, bit push ever other cycle)
    
    // Probably need the clock to cycle between every individual bit push!
    // In which case:
    // 384 / 6 = 64
    
    /*
    For each row of pixels, we repeat the following cycle of steps:
        - Clock in the data for the current row one bit at a time
        - Pull the latch and output enable pins high. This enables the latch, allowing the
         * row data to reach the output driver but it also disables the output so that no
         * LEDs are lit while we're switching rows.
        - Switch rows by driving the appropriate row select lines.
        - Pull the latch and output enable pins low again, enabling the output and closing
         * the latch so we can clock in the next row of data.

     */
    
//    for (;;)
//    {
        for (uint16_t j = 0; j < 16; j++)
        {
            inputRowAddress(j);
            
            PIOA_REGS->PIO_ODSR &= ~PIO_PA21;   // pull OE low (OE)
            PIOA_REGS->PIO_ODSR &= ~PIO_PA14;   // pull latch low
            
//            PIOA_REGS->PIO_ODSR |= PIO_PA21;    // pull OE high (disable output)
//            PIOA_REGS->PIO_ODSR |= PIO_PA14;    // pull latch high
            
            if ((j % 8) == 0)
            {
                for (i = 0; i < 64/*1*/; i++)
                {
                    // pseudo clock signal
                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk

                    PIOA_REGS->PIO_ODSR &= ~PIO_PA4; // set G0 high

//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk

                    PIOA_REGS->PIO_ODSR &= ~PIO_PA8; // set G1 low

//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk

                    PIOA_REGS->PIO_ODSR &= ~PIO_PA5; // set low B0
    //                    PIOA_REGS->PIO_ODSR |= PIO_PA5; // set B0 high

//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk

                    PIOA_REGS->PIO_ODSR &= ~PIO_PA9; // set low B1

//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk

                    PIOA_REGS->PIO_ODSR &= ~PIO_PA3; // set low R0

//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk

//                    PIOA_REGS->PIO_ODSR |= PIO_PA7; // set R1 high
                    PIOA_REGS->PIO_ODSR &= ~PIO_PA7; // set R1 low

                }
            }
            else
            {
                for (i = 0; i < 64/*1*/; i++)
                {
                    // pseudo clock signal
                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk

                    PIOA_REGS->PIO_ODSR |= PIO_PA4; // set G0 high

//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk

                    PIOA_REGS->PIO_ODSR &= ~PIO_PA8; // set G1 low

//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk

                    PIOA_REGS->PIO_ODSR &= ~PIO_PA5; // set low B0
    //                    PIOA_REGS->PIO_ODSR |= PIO_PA5; // set B0 high

//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk

                    PIOA_REGS->PIO_ODSR &= ~PIO_PA9; // set low B1

//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk

                    PIOA_REGS->PIO_ODSR &= ~PIO_PA3; // set low R0

//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//                        PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk

                    PIOA_REGS->PIO_ODSR |= PIO_PA7; // set R1 high
                }
            }

//            for (i = 0; i < 32; i++)
//            {
//                // pseudo clock signal
//                PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//                PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                PIOA_REGS->PIO_ODSR &= ~PIO_PA4; // set G0 low
//
////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                PIOA_REGS->PIO_ODSR &= ~PIO_PA8; // set G1 low
//
////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
////                PIOA_REGS->PIO_ODSR &= ~PIO_PA5; // set low B0
//                PIOA_REGS->PIO_ODSR |= PIO_PA5; // set B0 high
//
////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                PIOA_REGS->PIO_ODSR &= ~PIO_PA9; // set low B1
//
////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                PIOA_REGS->PIO_ODSR &= ~PIO_PA3; // set low R0
//
////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                PIOA_REGS->PIO_ODSR |= PIO_PA7; // set R1 high
//            }
            PIOA_REGS->PIO_ODSR |= PIO_PA21;    // pull OE high (disable output)
            PIOA_REGS->PIO_ODSR |= PIO_PA14;    // pull latch high
            
//            PIOA_REGS->PIO_ODSR &= ~PIO_PA21;   // pull OE low (OE)
//            PIOA_REGS->PIO_ODSR &= ~PIO_PA14;   // pull latch low
            
//            PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//            // latch high
//            PIOA_REGS->PIO_ODSR |= PIO_PA14;
//            // latch low
//            PIOA_REGS->PIO_ODSR &= ~PIO_PA14;
//            PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//            for (delay = 0; delay < 100000; delay++);
        }
//    }
    
    // Run clock and cycle between address lines for 1:16 scan rate
    
    // For whatever reason, the screen refreshes itself periodically
    for (;;)
    {
        for (j = 0; j < 16; j++)
        {
//            inputRowAddress(j);
            PIOA_REGS->PIO_ODSR |= PIO_PA21;    // pull OE high (disable output)
//            // latch high
//            PIOA_REGS->PIO_ODSR |= PIO_PA14;
//            PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//            
//            PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//            // latch low
//            PIOA_REGS->PIO_ODSR &= ~PIO_PA14;
            PIOA_REGS->PIO_ODSR &= ~PIO_PA21;    // pull OE low (enable output)
            // drive each address
            inputRowAddress(j);
        }
    }
    
    // This displays diagonal lines with green 
    

//    for (;;)
//    {
//        for (uint16_t j = 0; j < 16; j++)
//        {
//            inputRowAddress(j);
//            
//            PIOA_REGS->PIO_ODSR &= ~PIO_PA21;   // pull OE low (OE)
//            PIOA_REGS->PIO_ODSR &= ~PIO_PA14;   // pull latch low
//            
//            if (j == 8)
//            {
//                for (i = 0; i < 64/*1*/; i++)
//                {
//                    // pseudo clock signal
//                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                    PIOA_REGS->PIO_ODSR &= ~PIO_PA4; // set G0 high
//
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                    PIOA_REGS->PIO_ODSR &= ~PIO_PA8; // set G1 low
//
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                    PIOA_REGS->PIO_ODSR &= ~PIO_PA5; // set low B0
//    //                    PIOA_REGS->PIO_ODSR |= PIO_PA5; // set B0 high
//
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                    PIOA_REGS->PIO_ODSR &= ~PIO_PA9; // set low B1
//
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                    PIOA_REGS->PIO_ODSR &= ~PIO_PA3; // set low R0
//
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
////                    PIOA_REGS->PIO_ODSR |= PIO_PA7; // set R1 high
//                    PIOA_REGS->PIO_ODSR &= ~PIO_PA7; // set R1 low
//
//                }
//            }
//            else
//            {
//                for (i = 0; i < 64/*1*/; i++)
//                {
//                    // pseudo clock signal
//                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                    PIOA_REGS->PIO_ODSR |= PIO_PA4; // set G0 high
//
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                    PIOA_REGS->PIO_ODSR &= ~PIO_PA8; // set G1 low
//
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                    PIOA_REGS->PIO_ODSR &= ~PIO_PA5; // set low B0
//    //                    PIOA_REGS->PIO_ODSR |= PIO_PA5; // set B0 high
//
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                    PIOA_REGS->PIO_ODSR &= ~PIO_PA9; // set low B1
//
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                    PIOA_REGS->PIO_ODSR &= ~PIO_PA3; // set low R0
//
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//    //                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//
//                    PIOA_REGS->PIO_ODSR |= PIO_PA7; // set R1 high
//                }
//            }
//
////            for (i = 0; i < 32; i++)
////            {
////                // pseudo clock signal
////                PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
////                PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
////
////                PIOA_REGS->PIO_ODSR &= ~PIO_PA4; // set G0 low
////
//////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
////
////                PIOA_REGS->PIO_ODSR &= ~PIO_PA8; // set G1 low
////
//////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
////
//////                PIOA_REGS->PIO_ODSR &= ~PIO_PA5; // set low B0
////                PIOA_REGS->PIO_ODSR |= PIO_PA5; // set B0 high
////
//////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
////
////                PIOA_REGS->PIO_ODSR &= ~PIO_PA9; // set low B1
////
//////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
////
////                PIOA_REGS->PIO_ODSR &= ~PIO_PA3; // set low R0
////
//////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//////                    PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
////
////                PIOA_REGS->PIO_ODSR |= PIO_PA7; // set R1 high
////            }
//            PIOA_REGS->PIO_ODSR |= PIO_PA21;    // pull OE high (disable output)
//            PIOA_REGS->PIO_ODSR |= PIO_PA14;    // pull latch high
////            PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
////            // latch high
////            PIOA_REGS->PIO_ODSR |= PIO_PA14;
////            // latch low
////            PIOA_REGS->PIO_ODSR &= ~PIO_PA14;
////            PIOA_REGS->PIO_ODSR ^= PIO_PA13; // clk
//        }
//    }
    
    return (EXIT_SUCCESS);
}

// function to address A, B, C, D with one integer
void inputRowAddress(uint16_t addr)   // only need lower 4 bits of int
{
//    uint16_t digit0, digit1, digit2, digit3;
    
    if ((addr & BIT0) == 0)
        PIOA_REGS->PIO_ODSR &= ~PIO_PA10;
    else
        PIOA_REGS->PIO_ODSR |= PIO_PA10;
    
    if ((addr & BIT1) == 0)
        PIOA_REGS->PIO_ODSR &= ~PIO_PA11;
    else
        PIOA_REGS->PIO_ODSR |= PIO_PA11;

    if ((addr & BIT2) == 0)
        PIOA_REGS->PIO_ODSR &= ~PIO_PA12;
    else
        PIOA_REGS->PIO_ODSR |= PIO_PA12;
    
    if ((addr & BIT3) == 0)
        PIOA_REGS->PIO_ODSR &= ~PIO_PA27;
    else
        PIOA_REGS->PIO_ODSR |= PIO_PA27;
    
    return;
}