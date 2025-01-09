package view;

import controller.Controller;
import model.adt.*;
import model.expressions.*;
import model.state.PrgState;
import model.statements.*;
import model.type.*;
import model.value.*;
import repository.MyIRepository;
import repository.Repository;
import view.Commands.ExitCommand;
import view.Commands.RunExample;
import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) {

        IStatement ex1 = new CompStatement(new VarDeclStatement("v",new IntType()),
                new CompStatement(new AssignStatement("v",new ValueExpression(new StringValue("a"))),
                        new PrintStatement(new VariableExpression("v")))
        );
        MyIDictionary<String, IType> typeEnv1 = new MyDictionary<>();
        try {
            ex1.typecheck(typeEnv1);  // Checks the types of the program
        } catch (Exception e) {
            System.out.println("Type error: " + e.getMessage());
        }
        PrgState prg1 = createPrgState(ex1);
        MyIRepository repo1 = new Repository("log1.txt");
        Controller ctr1 = new Controller(repo1);
        ctr1.addProgramState(prg1);

        IStatement ex2 = new CompStatement(
                new VarDeclStatement("a", new IntType()),
                new CompStatement(
                        new VarDeclStatement("b", new IntType()),
                        new CompStatement(
                                new AssignStatement("a", new ArithmeticExpression(
                                        new ValueExpression(new IntValue(2)),
                                        ArithmeticOperation.PLUS,
                                        new ArithmeticExpression(
                                                new ValueExpression(new IntValue(3)),
                                                ArithmeticOperation.MULTIPLY,
                                                new ValueExpression(new IntValue(5))
                                        )
                                )),
                                new CompStatement(
                                        new AssignStatement("b", new ArithmeticExpression(
                                                new VariableExpression("a"),
                                                ArithmeticOperation.PLUS,
                                                new ValueExpression(new IntValue(1))
                                        )),
                                        new PrintStatement(new VariableExpression("b"))
                                ))));
        MyIDictionary<String, IType> typeEnv2 = new MyDictionary<>();
        try {
            ex2.typecheck(typeEnv2);  // Checks the types of the program
        } catch (Exception e) {
            System.out.println("Type error: " + e.getMessage());
        }
        PrgState prg2 = createPrgState(ex2);
        MyIRepository repo2 = new Repository("log2.txt");
        Controller ctr2 = new Controller(repo2);
        ctr2.addProgramState(prg2);

        IStatement ex3 = new CompStatement(
                new VarDeclStatement("a", new BoolType()),
                new CompStatement(
                        new VarDeclStatement("v", new IntType()),
                        new CompStatement(
                                new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignStatement("v", new ValueExpression(new IntValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
        MyIDictionary<String, IType> typeEnv3 = new MyDictionary<>();
        try {
            ex3.typecheck(typeEnv3);  // Checks the types of the program
        } catch (Exception e) {
            System.out.println("Type error: " + e.getMessage());
        }
        PrgState prg3 = createPrgState(ex3);
        MyIRepository repo3 = new Repository("log3.txt");
        Controller ctr3 = new Controller(repo3);
        ctr3.addProgramState(prg3);

        /* String varf;
        varf="test.in";
        int varc;
        readFile(varf,varc);print(varc);
        readFile(varf,varc);print(varc)
        closeRFile(varf) */
        IStatement test1 = new CompStatement(
                new VarDeclStatement("varf", new StringType()),new CompStatement(
                new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))), new CompStatement(
                new OpenRFileStatement(new VariableExpression("varf")), new CompStatement(
                new VarDeclStatement("varc", new IntType()), new CompStatement(
                new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompStatement(
                new PrintStatement(new VariableExpression("varc")), new CompStatement(
                new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompStatement(
                new PrintStatement(new VariableExpression("varc")), new CloseRFileStatement(new VariableExpression("varf"))
        ))))))));
        MyIDictionary<String, IType> typeEnv4 = new MyDictionary<>();
        try {
            test1.typecheck(typeEnv4);  // Checks the types of the program
        } catch (Exception e) {
            System.out.println("Type error: " + e.getMessage());
        }
        PrgState prg4 = createPrgState(test1);
        MyIRepository repo4 = new Repository("log4.txt");
        Controller ctr4 = new Controller(repo4);
        ctr4.addProgramState(prg4);

        IStatement ex_with_while = new CompStatement(
                new VarDeclStatement("v", new IntType()), new CompStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))), new CompStatement(
                                new WhileStatement(
                                        new RelationalExpression(new VariableExpression("v"),
                                                                RelationalOperation.GREATER, new ValueExpression(new IntValue(0))),
                                        new AssignStatement("v", new ArithmeticExpression(
                                                new VariableExpression("v"), ArithmeticOperation.MINUS, new ValueExpression(new IntValue(1)))
                                        )
                                ), new PrintStatement(new VariableExpression("v"))
        )));
        MyIDictionary<String, IType> typeEnv5 = new MyDictionary<>();
        try {
            ex_with_while.typecheck(typeEnv5);  // Checks the types of the program
        } catch (Exception e) {
            System.out.println("Type error: " + e.getMessage());
        }
        PrgState prg5 = createPrgState(ex_with_while);
        MyIRepository repo5 = new Repository("log5.txt");
        Controller ctr5 = new Controller(repo5);
        ctr5.addProgramState(prg5);

        IStatement ex_heap_alloc = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())), new CompStatement(
                    new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))), new CompStatement(
                        new VarDeclStatement("a", new RefType(new RefType(new IntType()))), new CompStatement(
                                new HeapAllocationStatement("a", new VariableExpression("v")), new CompStatement(
                                        new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a"))
        )))));
        MyIDictionary<String, IType> typeEnv6 = new MyDictionary<>();
        try {
            ex_heap_alloc.typecheck(typeEnv6);  // Checks the types of the program
        } catch (Exception e) {
            System.out.println("Type error: " + e.getMessage());
        }
        PrgState prg6 = createPrgState(ex_heap_alloc);
        MyIRepository repo6 = new Repository("log6.txt");
        Controller ctr6 = new Controller(repo6);
        ctr6.addProgramState(prg6);

        IStatement ex_heap_reading = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())), new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))), new CompStatement(
                                new VarDeclStatement("a", new RefType(new RefType(new IntType()))), new CompStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")), new CompStatement(
                                                new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                                                    new PrintStatement(new ArithmeticExpression(
                                                                            new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))),
                                                                            ArithmeticOperation.PLUS,
                                                                            new ValueExpression(new IntValue(5))))
        )))));
        MyIDictionary<String, IType> typeEnv7 = new MyDictionary<>();
        try {
            ex_heap_reading.typecheck(typeEnv7);  // Checks the types of the program
        } catch (Exception e) {
            System.out.println("Type error: " + e.getMessage());
        }
        PrgState prg7 = createPrgState(ex_heap_reading);
        MyIRepository repo7 = new Repository("log7.txt");
        Controller ctr7 = new Controller(repo7);
        ctr7.addProgramState(prg7);

        IStatement ex_heap_writing = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())), new CompStatement(
                new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))), new CompStatement(
                        new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))), new CompStatement(
                                new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                                new PrintStatement(new ArithmeticExpression(
                                                                    new HeapReadingExpression(new VariableExpression("v")),
                                                                    ArithmeticOperation.PLUS,
                                                                    new ValueExpression(new IntValue(5))
                                                                        )
                                )
        ))));

        MyIDictionary<String, IType> typeEnv8 = new MyDictionary<>();
        try {
            ex_heap_writing.typecheck(typeEnv8);  // Checks the types of the program
        } catch (Exception e) {
            System.out.println("Type error: " + e.getMessage());
        }
        PrgState prg8 = createPrgState(ex_heap_writing);
        MyIRepository repo8 = new Repository("log8.txt");
        Controller ctr8 = new Controller(repo8);
        ctr8.addProgramState(prg8);

        IStatement ex_garbage_coll = new CompStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(
                                new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                new HeapAllocationStatement("v", new ValueExpression(new IntValue(40)))
                        )));

        MyIDictionary<String, IType> typeEnv9 = new MyDictionary<>();
        try {
            ex_garbage_coll.typecheck(typeEnv9);  // Checks the types of the program
        } catch (Exception e) {
            System.out.println("Type error: " + e.getMessage());
        }
        PrgState prg9 = createPrgState(ex_garbage_coll);
        MyIRepository repo9 = new Repository("log9.txt");
        Controller ctr9 = new Controller(repo9);
        ctr9.addProgramState(prg9);

        IStatement ex_fork = new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(
                new VarDeclStatement("a", new RefType(new IntType())), new CompStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(10))), new CompStatement(
                                new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))), new CompStatement(
                                        new ForkStatement(new CompStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))), new CompStatement(
                                                        new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))
                                                )))), new CompStatement(
                                                        new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))
        ))))));

        MyIDictionary<String, IType> typeEnv10 = new MyDictionary<>();
        try {
            ex_fork.typecheck(typeEnv10);  // Checks the types of the program
        } catch (Exception e) {
            System.out.println("Type error: " + e.getMessage());
        }
        PrgState prg10 = createPrgState(ex_fork);
        MyIRepository repo10 = new Repository("log10.txt");
        Controller ctr10 = new Controller(repo10);
        ctr10.addProgramState(prg10);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", test1.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex_with_while.toString(), ctr5));
        menu.addCommand(new RunExample("6", ex_heap_alloc.toString(), ctr6));
        menu.addCommand(new RunExample("7", ex_heap_reading.toString(), ctr7));
        menu.addCommand(new RunExample("8", ex_heap_writing.toString(), ctr8));
        menu.addCommand(new RunExample("9", ex_garbage_coll.toString(), ctr9));
        menu.addCommand(new RunExample("10", ex_fork.toString(), ctr10));
        menu.show();
    }
    private static PrgState createPrgState(IStatement statement) {
        MyIStack<IStatement> execStack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<String> outputList = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeap heap = new MyHeap();
        return new PrgState(statement, execStack, symTable, outputList, fileTable, heap);
    }
}
