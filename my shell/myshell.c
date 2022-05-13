
/**  myshell.c  small-shell version 1
 **             first really useful version after prompting shell
 **             this one parses the command line into strings
 **             uses fork, exec, wait, and ignores signals
 Revision 5/3/2022 Authors: Kevin Spring, Eric Butler, Loic Sine.
 Added features to change directories. Display user name and directory path.
 Exits cleanly and siplays exit value.
 **/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <pwd.h>
#include <sys/wait.h>
#include "smsh.h"

char *get_user_name(); // Function prototype.
//#define DFL_PROMPT      "What command would you like to execute?: " Commented out because we're not using it. Left it in because we're utilizing someone else's code.

int main()
{
  char curr_dir[1000], *my_home_dir, *prompt, *cmdline, **arglist; // Declare char variables for current directory, shell prompt, command line, and arguments.

  getcwd(curr_dir, sizeof(curr_dir));             // Get current working directory, and store in the current directory array.
  prompt = strcat(get_user_name(), "@rubicon:~"); // Begin constructing our prompt by calling get_user_name which retrieves the user's log in name, then concatenates our server name to it.
  prompt = strcat(prompt, curr_dir);              // Further prompt construction, adds the directory we are currently in to our prompt.
  prompt = strcat(prompt, " $ ");                 // The finale of our prompt, just mimicking what we typically see in our shell.
  int bgprocess;
  int result, exit_status; // Used to store the exiting status.
  void setup();            // Function protoype?
  setup();                 // Calls setup to initialize our shell.

  while ((cmdline = next_cmd(prompt, stdin)) != NULL) // While We have a command.
  {
    if ((arglist = splitline(cmdline)) != NULL) // TODO: Come back to this.
    {
      while ((bgprocess = waitpid(-1, NULL, WNOHANG)) > 0)
      {
        printf("\n\t~~~~~~~ Background process %d has finished ~~~~~~~\n\n", bgprocess);
      }
      if (strcmp(arglist[0], "exit") == 0) // If the command is "exit".
      {
        if (arglist[1]) // If there is an exit value as well.
        {
          exit_status = atoi(arglist[1]);                 // Store the exit value in a variable.
          printf("Exiting with value %d\n", exit_status); // Alert the user what exit status is occuring.
          exit(exit_status);                              // Exit with that exit code.
        }
        else // Otherwise we exit smoothly.
        {
          printf("Exiting with value 0\n"); // We're exiting correctly.
          exit(0);                          // Exit smoothly.
        }
      }
      if (strcmp(arglist[0], "cd") == 0) // If the user wants to change directories.
      {
        if (arglist[1]) // And they have a specific directory to go to.
        {
          int myDir = chdir(arglist[1]); // variable for chdir so it doesn't execute multiple times

          if (myDir != 0) // If that directory doesn't exist.
          {
            printf("No such file of directory\n"); // Notify the directory doesn't exist.
          }
          else // Otherwise change to that directory.
          {
            char home_dir[50] = "/home/";                // begin creation of a home directory string
            my_home_dir = strcat(get_user_name(), "");   // throw the user name in there
            my_home_dir = strcat(home_dir, my_home_dir); // concat the home dir and the username
            /* Now cd .. dectects if you are home and will not let you go any further... May be useful? People love boundaries */
            if (strcmp(getcwd(curr_dir, sizeof(curr_dir)), my_home_dir) <= 0)
            {
              printf("You're already home no need to go back any further :-)\n");
              chdir(my_home_dir);                             // Change directories to home directory.
              getcwd(curr_dir, sizeof(curr_dir));             // Get the directory path we're in.
              prompt = strcat(get_user_name(), "@rubicon:~"); // Build a new prompt.
              prompt = strcat(prompt, curr_dir);              // Get the current directory.
              prompt = strcat(prompt, " $ ");                 // Unnecessary attempt to copy the prompt our shell displays.
            }
            /* If you're not "HOME" it will let you change directories */
            else
            {
              getcwd(curr_dir, sizeof(curr_dir));             // Get the directory we 'cd' to.
              prompt = strcat(get_user_name(), "@rubicon:~"); // Build a new prompt.
              prompt = strcat(prompt, curr_dir);              // Add current directory to prompt.
              prompt = strcat(prompt, " $ ");                 // Cherry on top.
            }
          }
        }
        else // Otherwise, no specified directory to change to, so by default we go to home directory.
        {
          chdir(getenv("HOME"));                          // Change directories to home directory.
          getcwd(curr_dir, sizeof(curr_dir));             // Get the directory path we're in.
          prompt = strcat(get_user_name(), "@rubicon:~"); // Build a new prompt.
          prompt = strcat(prompt, curr_dir);              // Get the current directory.
          prompt = strcat(prompt, " $ ");                 // Unnecessary attempt to copy the prompt our shell displays.
        }
      }
      else
      {
        result = execute(arglist); // Result is assigned the value arglist passed to execute, which returns the exit status of a child process? Via child_info in execute.c
      }
      freelist(arglist); // Free the memory.
    }
    free(cmdline); // Free the memory.
  }
  return 0;
}

void setup()
/*
 * purpose: initialize shell
 * returns: nothing. calls fatal() if trouble
 */
{
  signal(SIGINT, SIG_IGN);
  signal(SIGQUIT, SIG_IGN);
}

void fatal(char *s1, char *s2, int n) // For displaying an error message when things don't go well.
{
  fprintf(stderr, "Error: %s,%s\n", s1, s2);
  exit(n);
}

char *get_user_name() // Function for get user name utilizing passwd struct.
{
  int uid = getuid();
  struct passwd *pd = getpwuid(uid);

  return pd->pw_name;
}