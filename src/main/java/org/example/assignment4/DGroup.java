package org.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class DGroup implements Groupable {

    private List<Groupable> children;

    public DGroup(List<Groupable> items) {
        children = new ArrayList<Groupable>(items);
    }

    public boolean isGroup() {
        return true;
    }

    public List<Groupable> getChildren() {
        return children;
    }



}
