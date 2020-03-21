#include <iostream>
#include <algorithm>

// #define LOG_NODE_LIFECYCLE

#define LOG_TREE_LIFECYCLE

template<typename ValueType>
class BinTree
{
protected:
  class Node
  {
  public:
    Node(ValueType value) : value(value), left(nullptr), right(nullptr), count(0)
     { 
        #ifdef LOG_NODE_LIFECYCLE
          std::cout<<"node ctor"<< static_cast<void*>(this) << std::endl;
        #endif
      }
  private:
    // TODO impl rule of five
    Node(const Node&);
    Node& operator=(const Node&);
    Node(const Node&&);
    Node& operator=(const Node&&);
  public:
    ValueType get_value(){ return value;}
    Node* left_child(){ return left;}
    Node* right_child(){ return right;}
    void left_child(Node* node){ this->left=node;}
    void right_child(Node* node){ this->right=node;}
    int get_count(){ return count;};
    void set_count(int c){ count=c; };
    void incr_count(){ this->count++;}
  private:
    ValueType value;
    Node* left;
    Node* right;
    int count;
  }; /* class Node */
public:
  BinTree(Node* root=nullptr, Node* treep=nullptr) : root(root), treep(treep), depth(0)
  {
    #ifdef LOG_TREE_LIFECYCLE
      std::cout<<"btree ctor "<< static_cast<void*>(this) << std::endl;
    #endif
  }
  
  ~BinTree()
  {
    #ifdef LOG_TREE_LIFECYCLE
      std::cout<<"btree dtor "<< static_cast<void*>(this) << std::endl;
    #endif
    deltree(root);
  }
  
  BinTree(const BinTree& old)
  {
    #ifdef LOG_TREE_LIFECYCLE
      std::cout<<"btree copy ctor "<< static_cast<void*>(this) << std::endl;
    #endif
    root = deep_copy(old.root,old.treep);
  }

  BinTree& operator=(const BinTree& old) 
  {
    #ifdef LOG_TREE_LIFECYCLE
      std::cout<<"btree copy assign "<< static_cast<void*>(this) << std::endl;
    #endif
    BinTree tmp(old);
    std::swap(*this,tmp);
    return *this;
  }
  
  BinTree(BinTree&& old)
  {
    #ifdef LOG_TREE_LIFECYCLE
      std::cout<<"btree move ctor "<< static_cast<void*>(this) << std::endl;
    #endif
    root = nullptr;
    treep = nullptr;
    *this=std::move(old);
  }
  
  BinTree& operator=(BinTree&& old)
  {
    #ifdef LOG_TREE_LIFECYCLE
      std::cout<<"btree move assign "<< static_cast<void*>(this) << std::endl;
    #endif
    std::swap(old.root,root);
    std::swap(old.treep,treep);
    return *this;
  }


public:
  BinTree& operator<<(ValueType);
  void print(){ print(root, std::cout);}
  void print(Node* node, std::ostream& os);
  void deltree(Node* node);
  
private:
  Node* deep_copy(Node* old_n, Node* old_treep)
  {
    Node* newn = nullptr; 
    if(old_n){
      newn = new Node(old_n->get_value());
      newn->set_count(old_n->get_count());
      newn->left_child(deep_copy(old_n->left_child(),old_treep));
      newn->right_child( deep_copy(old_n->right_child(),old_treep));
      if(old_n==old_treep){this->treep=newn;}
    }
    return newn;
  }
  
protected:

  Node* root;
  Node* treep;
  int depth;
}; /* class Tree */

template<typename ValueType>
BinTree<ValueType>& BinTree<ValueType>::operator<<(ValueType value)
{
  if(!treep){
    root = treep = new Node(value);
  }else if(treep->get_value() == value){
    treep->incr_count();
  }else if(treep->get_value() > value){
    if(!treep->left_child()){
      treep->left_child(new Node(value));
    }else{
      treep = treep->left_child();
      *this<<value;
    }
  }else{
    if(!treep->right_child()){
      treep->right_child(new Node(value));
    }else{
      treep = treep->right_child();
      *this<<value;
    }
  }
  treep = root;
  return *this;
}

template<typename ValueType>
void BinTree<ValueType>::print(Node* node, std::ostream& os)
{
  if(node){
    ++depth;
    print(node->right_child(),os );
    for(int i = 1; i < depth; ++i){os << "---";}
    os << node->get_value() << " " << depth << " " << node->get_count() << std::endl;
    print(node->left_child(),os );
    --depth;
  }
}


template<typename ValueType>
void BinTree<ValueType>::deltree(typename BinTree<ValueType>::Node* node)
{
  if(node){
    deltree(node->left_child() );
    deltree(node->right_child() );
    #ifdef LOG_NODE_LIFECYCLE
      std::cout<<"node delete "<< static_cast<void*>(node) << std::endl;
    #endif
    delete node;
  }
}
template<typename ValueType, ValueType vr, ValueType v0>
class ZLWTree : public BinTree<char>
{
public:
  ZLWTree() : BinTree<char>(new typename BinTree<char>::Node(vr))
  {
    this->treep = this->root;
  }
private:
public:
  ZLWTree& operator<<(char);
private:
  
};
template<typename ValueType, ValueType vr, ValueType v0>
ZLWTree<ValueType, vr, v0>& ZLWTree<ValueType, vr, v0>::operator<<(char value)
{
  if(value==v0){
    if(!this->treep->left_child()){
      typename BinTree<char>::Node* node = new typename BinTree<char>::Node(value);
      this->treep->left_child(node);
      this->treep = this->root;
    }else{
      this->treep = this->treep->left_child();
    }
  }else{
    if(!this->treep->right_child()){
      typename BinTree<char>::Node* node = new typename BinTree<char>::Node(value);
      this->treep->right_child(node);
      this->treep = this->root;
    }else{
      this->treep = this->treep->right_child();
    }
  }
  return *this;
}

void test_cp_mv();

void test_bintree();

void test_zlw();

int main(int argc, char** argv, char** env)
{
  test_cp_mv();
 
}

void test_cp_mv()
{
  BinTree<int> bt;
  bt <<8<<9<<5<<2<<7;
  bt.print();
  std::cout << std::endl; 
  ZLWTree<char,'/','0'> zt;
  zt <<'0'<<'0'<<'0'<<'0'<<'0';
  zt.print();
  ZLWTree<char,'/','0'> zt2(zt);
  ZLWTree<char,'/','0'> zt3;
  zt3 <<'1'<<'1'<<'1'<<'1'<<'1';
  std::cout<<"***"<<std::endl;
  zt = zt3;
  std::cout<<"***"<<std::endl;
  ZLWTree<char,'/','0'> zt4 = std::move(zt2);
}

void test_bintree()
{
  BinTree<int> bt;
  bt <<8<<9<<5<<2<<7;
  bt.print();
  std::cout << std::endl;  
}

void test_zlw()
{
  ZLWTree<char,'/','0'> zt;
  zt<<'0'<<'1'<<'1'<<'1'<<'1'<<'0'<<'0'<<'1'<<'0'<<'0'<<'1'<<'0'<<'0'<<'1'<<'0'<<'0'<<'0'<<'1'<<'1'<<'1';
  zt.print();
  std::cout << std::endl;
}