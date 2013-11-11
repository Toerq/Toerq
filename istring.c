////#include "istring.h"
#include <assert.h>
#ifndef ISTRING_H
#define ISTRING_H
#include <stdlib.h>
#define intsize = sizeof(int)
#define START(P) ((istring) (P - sizeof(int)))
//#define STARTWTF(P) ((istring) (P - 4*intsize))
#define STRING(P) (((char*) P) + sizeof(int)) //p[4] ist för *(P+4)
//hjälpmakron, START(p) och STRING(p). Det första omvandlar en char*-pekare till en pekare till (”starten av”) en istring och STRING gör det omvända. All pekararitmetik i modulen skall vara inkapslad i dessa makron.
#include <stdio.h>

#include <string.h>

typedef struct istring {
  int length;
  char text[];
} _istring, *istring;


/*
 * Returns a new istring instance created from supplied
 * string. Returns NULL if out of memory. Returns NULL string if
 * argument str is NULL. 
 */

char *istring_mk(const char* str) {
  //if (str == NULL) return NULL;
  int length = strlen(str);
  _istring *string = malloc(length + 1 + sizeof(int));
  START(string)->length = length;
    strcpy(string->text, str);
    
    return STRING(string);
}

/*
 * Deallocates the supplied istring.
 */
void istring_rm(char *str) {
  free(START(str));
}

/*
 * Returns a standard null terminated char* representation of the
 * supplied istring. Returns NULL if out of memory.
 */
char *istring_to_string(const char *str) {
  char* newString = "";
  strcpy(newString, str);
  //(istring) str - 5;
  free(START(str));
  return newString;
}

/*
 * Inspects the length of the string from str and if it differs from
 * length, updates length accordingly. If str does not contain a \0
 * token, it will be inserted at the first element which is not a
 * printable ascii character, and length updated accordingly. This
 * function must be as efficient as possible and not rescan the string
 * if it can be avoided.
 *
 * This function is useful when istrings have been manipulated as
 * regular C strings, to reestablish the length invariant.
 */
size_t istrfixlen(char *s) {
  int charCounter = 0;
  while(s[charCounter] != '\0') {
    if ((int)s[charCounter] > 255 || (int)s[charCounter] < 0) {
      s[charCounter] = '\0';
      break;
    }
    charCounter++;
  }
  START(s)->length = charCounter; 
  return charCounter;
}

/* 
 * Sets the length of an string and inserts a corresponding '\0'
 * character. If the length argument is out of bounds for the string,
 * a new istring should be returned with the contents of the original
 * string. The last character of the original string will be repeated
 * to fill the string to its given length.
 */

char* istrslen(char *s, size_t length) {
  char replaceChar;
  int replacementCharSet;
  int charCounter;
  assert(START(s)->length > 0);
  START(s)->length = length;
  while(charCounter <= length) {
    if ((int) s[charCounter] > 255 || ((int) s[charCounter] < 0 && !replacementCharSet)) { 
      replacementCharSet = 1;
      replaceChar = s[charCounter];
    }
    s[charCounter] = replaceChar;
    charCounter++;
  }
  s[charCounter] = '\0';
  return s;
}

/*
 * For definitions, see the manual page of respective function on the
 * solaris system and the assignment text.
 * 
 * Think about whether having O(1) access to the length can be used to
 * improve the implementations, if the string.h equivalents and use
 * that to guide your implementations!
 */

//returns a pointer to the first char 'c' in s.
char *istrchr(const char *s, int c) {
  // kan använda *s och kolla om s[*s] är '\0'
  int charCounter = 0;
  do {
    if(s[charCounter] == c) return (s + sizeof(char)*charCounter);
    charCounter++;
  }
  while(s[charCounter] != '\0');
  return 0;
}

//returns a pointer to the last char 'c' in s.
char *istrrchr(const char *s, int c) {
// kan använda *s och kolla om s[*s] är '\0'
  int charCounter = 0;
  char *lastChar;
  int charFound = 0;
  do {
    if(s[charCounter] == c) {
      charFound = 1;
      lastChar = s + charCounter;
    }
    charCounter++;
  }
  while(s[charCounter] != '\0');
  if(charFound != 0) return lastChar;
  return 0;
}

int istrcmp(const char *s1, const char *s2) {
  if(START(s1) != START(s2)) return 0; 
  int charCounter = 0;
  do {
    if(s1[charCounter] != s2[charCounter]) return 0;
    charCounter++;
  }
    while(s1[charCounter] != '\0' && s2[charCounter] != '\0');
  return 1;
}

int istrncmp(const char *s1, const char *s2, size_t n) {
  if(START(s1)->length != START(s2)->length && 
     START(s1)->length < n) return 0;  
  int charCounter = 0;
  do {
    if(s1[charCounter] != s2[charCounter]) return 0;
    charCounter++;
  }
  while(s1[charCounter] != '\0' && s2[charCounter] != '\0' && charCounter <= n);
  return 1;
}


  
size_t istrlen(const char *s) {
  return START(s)->length;
}
 /*
 * I nedanstående funktioner är dst en pekare till en vanlig
 * sträng och inte en istring. Däremot skall minnesutrymmet
 * "konverteras" till en istring av funktionerna, d.v.s. efter att
 * t.ex. istrcpy anropats bör man vid anropsplatsen göra dst =
 * STRING(dst) för att hoppa över längd-delen av strängen.
*/
char *istrcpy(char *dst, const char *src) {
  int length = 0;
  dst = dst + sizeof(int);
  length = START(src)->length;
  strcpy(dst,src);
  START(dst)->length = length;
  return dst; 
}
char *istrncpy(char *dst, const char *src, size_t n) {
  int length = 0;
  dst = dst + sizeof(int);
  length = START(src)->length;
  strncpy(dst,src,n);
  START(dst)->length = length;
  return dst;
}

char *istrcat(char *dst, const char *src) {
  int length = 0;
  length = (START(src)->length + strlen(dst));
  dst = strcat(dst,src);
  START(dst)->length = length;
  return dst;
}
  char *istrncat(char *dst, const char *src, size_t n) {
  int length = 0;
  length = (START(src)->length + strlen(dst));
  dst = strncat(dst,src,n);
  START(dst)->length = length;
  return dst;
}



#endif

 

//int main() {
// return 0;
//}
