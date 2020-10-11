#include <jni.h> 
#include <iostream>
#include "com_rkeeves_HelloJNI.h" 

JNIEXPORT void JNICALL Java_com_rkeeves_HelloJNI_sayHello(JNIEnv* env, jobject thisObj) {
	std::cout << "Hello World from C++!" << std::endl;
	return;
}