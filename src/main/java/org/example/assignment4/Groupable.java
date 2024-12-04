package org.example.assignment4;

import java.util.List;

public interface Groupable {
    boolean isGroup();
    List<Groupable> getChildren();
}
