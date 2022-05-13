
/*
* Compression and decompression for an ASCII text file.
* Compresses each 8-bit character to 7-bits for compression.
* Translates back 7-bit pieces to 8-bit characters appropriately
* for decompression.
* Compressed files hold the size of the original file in the
* first four bytes of memory.
*
* Gideon Spaulding/Eric Butler, CSCI 296, 3/1/2022
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/stat.h>

void compress(int file_d_orig, int file_d_compress);
void decompress(int file_d_orig, int file_d_decompress);
void error_message_exit(char *msg);

/*
* Majority of input/output file handling and creation
* performed in main
*/
int main(int argc, char *argv[])
{
  int input_file;
  int output_file;

  if(argc != 2)
  {
    fprintf(stderr, "use: progname filename\n");
    exit(1);
  }

  int inputlength = strlen(argv[1]);
  char *file_end_ext = &( argv[1][inputlength - 5] ); /* last 5 chars of input */

  if ( ( input_file = open(argv[1], O_RDONLY) ) == -1 )
  {
    error_message_exit("Cannot open file ");
  }

  /* if file ends with compressed extension */
  if(strcmp(".z827", file_end_ext) == 0) /* decompression */
  {
    char decomp_name[inputlength - 5];
    strncpy(decomp_name, argv[1], inputlength - 5); /* same name w/o ".z827" */

    if( ( output_file = creat(decomp_name, 0644) ) == -1 ) /* creat new comp file */
    {
      error_message_exit("Error with creat: ");
    }

    decompress(input_file, output_file);
    printf("Decompression successful\n");
  }
  else /* compression */
  {
    char inputname[strlen(argv[1])];
    strcpy(inputname, argv[1]);
    char *compfilename = strcat(inputname, ".z827"); /* comp file name + extension */

    if( ( output_file = creat(compfilename, 0644) ) == -1 ) /* creat new comp file */
    {
      error_message_exit("Error with creat: ");
    }

    compress(input_file, output_file);
    printf("Compression successful\n");
  }

    /* Close the output file */
  if( close(output_file)  == -1)
  {
    error_message_exit("Could not close ouput file ");
  }

    /* Close the input file */
  if( close(input_file) == -1)
  {
    error_message_exit("Could not close input file "); 
  }

    /* Remove the input file */
  if(unlink(argv[1]) == -1)
  {
    perror("Error removing original file ");
  }


  return 0;

}


/* compresses original ASCII file to a compressed .z827 file
* takes two file handles one for original file and one for
* file to be compressed as parameters.
 */
#define BUFFERSIZE 4096
void compress(int orig_file, int comp_file)
{
    printf("Compressing file...\n");
    int num_chars; /* for read system call return */
    unsigned int comp_buffer = 0; /* buffer to hold chars binary values in */
    unsigned char c; /* character(s) to be read, buffered, compressed */
    unsigned int totalsize = 0; /* tally for total filesize */
    int counter = 0; /* counter for bit shifting */

    if(lseek(comp_file, 4, SEEK_SET) == -1) /* blank four bytes fill with size at end */
    {
        error_message_exit("lseek error: ");
    }

    /* allocated memory buffer */
    unsigned char *buffer = (unsigned char *)malloc(sizeof(char) * BUFFERSIZE);

    while ( ( num_chars = read(orig_file, buffer, BUFFERSIZE) ) > 0 )
    {

        int i = 0; /* Loop control variable */
        if(counter == 0)
        {
            c = buffer[0]; /* put first char into compress buffer */
            comp_buffer += c;
            i++; /* start loop at 1 */
        }

        while(i < num_chars)
        { /* check to see if 8th bit is set in data */
            if( (buffer[i] & 128) != 0 )
            {
                printf("Invalid data, ASCII chars only\n");
                exit(1);
            }
            /* add new char shifted a variable amount into buffer */
            comp_buffer = comp_buffer | ( buffer[i] << (7 - counter++) );
            c = (comp_buffer & 255); /* use mask to extract 8 bits */

            if(counter < 8)
            {
                if( write(comp_file, &c, 1) != -1) /* write to compressed file */
                {
                    comp_buffer = comp_buffer >> 8;
                }
                else
                {
                    error_message_exit("Error writing compressed file");
                }
            }
            else /* reset the counter 8 (buffer size of 7, so no write) */
            {
                counter = 0;
            }

            i++;
        }

        totalsize += num_chars;

    }

    free(buffer); /* free malloc space */

    if(num_chars == -1)
    {
        error_message_exit("Read error: ");
    }

    if( write(comp_file, &comp_buffer, 1) == -1) /* clear out remaining from buffer */
    {
        error_message_exit("Error writing comprssed file ");
    }

    if( lseek(comp_file, 0, SEEK_SET) == -1) /* move write head to beginning of file */
    {
        error_message_exit("lseek error");
    }

    if(write(comp_file, &totalsize, 4) == -1) /* write in size of file */
    {
        error_message_exit("Error writing to compressed file ");
    }

}

/*
* Decompresses file handle comp_file .z827 binary file
* into an ASCII characters file with handle decomp_file.
*/
void decompress (int comp_file, int decomp_file)
{
    printf("Decompressing file...\n");
    int decomp_size;
    unsigned int decomp_buffer = 0; /* buffer to hold chars binary values in */
    unsigned char c; /* character(s) to be output to decompressed file */
    int counter = 0;
    int num_chars;

    /* first read how many chars for original file */
    if( read(comp_file, &decomp_size, 4) == -1 )
    {
        error_message_exit("Error reading: ");
    }

    /* allocated memory buffer */
    char *buffer = (char *)malloc(sizeof(char) * BUFFERSIZE);

    while ( ( num_chars = read(comp_file, buffer, BUFFERSIZE) ) > 0 )
    {
        int i = 0;

        while(i < num_chars)
        {

            decomp_buffer += ( buffer[i] << counter++ );
            c = (decomp_buffer & 127);
            decomp_buffer = decomp_buffer >> 7;

            if(write(decomp_file, &c, 1) == -1)
            {
                error_message_exit("Write error ");
            }

            if(counter == 7) /* on the 7th shift, we get an extra char we can write */
            {
              /* Mask to extract only the original 7-bits (8th bit of orig is 0) */
              c = (decomp_buffer & 127);
              decomp_buffer = decomp_buffer >> 7;
              if(write(decomp_file, &c, 1) == -1)
              {
                error_message_exit("Write error ");
              }

              counter = 0; /* reset the counter */
            }

            i++;
        }
    }
    free(buffer); /* free malloc space */

    if(num_chars == -1)
    {
        error_message_exit("Read error: ");
    }
}

void error_message_exit(char *msg)
{
    perror(msg);
    exit(1);
}