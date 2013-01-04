
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



#include <sys/types.h>
#include <stdint.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <strings.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>

#define STRLEN 256

typedef struct { int type; int value; } _intX3D_SFBool;
typedef struct { int type; float value; } _intX3D_SFFloat;
typedef struct { int type; double value; } _intX3D_SFTime;
typedef struct { int type; int value; } _intX3D_SFInt32;
typedef struct { int type; int adr; } _intX3D_SFNode;
typedef struct { int type; float r[4]; } _intX3D_SFRotation;
typedef struct { int type; float c[2]; } _intX3D_SFVec2f;
typedef struct { int type; double c[2]; } _intX3D_SFVec2d;
typedef struct { int type; float c[3]; } _intX3D_SFColor;
typedef struct { int type; float c[3]; } _intX3D_SFVec3f;
typedef struct { int type; double c[3]; } _intX3D_SFVec3d;
typedef struct { int type; float r[4]; } _intX3D_SFColorRGBA;
typedef struct { int type; int len; char *strptr;} _intX3D_SFString;
typedef struct { int type; int len; char *strptr;} _intX3D_SFImage;

typedef struct { int type; int n; _intX3D_SFColor *p; } _intX3D_MFColor;
typedef struct { int type; int n; _intX3D_SFColorRGBA *p; } _intX3D_MFColorRGBA;
typedef struct { int type; int n; _intX3D_SFFloat *p; } _intX3D_MFFloat;
typedef struct { int type; int n; _intX3D_SFTime *p; } _intX3D_MFTime;
typedef struct { int type; int n; _intX3D_SFRotation *p; } _intX3D_MFRotation;
typedef struct { int type; int n; _intX3D_SFVec3d *p; } _intX3D_MFVec3d;
typedef struct { int type; int n; _intX3D_SFVec2d *p; } _intX3D_MFVec2d;
typedef struct { int type; int n; _intX3D_SFVec3f *p; } _intX3D_MFVec3f;
typedef struct { int type; int n; _intX3D_SFVec2f *p; } _intX3D_MFVec2f;
typedef struct { int type; int n; _intX3D_SFBool *p; } _intX3D_MFBool;
typedef struct { int type; int n; _intX3D_SFInt32 *p; } _intX3D_MFInt32;
typedef struct { int type; int n; _intX3D_SFNode *p; } _intX3D_MFNode;
typedef struct { int type; int n; _intX3D_SFString *p; } _intX3D_MFString;
typedef struct { int type; int n; _intX3D_SFImage *p; } _intX3D_MFImage;

typedef union _X3DNode {
	int 			type;
	_intX3D_MFBool		X3D_MFBool;
	_intX3D_SFBool		X3D_SFBool;
	_intX3D_SFFloat		X3D_SFFloat;
	_intX3D_SFTime		X3D_SFTime;
	_intX3D_SFInt32		X3D_SFInt32;
	_intX3D_MFColor 	X3D_MFColor;
	_intX3D_MFColorRGBA	X3D_MFColorRGBA;
	_intX3D_SFString	X3D_SFString;
	_intX3D_SFNode		X3D_SFNode;
	_intX3D_SFRotation	X3D_SFRotation;
	_intX3D_SFVec2f		X3D_SFVec2f;
	_intX3D_SFVec2d		X3D_SFVec2d;
	_intX3D_SFColor		X3D_SFColor;
	_intX3D_SFColor		X3D_SFVec3f;
	_intX3D_SFVec3d		X3D_SFVec3d;
	_intX3D_SFColorRGBA	X3D_SFColorRGBA;
	_intX3D_MFFloat		X3D_MFFloat;
	_intX3D_MFTime		X3D_MFTime;
	_intX3D_MFInt32		X3D_MFInt32;
	_intX3D_MFString	X3D_MFString;
	_intX3D_MFNode		X3D_MFNode;
	_intX3D_MFRotation	X3D_MFRotation;
	_intX3D_MFVec2f		X3D_MFVec2f;
	_intX3D_MFVec3f		X3D_MFVec3f;
	_intX3D_MFImage		X3D_MFImage;
	_intX3D_MFVec3d		X3D_MFVec3d;
	
} X3DNode;

