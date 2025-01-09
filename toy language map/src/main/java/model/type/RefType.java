package model.type;

import model.value.IValue;
import model.value.RefValue;

public class RefType implements IType{

    private IType inner;
    public RefType(IType inner) {this.inner = inner;}
    public IType getInner() {return inner;}

    @Override
    public boolean equals(IType type) {
        return type instanceof RefType && inner.equals(((RefType)type).getInner());
    }

    public String toString() {return "Ref(" + inner.toString() + ")";}

    @Override
    public IValue getDefaultValue() {
        return new RefValue(0,inner);
    }
}
