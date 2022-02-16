package br.edu.imd.edb.heap;


import br.edu.imd.edb.tree.Node;

import java.util.Arrays;

public class Heap {
    private /*@ spec_public @*/ Node[] node;
    private /*@ spec_public @*/ int size;
    private /*@ spec_public @*/ int capacity;

    /*@ public invariant size >=0;
      @ public invariant capacity >=0;
      @ public invariant node != null;
      @*/

    /*@
      @ assignable this.capacity, this.size, node;
      @ ensures this.capacity == 10;
      @ ensures this.size == 0;
      @ ensures node != null;
      @*/
    public Heap() {
        this.capacity = 10;
        this.size = 0;
        node = new Node[capacity];
    }

    /*@ requires no != null;
      @ assignable node[getSize()], size;
      @ ensures size == \old(size) + 1;
      @*/
    public void insert(Node no) {
        ensureCapacity();
        node[getSize()] = no;
        heapifyUp(getSize());
        size++;
    }

    /*@ requires index >= 0; @*/
    private void heapifyUp(int index) {
        int parentIndex = getParentIndex(index);

        if (parentIndex < 0) {
            return;
        }

        Node raiz = node[parentIndex];
        Node no = node[index];

        if (no.getValue().getQuantitie() < raiz.getValue().getQuantitie()) {
            node[index] = raiz;
            node[parentIndex] = no;
            heapifyUp(parentIndex);
        }
    }

    /*@
      @ pure;
      @ ensures \result == (int) Math.floor((index - 1) / 2);
      @*/
    public int getParentIndex(int index) {

        return (int) Math.floor((index - 1) / 2);
    }

    /*@
      @ assignable node, capacity;
      @ ensures size != capacity;
      @*/
    private void ensureCapacity() {
        if (size == capacity) {
            node = Arrays.copyOf(node, capacity * 2);
            capacity = capacity * 2;
        }
    }

    /*@
      @ pure;
      @ ensures \result == size;
      @*/
    public int getSize() {

        return size;
    }

    /*@
      @ ensures \result == node[0] || \result == null;
      @*/
    public Node peek() {
        if (getSize() == 0) {
            return null;
        }
        return node[0];
    }

    /*@
      @ requires size > 0;
      @ assignable size;
      @*/
    public void remove() {
        node[0] = node[getSize() - 1];
        node[getSize() - 1] = null;
        size--;
        heapifyDown(0);
    }

    /*@
      @ requires index >= 0;
      @ assignable node[index];
      @*/
    private void heapifyDown(int index) {
        int leftChild = index * 2 + 1;
        int rightChild = index * 2 + 2;

        int childIndex = -1;
        if (leftChild < getSize()) {
            childIndex = leftChild;
        }

        if (childIndex == -1) {
            return;
        }

        if (rightChild < getSize()) {
            if (node[rightChild].getValue().getQuantitie() < node[leftChild].getValue().getQuantitie()) {
                childIndex = rightChild;
            }
        }

        if (node[index].getValue().getQuantitie() > node[childIndex].getValue().getQuantitie()) {
            Node tmp = node[index];
            node[index] = node[childIndex];
            node[childIndex] = tmp;
            heapifyDown(childIndex);
        }
    }


}
