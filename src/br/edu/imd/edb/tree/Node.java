package br.edu.imd.edb.tree;

import br.edu.imd.edb.entities.Char;

public class Node {


    private /*@ spec_public nullable @*/ Char value;
    private /*@ spec_public nullable @*/ Node left;
    private /*@ spec_public nullable @*/ Node right;
    private /*@ spec_public nullable @*/ Integer bit;
    private /*@ spec_public nullable @*/ String pathBit;

    public Integer getBit() {
        return bit;
    }

    public void setBit(Integer bit) {
        this.bit = bit;
    }

//construtores
    /*@
	@ assignable value, left, right;
	@*/
    public Node() {

        this.value = null;
        this.left = null;
        this.right = null;
    }
    /*@
      @ assignable value, left, right;
      @*/
    public Node(Integer c, Integer x) {
        this.value = new Char(c, x);
        this.left = null;
        this.right = null;
    }

    /*@
      @ assignable value, left, right, bit;
      @*/
    public Node(Integer x, Node left, Node right) {
        this.value = new Char(x);
        this.left = left;
        this.right = right;
        this.bit = null;
        this.left.setBit(0);
        this.right.setBit(1);
    }

    //Get && Set


    public Char getValue() {
        return value;
    }



    public void setValue(Char value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public String getPathBit() {
        return pathBit;
    }

    public void setPathBit(String pathBit) {
        this.pathBit = pathBit;
    }


    @Override
    public boolean equals(Object o) {
        if (this.value.getQuantitie() == ((Node) o).getValue().getQuantitie() && this.value.getCharacter() == ((Node) o).getValue().getCharacter()) {
            if (this.left != null && ((Node) o).left != null) {
                if (!this.left.equals(((Node) o).left)) {
                    return false;
                }

            } else if (this.right != null && ((Node) o).right != null) {
                if (!this.right.equals(((Node) o).right)) {
                    return false;
                }

            }
                return true;

        }

        return false;
    }


}