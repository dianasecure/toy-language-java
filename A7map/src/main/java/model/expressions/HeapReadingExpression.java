package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class HeapReadingExpression implements IExpression{

    IExpression expression;
    public HeapReadingExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ExpressionException {
        IValue ref_value = expression.evaluate(symTbl, heap);

        if(!(ref_value.getType() instanceof RefType)){
            throw new ExpressionException("HeapReadingExpression expects a ref type");
        }
        Integer addr = ((RefValue) ref_value).getAddr();
        if(!heap.contains(addr)){
            throw new ExpressionException("Address not in the heap!");
        }
        return heap.get(addr);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException{
        IType typ = expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new ExpressionException("the rH argument is not a Ref Type");
    }


    public String toString(){
        return "rH(" + expression.toString() + ")";
    }

    @Override
    public IExpression deepCopy() {
        return null;
    }
}
