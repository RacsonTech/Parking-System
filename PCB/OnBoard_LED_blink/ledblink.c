/* 
 * File:   newmain.c
 * Author: vinnieg
 *
 * Created on October 10, 2022, 1:21 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <xc.h>

/*
 * 
 */
int main(int argc, char** argv)
{
//    uint32_t *led_ptr;
    
    // Disable watchdog timer
    WDT_REGS->WDT_MR = WDT_MR_WDDIS(1);

    
    PIOA_REGS->PIO_PER |= PIO_PA22;  // enable desired pin
    PIOA_REGS->PIO_OER |= PIO_PA22;  // output enable desired pin
    PIOA_REGS->PIO_OWER |= PIO_PA22; // output write enable desired pin
    

    for (;;)
    {
        PIOA_REGS->PIO_ODSR ^= PIO_PA22; // PIO_ODSR = Output Data Status Register

        for (volatile uint32_t i = 0; i < 500000; i++);
    }
    
    return (EXIT_SUCCESS);
}
