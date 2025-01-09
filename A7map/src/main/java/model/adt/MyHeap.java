package model.adt;

import exceptions.ExpressionException;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap{
    private MyIDictionary<Integer, IValue> map;
    private Integer freeLocation;

    public MyHeap(){
        map = new MyDictionary<>();
        freeLocation = 1;
    }

    @Override
    public Integer allocate(IValue name){
        map.insert(freeLocation++, name);
        return freeLocation - 1;
    }

    @Override
    public IValue getAddress(Integer addr) throws ExpressionException {
        return map.get(addr);
    }

    @Override
    public boolean contains(Integer address) {
        return map.contains(address);
    }

    @Override
    public Integer getFreeLocation(){return freeLocation;}

    @Override
    public IValue get(Integer address) throws ExpressionException {
        return map.get(address);
    }

    @Override
    public void set(Integer addr, IValue value){
        map.insert(addr, value);
    }

    @Override
    public Map<Integer, IValue> getContent() {
        Map<Integer, IValue> heap = new HashMap<>();
        map.getKeys().forEach(key -> {
            try {
                heap.put(key, map.get(key));
            } catch (ExpressionException e) {
                e.printStackTrace();
            }
        });
        return heap;
    }

    @Override
    public void setContent(Map<Integer, IValue> heap) {
        map = new MyDictionary<>();
        heap.forEach((k, v) -> map.insert(k, v));
    }

    @Override
    public MyIDictionary<Integer, IValue> getHeap(){
        return map;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Heap:\n");
        map.getKeys().forEach(key-> {
            try {
                builder.append(key.toString()).append("->").append(map.get(key).toString()).append("\n");
            } catch (ExpressionException e) {
                builder.append("");
            }
        });
        return builder.toString();
    }
}
