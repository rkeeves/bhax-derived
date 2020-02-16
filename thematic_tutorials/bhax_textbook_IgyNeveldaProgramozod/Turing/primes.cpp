#include <bits/stdc++.h> 
#include <algorithm>
#include <vector>
  // Work in progress
  // DONT LOOK
  // PLEASE
  // 
  // left a lot of junk because of the powershell issue came
  // Note to self: Never say again anything about win7 :D
  // Greta Thurnberg away!

std::vector<int> get_primes(int n) 
{ 
    std::vector<bool> prime(n+1,true); 
    prime[0] = false; 
    prime[1] = false; 
    int m = sqrt(n); 
    for (int p=2; p<=m; p++) { 
        if (prime[p]) { 
            for (int i=p*2; i<=n; i += p){prime[i] = false; }
        } 
    } 
    std::vector<int> ans; 
    for (int i=0;i<n;i++) 
        if (prime[i]) 
            ans.push_back(i); 
    return ans; 
}

int main()
{
  const int N = 14;
  /* 
  I warned you!
  */
  std::vector<int> primes = get_primes(N);
  std::cout<<"primes"<<std::endl;
  std::for_each(primes.begin(), primes.end(), [](const int& e){std::cout<<e<<" ";});
  std::cout<<std::endl;
  std::vector<int> t1;
  if(primes.size() > 1){
    for (int i=1; i<primes.size(); i++) { 
      if(2==(primes[i]-primes[i-1])){
        t1.push_back(primes[i-1]);
      }
    }
  }
  std::cout<<"t1"<<std::endl;
  std::for_each(t1.begin(), t1.end(), [](const int& e){std::cout<<e<<" ";});
  std::cout<<std::endl;
  std::vector<double> rec;
  for (int i=0; i<t1.size(); i++) { 
    rec.push_back(1.0/t1[i]+1.0/(t1[i]+2));
  }
  std::cout<<"rec"<<std::endl;
  std::for_each(rec.begin(), rec.end(), [](const int& e){std::cout<<e<<" ";});
  std::cout<<std::endl
  
  return 0;
}