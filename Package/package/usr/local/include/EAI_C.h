
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



#ifndef __EAI_C_HEADERS__
#define __EAI_C_HEADERS__
#include <sys/types.h>
#include <stdint.h>
#include <EAIHeaders.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <strings.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>


#include <stdint.h>
#include <math.h>
#include <stddef.h>

#include <X3DNode.h>

#include <GeneratedHeaders.h>

/* copied from ../CFuncs/ */
typedef size_t indexT;
#define ARR_SIZE(arr) (int)(sizeof(arr)/sizeof((arr)[0]))
/* Table of built-in fieldIds */
extern const char *FIELDTYPES[];
extern const indexT FIELDTYPES_COUNT;


/* definitions to help scanning values in from a string */
#define SCANTONUMBER(value) while ((*value==' ') || (*value==',')) value++;
#define SCANTOSTRING(value) while ((*value==' ') || (*value==',')) value++;
#define SCANPASTFLOATNUMBER(value) while (isdigit(*value) \
		|| (*value == '.') || \
		(*value == 'E') || (*value == 'e') || (*value == '-')) value++;
#define SCANPASTINTNUMBER(value) if (isdigit(*value) || (*value == '-')) value++; \
		while (isdigit(*value) || \
		(*value == 'x') || (*value == 'X') ||\
		((*value >='a') && (*value <='f')) || \
		((*value >='A') && (*value <='F')) || \
		(*value == '-')) value++;

/*cstruct*/
struct Multi_Float { size_t n; float  *p; };
struct SFRotation { float r[4]; };
struct Multi_Rotation { size_t n; struct SFRotation  *p; };

struct Multi_Vec3f { size_t n; struct SFColor  *p; };
/*cstruct*/
struct Multi_Bool { size_t n; int  *p; };
/*cstruct*/
struct Multi_Int32 { size_t n; int  *p; };

struct Multi_Node { size_t n; void * *p; };
struct SFColor { float c[3]; };
struct Multi_Color { size_t n; struct SFColor  *p; };
struct SFColorRGBA { float r[4]; };
struct Multi_ColorRGBA { size_t n; struct SFColorRGBA  *p; };
/*cstruct*/
struct Multi_Time { size_t n; double  *p; };
/*cstruct*/
struct Multi_String { size_t n; struct Uni_String * *p; };
struct SFVec2f { float c[2]; };
struct Multi_Vec2f { size_t n; struct SFVec2f  *p; };
/*cstruct*/
/*cstruct*/
struct SFVec3d { double c[3]; };
struct Multi_Vec3d { size_t n; struct SFVec3d  *p; };
/*cstruct*/
struct Multi_Double { size_t n; double  *p; };
struct SFMatrix3f { float c[9]; };
struct Multi_Matrix3f { size_t n; struct SFMatrix3f  *p; };
struct SFMatrix3d { double c[9]; };
struct Multi_Matrix3d { size_t n; struct SFMatrix3d  *p; };
struct SFMatrix4f { float c[16]; };
struct Multi_Matrix4f { size_t n; struct SFMatrix4f  *p; };
struct SFMatrix4d { double c[16]; };
struct Multi_Matrix4d { size_t n; struct SFMatrix4d  *p; };
struct SFVec2d { double c[2]; };
struct Multi_Vec2d { size_t n; struct SFVec2d  *p; };
struct SFVec4f { float c[4]; };
struct Multi_Vec4f { size_t n; struct SFVec4f  *p; };
struct SFVec4d { double c[4]; };
struct Multi_Vec4d { size_t n; struct SFVec4d  *p; };

/*cstruct*/
/*cstruct*/

struct Uni_String {
        size_t len;
        char * strptr;
	int touched;
};


#define FREE_IF_NZ(a) if(a) {free(a); a = 0;}
#endif
