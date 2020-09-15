# ...
RELAXNG_LOCAL = /home/rkeeves/dev/relaxng/docbookxi.rng

all:	clean validate build cleanuptemp
	
build: bhax-textbook-fdl.xml bhax-textbook.xsl
	dblatex bhax-textbook-fdl.xml -p bhax-textbook.xsl

.PHONY: validate
validate:
	xmllint --xinclude bhax-textbook-fdl.xml --output output.xml
	xmllint --relaxng $(RELAXNG_LOCAL) output.xml --noout
	
	
.PHONY: clean
clean:
	rm -f bhax-textbook-fdl.pdf output.xml

cleanuptemp:
	rm -f output.xml	
