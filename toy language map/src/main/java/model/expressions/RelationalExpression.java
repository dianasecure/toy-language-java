package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.BoolType;
import model.type.IType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

public class RelationalExpression implements IExpression{
    private IExpression left;
    private IExpression right;
    private RelationalOperation operation;

    public RelationalExpression(IExpression left, RelationalOperation operation, IExpression right){
        this.left = left;
        this.operation = operation;
        this.right = right;
    }

    @Override
    public String toString(){
        return left.toString() + " " + operation.toString().toLowerCase() + " " + right.toString();
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ExpressionException {
        IValue left = this.left.evaluate(symTbl, heap);
        IValue right = this.right.evaluate(symTbl, heap);
        if(!(left.getType().equals(new IntType()) && right.getType().equals(new IntType()))){
            throw new ExpressionException("One of the values is not int!\n");
        }
        int leftValue = ((IntValue)left).getValue();
        int rightValue = ((IntValue)right).getValue();
        if (this.operation == RelationalOperation.LESS){
            return new BoolValue(leftValue < rightValue);
        }
        else if (this.operation == RelationalOperation.LESS_OR_EQUAL){
            return new BoolValue(leftValue <= rightValue);
        }
        else if (this.operation == RelationalOperation.EQUAL){
            return new BoolValue(leftValue == rightValue);
        }
        else if (this.operation == RelationalOperation.NOT_EQUAL){
            return new BoolValue(leftValue != rightValue);
        }
        else if (this.operation == RelationalOperation.GREATER){
            return new BoolValue(leftValue > rightValue);
        }
        else if (this.operation == RelationalOperation.GREATER_OR_EQUAL){
            return new BoolValue(leftValue >= rightValue);
        }
        else {
            throw new ExpressionException("Unknown operation type\n");
        }
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType typ1, typ2;
        typ1=left.typecheck(typeEnv);
        typ2=right.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else {
                throw new ExpressionException("second operand is not an integer! \n"); }
        } else {
            throw new ExpressionException("first operand is not an integer! \n"); }
    }

    @Override
    public IExpression deepCopy() {
        return null;
    }
}
