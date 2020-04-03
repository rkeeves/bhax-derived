// TODO Bátfai Norbert GNU GPL v3
#include <iostream>
template <typename ValueType>
class BinTree {
protected:
class Node {

private:
ValueType value;
Node *left;
Node *right;
int count{0};

// TODO rule of five
Node(const Node &);
Node & operator=(const Node &);
Node(Node &&);
Node & operator=(Node &&);

public:
Node(ValueType value): value(value), left(nullptr), right(nullptr) {}
ValueType getValue(){return value;}
Node * leftChild(){return left;}
Node * rightChild(){return right;}
void leftChild(Node * node){left = node;}
void rightChild(Node * node){right = node;}
int getCount(){return count;}
void incCount(){++count;}
};
Node *root;
Node *treep;
int depth{0};

public:
BinTree(Node *root = nullptr, Node *treep = nullptr): root(root), treep(treep)
{
  std::cout<<"BT ctor "<<std::endl;
}

BinTree(const BinTree &old)
{
  std::cout<<"BT copy ctor "<<std::endl;
  root = cp(old.root, old.treep);
}

BinTree & operator=(const BinTree &old)
{
  std::cout<<"BT copy assignment "<<std::endl;
  BinTree tmp(old);
  std::swap(*this,tmp);
  return *this;
}

BinTree(BinTree &&old)
{
  std::cout<<"BT move ctor "<<std::endl;
  root = nullptr;
  treep = nullptr;
  *this=std::move(old);
}

BinTree & operator=(BinTree &&old)
{
  std::cout<<"BT move assignment"<<std::endl;
  std::swap(old.root,root);
  std::swap(old.treep,treep);
  return *this;
}

~BinTree()
{
  std::cout<<"BT dtor "<<std::endl;
  deltree(root);
}
private:

Node* cp(Node *node, Node *treep)
{
  Node* newNode = nullptr;
  if(node)
  {
    newNode = new Node(node->getValue());
    newNode->leftChild(cp(newNode->leftChild(),treep));
    newNode->rightChild(cp(newNode->rightChild(),treep));
    if(node == treep){
      this->treep=newNode;
    }
  }
  return newNode;
}
public:

BinTree & operator<<(ValueType value);
void print(){print(root, std::cout);}
void print(Node *node, std::ostream & os);
void deltree(Node *node);
};
template <typename ValueType>
class ZLWTree : public BinTree<ValueType> {
public:
ZLWTree(): BinTree<ValueType>(new typename BinTree<ValueType>::Node('/')) {
this->treep = this->root;
}
ZLWTree & operator<<(ValueType value);


};
template <typename ValueType>
BinTree<ValueType> & BinTree<ValueType>::operator<<(ValueType value)
{
if(!treep) {

root = treep = new Node(value);

} else if (treep->getValue() == value) {

treep->incCount();

} else if (treep->getValue() > value) {

if(!treep->leftChild()) {

treep->leftChild(new Node(value));

} else {

treep = treep->leftChild();
*this << value;
}

} else if (treep->getValue() < value) {

if(!treep->rightChild()) {

treep->rightChild(new Node(value));

} else {

treep = treep->rightChild();
*this << value;
}

}

treep = root;

return *this;
}
template <typename ValueType>
ZLWTree<ValueType> & ZLWTree<ValueType>::operator<<(ValueType value)
{

if(value == '0') {

if(!this->treep->leftChild()) {

typename BinTree<ValueType>::Node * node = new typename BinTree<ValueType>::Node(value);
this->treep->leftChild(node);
this->treep = this->root;

} else {

this->treep = this->treep->leftChild();
}

} else {
if(!this->treep->rightChild()) {

typename BinTree<ValueType>::Node * node = new typename BinTree<ValueType>::Node(value);
this->treep->rightChild(node);
this->treep = this->root;

} else {

this->treep = this->treep->rightChild();
}

}

return *this;
}
template <typename ValueType>
void BinTree<ValueType>::print(Node *node, std::ostream & os)
{
  if(node)
  {
    ++depth;
    print(node->leftChild(), os);

    for(int i{0}; i<depth; ++i)
    os << "---";
    os << node->getValue() << " " << depth << " " << node->getCount() << std::endl;

    print(node->rightChild(), os);
    --depth;
  }

}
template <typename ValueType>
void BinTree<ValueType>::deltree(Node *node)
{
if(node)
{
  deltree(node->leftChild());
  deltree(node->rightChild());

  delete node;
}

}
int main(int argc, char** argv, char ** env)
{
  BinTree<int> bt;

  bt << 8 << 9 << 5 << 2 << 7;

  bt.print();

  std::cout << std::endl;
  ZLWTree<char> zt;
  zt <<'0'<<'1'<<'1'<<'1'<<'1'<<'0'<<'0'<<'1'<<'0'<<'0'<<'1'<<'0'<<'0'<<'1'<<'0'<<'0'<<'0'<<'1'<<'1'<<'1';
  ZLWTree<char> zt2(zt);

  ZLWTree<char> zt3;
  zt3<<'1'<<'1'<<'1'<<'1'<<'1';
  std::cout<<"***"<<std::endl;
  zt2 = zt3;
  std::cout<<"***"<<std::endl;
  ZLWTree<char> zt4 = std::move(zt2);
  
  return 0;
  
}