package model.adt;

import exceptions.ExpressionException;
import model.value.IValue;

import java.util.Map;

public interface MyIHeap {
    Integer allocate(IValue name);
    IValue getAddress(Integer addr) throws ExpressionException;

    boolean contains(Integer address);

    Integer getFreeLocation();

    IValue get(Integer address) throws ExpressionException;

    void set(Integer addr, IValue value);

    Map getContent();

    void setContent(Map<Integer, IValue> heap);

    MyIDictionary getHeap();
}
