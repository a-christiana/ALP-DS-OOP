package model;

public enum TriageLevel {
    RED(1),
    YELLOW(2),
    GREEN(3);

    private int priority;
    
    TriageLevel(int priority){
        this.priority = priority;
    }
    
    public int getPriority() {
        return priority;
    }
}
