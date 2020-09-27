#include <iostream>

class Parent{
public:	
	virtual ~Parent(){
		std::cout << "Parent->dtor"<<std::endl;
	}
	
	void f(){
		std::cout << "Parent->f"<<std::endl;
	}
	
	virtual void g(){
		std::cout << "Parent->g"<<std::endl;
	}
	
	virtual void h() = 0;
};

class Child : public Parent{
public:
	~Child(){
		std::cout << "Child->dtor"<<std::endl;
	}
	
	void f(){
		std::cout << "Child->f"<<std::endl;
	}
	
	void g() override{
		std::cout << "Child->g"<<std::endl;
	}

	void h() override{
		std::cout << "Child->h"<<std::endl;
	}
};


int main(){
	Child c;
	Parent& p = c;
	p.f();
	p.g();
	p.h();
}