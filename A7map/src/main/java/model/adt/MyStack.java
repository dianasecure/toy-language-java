package model.adt;

import exceptions.EmptyStackException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    Stack<T> stack;
    
    public MyStack(){
        stack = new Stack<>();
    }

    @Override
    public List getValues() {
        return stack.subList(0,stack.size());
    }

    @Override
    public T pop() throws EmptyStackException {
        if (this.isEmpty()){
            throw new EmptyStackException("Stack is empty!");
        }
        return this.stack.pop();
    }

    @Override
    public void push(T element) {
        this.stack.push(element);
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for ( T el : this.stack ){
            str.append("---\n");
            str.append(el);
            str.append("\n");
        }

        return str.toString();
    }
}