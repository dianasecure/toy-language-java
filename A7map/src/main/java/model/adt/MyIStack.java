package model.adt;

import exceptions.EmptyStackException;
import model.statements.IStatement;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws EmptyStackException;
    void push(T element);
    boolean isEmpty();
    int size();
    String toString();
    List getValues();
}