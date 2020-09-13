# ...
RELAXNG_LOCAL = /home/rkeeves/dev/relaxng/docbookxi.rng

all:	clean validate build
	
build: prog2-textbook.xml prog2-textbook.xsl
	dblatex prog2-textbook.xml -p prog2-textbook.xsl

.PHONY: validate
validate:
	xmllint --xinclude prog2-textbook.xml --output output.xml
	xmllint --relaxng $(RELAXNG_LOCAL) output.xml --noout
	rm -f output.xml	
	
.PHONY: clean
clean:
	rm -f prog2-textbook.pdf output.xml