#ifndef TRUE
#define TRUE 1==1
#define FALSE 1!=1
#endif

#define REMOVE_EOT {char *lp; lp=strstr(ptr,"RE_EOT"); if (lp!=NULL) {lp--; *lp='\0';}};
#define SKIP_IF_GT_SPACE        while (*ptr > ' ') ptr++;
#define SKIP_CONTROLCHARS       while ((*ptr != '\0') && (*ptr <= ' ')) ptr++;


/* structures */


struct _intX3DEventIn {
	int		nodeptr;
	int 		offset;
	int		datatype;
	int 		datasize;
	int		scripttype;
	char 		*field;
};

#define X3DEventIn struct _intX3DEventIn
#define X3DEventOut struct _intX3DEventIn
/* single value structures */


X3DNode *X3D_getNode (char *name);
X3DEventIn *X3D_getEventIn(X3DNode *node, char *name);
X3DEventOut *X3D_getEventOut(X3DNode *node, char *name);
void X3D_setValue (X3DEventIn *dest, X3DNode *node);
void X3D_addRoute (X3DEventOut *from, X3DEventIn *to);
void X3D_deleteRoute (X3DEventOut *from, X3DEventIn *to);


/* initialize, shutdown public methods */
void X3D_initialize(char *);
void X3D_shutdown();
#ifdef WIN32
void* freewrlReadThread(void *);
void* freewrlSwigThread(void *);
int findFieldInFIELDTYPES(char *fieldtype);
void X3D_freeNode(X3DNode* node);
X3DNode* X3D_getValue (X3DEventOut *src);
#else
void freewrlReadThread(void);
void freewrlSwigThread(void);
#endif

/* float public methods */
float X3D_getCurrentSpeed();
float X3D_getCurrentFrameRate();

/* null public methods */
void X3D_firstViewpoint();
void X3D_lastViewpoint();
void X3D_nextViewpoint();
void X3D_previousViewpoint();
void X3D_setDescription(char *newDescription);

/* string return val public methods */
char *X3D_getDescription();
char *X3D_getName();
char *X3D_getVersion();
char *X3D_getWorldURL();

/* MFNode public methods */
X3DNode *X3D_createVrmlFromString(char *str);
X3DNode *X3D_newSFVec3f (float a, float b, float c);
X3DNode *X3D_newSFColor (float a, float b, float c);
X3DNode *X3D_newSFVec2f (float a, float b);
X3DNode *X3D_newSFRotation (float a, float b,float c, float d);
X3DNode *X3D_newSFColorRGBA (float a, float b,float c, float d);
X3DNode *X3D_newSFVec3d (double a, double b,double c);
X3DNode *X3D_newSFVec2d (double a, double b);
X3DNode *X3D_newSFBool (int a);
X3DNode *X3D_newSFFloat (float a);
X3DNode *X3D_newSFTime (double a);
X3DNode *X3D_newSFInt32 (int a);
X3DNode *X3D_newSFString();
X3DNode *X3D_newSFNode();
X3DNode *X3D_newSFImage();
X3DNode *X3D_newMFColor();
X3DNode *X3D_newMFFloat();
X3DNode *X3D_newMFTime();
X3DNode *X3D_newMFInt32();
X3DNode *X3D_newMFString();
X3DNode *X3D_newMFNode();
X3DNode *X3D_newMFRotation();
X3DNode *X3D_newMFVec2f();
X3DNode *X3D_newMFVec3f();
X3DNode *X3D_newMFColorRGBA();
X3DNode *X3D_newMFBool();
X3DNode *X3D_newMFVec3d();
X3DNode *X3D_newMFVec2d();

