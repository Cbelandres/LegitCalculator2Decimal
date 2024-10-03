package com.example.listanimals;

public class Animal {
    private String name;
    private int imageResId;
    private boolean checked;

    public Animal(String name, int imageResId, boolean checked) {
        this.name = name;
        this.imageResId = imageResId;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name; // Fixed: Assign name instead of checked
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
