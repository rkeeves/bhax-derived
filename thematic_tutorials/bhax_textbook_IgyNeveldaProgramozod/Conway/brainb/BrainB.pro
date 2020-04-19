 #
 # @brief Benchmarking Cognitive Abilities of the Brain with Computer Games
 #
 # @file BrainB.pro
 # @author  Norbert Bátfai <nbatfai@gmail.com>
 # @version 0.0.1
 #
 # @section LICENSE
 #
 # Copyright (C) 2017 Norbert Bátfai, nbatfai@gmail.com
 #
 # This program is free software: you can redistribute it and/or modify
 # it under the terms of the GNU General Public License as published by
 # the Free Software Foundation, either version 3 of the License, or
 # (at your option) any later version.
 #
 # This program is distributed in the hope that it will be useful,
 # but WITHOUT ANY WARRANTY; without even the implied warranty of
 # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 # GNU General Public License for more details.
 #
 # You should have received a copy of the GNU General Public License
 # along with this program.  If not, see <http://www.gnu.org/licenses/>.
 #
 # @section DESCRIPTION
 #
 #
 
QT += widgets core
CONFIG += c++11 c++14 c++17
# QMAKE_CXXFLAGS += -fopenmp
# win32:LIBS += -fopenmp 

TEMPLATE = app
TARGET = BrainB
INCLUDEPATH += .

HEADERS += BrainBThread.h  BrainBWin.h
SOURCES += BrainBThread.cpp  BrainBWin.cpp  main.cpp

INCLUDEPATH += C:\opencv430\build-mingw\install\include

OPENCV_LIB_DIR = C:\opencv430\build-mingw\install\x64\mingw\lib

LIBS += $$OPENCV_LIB_DIR\libopencv_calib3d430.dll.a
LIBS += $$OPENCV_LIB_DIR\libopencv_core430.dll.a
LIBS += $$OPENCV_LIB_DIR\libopencv_dnn430.dll.a
LIBS += $$OPENCV_LIB_DIR\libopencv_features2d430.dll.a
LIBS += $$OPENCV_LIB_DIR\libopencv_flann430.dll.a
LIBS += $$OPENCV_LIB_DIR\libopencv_gapi430.dll.a
LIBS += $$OPENCV_LIB_DIR\libopencv_highgui430.dll.a
LIBS += $$OPENCV_LIB_DIR\libopencv_imgcodecs430.dll.a
LIBS += $$OPENCV_LIB_DIR\libopencv_imgproc430.dll.a
LIBS += $$OPENCV_LIB_DIR\libopencv_ml430.dll.a
LIBS += $$OPENCV_LIB_DIR\libopencv_objdetect430.dll.a
LIBS += $$OPENCV_LIB_DIR\libopencv_photo430.dll.a
LIBS += $$OPENCV_LIB_DIR\libopencv_stitching430.dll.a
LIBS += $$OPENCV_LIB_DIR\libopencv_video430.dll.a
LIBS += $$OPENCV_LIB_DIR\libopencv_videoio430.dll.a
