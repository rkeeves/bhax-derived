# This is a really simplistic makefile
# For real projects use CMake, or Premake5

CXX = g++
CXXFLAGS = -Wall -g

rebuild: clean build

build: polargenmain.o polargen.o
	$(CXX) $(CXXFLAGS) -o polargenprog polargenmain.o polargen.o

polargenmain.o: polargenmain.cpp polargen.h
	$(CXX) $(CXXFLAGS) -c polargenmain.cpp
	
polargen.o: polargen.h

clean:
	rm -f polargenprog polargenmain.o polargen.o
