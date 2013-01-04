/* MIDIController - midi_control.c
*
* wait on a UDP port, receive MIDI sequence, and send it out to the
* MIDI device
*
* John Stewart CRC Canada.
*
*/

#include	<stdio.h>
#include	<sys/types.h>
#include	<sys/socket.h>
#include	<netinet/in.h>
#include	<fcntl.h>
#include	<stdlib.h>

#define 	PORT	3337

int write_synth (char *midi_data) {
  FILE *ofd;

  strncat (midi_data,"\n");
printf ("going to write %s to midi-port\n",midi_data);
  ofd = fopen ("/tmp/synth.mg", "w");
  fputs (midi_data,ofd);
  fclose (ofd);

printf ("catting data\n");
  system ("cat /tmp/synth.mg");

printf ("converting to midi\n");
  system ("cd /tmp; midge -v synth.mg");
  system ("playmidi /tmp/synth.mid");

}


int sock_init (addr)
  struct sockaddr *addr;

  {
    struct sockaddr_in *in_name;
    struct sockaddr_un *un_name;
    struct hostent *hptr, *gethostbyname();
    int sd;

    if ((sd = socket (AF_INET, SOCK_DGRAM, 0)) < 0)
      {perror ("sockinit"); exit(1);}

    in_name = (struct sockaddr_in *) addr;
    in_name->sin_family = AF_INET;
    in_name->sin_port = htons(PORT);

    in_name->sin_addr.s_addr = INADDR_ANY;
    return (sd);
  }

main (argc, argv)
  char *argv[];
  {
    int ld;			/* Listen socket descriptor		*/
    int addrlen, nbytes; 	/* socketaddr length, read nbytes req	*/

    struct sockaddr_in name;	/* internet socket name (addr)		*/
    struct sockaddr_in *ptr;	/* pointer to get port number		*/
    struct sockaddr addr, from;	/* generic socket name (addr)		*/

    int fromlen = sizeof from;	/* address length (16 bytes)		*/
    char buf[2048];		/* i/o buffer				*/

    struct hostent *hp;		/* /etc/hosts entry			*/
    struct hostent *gethostbyaddr();

    ld = sock_init (&name);

    if (bind(ld,&name, sizeof(name)) < 0)
      { perror ("MIDI INET Domain Bind"); exit(2);}

    addrlen = sizeof (addr);

    if (getsockname(ld,&addr,&addrlen)<0)
      {perror ("MIDI INET Domain getsockname"); exit(3);}

    ptr = (struct sockaddr_in *) &addr;
    printf ("MIDI Socket has port number: %d\n",htons(ptr->sin_port));

    do {
      memset (buf, 0, sizeof (buf));
      if ((nbytes = recvfrom(ld,buf,sizeof(buf),0,&from,&fromlen)) < 0)
        {perror ("MIDI INET Domain receive"); exit(4);}
      else {
        printf ("MIDI Received %s\n", buf);
        write_synth(buf);
      }

    } while (nbytes != 0);
  }


