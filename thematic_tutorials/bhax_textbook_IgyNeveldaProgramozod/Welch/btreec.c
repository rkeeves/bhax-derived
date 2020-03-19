#include <stdio.h>  // print
#include <stdlib.h> // mem
#include <math.h>   // sqrt

#define CUSTOM_DEBUG_OUT

typedef struct node_t{
  struct node_t* left;
  struct node_t* right;
  char value;
} Node;


typedef struct bintree_t{
  Node* root;
  Node* cur;
  int leaf_count;
} BinTree;

typedef void* UserData;

typedef void (*traversal_fn)(int depth, Node* node, UserData*ud);

void foreach_preorder(int depth,Node* root, traversal_fn fptr, UserData* ud )
{
  if(root == NULL) return;
  fptr(depth,root,ud);
  foreach_preorder(depth+1,root->right, fptr, ud );
  foreach_preorder(depth+1,root->left, fptr, ud );
}

void foreach_inorder(int depth,Node* root, traversal_fn fptr, UserData* ud )
{
  if(root == NULL) return;
  foreach_inorder(depth+1,root->right, fptr, ud );
  fptr(depth,root,ud);
  foreach_inorder(depth+1,root->left, fptr, ud );
}

void foreach_postorder(int depth,Node* root, traversal_fn fptr, UserData* ud )
{
  if(root == NULL) return;
  foreach_postorder(depth+1,root->right, fptr, ud );
  foreach_postorder(depth+1,root->left, fptr, ud );
  fptr(depth,root,ud);
}

void foreach_leaf(int depth,Node* root, traversal_fn fptr, UserData* ud )
{
  if(root == NULL) return;
  foreach_leaf(depth+1,root->right, fptr, ud );
  if(root->right==NULL&&root->left==NULL) fptr(depth,root,ud);
  foreach_leaf(depth+1,root->left, fptr, ud );
}

Node* make_node(char value)
{
  Node* node;
  node = (Node*) malloc(sizeof(Node));
  if(node == NULL){return NULL;}
  node->value=value;
  node->left= NULL;
  node->right=NULL;
  #ifdef CUSTOM_DEBUG_OUT
    printf("Alloc node %p\n",node);
  #endif
  return node;
}

BinTree* make_bintree()
{
  BinTree* tree;
  Node* root;
  root = make_node('/');
  if(root == NULL){return NULL;}
  tree = (BinTree*) malloc(sizeof(BinTree));
  if(tree == NULL){free(root);return NULL;}
  tree->root = tree->cur = root;
  tree->leaf_count=1;
  #ifdef CUSTOM_DEBUG_OUT
      printf("Alloc tree %p\n",tree);
  #endif
  return tree;
}

void push_value(BinTree* tree, char value)
{
  if(tree==NULL)return;
  if(tree->root==NULL || tree->cur==NULL)return;
  if(value=='0'){
    if(tree->cur->left == NULL){
      if(tree->cur->right!=NULL) tree->leaf_count=tree->leaf_count+1;
      tree->cur->left = make_node(value);
      tree->cur = tree->root;
    }else{
      tree->cur = tree->cur->left;
    }
  }else{
    if(tree->cur->right == NULL){
      if(tree->cur->left!=NULL) tree->leaf_count=tree->leaf_count+1;
      tree->cur->right = make_node(value);
      tree->cur = tree->root;
    }else{
      tree->cur = tree->cur->right;
    }
  }
}

void print_node(int depth, Node* node, UserData*ud)
{
  int i;
  if(node == NULL) return;
  for(i=0;i<depth; ++i){printf("---"); }
  printf("%c (%d) %p\n",node->value, depth,node);
}

void free_node(int depth, Node* node, UserData*ud)
{
  if(node == NULL) return;
  #ifdef CUSTOM_DEBUG_OUT
    printf("Free node %p\n",node);
  #endif
  free(node);
}

void free_tree(BinTree* tree)
{
  if(tree==NULL) return;
  foreach_postorder(0,tree->root,free_node,NULL);
  #ifdef CUSTOM_DEBUG_OUT
    printf("Free tree %p\n",tree);
  #endif
  free(tree);
}

typedef struct leafcountdata_t{
  int cur;
  int* heights;
} LeafCountData;

void collect_leaf_heights(int depth, Node* node, UserData*ud)
{
  if(node == NULL) return;
  LeafCountData* lcd = (LeafCountData*)ud;
  lcd->heights[lcd->cur] = depth;
  lcd->cur=lcd->cur+1;
}

int main(int argc, char** argv)
{
  int i;
  int leaf_count;
  BinTree* bt;
  int *leaf_heights;
  int max_melyseg;
  double deviation, avg;
  LeafCountData lc; 
  char arr[20] = {'0','1','1','1','1','0','0','1','0','0','1','0','0','1','0','0','0','1','1','1'};
  bt = make_bintree();
  if(bt == NULL){ return 1;}
  for(i = 0; i < 20; ++i){push_value(bt,arr[i]);}
  printf("\nINORDER\n");
  foreach_inorder(0,bt->root,print_node,NULL);
  printf("\n");
  leaf_count = bt->leaf_count;
  leaf_heights = (int*) malloc(sizeof(int)*leaf_count);
  if(leaf_heights==NULL){free_tree(bt); return 1;}
  lc.cur=0;
  lc.heights=leaf_heights;
  foreach_leaf(0,bt->root,collect_leaf_heights,(UserData)&lc);
  printf("Leaf depths: ");
  max_melyseg = 0;
  deviation = 0.0;
  for(i = 0; i < leaf_count; ++i){
    if(max_melyseg<leaf_heights[i]){max_melyseg=leaf_heights[i];}
    avg = avg+leaf_heights[i];
    printf(" %d",leaf_heights[i]);
  }
  printf("\n");
  avg = avg / leaf_count;
  deviation = 0.0;
  for(i = 0; i < leaf_count; ++i){
   deviation = deviation + ((leaf_heights[i]-avg)*(leaf_heights[i]-avg));
  }
  if (leaf_count - 1 > 0)
    deviation = sqrt( deviation / (leaf_count - 1));
  else
    deviation = sqrt ( deviation );
  printf("melyseg=%d\n",max_melyseg);
  printf("atlag=%f\n",avg);
  printf("szoras=%f\n",deviation);
  printf("\nPREORDER\n");
  foreach_preorder(0,bt->root,print_node,NULL);
  printf("\nPOSTORDER\n");
  foreach_postorder(0,bt->root,print_node,NULL);
  printf("\n");
  free_tree(bt);
  free(leaf_heights);
  return 0;
}
