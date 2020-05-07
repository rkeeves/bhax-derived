#include "z3a18qa5_from_scratch.h"

#include <fstream>
#include <set>
#include <iomanip>


class COVID19Var {

    private:
        std::string loc;
        std::string date;
        std::string id;        
        ZLWTree<char, '/', '0'> zlwBt;        

    public:
        COVID19Var(std::string id, std::string loc, std::string date):id(id), loc(loc), date(date)
        {
            std::ifstream ifs{std::string("genomes/")+id+loc+date};
            std::istreambuf_iterator<char> isbiEnd;

            
            for(std::istreambuf_iterator<char> isbi{ifs}; isbi != isbiEnd; ++isbi)
                zlwBt << *isbi;
        
            zlwBt.eval();                  
        }    
        
        void print() {zlwBt.print();}
        
        bool operator<(const COVID19Var & other) const { return zlwBt < other.zlwBt;}
        
        double getMean() const {return zlwBt.getMean();}
        double getVar() const {return zlwBt.getVar();}        
        std::string getName() const {return id+std::string(" ")+loc+std::string(" ")+date;}        
        
};

int main()
{
    
    COVID19Var cv1{"MN908947", "China", "2019-12"};
    COVID19Var cv2{"MT019529", "China:Wuhan", "2019-12-23"};
    COVID19Var cv3{"LR757998", "China:Wuhan", "2019-12-26"}; 	
    COVID19Var cv4{"MN996527", "China:Wuhan", "2019-12-30"}; 	
    COVID19Var cv5{"MN938384", "China:Shenzhen", "2020-01-10"}; 	
    COVID19Var cv6{"MT259226", "China:Hubei,Wuhan", "2020-01-10"};    
    COVID19Var cv7{"MN985325", "USA:WA", "2020-01-19"};
    COVID19Var cv8{"MT039873", "China:Hangzhou", "2020-01-20"};
    COVID19Var cv9{"MN988713", "USA:IL", "2020-01-21"};
    COVID19Var cv10{"MN994468", "USA:CA", "2020-01-22"};
    COVID19Var cv11{"MT027062", "USA:CA", "2020-01-29"};    
    COVID19Var cv12{"MT258383", "USA:CA", "2020-03-18"};
    COVID19Var cv13{"MT263459", "USA:WA", "2020-03-24"};    
    COVID19Var cv14{"MT044257", "USA:IL", "2020-01-28"};
    COVID19Var cv15{"MT276326", "USA:GA", "2020-02-29"}; 
    
    COVID19Var cv16{"AF0868332", "EbolaMayingaZaire", "1976"};     
    COVID19Var cv17{"KY7860271", "Ebola", "2001"};
    
    std::set<COVID19Var> cs;
    cs.insert(cv1);
    cs.insert(cv2);
    cs.insert(cv3);
    cs.insert(cv4);
    cs.insert(cv5);
    cs.insert(cv6);
    cs.insert(cv7);
    cs.insert(cv8);
    cs.insert(cv9);
    cs.insert(cv10);
    cs.insert(cv11);
    cs.insert(cv12);
    cs.insert(cv13);
    cs.insert(cv14);
    cs.insert(cv15);

    cs.insert(cv16);
    cs.insert(cv17);
    
    for(std::set<COVID19Var>::iterator it=cs.begin(); it!=cs.end(); ++it)
    {        
        std::cout << std::setw(3)<< std::distance(cs.begin(), it)+1 << ". " 
        << std::setw(8) << (*it).getVar() << " " << std::setw(8) << (*it).getMean() << " " << (*it).getName() << std::endl;
    }
    
}
