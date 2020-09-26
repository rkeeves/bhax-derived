#include <iostream>
#include <stdexcept>

class Bird{
public:

    virtual ~Bird() = default;
    
	virtual void lay_egg() = 0;
	
	virtual void fly() = 0;
};

class Eagle : public Bird {
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
	
	virtual void fly() override{
		throw std::runtime_error("No fly today boyo");
	}
	
	virtual void lay_egg() override{
		std::cout<< "egg Penguin" << std::endl;
	}
};

int main(){
	
	Bird* b = nullptr;
	try{
		b = new Eagle();
		b->lay_egg();
		b->fly();
		delete b;
		b = new Penguin();
		b->lay_egg();
		b->fly();
	}catch(const std::runtime_error& e){
		std::cout << "Oops: " << e.what() << std::endl;
	}
	if(b != nullptr){
	    delete b;
	}
}