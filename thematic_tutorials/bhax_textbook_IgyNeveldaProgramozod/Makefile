
RM = rm -f
RELAXNG = C:/Users/UBMCGU/home/relaxng/docbookxi.rng
PDF_TARGET = bhax-textbook_fdl.pdf
VALIDATE_OUTFILE = validate_output.xml
PY_ROOT = C:/Python27
PY = $(PY_ROOT)/python.exe
DBLATEX = $(PY_ROOT)/Scripts/dblatex

all:	clean validate fdl

build: $(PDF_TARGET)
	
$(PDF_TARGET): bhax-textbook-fdl.xml bhax-textbook.xsl
	$(PY) $(DBLATEX) bhax-textbook-fdl.xml -p bhax-textbook.xsl
	
.PHONY: validate
validate:
	xmllint --xinclude bhax-textbook-fdl.xml --output $(VALIDATE_OUTFILE)
	xmllint --relaxng $(RELAXNG) $(VALIDATE_OUTFILE) --noout
	$(RM) $(VALIDATE_OUTFILE)
	
.PHONY: clean
clean:
	$(RM) $(PDF_TARGET)
