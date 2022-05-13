/*
* scroll is a program that allows user to read input file
* as a command line argument or piped or redirected as
* standard input (stdin). scroll allows text to be displayed
* either page-by-page or scrolled line-by-line.
* scroll shows status of file read and scroll speed.
* scroll modifies terminal settings for fluid, pleasing, and
* intuitive functionality. Upon exit, scroll restores previous
* terminal settings.
*
* scroll controls:
* Enter - toggle scrolling on/off
* Space - display next page
* f - increase scroll speed
* s - decrease scroll speed
* q - exit program
*
* Eric Butler & Gideon Spaulding, CSCI296, 4/11/2022
*/

#include <termios.h>
#include <fcntl.h>
#include <signal.h>
#include <string.h>
#include <sys/time.h>
#include <sys/ioctl.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

#define FILESIZE 9999999 //generous size for file_buffer
#define INITIAL_SPEED 2000000 //2 million microseconds = 2 secs

void terminal_settings(int file_d);
void get_win_size(struct winsize *window);
void set_terminal(struct termios *settings);
int read_file(int file_d, char *filebuff);
void display_one_page(int numrows, int numcols);
int prompt(char *message, int percent_done, int scroll_speed);
void erase_prompt(int num_backspace);
int numdigitchars(int number);
void display_one_line(int numcols);
void reset_and_exit();
int set_interval_timer(int num_microseconds, struct itimerval *);
void int_handler();
void alarm_scroll_handler();
void dummy_handler();
void scroll_controls(int numrows, int numcols);
void scroll_toggle(int toggle);
int pct_read();


struct termios orig_term_settings;
char file_buffer[FILESIZE];
char *buff_ptr = file_buffer;//pointer to the char[] of input
int num_rows;
int num_cols;
int scroll_pct; // Expressed as % of the INITIAL_SPEED

int main(int argc, char *argv[])
{
    int file_d;
    int file_size;
    scroll_pct = 100;

    struct winsize window;
    get_win_size(&window); // get window size info
    num_rows = window.ws_row;
    num_cols = window.ws_col;

    signal(SIGINT, int_handler);
    signal(SIGQUIT, int_handler);

    if(argc > 2)
    {
        fprintf(stderr, "use: progname [filename]\n");
        reset_and_exit();
    }
    if(argc == 1) //read from stdin
    {
        file_size = read_file(0, file_buffer); //file descriptor of 0 for stdin
        int terminal = open("/dev/tty", O_RDONLY);
        terminal_settings(terminal); //set terminal settings
    }
    if(argc == 2) //read from file
    {
        terminal_settings(0); //set stdin (defaulted to terminal) settings
        if( ( file_d = open(argv[1], O_RDONLY) ) == -1 )
        {
            perror("Error opening file");
            reset_and_exit();
        }

        file_size = read_file(file_d, file_buffer);
    }

    scroll_controls(num_rows, num_cols);

    tcsetattr(0,TCSANOW,&orig_term_settings); //restore original terminal settings
    return 0;
}
/*
* Sets terminal settings for scroll functionality
*/
void terminal_settings(int file_d)
{
    //store original terminal settings
    tcgetattr(file_d, &orig_term_settings);
    // struct for new settings
    struct termios new_term_settings;
    tcgetattr(file_d, &new_term_settings);
    new_term_settings.c_lflag &= ~ECHO;     // turn off echo bit
    new_term_settings.c_lflag &= ~ICANON;   // turn off buffering
    new_term_settings.c_lflag |= O_NONBLOCK;
    new_term_settings.c_cc[VMIN] = 1;      // get 1 char at a time
    tcsetattr(file_d, TCSANOW, &new_term_settings);  // set attribs
}
/*
* Handler for SIGINT and SIGQUIT
*/
void int_handler()
{
    reset_and_exit();
}
/*
* Reset terminal settings, then exit
*/
void reset_and_exit()
{
    tcsetattr(0,TCSANOW,&orig_term_settings); //restore original terminal settings
    exit(1);
}
/*
* loads terminal window size into a winsize struct
*/
void get_win_size(struct winsize *window)
{
    if( ioctl(1, TIOCGWINSZ, window) == -1)
    {
        perror("Error getting window size");
        reset_and_exit();
    }
}
/*
* Reads file up to FILESIZE into a char[] file buffer.
* Returns number of bytes read into buffer.
*/
int read_file(int file_d, char *filebuffer)
{
    int file_size;

    if( ( file_size = read(file_d, filebuffer, FILESIZE) ) == -1)
    {
        perror("Error reading file");
        reset_and_exit();
    }
    close(file_d);

    return file_size;
}
/*
* Displays one line of input file which is in a buffer.
*
* Increments value pointed by buff_ptr to position
* where writing left off to keep track of file position
* for future display references.
*
* Calls prints and erases of prompts with scroll info
*/
void display_one_line(int numcols)
{
    static int prompt_size = 0;
    int col_number = 0;
    char c;

    if(*buff_ptr == '\0') //if at end, don't try to do anything.
    {
        erase_prompt(prompt_size);
        prompt_size = prompt("End of File (Press q to quit)", 100, scroll_pct);
        signal(SIGALRM, SIG_IGN); //done with alarm/scrolling
        return;
    }

    erase_prompt(prompt_size);

    while(col_number < numcols)
    {
        c = *buff_ptr; //set char from file_buffer
        putchar(c); // display char
        buff_ptr += sizeof(char); // move pointer to next char in file_buffer

        if(c == '\n') //counts as a whole or rest of row
        {
            col_number = numcols;
        }
        else if (c == '\t') //adjust column count accordingly
        {
            col_number += ( 8 - (col_number % 8) );
        }
        else
        {
            col_number++;
        }
    }
    if( c != '\n') // prevent line wrapping
    {
        printf("\n");
    }
    prompt_size = prompt("--Scroll--", pct_read(), scroll_pct);
}
/*
* Displays one page of the file based on window size.
* Leaves room on last row of window for prompt.
*/
void display_one_page(int numrows, int numcols)
{
    int rows_displayed = 0;

    while( rows_displayed < (numrows - 1))
    {
        display_one_line(numcols);
        rows_displayed++;
    }
}
/*
* Display a prompt in reverse video.
* Tracks and returns number of chars used in each prompt
* for accurate erasing/writing over of previous prompt with
* erase_prompt
*/
int prompt(char *message, int pct_done, int scroll_pct_speed)
{
    //initialized with standard spacing & styling char count
    int numchars = 30;
    numchars += numdigitchars(pct_done);
    numchars += numdigitchars(scroll_pct_speed);
    numchars += strlen(message);

    printf("\033[7m %s | File: %d%% | Scroll Speed: %d%% \033[m",
     message, pct_done, scroll_pct_speed);
    fflush(stdout);
    return numchars;
}
/*
* erase a previous prompt based on how much was displayed
*/
void erase_prompt(int num_backspace)
{
    for(int i = 0; i < num_backspace; i++)
    {
        printf("\b");
    }
    printf("\033[0K"); //clear reverse video prompt
    fflush(stdout);
    printf("\r");
}
/*
* Helper method for prompt.
* counts the digits/chars of a number.
*/
int numdigitchars(int number)
{
    int numdig = 1;
    if(number < 0)
    {
        numdig++; //if negative add one for the sign
    }
    while( (number = number / 10) != 0)
    {
        numdig++;
    }
    return numdig;
}
/*
* returns the percentage of the file displayed to terminal so far.
*/
int pct_read()
{
    return ((strlen(file_buffer) - strlen(buff_ptr)) * 100) / strlen(file_buffer);
}
/*
*
*/
int set_interval_timer(int num_microsecs, struct itimerval *timer_settings)
{
    //struct itimerval timer_settings;
    long num_secs;
    long num_msecs;

    num_secs = num_microsecs / 1000000;
    num_msecs = num_microsecs % 1000000;

    timer_settings -> it_interval.tv_sec = num_secs;
    timer_settings -> it_interval.tv_usec = num_msecs;
    timer_settings -> it_value.tv_sec = num_secs;
    timer_settings -> it_value.tv_usec = num_msecs;

    return setitimer(ITIMER_REAL, timer_settings, NULL);
}

void alarm_scroll_handler()
{
    display_one_line(num_cols);
}
/*
* a dummy handler for toggling scrolling off
* the alarm will continue running, but no longer
* display any information like when the prompting handler
* is on.
*/
void dummy_handler()
{
}
/*
* Main controls while program runs
*/
void scroll_controls(int numrows, int numcols)
{
    char c;
    int scroll_switch = 0; //start with scroll off
    scroll_toggle(scroll_switch);
    int speed = INITIAL_SPEED;
    struct itimerval timer_settings;

    display_one_page(num_rows, num_cols);// begin with first page
    while (1)
    {
        switch ( c = getchar() )
        {
            case ' ':
                display_one_page(numrows, numcols);
                break;
            case '\n': //MOD the timmer
                if(scroll_switch == 0) // if scroll is off, turn it on
                {
                    set_interval_timer(speed, &timer_settings);
                    scroll_toggle((scroll_switch = 1));
                }
                else
                {
                    speed = INITIAL_SPEED; // reset scroll speed
                    scroll_toggle((scroll_switch = 0));
                }
                break;
            case 'f':
                speed = speed - ( (speed * 20) / 100);
                set_interval_timer(speed, &timer_settings);
                if(*buff_ptr != '\0')
                {
                    printf("\r");
                    prompt("--Scroll--", pct_read(), scroll_pct);
                }
                break;
            case 's':
                speed = speed + ( (speed * 20) / 100);
                set_interval_timer(speed, &timer_settings);
                if( *buff_ptr != '\0')
                {
                    printf("\r");
                    prompt("--Scroll--", pct_read(), scroll_pct);
                }
                break;
            case 'q':
                printf("\n");
                reset_and_exit();
            default:
                break;
        }
        scroll_pct = ((INITIAL_SPEED * 100) / speed);
    }
}
/*
* Simple function that toggles the scrolling output on and off
* Intended for 1 to toggle on, and 0 to toggle off.
* Toggles scrolling off by changing the alarm signal to a
* dummy handler that does nothing. When toggled on, it
* passes to a handler that acts as the scrolling display.
*/
void scroll_toggle(int toggle)
{
    if(toggle == 1)
    {
        signal(SIGALRM, alarm_scroll_handler);
    }
    else
    {
        signal(SIGALRM, dummy_handler);
    }
}
