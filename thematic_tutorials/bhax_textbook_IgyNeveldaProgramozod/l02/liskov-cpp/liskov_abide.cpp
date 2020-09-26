#include <iostream>
#include <stdexcept>

class Bird{
public:

    virtual ~Bird() = default;
    
	virtual void lay_egg() = 0;
	
};

class FlyingBird : public Bird{
public:	
	virtual ~FlyingBird() = default;
	
	virtual void fly() = 0;
};

class Eagle : public FlyingBird {
public:
	~Eagle(){
		std::cout<< "dtor Eagle" << std::endl;
	}
	
	virtual void fly() override{
    	std::cout<< "fly Eagle" << std::endl;
	}
	
	virtual void lay_egg() override{
		std::cout<< "egg Eagle" << std::endl;
	}
};

class Penguin : public Bird {
public:
	~Penguin(){
		std::cout<< "dtor Penguin" << std::endl;
	}
	
	virtual void lay_egg() override{
		std::cout<< "egg Penguin" << std::endl;
	}
};

int main(){
	FlyingBird* fb = new Eagle();
	fb->lay_egg();
	fb->fly();
	delete fb;
	Bird* b = new Penguin();
	b->lay_egg();
	delete b;
}