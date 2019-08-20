package com.iChair.mesas;

public class Silla {
    private String Estado;
    private Long Mesa;

    public Silla(){
    }

    public Silla(String estado, Long mesa) {
        this.Estado = estado;
        this.Mesa = mesa;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        this.Estado = estado;
    }


    public Long getMesa() {
        return Mesa;
    }

    public void setMesa(Long mesa) {
        this.Mesa = mesa;
    }

    @Override
    public String toString() {
        return "Silla{" +
                "Estado='" + Estado + '\'' +
                ", Mesa='" + Mesa + '\'' +
                '}';
    }
}
