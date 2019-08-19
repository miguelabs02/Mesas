package com.iChair.mesas;

import java.util.List;

public class Mesa {
    public Long Mesa;
    public List<Silla> Silla;
    //public int Estado;

    public Mesa(Long mesa){
        Mesa = mesa;
    }
    public Mesa(Long mesa, List<com.iChair.mesas.Silla> silla) {
        Mesa = mesa;
        Silla = silla;
    }

    public Long getMesa() {
        return Mesa;
    }

    public void setMesa(Long mesa) {
        Mesa = mesa;
    }

    public List<Silla> getSilla() {
        return Silla;
    }

    public void setSilla(List<com.iChair.mesas.Silla> silla) {
        Silla = silla;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "Mesa=" + Mesa +
                ", Silla=" + Silla +
                '}';
    }
}
