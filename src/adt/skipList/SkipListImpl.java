  package adt.skipList;
   
   public class SkipListImpl<T> implements SkipList<T> {
   
   	protected SkipListNode<T> root;
   	protected SkipListNode<T> NIL;
   
   	protected int maxHeight;
   
  	protected double PROBABILITY = 0.5;
  
  	private int size;
  
  	public SkipListImpl(int maxHeight) {
  		this.size = 0;
  		this.maxHeight = maxHeight;
  		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
  		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
  		connectRootToNil();
  	}
  
  	/**
  	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
  	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve conectar
  	 * todos os forward. Senao o ROOT eh inicializado com level=1 e o metodo deve
  	 * conectar apenas o forward[0].
  	 */
  	private void connectRootToNil() {
  		for (int i = 0; i < maxHeight; i++) {
  			root.forward[i] = NIL;
  		}
  	}
  
  	@Override
  	public void insert(int key, T newValue, int height) {
  		SkipListNode<T> searchedNode = this.search(key);
  		if (this.search(key) == null) {
  			if (height > this.maxHeight) {
  				throw new RuntimeException();
  			}
  			
  			SkipListNode<T> node = new SkipListNode<>(key, height, newValue);
  			this.insert(this.root, this.root.height() - 1, node);
  			this.size++;
  		} else {
  			searchedNode.value = newValue;
  		}
  	}
  
  	private void insert(SkipListNode<T> actual, int level, SkipListNode<T> node) {
  		while (level > 0 && node.key <= actual.forward[level].key) {
  			level--;
  		}
  		if (node.key > actual.forward[level].key) {
  			this.insert(actual.forward[level], level, node);
  		}
  
  		while (level < actual.height() && level < node.height()) {
  			if (actual.forward[level].key > node.key) {
  				node.forward[level] = actual.forward[level];
  				actual.forward[level] = node;
  			}
  
  			level++;
  		}
  	}
  
  	@Override
  	public void remove(int key) {
  		if (this.search(key) != null) {
  			this.remove(this.root, this.root.height() - 1, key);
  			this.size--;
  		}
  	}
  
  	private void remove(SkipListNode<T> actual, int level, int key) {
  		if (actual != NIL) {
  			while (level > 0 && key < actual.forward[level].key) {
  				level--;
  			}
  			if (key > actual.forward[level].key) {
  				this.remove(actual.forward[level], level, key);
  			} else if (key == actual.forward[level].key) {
  				while (level >= 0) {
  					while (key != actual.forward[level].key) {
  						actual = actual.forward[level];
  					}
  					actual.forward[level] = actual.forward[level].forward[level];
  					level--;
  				}
  			}
  		}
  	}
  
  	@Override
  	public int height() {
  		int height = 0;
  		if (this.size > 0) {
  			for (int i = root.height() - 1; i >= 0; i--) {
 				if (root.forward[i] != NIL) {
 					height = i + 1;
 					break;
 				}
 			}
 		}
 		return height;
 	}
 
 	@Override
 	public SkipListNode<T> search(int key) {
 		SkipListNode<T> result = null;
 		if (size > 0) {
 			result = this.search(this.root, this.root.height() - 1, key);
 		}
 		return result;
 	}
 
 	private SkipListNode<T> search(SkipListNode<T> actual, int level, int key) {
 		SkipListNode<T> result = null;
 		if (actual != NIL) {
 			while (level > 0 && key < actual.forward[level].key) {
 				level--;
 			}
 			if (key == actual.forward[level].key) {
 				result = actual.forward[level];
 			} else if (key > actual.forward[level].key) {
 				result = this.search(actual.forward[level], level, key);
 			}
 		}
 
 		return result;
 	}
 
 	@Override
 	public int size() {
 		return this.size;
 	}
 
 	@Override
 	public SkipListNode<T>[] toArray() {
 		int idx = 0;
 		SkipListNode<T>[] array = new SkipListNode[this.size + 2];
 		SkipListNode<T> actual = this.root;
 		while (actual != this.NIL) {
 			array[idx++] = actual;
 			actual = actual.forward[0];
 		}
 		array[idx] = NIL;
 
 		return array;
 	}
 
 }