/* execute.c - code used by small shell to execute commands
Revised 5/3/2022. Authors: Kevin Spring, Eric Butler, Loic Sine.
To enable background processes to run while
foreground processes also run.
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>
#include <string.h>

typedef int bool;
#define true 1
#define false 0

int execute(char *argv[])
/*
 * purpose: run a program passing it arguments
 * returns: status returned via wait, or -1 on error
 *  errors: -1 on fork() or wait() errors
 */
{
  int i = 0;                                        /* This will be our array iterator */
  size_t size = sizeof *(argv) / sizeof *(argv[0]); /* This is the size of the argv array that we will iterate through */
  int pid;
  int child_info = -1;
  bool background = false; // background flag
  int bgprocess;

  /* Check if the first argument exists */
  if (argv[0] == NULL)
  { /* nothing succeeds     */
    return 0;
  }

  /* Using a for loop to traverse the argvs */
  for (i; i < size; i++)
  {
    /* if the argv exists */
    if (argv[i] != NULL)
    {
      /* check to see if the argv is an ampersand */
      if (strcmp(argv[i], "&") == 0)
      {
        background = true;
        argv[i] = NULL; /* change the ampersand to a null character to avoid an error message */

        break;
      }
    }
  }

  if ((pid = fork()) == -1)
  {
    perror("fork");
  }
  else if (pid == 0)
  {
    signal(SIGINT, SIG_DFL);
    signal(SIGQUIT, SIG_DFL);
    execvp(argv[0], argv);
    perror("cannot execute command");
    exit(1);
  }

  else
  {
    printf("Process %d has begun.\n\n", pid); // say the program pid and where is operating

    if (!background)
    {
      if (waitpid(pid, &child_info, WNOHANG) == -1)
      {
        perror("wait");
      }
      else if (waitpid(pid, &child_info, WNOHANG == 0))
      {
        printf("\nProcess %d done.\n", pid);
        kill(pid, SIGKILL);
      }
    }
    else if (waitpid(pid, &child_info, WNOHANG) == -1)
    {
      perror("wait");
    }
  }
  return child_info;
