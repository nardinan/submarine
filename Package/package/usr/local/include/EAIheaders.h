/*
=INSERT_TEMPLATE_HERE=

$Id: EAIHeaders.h,v 1.15 2010/09/30 18:58:19 davejoubert Exp $

EAI and java CLASS invocation

*/

/****************************************************************************
    This file is part of the FreeWRL/FreeX3D Distribution.

    Copyright 2009 CRC Canada. (http://www.crc.gc.ca)

    FreeWRL/FreeX3D is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FreeWRL/FreeX3D is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with FreeWRL/FreeX3D.  If not, see <http://www.gnu.org/licenses/>.
****************************************************************************/



/**********************************************************************************************/
/*                                                                                            */
/* This file is part of the FreeWRL/FreeX3D Distribution, from http://freewrl.sourceforge.net */
/*                                                                                            */
/**********************************************************************************************/


#ifndef __FREEWRL_EAI_H__
#define __FREEWRL_EAI_H__

#include <pthread.h>

#define EBUFFLOCK pthread_mutex_lock(&eaibufferlock)
#define EBUFFUNLOCK pthread_mutex_unlock(&eaibufferlock)

extern int eaiverbose;

void shutdown_EAI(void);
int EAI_GetNode(const char *str);
struct X3D_Node *EAI_GetViewpoint(const char *str);
void EAI_killBindables (void);

/* function prototypes */
void EAIListener (void);
unsigned int EAI_SendEvent (char *ptr);
void EAI_RNewW(char *bufptr);
void EAI_RW(char *bufptr);

/* more function prototypes to avoid implicit declarations */
void Parser_deleteParserForScanStringValueToMem(void);			/* from EAI_C_CommonFunctions.c */
void Parser_scanStringValueToMem(struct X3D_Node *node, size_t coffset, int ctype, char *value, int isXML);
									/* from EAI_C_CommonFunctions.c */
int returnRoutingElementLength(int);					/* from EAI_C_CommonFunctions.c */
void createLoadURL(char *);						/* from EAIEventsIn.c */
void EAI_parse_commands(void);						/* from EAIEventsIn.c */
void EAI_Anchor_Response(int);						/* from EAIEventsIn.c */

/* debugging */
char *eaiPrintCommand (char command);


#define EAI_NODETYPE_STANDARD   93435
#define EAI_NODETYPE_PROTO      43534
#define EAI_NODETYPE_SCRIPT     234425



#define MAXEAIHOSTNAME	255		/* length of hostname on command line */
#define EAIREADSIZE	8192		/* maximum we are allowed to read in from socket */
#define EAIBASESOCKET   9877		/* socket number to start at */
#define MIDIPORTOFFSET 5		/* offset for midi EAI port to start at */


/* these are commands accepted from the EAI client */
#define GETNODE		'A'
#define GETEAINODETYPE	'B'
#define SENDCHILD 	'C'
#define SENDEVENT	'D'
#define GETVALUE	'E'
#define GETFIELDTYPE	'F'
#define	REGLISTENER	'G'
#define	ADDROUTE	'H'
#define REREADWRL	'I'
#define	DELETEROUTE	'J'
#define GETNAME		'K'
#define	GETVERSION	'L'
#define GETCURSPEED	'M'
#define GETFRAMERATE	'N'
#define	GETURL		'O'
#define	REPLACEWORLD	'P'
#define	LOADURL		'Q'
#define VIEWPOINT	'R'
#define CREATEVS	'S'
#define	CREATEVU	'T'
#define	STOPFREEWRL	'U'
#define UNREGLISTENER   'W'
#define GETRENDPROP	'X'
#define GETENCODING	'Y'
#define CREATENODE	'a'
#define CREATEPROTO	'b'
#define UPDNAMEDNODE	'c'
#define REMNAMEDNODE	'd'
#define GETPROTODECL 	'e'
#define UPDPROTODECL	'f'
#define REMPROTODECL	'g'
#define GETFIELDDEFS	'h'
#define GETNODEDEFNAME	'i'
#define GETROUTES	'j'
#define GETNODETYPE	'k'
#define MIDIINFO  	'l'
#define MIDICONTROL  	'm'
#define DUMPSCENE  	'n'


/* command string to get the rootNode - this is a special match... */
#define SYSTEMROOTNODE "_Sarah_this_is_the_FreeWRL_System_Root_Node"


/* Subtypes - types of data to get from EAI  - we don't use the ones defined in
   headers.h, because we want ASCII characters */

#define	EAI_SFBool		'b'
#define	EAI_SFColor		'c'
#define	EAI_SFFloat		'd'
#define	EAI_SFTime		'e'
#define	EAI_SFInt32		'f'
#define	EAI_SFString		'g'
#define	EAI_SFNode		'h'
#define	EAI_SFRotation		'i'
#define	EAI_SFVec2f		'j'
#define	EAI_SFImage		'k'
#define	EAI_MFColor		'l'
#define	EAI_MFFloat		'm'
#define	EAI_MFTime		'n'
#define	EAI_MFInt32		'o'
#define	EAI_MFString		'p'
#define	EAI_MFNode		'q'
#define	EAI_MFRotation		'r'
#define	EAI_MFVec2f		's'
#define EAI_MFVec3f		't'
#define EAI_SFVec3f		'u'
#define EAI_MFColorRGBA		'v'
#define EAI_SFColorRGBA		'w'
#define EAI_MFBool		'x'
#define EAI_FreeWRLPTR		'y'
#define EAI_MFVec3d		'A'
#define EAI_SFVec2d		'B'
#define EAI_SFVec3d		'C'
#define EAI_MFVec2d		'D'
#define EAI_SFVec4d		'E'
#define EAI_MFDouble		'F'
#define EAI_SFDouble		'G'
#define EAI_SFMatrix3f		'H'
#define EAI_MFMatrix3f		'I'
#define EAI_SFMatrix3d		'J'
#define EAI_MFMatrix3d		'K'
#define EAI_SFMatrix4f		'L'
#define EAI_MFMatrix4f		'M'
#define EAI_SFMatrix4d		'N'
#define EAI_MFMatrix4d		'O'
#define EAI_SFVec4f		'P'
#define EAI_MFVec4f		'Q'
#define EAI_MFVec4d		'R'



/* Function Prototype for plugins, Java Class Invocation */
int createUDPSocket();
int conEAIorCLASS(int socketincrement, int *sockfd, int *listenfd);
void EAI_send_string (char *str, int listenfd);
char *read_EAI_socket(char *bf, int *bfct, int *bfsz, int *listenfd);
extern int EAIlistenfd;
extern int EAIsockfd;
extern int EAIport;
extern int EAIwanted;
extern int EAIbufsize;
extern int EAIMIDIlistenfd;
extern int EAIMIDIsockfd;
extern int EAIMIDIwanted;
extern char *EAIbuffer;
extern int EAIbufcount;
extern char EAIListenerData[EAIREADSIZE];

#define MIDI_CONTROLLER_UNUSED 4
#define MIDI_CONTROLLER_FADER 1
#define MIDI_CONTROLLER_KEYPRESS 2
#define MIDI_CONTROLLER_UNKNOWN 999


#endif /* __FREEWRL_EAI_H__ */
