package model.statements;

import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.type.IType;

public class VarDeclStatement implements IStatement{
    private String varName;
    IType type;

    public VarDeclStatement(String varName, IType type){
        this.varName = varName;
        this.type = type;
    }

    public PrgState execute(PrgState state) throws StatementException {
        if ( state.getSymTable().contains(this.varName) ){
            throw new StatementException("Variable with this name already exists!\n");
        }
        state.getSymTable().insert(this.varName, this.type.getDefaultValue());
        return null;
    }

    public IStatement deepCopy(){
        return new VarDeclStatement(varName, type);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        typeEnv.insert(varName, type);
        return typeEnv;
    }

    public String toString(){
        return type.toString() + " " + varName + ";\n";
    }

}