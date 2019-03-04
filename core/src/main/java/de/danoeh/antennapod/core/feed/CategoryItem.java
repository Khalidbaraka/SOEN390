package de.danoeh.antennapod.core.feed;

public class CategoryItem {
    private String Name;
    private int Image ;

    public CategoryItem() {
    }

    public CategoryItem(String name, int image) {
        Name = name;
        Image = image;
    }


    public String getName() {
        return Name;
    }

    public int getImage() {
        return Image;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setImage(int image) {
        Image = image;
    }
}

