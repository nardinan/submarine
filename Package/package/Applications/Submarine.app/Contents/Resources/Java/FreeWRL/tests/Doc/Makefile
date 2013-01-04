## $Id: Makefile,v 1.5 2002/05/28 17:46:47 ayla Exp $
##
## Makefile for LaTeX documentation.

all: dvi

.PHONY: all clean realclean spotless

day=`date +%d-%m-%Y`
bak_dir=./bak

DOCS =\
       vts_local.tex\
       vts.tex

INC =\
      Appearance.tex\
      Bindable-Nodes.tex\
      Geometric-Properties.tex\
      Geometry.tex\
      Grouping-Nodes.tex\
      Interpolators.tex\
      Lights.tex\
      Miscellaneous.tex\
      Sensors.tex\
      Sounds.tex\
      Special-Groups.tex

AUX =\
     ${DOCS:.tex=.aux}\
     ${INC:.tex=.aux}
DVI = ${DOCS:.tex=.dvi}
LOG = ${DOCS:.tex=.log}
PS  = ${DOCS:.tex=.ps}
PDF = ${DOCS:.tex=.pdf}

##TOC = ${DOCS:.tex=.toc}

vts: vts.dvi

vts-ps: vts.ps

vts-pdf: vts.pdf

local: vts_local.dvi

local-ps: vts_local.ps

local-pdf: vts_local.pdf

dvi: ${DVI}

ps: ${PS}

pdf: ${PDF}

clean:
	-rm ${LOG} ${PS} ${PDF}

realclean: clean
	-rm ${DVI} ${AUX}

spotless: realclean
	-rm -Rf ${bak_dir}
	-rm -f *~

display: dvi
	xdvi vts.dvi

display-local: dvi
	xdvi vts_local.dvi

bak_dir:
	-mkdir -p ${bak_dir}

## Implicit rules:

%.dvi: %.tex force
	latex $<

%.ps: %.dvi
	dvips -o $@ $<

%.pdf: %.ps
	ps2pdf $< $@

# empty target forces dependent targets to run their commands
force:
