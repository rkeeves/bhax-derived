#include <iostream>
#include <string>
#include <vector>
#include <utility>
#include <functional>
#include <algorithm>

// #define LOG_NODE_LIFECYCLE

#define LOG_TREE_LIFECYCLE

template<typename V>
class BTree;

template<typename V>
class SearchTree;

template<typename V>
class LZWTree;

template<typename V>
class BNode
{
// [LIFECYCLE]
public:
  BNode(V v) : v(v), l(nullptr), r(nullptr), dup(0)
  { 
    #ifdef LOG_NODE_LIFECYCLE
      std::cout<<"node ctor"<< static_cast<void*>(this) << std::endl;
    #endif
  }

private:
  BNode(const BNode&);
  BNode& operator=(const BNode&);
  BNode(BNode&&);
  BNode& operator=(BNode&&);
// [API]
public:
  unsigned int get_duplication_count() const { return dup; }
  bool has_left() const{ return (l!=nullptr);}
  bool has_right() const{ return (r!=nullptr);}
  bool is_leaf() const{ return ((r==nullptr)&&(l==nullptr));}
  V get_value() const { return v;}
// [STATE]
private:
  V v;
  BNode* l;
  BNode* r;
  unsigned int dup;

friend class BTree<V>;
friend class SearchTree<V>;
friend class LZWTree<V>;
};

template<typename V>
class BTree
{
protected:
  
// [LIFECYCLE]
public:
  BTree() : root(nullptr), cur(root)
  {
    #ifdef LOG_TREE_LIFECYCLE
      std::cout<<"btree ctor "<< static_cast<void*>(this) << std::endl;
    #endif
  }
  
  virtual ~BTree() 
  { 
    del_subtree(root);
    #ifdef LOG_TREE_LIFECYCLE
      std::cout<<"btree dtor "<< static_cast<void*>(this) << std::endl;
    #endif
  }

  BTree(const BTree& old)
  {
    #ifdef LOG_TREE_LIFECYCLE
      std::cout<<"btree copy ctor "<< static_cast<void*>(this) << std::endl;
    #endif
    root = deep_copy(old.root ,old.cur);
  }
  
  BTree& operator=(const BTree& old)
  {
    #ifdef LOG_TREE_LIFECYCLE
      std::cout<<"btree copy assign "<< static_cast<void*>(this) << std::endl;
    #endif
    BTree tmp(old);
    std::swap(*this,tmp);
    return *this;
  }
  
  BTree(BTree&& old)
  {
    #ifdef LOG_TREE_LIFECYCLE
      std::cout<<"btree move ctor "<< static_cast<void*>(this) << std::endl;
    #endif
    root = nullptr;
    *this = std::move(old);
  }
  
  BTree& operator=(BTree&& old)
  {
    #ifdef LOG_TREE_LIFECYCLE
      std::cout<<"btree move assign "<< static_cast<void*>(this) << std::endl;
    #endif
    std::swap(old.root, root);
    std::swap(old.cur, cur);
    return *this;
  }
// [API]
public:
    
  virtual BTree& operator<<(V val){ return *this;}
  
  template<typename F>
	void foreach_inorder(F f){ foreach_inorder(0,root, f);  }
  
  template<typename F>
	void foreach_postorder(F f){ foreach_postorder(0,root, f); }
  
  template<typename F>
	void foreach_preorder(F f){  foreach_preorder(0,root, f); }
  
private:
  template<typename F>
	void foreach_inorder(int depth, BNode<V>* n, F f)
  { 
    if(n){
      foreach_inorder(depth+1,n->r,f);
      f(depth,n->v,n->dup, n->is_leaf());
      foreach_inorder(depth+1,n->l,f);
    }
  }
  
  template<typename F>
	void foreach_postorder(int depth, BNode<V>* n, F f)
  { 
    if(n){
      foreach_postorder(depth+1,n->r,f);
      foreach_postorder(depth+1,n->l,f);
      f(depth,n->v,n->dup, n->is_leaf());
    }
  }
  
  template<typename F>
	void foreach_preorder(int depth, BNode<V>* n, F f)
  { 
    if(n){
      f(depth,n->v,n->dup, n->is_leaf());
      foreach_preorder(depth+1,n->r,f);
      foreach_preorder(depth+1,n->l,f);
    }
  }
  
  void del_subtree(BNode<V>* n)
  {
    if(n){
      del_subtree(n->l);
      del_subtree(n->r);
      #ifdef LOG_NODE_LIFECYCLE
        std::cout<<"node delete "<< static_cast<void*>(n) << std::endl;
      #endif
      delete n;
    }
  }
  
  BNode<V>* deep_copy(BNode<V>* old_n, BNode<V>* old_cur)
  {
    BNode<V>* newn = nullptr; 
    if(old_n){
      newn = new BNode<V>(old_n->v);
      newn->l = deep_copy(old_n->l,old_cur);
      newn->r = deep_copy(old_n->r,old_cur);
      if(old_n==old_cur){this->cur=newn;}
    }
    return newn;
  }

// [STATE]
protected:
  BNode<V>* root;
  BNode<V>* cur;
};

template<typename V>
class SearchTree : public BTree<V>
{
// [LIFECYCLE]
public:
  SearchTree() : BTree<V>(){};
  
// [API]
  virtual SearchTree& operator<<(V val) override
  {
    // invalid states
    if( this->root == nullptr && this->cur != nullptr
        || this->root != nullptr && this->cur == nullptr)
    {return *this;}
    // valid, but tree uninited, so we inject root
    if(this->root == nullptr){ this->cur = this->root = new BNode<V>(val); return *this; }
    // valid, tree inited
    if(val == this->cur->v) {
      this->cur->dup++;
    }else{
      if(val<this->cur->v){
        // left
        if(this->cur->l == nullptr){
          this->cur->l = new BNode<V>(val);
        }else{
          this->cur = this->cur->l;
          *this<<val;
        }
      }else{
        // right
        if(this->cur->r == nullptr){
          this->cur->r = new BNode<V>(val);
        }else{
          this->cur = this->cur->r;
          *this<<val;
        }
      }
    }
    this->cur = this->root;
    return *this;
  }
private:
  
// [STATE]
private:

};

template<typename V>
class LZWTree : public BTree<V>
{
// [LIFECYCLE]
public:
  LZWTree(V vr, std::function<bool(V)> predicate_is_left) 
  : BTree<V>(), vr(vr),predicate_is_left(predicate_is_left)
  { 
    #ifdef LOG_TREE_LIFECYCLE
      std::cout<<"lzwree ctor "<< static_cast<void*>(this) << std::endl;
    #endif
  };
  
  virtual ~LZWTree() 
  { 
    #ifdef LOG_TREE_LIFECYCLE
      std::cout<<"lzwree dtor "<< static_cast<void*>(this) << std::endl;
    #endif
  }

  
// [API]
  virtual LZWTree& operator<<(V val) override
  {
    // invalid states
    if( this->root == nullptr && this->cur != nullptr
        || this->root != nullptr && this->cur == nullptr)
    {return *this;}
    // valid, but tree uninited
    // we just inject first the root value
    if(this->root == nullptr){ this->cur = this->root = new BNode<V>(vr); }
    // valid, tree inited
    if(predicate_is_left(val)){
      // left
      if(this->cur->l == nullptr){
        this->cur->l = new BNode<V>(val);
        this->cur = this->root;
      }else{
        this->cur = this->cur->l;
      }
    }else{
      // right
      if(this->cur->r == nullptr){
        this->cur->r = new BNode<V>(val);
        this->cur = this->root;
      }else{
        this->cur = this->cur->r;
      }
    }
    return *this;
  }
// [STATE]
private:
  V vr;
  std::function<bool(V)> predicate_is_left;
};

template<typename V>
void printer(int depth, V node_value, int duplicate_count, bool leaf)
{
  for(int i = 0; i < depth+1; ++i)std::cout<<"---";
  std::cout<<node_value<<" ("<<depth<<")"<<std::endl;
};

typedef struct depthdata_t{
  int leaf_count;
  int max;
  double avg;
  double dev;
}DepthData;

template<typename V>
std::vector<int> collect_depths(BTree<V>& tree){
  std::vector<int> depths;
	auto leaf_depth_counter = [&depths](int depth,V node_value, int duplicate_count, bool leaf)
	{ 
    if(leaf)depths.push_back(depth);
	};
  tree.foreach_inorder(leaf_depth_counter);
  return std::move(depths);
}

DepthData compute_depth_data(const std::vector<int>& depths)
{
  const int leaf_count = depths.size();
  if(leaf_count < 1){return {.leaf_count=0,.max=0,.avg=0,.dev=0};}
  int max = 0;
  double avg = 0.0;
  auto calc = [&max, &avg](const int& n) {if(max<n){max=n;} avg+=n;};
  std::for_each(depths.begin(), depths.end(),calc );
  avg=avg/leaf_count;
  auto calc_dev_sq=[&leaf_count](double sum, double depth){  return sum+depth/leaf_count;};
  double dev = std::accumulate(depths.begin(), depths.end(), 0.0, calc_dev_sq);
  if(leaf_count > 1){dev = sqrt( dev/(leaf_count-1));}
  else{dev = sqrt( dev );}
  return {.leaf_count=leaf_count,.max=max,.avg=avg,.dev=dev};
}

