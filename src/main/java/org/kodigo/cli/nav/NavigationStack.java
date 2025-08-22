package org.kodigo.cli.nav;

import java.util.ArrayDeque;
import java.util.Deque;

public class NavigationStack {
    private final Deque<Screen> stack = new ArrayDeque<>();

    public void push(Screen s){ stack.push(s); }

    public Screen pop(){ return stack.size() > 1 ? stack.pop() : stack.peek(); }

    public Screen current(){ return stack.peek(); }
}
