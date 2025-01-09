package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class HeapAllocationStatement implements IStatement{
    String var_name;
    IExpression expression;
    public HeapAllocationStatement(String var_name, IExpression expression) {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if(!state.getSymTable().contains(this.var_name)){
            throw new StatementException("Variable '" + this.var_name + "' does not exist");
        }
        if(!(state.getSymTable().get(this.var_name).getType() instanceof RefType)){
            throw new StatementException("Variable '" + this.var_name + "' is not a ref type");
        }
        IValue eval = this.expression.evaluate(state.getSymTable(), state.getHeap());
        if(!eval.getType().equals(((RefValue)state.getSymTable().get(this.var_name)).getLocationType())){
            throw new StatementException("Variable '" + this.var_name + "' is not the same type as the expression");
        }
        Integer addr = state.getHeap().allocate(state.getSymTable().get(this.var_name));
        state.getHeap().set(addr, eval);

        state.getSymTable().insert(this.var_name, new RefValue(addr, ((RefValue) state.getSymTable().get(this.var_name)).getLocationType()));

        return null;

    }

    public String toString(){ return "new(" + var_name + ", " + expression.toString() + ");\n"; }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocationStatement(this.var_name,this.expression);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType typevar = typeEnv.get(var_name);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new StatementException("NEW statement: right hand side and left hand side have different types ");
    }
}
