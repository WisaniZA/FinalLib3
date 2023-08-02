package com.lib.bean;

import java.util.Objects;

public class Category {

    private int cat_id;
    private String genre;
    private boolean isActive;

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Category() {
        this(0, "", true);
    }

    public Category(int cat_id, String genre) {
        this(cat_id, genre, true);
    }
    public Category(String genre) {
        this(0, genre, true);
    }

    public Category(int cat_id, String genre, boolean isActive) {
        this.cat_id = cat_id;
        this.genre = genre;
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.cat_id;
        hash = 83 * hash + Objects.hashCode(this.genre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        if (this.cat_id != other.cat_id) {
            return false;
        }
        if (!Objects.equals(this.genre, other.genre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Category{" + "cat_id=" + cat_id + ", genre=" + genre + ", isActive=" + isActive + '}';
    }
    

}
