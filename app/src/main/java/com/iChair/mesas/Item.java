package com.iChair.mesas;

import java.util.List;

class Item {
    private Long id_item;
    private int image_source;
    private List<Silla> silla;

    public Item() {

    }

    public Item(Long id_item, int image_source, List<Silla> silla) {
        this.id_item = id_item;
        this.image_source = image_source;
        this.silla = silla;
    }

    public Long getId_item() {
        return id_item;
    }

    public void setId_item(Long id_item) {
        this.id_item = id_item;
    }

    public int getImage_source() {
        return image_source;
    }

    public void setImage_source(int image_source) {
        this.image_source = image_source;
    }

    public List<Silla> getSilla() {
        return silla;
    }

    public void setSilla(List<Silla> silla) {
        this.silla = silla;
    }

}