extern int _X3D_queryno;
extern int _X3D_FreeWRL_FD;
extern int _X3D_FreeWRL_Swig_FD;
extern int isSwig;
int _X3D_countWords(char *ptr);
char *_X3D_make1StringCommand (char command, char *name);
char *_X3D_make2StringCommand (char command, char *str1, char *str2);
char *_X3D_Browser_SendEventType(int adr,char *name, char *evtype);
char *_X3D_makeShortCommand (char command);
void _X3D_sendEvent (char command, char *string);
void _handleFreeWRLcallback(char *command);

void X3D_error(char *msg);
char *fieldTypeName(char type);

char * _RegisterListener (X3DEventOut *node, int adin);
int X3DAdvise (X3DEventOut *node, void *fn);
void _handleReWireCallback(char *buf);
char mapFieldTypeToEAItype (int st);
int mapEAItypeToFieldType (char st);
void sendMIDITableToFreeWRL(char *buf);
void sendMIDIControlToFreeWRL(long relativeSamplePos, int bus, int channel, int controller, int value);
X3DNode* X3D_getValue(X3DEventOut *src);
void X3D_freeEventIn(X3DEventIn* ev);
void X3D_freeEventOut(X3DEventOut* ev);
void X3D_freeNode(X3DNode* node);
X3DNode* X3D_newSFVec3f(float a, float b, float c);
void X3D_getSFVec3f(X3DNode* node, float* value);
X3DNode* X3D_newColor(float a, float b, float c);
void X3D_getSFColor(X3DNode* node, float* value);
X3DNode* X3D_newSFVec2f(float a, float b);
void X3D_getSFVec2f(X3DNode* node, float* value);
X3DNode* X3D_newSFRotation(float a, float b, float c, float d);
void X3D_getSFRotation(X3DNode* node, float* value);
X3DNode* X3D_newSFColorRGBA(float a, float b, float c, float d);
void X3D_getSF(X3DNode* node, float* value);
X3DNode* X3D_newSFBool(int a);
void X3D_getSFBool(X3DNode* node, int* value);
X3DNode* X3D_newSFFloat(float a);
void X3D_getSFFloat(X3DNode* node, float* value);
void X3D_getSFTime(X3DNode* node, double* value);
X3DNode* X3D_newSFTime(double a);
X3DNode* X3D_newSFInt32(int a);
void X3D_getSFInt32(X3DNode* node, int* value);
X3DNode* X3D_newSFString(char* a);
char* X3D_getSFString(X3DNode* node);
X3DNode* X3D_newMFInt32(int num, int* array);
void X3D_getMFInt32(X3DNode*, int** array, int* num);
X3DNode* X3D_newMFFloat(int num, float* array);
void X3D_getMFFloat(X3DNode*, float** array, int* num);
X3DNode* X3D_newMFVec3f(int num, float(* array)[3]);
void X3D_getMFVec3f(X3DNode*, float*** array, int* num);
X3DNode* X3D_newMFColor(int num, float(* array)[3]);
void X3D_getMFColor(X3DNode*, float*** array, int* num);
X3DNode* X3D_newMFVec2f(int num, float(* array)[2]);
void X3D_getMFVec2f(X3DNode*, float*** array, int* num);
X3DNode* X3D_newMFRotation(int num, float(* array)[4]);
void X3D_getMFRotation(X3DNode*, float*** array, int* num);
X3DNode* X3D_newMFColorRGBA(int num, float(* array)[4]);
void X3D_getMFColorRGBA(X3DNode*, float*** array, int* num);
X3DNode* X3D_newMFBool(int num, int* array);
void X3D_getMFBool(X3DNode*, int** array, int* num);
X3DNode* X3D_newMFVec3d(int num, double(* array)[3]);
void X3D_getMFVec3d(X3DNode*, double*** array, int* num);
X3DNode* X3D_newMFString(int num, char array[][STRLEN]);
void X3D_getMFString(X3DNode* node, char*** array, int* num);
X3DNode* X3D_newSFNode();
X3DNode* X3D_newSFImage();
X3DNode* X3D_newMFNode();
