#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int eestrtab (char **string) {
	char *position = *string, *stringbackup = NULL;
	unsigned short spaces = 0;
	while ((strlen(position) > 0) && ((*position == ' ') || (*position == '\t'))) { position++; spaces++; }
	if ((spaces > 0) && (strlen(position) > 0)) {
		if ((stringbackup = (char *) malloc (strlen(position)+1))) {
			strcpy(stringbackup, position);
			free(*string);
			*string = stringbackup;
		} else return 1;
	}
	return 0;
}

int analyze (const char *inputfile) {
	FILE *filestream = fopen(inputfile, "r");
	FILE *fileoutput = fopen("./output.txt", "w");
	char *stringbackup = NULL, *command = NULL, *information = NULL, *category = NULL;
	bool endofreader = false, endofsegment = true;
	int result = 0, counter = 0;
	if (filestream) {
		do {
			if ((stringbackup = (char *) malloc (1024+1))) {
				if (fgets(stringbackup, 1024, filestream)) {
                    if (eestrtab(&stringbackup) != 0) result = 1;
					if (strlen(stringbackup) > 0) {
						stringbackup[strlen(stringbackup)-1]='\0';
                        			if ((!endofsegment) && (strncmp(stringbackup, "<td bgcolor=\"#669999\" align=\"right\" valign=\"top\">", strlen("<td bgcolor=\"#669999\" align=\"right\" valign=\"top\">")) == 0)) {
                            				endofsegment = true;
                            				fprintf(fileoutput, "</tr></table>");
                        			} else if ((endofsegment) && (strncmp(stringbackup, "</tr>", strlen("</tr>")) == 0)) {
                            				endofsegment = false;
							counter++;
                            				fprintf(fileoutput, "\n\n\n[=DISTANCE=]\n\n\n<table>");
                        			} else if (!endofsegment) {
                            				fprintf(fileoutput, stringbackup);
                        			}
					}
				} else endofreader = true;
			} else result = 1;
			if (stringbackup) { free(stringbackup); stringbackup = NULL; }
		} while ((result == 0) && (!endofreader));
		fclose(filestream);
        	fclose(fileoutput);
	} else result = 1;
	printf("%d readed structures\n", counter);
	return result;
}

int main (int argc, char *argv[]) {
    return analyze("./input.html");
}
