#include <iostream>
#include <string>
template<typename ValueType>
class BinTree
{
protected:
  class Node
  {
  public:
    Node(ValueType value) : value(value), left(nullptr), right(nullptr), count(0){}
  private:
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
    void incr_count(){ this->count++;}
  private:
    ValueType value;
    Node* left;
    Node* right;
    int count;
  }; /* class Node */
public:
  // CHANGE 1
  BinTree(ValueType rootval) : root(rootval), treep(&root), depth(0)
  {
    
  }
  ~BinTree()
  {
    // CHANGE 2
    deltree(root.left_child() );
    deltree(root.right_child() );
  }
private:
  BinTree(const BinTree&);
  BinTree& operator=(const BinTree&);
  BinTree(const BinTree&&);
  BinTree& operator=(const BinTree&&);


public:
  BinTree& operator<<(ValueType);
  void print(){ print(&root, std::cout);}
  void print(Node* node, std::ostream& os);
  void deltree(Node* node);
protected:
  Node root;
  Node* treep;
  int depth;
}; /* class Tree */

template<typename ValueType>
BinTree<ValueType>& BinTree<ValueType>::operator<<(ValueType value)
{
  // CHANGE 3
  if(treep->get_value() == value){
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
  // CHANGE 4
  treep = &root;
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
    delete node;
  }
}
// CHANGE 5
template<typename ValueType>
class ZLWTree : public BinTree<ValueType>
{
public:
  // CHANGE 6
  ZLWTree(const ValueType&& rootval, const ValueType&& leftval) : BinTree<ValueType>(std::move(rootval)), leftval(std::move(leftval))
  {
    // CHANGE 7 
    this->treep = &(this->root);
  }
private:
public:
  ZLWTree& operator<<(ValueType);
private:
  ValueType leftval;
};

template<typename ValueType>
ZLWTree<ValueType>& ZLWTree<ValueType>::operator<<(ValueType value)
{
  if(value==this->leftval){
    if(!this->treep->left_child()){
      typename BinTree<ValueType>::Node* node = new typename BinTree<ValueType>::Node(value);
      this->treep->left_child(node);
      // CHANGE 8
      this->treep = &(this->root);
    }else{
      this->treep = this->treep->left_child();
    }
  }else{
    if(!this->treep->right_child()){
      typename BinTree<ValueType>::Node* node = new typename BinTree<ValueType>::Node(value);
      this->treep->right_child(node);
      // CHANGE 9
      this->treep = &(this->root);
    }else{
      this->treep = this->treep->right_child();
    }
  }
  return *this;
}


int main(int argc, char** argv, char** env)
{
  BinTree<int> bt(8);
  bt <<9<<5<<2<<7;
  std::cout << "BinTree" << std::endl;
  bt.print();
  ZLWTree<char> zt('/','0');
  zt<<'0'<<'1'<<'1'<<'1'<<'1'<<'0'<<'0'<<'1'<<'0'<<'0'<<'1'<<'0'<<'0'<<'1'<<'0'<<'0'<<'0'<<'1'<<'1'<<'1';
  std::cout << "ZLW char" << std::endl;
  zt.print();
  ZLWTree<std::string> zst("/","0");
  zst<<"0"<<"1"<<"1"<<"1"<<"1"<<"0"<<"0"<<"1"<<"0"<<"0"<<"1"<<"0"<<"0"<<"1"<<"0"<<"0"<<"0"<<"1"<<"1"<<"1";
  std::cout << "ZLW string" << std::endl;
  zst.print();
  return 0;
}