package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.IType;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;

public class ArithmeticExpression implements IExpression{
    private IExpression left;
    private IExpression right;
    private ArithmeticOperation operation;

    public ArithmeticExpression(IExpression left, ArithmeticOperation operation, IExpression right)
    {
        this.left = left;
        this.operation = operation;
        this.right = right;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ExpressionException {
        IValue leftvalue = this.left.evaluate(symTbl, heap);
        IValue rightvalue = this.right.evaluate(symTbl, heap);

        if(!leftvalue.getType().equals(new IntType())){
            throw new ExpressionException("Invalid left expression! \n");
        }
        if(!rightvalue.getType().equals(new IntType()))
        {
            throw new ExpressionException("Invalid right expression\n");
        }

        int intleftvalue = ((IntValue) leftvalue).getValue();
        int intrightvalue = ((IntValue) rightvalue).getValue();

        switch(operation){
            case PLUS -> {
                return new IntValue(intleftvalue + intrightvalue);
            }
            case MINUS -> {
                return new IntValue(intleftvalue - intrightvalue);
            }
            case MULTIPLY -> {
                return new IntValue(intleftvalue * intrightvalue);
            }
            case DIVIDE -> {
                if (intrightvalue == 0){
                    throw new ExpressionException("Division by zero\n");
                }
                return new IntValue(intleftvalue / intrightvalue);
            }
            default -> {
                throw new ExpressionException("Unknown operation type\n");
            }
        }
    }

    public String enumToString(){
        switch(operation){

            case PLUS -> {
                return "+";
            }
            case MINUS -> {
                return "-";
            }
            case MULTIPLY -> {
                return "*";
            }
            case DIVIDE -> {
                return "/";
            }
            default -> {
                return "";
            }
        }
    }

    public String toString(){
        return left.toString() + " " + enumToString() + " " + right.toString();
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticExpression(left.deepCopy(), operation, right.deepCopy());
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

}