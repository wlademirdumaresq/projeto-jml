package br.edu.imd.edb.entities;

import br.edu.imd.edb.heap.Heap;

public class Char {
    private /*@ spec_public nullable @*/Integer character;
    private /*@ spec_public @*/Integer quantitie;


    /*@
      @ requires character != null && quantitie != null;
      @ assignable this.character, this.quantitie;
      @*/
    public Char(Integer character, Integer quantitie) {
        this.character = character;
        this.quantitie = quantitie;
    }

    /*@
      @ requires quantitie != null;
      @ assignable this.character, this.quantitie;
      @*/
    public Char(Integer quantitie) {
        this.character = null;
        this.quantitie = quantitie;
    }

    /*@
      @ pure;
      @ ensures \result == character;
      @*/
    public Integer getCharacter() {


        return character;
    }

    /*@
      @ requires character != null;
      @ assignable this.character;
      @ ensures this.character == character;
      @*/
    public void setCharacter(Integer character) {
        this.character = character;
    }


    /*@
      @ pure;
      @ ensures \result == quantitie;
      @*/
    public Integer getQuantitie() {
        return quantitie;
    }

    /*@
      @ requires quantitie != null;
      @ assignable this.quantitie;
      @ ensures this.quantitie == quantitie;
      @*/
    public void setQuantitie(Integer quantitie) {
        this.quantitie = quantitie;
    }


}