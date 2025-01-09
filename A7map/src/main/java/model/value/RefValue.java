package model.value;

import model.type.IType;
import model.type.RefType;

public class RefValue implements IValue{
    int address;
    IType locationType;

    public RefValue(int addr, IType type) {this.address = addr; this.locationType = type;}

    public int getAddr() {return address;}

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    public IType getLocationType() {return this.locationType;}

    @Override
    public boolean equals(IValue value) {
        return value.getType().equals(new RefType(locationType)) && ((RefValue) value).address == address;
    }

    public String toString(){
        return "(" + address + "," + locationType + ")";
    }
}
