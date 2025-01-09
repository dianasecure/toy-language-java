package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.state.PrgState;
import model.type.IType;
import model.value.IValue;

public interface IExpression {

    IValue evaluate(MyIDictionary<String, IValue> symTbl, MyIHeap heap) throws ExpressionException;

    IExpression deepCopy();

    IType typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException;

}