void print_depth_data(const DepthData& dd)
{
  std::cout 
  << "Leaf " << dd.leaf_count << std::endl
  << "Max  " << dd.max << std::endl
  << "Avg  " << dd.avg << std::endl
  << "Dev  " << dd.dev << std::endl;
}

void test_binary();

void test_binary_mv_cp();

void test_lzw_char();

void test_lzw_str();

void test_lzw_cp_mv();

int main(int argc, char** argv, char** env)
{
  
  test_lzw_cp_mv();
}

void test_binary()
{
  SearchTree<int> bt;
  bt<<8<<9<<5<<2<<7<<9;
  std::cout << "BIN INORDER" << std::endl;
  bt.foreach_inorder(printer<int>);
  print_depth_data(compute_depth_data(collect_depths<int>(bt)));
  std::cout << "BIN POSTORDER" << std::endl;
  bt.foreach_postorder(printer<int>);
  std::cout << "BIN PREORDER" << std::endl;
  bt.foreach_preorder(printer<int>);
}

void test_binary_mv_cp()
{
  SearchTree<int> zt;
  zt <<8<<7<<10<<9<<11;
  zt.foreach_inorder(printer<int>);
  SearchTree<int> zt2(zt);
  SearchTree<int> zt3;
  zt3 <<11<<8<<7<<9<<10;
  std::cout<<"zt = zt3"<<std::endl;
  zt = zt3;
  std::cout<<"std::move(zt2)"<<std::endl;
  SearchTree<int> zt4 = std::move(zt2);
}

void test_lzw_char()
{
  auto pred_left = [](std::string s){ return (s.compare("0")==0);};
  LZWTree<std::string> bt("/",pred_left);
  bt <<"0"<<"1"<<"1"<<"1"<<"1"<<"0"<<"0"<<"1"<<"0"<<"0"<<"1"<<"0"<<"0"<<"1"<<"0"<<"0"<<"0"<<"1"<<"1"<<"1";
  std::cout << "LZW STR INORDER" << std::endl;
  bt.foreach_inorder(printer<std::string>);
  print_depth_data(compute_depth_data(collect_depths<std::string>(bt)));
  std::cout << "LZW STR POSTORDER" << std::endl;
  bt.foreach_postorder(printer<std::string>);
  std::cout << "LZW STR PREORDER" << std::endl;
  bt.foreach_preorder(printer<std::string>);
}

void test_lzw_str()
{
  auto pred_left = [](char c){ return (c=='0');};
  LZWTree<char> bt('/',pred_left);
  bt <<'0'<<'1'<<'1'<<'1'<<'1'<<'0'<<'0'<<'1'<<'0'<<'0'<<'1'<<'0'<<'0'<<'1'<<'0'<<'0'<<'0'<<'1'<<'1'<<'1';
  std::cout << "LZW CHAR INORDER" << std::endl;
  bt.foreach_inorder(printer<char>);
  print_depth_data(compute_depth_data(collect_depths<char>(bt)));
  std::cout << "LZW CHAR POSTORDER" << std::endl;
  bt.foreach_postorder(printer<char>);
  std::cout << "LZW CHAR PREORDER" << std::endl;
  bt.foreach_preorder(printer<char>);
}

void test_lzw_cp_mv()
{
  auto pred_left = [](char c){ return (c=='0');};
  LZWTree<char> zt('/',pred_left);
  zt <<'0'<<'0'<<'0'<<'0'<<'0';
  std::cout << "zt print"<<std::endl;
  zt.foreach_inorder(printer<char>);
  LZWTree<char> zt2(zt);
  std::cout << "zt2 print"<<std::endl;
  zt2.foreach_inorder(printer<char>);
  LZWTree<char> zt3('/',pred_left);
  zt3 <<'1'<<'1'<<'1'<<'1'<<'1';
  std::cout << "zt3 print"<<std::endl;
  zt3.foreach_inorder(printer<char>);
  std::cout<<"zt = zt3"<<std::endl;
  zt = zt3;
  std::cout << "zt print"<<std::endl;
  zt.foreach_inorder(printer<char>);
  std::cout<<"std::move(zt2)"<<std::endl;
  LZWTree<char> zt4 = std::move(zt2);
  std::cout << "zt4 print"<<std::endl;
  zt4.foreach_inorder(printer<char>);
}