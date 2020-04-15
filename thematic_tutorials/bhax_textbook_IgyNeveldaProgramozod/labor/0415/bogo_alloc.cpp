#include <iostream>
#include <string>
#include <vector>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
class Arena
{
public:
  char* freememp;
  Arena(char* memp, int memsize)
  : memp(memp), memsize(memsize)
  {
    freememp = memp+1000;
  }
  
private:
  char* memp;
  int memsize;
};

template<typename T>
class CustomAlloc
{
public:  
  using size_type =size_t ;
  using pointer = T*;
  using const_pointer = const T*;
  using value_type = T;
  
  CustomAlloc(Arena& arena)
  : arena(arena)
  {
    
  }
  
  pointer allocate(size_type n)
  {
    std::cout<<"Allocating "<<n<<" of objects "
    << n*sizeof(value_type)<<std::endl;

    //return reinterpret_cast<pointer>(
    //new char[n*sizeof(value_type)]);
    
    
    char* retmemp = arena.freememp;
    arena.freememp += n*sizeof(value_type);
    return reinterpret_cast<pointer>(retmemp);
  }
  
  void deallocate(pointer p, size_type s)
  {
   std::cout<<"Deallocating "<<s<<" of objects "
    << s*sizeof(value_type)<<std::endl;
    //delete[] reinterpret_cast<char*>(p);
  }
private:
  Arena& arena;
};

int main()
{
  const int Megabytes = 1024*1024*10;
  int shmid;
  char* shmp;
  shmid = shmget(2356, Megabytes,IPC_CREAT|S_IRUSR|S_IWUSR);
  
  shmp = (char *) shmat(shmid,NULL,0);
  
  Arena arena{shmp,Megabytes};
  CustomAlloc<int> allocobj(arena);
  
  using TP = std::vector<int,CustomAlloc<int>>;
  
  if(fork() == 0)
  {
    TP* vp = new(shmp) TP(allocobj);
    v->push_back(42);
    v->push_back(43);
    v->push_back(44);
    
    shmdt(shmp);
  }else{
    sleep(1); // 1 seconds
    TP* vp = (TP*) shmp;
    std::cout<< (*vp)[0] << std::endl;
    std::cout<< (*vp)[1] << std::endl;
    std::cout<< (*vp)[2] << std::endl;
    shmdt(shmp);
  }
  return 0;
}