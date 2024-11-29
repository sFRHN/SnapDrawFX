package org.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class LineModel {

    private List<DLine> lines;
    private List<Subscriber> subscribers;

    public LineModel() {
        lines = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    public DLine addLine(double x1, double y1, double x2, double y2) {
        DLine line = new DLine(x1, y1, x2, y2);
        lines.add(line);
        notifySubscribers();
        return line;
    }

    public void deleteLine(DLine line) {
        lines.remove(line);
        notifySubscribers();
    }


    public void addSubscriber(Subscriber sub) {
        subscribers.add(sub);
    }

    private void notifySubscribers() {
        subscribers.forEach(Subscriber::modelUpdated);
    }

}
