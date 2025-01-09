package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class HeapWritingStatement implements IStatement{
    private String var_name;
    private IExpression expression;
    public HeapWritingStatement(String var_name, IExpression expression) {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {

        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        MyIHeap heap = state.getHeap();

        if(!symTbl.contains(var_name)){
            throw new ExpressionException("Variable '" + var_name + "' not found in sym table");
        }

        IValue val = symTbl.get(var_name);

        if(!(val.getType() instanceof RefType)){
            throw new ExpressionException("Variable '" + var_name + "' is not a ref type");
        }

        if(!heap.contains(((RefValue)val).getAddr())){
            throw new ExpressionException("Variable '" + var_name + "' is not in the heap");
        }

        IValue ref_value = expression.evaluate(symTbl, heap);

        if(ref_value.equals(val)){
            throw new ExpressionException("Variable '" + var_name + "' is not of type '" + ref_value.getType() + "'");
        }

        heap.set(((RefValue) val).getAddr(), ref_value);
        return null;
    }

    public String toString(){
        return "wH(" + var_name + ", " + expression.toString() + ");\n";
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType typevar = typeEnv.get(var_name);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new StatementException("HW statement: right hand side and left hand side have different types ");
    }
}
