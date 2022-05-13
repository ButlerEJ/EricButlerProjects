//#####################################################################
//Author: Eric Butler                                                 #
//Date: 3/12/2022                                                     #
//Purpose: Create our version of a submit program. Allows the user to #
//copy files from one directory to another                            #
//#####################################################################

#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdlib.h>
#include <limits.h>
#include <pwd.h>
#include <dirent.h>
#include <string.h>
#include <fcntl.h>
#include <time.h>

char *get_user_name(); //Function prototypes.
char * list_dir(const char*);
int does_dir_exist(const char*);
char *make_new_dir(char*);
void copy_file (char*, char*);

int main (int argc, char *argv[])
{
  char file_path[300] = "./submit";
  char course[50];
  char *name = get_user_name();
  char cwd[PATH_MAX];
  char assignment[50];
  char *slash = "/";
  char submissions[200];
  char filestat [400];
  printf("Checking for a ""./submit""  directory...\n"); //For debugging.

  if ((does_dir_exist(file_path)  == -1))
  {
    perror("Submit directory doesn't exist\n");
    exit(1);
  }
  else
  {
    printf("We have a submit directory\n");
  }

  printf("Which course are you looking for: "); //Prompt for and store selected course.
  scanf("%s", course);

  strcat(file_path, slash); //Begin building what will be the directory path.
  strcat(file_path, course);

  if (does_dir_exist(course)) //Check to see if course directory already exists. If not, then create it.
  {
    printf("Already exists.\n");
  }
  else
  {
    make_new_dir(file_path);
  }

  printf("Retrieving your user name...\n"); //Begin to get the user name.

  strcat(file_path, slash); //Further develop directory path.
  strcat(file_path, get_user_name());

  if (!does_dir_exist(name)) //If they don't have a user name directory then make one.
  {
   printf("Creating your user directory.\n");
   make_new_dir(file_path);
  }
  else
  {
    perror("Failed to create name dir");
  }

  printf("Which assignment are you looking for?: "); //Prompt for assignment
  scanf("%s", assignment);

  strcat(file_path, slash); //Build destination directory path.
  strcat(file_path, assignment);

  if (!does_dir_exist(assignment)) //Make a directory for the assignment.
  {
    make_new_dir(file_path);
  }
  else
  {
    perror("Failed to create assignment dir");
  }
  //TODO: Need to craft a loop so the user can submit more than one file at a time.
  printf("Which files would you like to submit? : "); //prompt for submission files. Save the current directory path. Then create a spot for the file to be submitted to.
  scanf("%s", submissions);
  strcat(filestat, file_path);
  strcat(file_path, slash);
  strcat(file_path, submissions);
  copy_file(submissions, file_path); //Put the file in the location we just created.
  list_dir(filestat); //Display file information.
  return 0;
}

int does_dir_exist(const char *dir_path) //Function to determine whether a directory exists using stat struct.
{
  struct stat stats;

  stat(dir_path, &stats);

  if(S_ISDIR(stats.st_mode))
  {
    return 1;
  }
  return 0;
}

char *list_dir(const char *path) //Function to list file information from a directory.
{
  struct dirent **file_list;
  struct stat file_stat;
  // struct tm date;
  int n;
  int i;
  n = scandir(path, &file_list, NULL, alphasort); //Scan directory and sort alphabetically.
  if (stat(path, &file_stat) == -1)
  {
    perror("Stat: ");
  }
  if (n < 0)
  {
    perror("scandir error\n");
  }
  else
  {
    printf("NAME          DATE       TIME           SIZE\n");
    while (i < n)
    {
      if (file_list[i]->d_name[0] != '.' && file_list[i]->d_name[strlen(file_list[i]->d_name)-1] != '~') //If file name doesn't start with a '.' or '~' then print the file name.
      {
        printf("%-11s   %s   %d\n",file_list[i]->d_name, ctime(&file_stat.st_mtime), file_stat.st_size);
        free(file_list[i]); //Restore space for current index.
      }
      i++;
    }
    free(file_list); //Free the space.
  }
}

char *get_user_name() //Function for get user name utilizing passwd struct.
{
  int uid = getuid();
  struct passwd *pd = getpwuid(uid);

  return pd -> pw_name;
}

char *make_new_dir(char *dirname) //Function to create a new directory utilizing mkdir().
{
  int make;
  make = mkdir(dirname, 0777);
  if (!make)
  {
    printf("Directory created\n");
  }
  else
  {
    perror("Unable to create directory\n");
  }
  return 0;
}

#define BUFFERSIZE 4096
#define COPYMODE   0644
void copy_file(char *orig_file, char *new_file) //Function for copying a file from on directory to another. Source: Anne Applin.
{
  int in_fd, out_fd, n_chars;
  char buf[BUFFERSIZE];

  if ((in_fd = open(orig_file, O_RDONLY)) == -1)
  {
    perror("Cannot open original file.");
  }
  if ((out_fd = creat(new_file, COPYMODE)) == -1)
  {
    perror("Cannot creat a new file");
  }

  while ((n_chars = read(in_fd, buf, BUFFERSIZE)) > 0)
  {
    if (write(out_fd, buf, n_chars) != n_chars)
    {
      perror("Write error to new file");
    }
    if (n_chars == -1)
    {
      perror("Read error from original file");
    }
  }
    if (close(in_fd) == -1 || close(out_fd) == -1)
    {
      perror("Error closing files");
    }
    return;
}
